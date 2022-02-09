package org.shoe

import org.gradle.api.Project
import org.gradle.api.Plugin

class GitBuildVersioningPlugin implements Plugin<Project> {
    void apply(Project project) {
        def extension = project.extensions.create('git_build_versioning', GitBuildVersioningPluginExtension)
        project.tasks.register('greeting') {
            doLast {
                println "${extension.message.get()} from ${extension.greeter.get()}"
            }
        }
    }
}
