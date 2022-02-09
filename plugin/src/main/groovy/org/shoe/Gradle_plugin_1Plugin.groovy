package org.shoe

import org.gradle.api.Project
import org.gradle.api.Plugin

class Gradle_plugin_1Plugin implements Plugin<Project> {
    void apply(Project project) {
        // Register a task
        project.tasks.register("greeting") {
            doLast {
                println("Hello from plugin 'org.shoe.greeting'")
            }
        }
    }
}
