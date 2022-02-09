package org.shoe


import org.gradle.api.Project
import org.gradle.api.provider.Property

import javax.naming.spi.ObjectFactory

class GitBuildVersioningPluginExtension {
    Property<String> message
    Property<String> greeter

    GitBuildVersioningPluginExtension(Project project) {
        message = project.objects.property(String)
        if (project.hasProperty('message')) {
            greeter = message.convention(project.provider({ project.greeter }))
        } else {
            message.convention('Hello')
        }

        greeter = project.objects.property(String)
        if (project.hasProperty('greeter')) {
            greeter = message.convention(project.provider({ project.greeter }))
        } else {
            message.convention('The Great Unknown')
        }
    }

}
