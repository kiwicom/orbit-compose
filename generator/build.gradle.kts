plugins {
    id("java-library")
    kotlin("jvm")
    kotlin(Libs.KotlinX.Serialization.gradlePlugin) version Libs.KotlinX.Serialization.pluginVersion
    id(Libs.ktlintGradlePlugin) version Libs.ktlintVersion
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

kotlinter {
    reporters = arrayOf("json")
    experimentalRules = true
}

dependencies {
    implementation(Libs.kotlinPoet)
    implementation(Libs.androidSdkCommon)
    implementation(Libs.Kotlin.stdlib)
    implementation(Libs.Kotlin.reflect)
    implementation(Libs.KotlinX.Serialization.serialization)
}
