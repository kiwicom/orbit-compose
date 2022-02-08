rootProject.name = "OrbitCompose"

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
        dependencies {
            classpath("com.android.tools:r8:3.1.51")
        }
    }
}

include(":catalog")
include(":ui")
include(":icons")
include(":illustrations")
include(":generator")

enableFeaturePreview("VERSION_CATALOGS")
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
