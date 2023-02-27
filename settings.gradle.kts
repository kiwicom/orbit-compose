@file:Suppress("UnstableApiUsage")

rootProject.name = "OrbitCompose"

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    resolutionStrategy {
        eachPlugin {
            when (requested.id.name) {
                "com.android.application" -> useModule("com.android.tools.build:gradle")
                "com.google.firebase.appdistribution" -> useModule("com.google.firebase:firebase-appdistribution-gradle")
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

include(":catalog")
include(":ui")
include(":icons")
include(":illustrations")
include(":lint")
include(":generator")
