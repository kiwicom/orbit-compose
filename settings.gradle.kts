rootProject.name = "OrbitCompose"

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    resolutionStrategy {
        eachPlugin {
            when (requested.id.name) {
                "com.android.application" -> useModule("com.android.tools.build:gradle")
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
        maven { url = uri("https://kotlin.bintray.com/kotlinx/") }
    }
}

include(":catalog")
include(":ui")
include(":icons")
include(":illustrations")
include(":lint")
include(":generator")
