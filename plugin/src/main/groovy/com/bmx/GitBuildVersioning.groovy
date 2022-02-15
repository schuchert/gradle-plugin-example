package com.bmx

class GitBuildVersioning {
    String build_tag = 'local_build'
    String propertiesFile = 'gradle.properties'
    String versionProperty = 'version'
    String statusProperty = 'status'
    String statusValue = 'integration'
    boolean publishChanges = true

    PowershellExecutor executor
    Properties allProperties

    GitBuildVersioning(Map conf = [:]) {
        this.executor = new PowershellExecutor()
        build_tag = System.getenv()['BUILD_TAG'] ?: build_tag
        configure(conf)
        readProperties()
    }

    void configure(Map conf = [:]) {
        propertiesFile = conf.propertiesFile ?: propertiesFile
        versionProperty = conf.versionProperty ?: versionProperty
        statusProperty = conf.statusProperty ?: statusProperty
        statusValue = conf.statusValue ?: statusValue
        publishChanges = conf.publishChanges ?: publishChanges
    }

    void readProperties() {
        allProperties = new Properties()
        Reader reader = new File(propertiesFile).newReader()
        allProperties.load(reader)
        reader.close()
    }

    def writeProperties() {
        Writer writer = new File(propertiesFile).newWriter()
        allProperties.store(writer, null)
        writer.close()
    }

    def setVersion(String nextVersion) {
        allProperties.setProperty(versionProperty, nextVersion)
    }

    def getVersion() {
        return allProperties[versionProperty]
    }

    def toReleaseVersion() {
        def releaseVersion = getVersion().replaceAll('(?i)-SNAPSHOT$', '')
        setVersion(releaseVersion)
    }

    def updateStatus() {
        if (allProperties.containsKey(statusProperty))
            allProperties.setProperty(statusProperty, statusValue)
        else
            println("[WARNING] Could not find property named ${statusProperty} in ${propertiesFile} to update.")
    }

    String calculateNextVersion() {
        List version = getVersion().tokenize('.')
        def currentIncrement = version[-1].findAll(/\d+/)[0]
        def nextIncrement = currentIncrement.toInteger() + 1
        version[-1] = version[-1].replaceFirst(currentIncrement, nextIncrement.toString())
        def nextVersion = version.join('.')

        if (!(nextVersion =~ /(?i)-SNAPSHOT$/)) {
            nextVersion = nextVersion + '-SNAPSHOT'
        }

        return nextVersion
    }

    def commit(String message) {
        def commitMessage = "[Jenkins] ${message}. Build: ${build_tag}"

        executor.execute("git add '${propertiesFile}'")
        executor.execute("git commit -m '${commitMessage}'")
    }

    def push() {
        executor.execute("git push -u origin")
    }

    def tagWithVersion() {
        String tagName = "v" + getVersion()
        executor.execute("git tag -a ${tagName} -m 'release build ${tagName}'")
    }

    def prepareForReleaseBuild() {
        toReleaseVersion()
        updateStatus()
        writeProperties()
    }

    def pushReleaseBuild() {
        commit('Create release version')
        tagWithVersion()
        setVersion(calculateNextVersion())
        updateStatus()

        writeProperties()
        commit('Incrementing version number')
        push()
    }

    def rollbackReleaseBuild() {
        executor.execute("git checkout ${propertiesFile}")
    }

}
