package org.shoe

import org.gradle.api.Project
import org.gradle.api.Plugin

class Gradle_plugin_1Plugin implements Plugin<Project> {
    void apply(Project project) {
        def extension = project.extensions.create('greeting', Gradle_plugin1PluginExtension)
        project.tasks.register("greeting") {
            doLast {
                println "${extension.message.get()} from ${extension.greeter.get()}"
            }
        }
    }
}
