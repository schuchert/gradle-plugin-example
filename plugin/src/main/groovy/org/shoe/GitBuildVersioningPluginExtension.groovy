package org.shoe

import org.gradle.api.provider.Property

abstract class GitBuildVersioningPluginExtension {
    abstract Property<String> getMessage()
    abstract Property<String> getGreeter()
}
