plugins {
    id("java-library")
    kotlin("jvm")
    kotlin("plugin.serialization") version "1.4.10"
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

dependencies {
    implementation(Libs.kotlinPoet)
    implementation(Libs.androidSdkCommon)
    implementation(Libs.Kotlin.stdlib)
    implementation(Libs.KotlinX.Serialization.serialization)
}
