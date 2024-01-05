@file:Suppress("UnstableApiUsage")

rootProject.name = "OrbitCompose"

includeBuild("./build-logic")

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    resolutionStrategy {
        eachPlugin {
            when (requested.id.name) {
                "com.android.library" -> useModule("com.android.tools.build:gradle")
            }
        }
    }
    repositories {
        gradlePluginPortal()
        mavenCentral()
        google()
    }
    buildscript {
        repositories {
            google()
        }
    }
}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
    }
}

include(":baselineprofile")
include(":catalog")
include(":catalog:semantics")
include(":generator")
include(":icons")
include(":illustrations")
include(":lint")
include(":ui")
