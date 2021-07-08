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
}

include(":app")
include(":lib")
include(":icons")
include(":generator")

enableFeaturePreview("VERSION_CATALOGS")
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
