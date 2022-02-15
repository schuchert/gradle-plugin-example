package com.bmx

import org.gradle.api.Project
import org.gradle.api.Plugin

class GitBuildVersioningPlugin implements Plugin<Project> {
    void apply(Project project) {
        def extension = project.extensions.create('git_build_versioning', GitBuildVersioningPluginExtension)
        project.tasks.register('prepareForReleaseBuild') {
            doLast {
                new com.bmx.GitBuildVersioning().prepareForReleaseBuild()
            }
        }
        project.tasks.register('pushReleaseBuild') {
            doLast {
                new com.bmx.GitBuildVersioning().pushReleaseBuild()
            }
        }
        project.tasks.register('rollbackReleaseBuild') {
            doLast {
                new com.bmx.GitBuildVersioning().rollbackReleaseBuild()
            }
        }
    }
}
