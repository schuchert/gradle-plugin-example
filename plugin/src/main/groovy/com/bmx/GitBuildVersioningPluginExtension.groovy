package com.bmx


import org.gradle.api.Project
import org.gradle.api.provider.Property

class GitBuildVersioningPluginExtension {
    Property<String> message
    Property<String> greeter

    GitBuildVersioningPluginExtension(Project project) {
        message = initProperty(project, 'message', { project.message }, 'Hello')
        greeter = initProperty(project, 'greeter', { project.greeter }, 'The Great Unknown')
    }

    private Property<String> initProperty(Project project, String propertyName, groovy.lang.Closure fromProject, String defaultValue) {
        def property = project.objects.property(String)

        if (project.hasProperty(propertyName)) {
            property.convention(project.provider(fromProject))
        } else {
            property.convention(defaultValue)
        }

        return property
    }

}
