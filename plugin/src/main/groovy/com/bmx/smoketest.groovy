def config = [publish: true]

def foo = new GitBuildVersioning(config)
foo.prepareForReleaseBuild()
foo.pushReleaseBuild()
