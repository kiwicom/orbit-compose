@file:Suppress("DSL_SCOPE_VIOLATION")

plugins {
    id("java")
    kotlin("jvm")
    kotlin("plugin.serialization")
    alias(libs.plugins.kotlinter)
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

kotlinter {
    reporters = arrayOf("json")
    experimentalRules = true
    disabledRules = arrayOf("experimental:trailing-comma") // https://github.com/pinterest/ktlint/issues/1367 ?
}

dependencies {
    implementation(libs.android.sdkCommon)
    implementation(libs.kotlin.stdlib)
    implementation(libs.kotlin.reflect)
    implementation(libs.kotlin.serialization)
    implementation(libs.square.kotlinPoet)
}

tasks.register<JavaExec>("colors") {
    mainClass.set("kiwi.orbit.compose.generator.colors.RunnerKt")
    classpath = sourceSets.getByName("main").runtimeClasspath
}

tasks.register<JavaExec>("icons") {
    mainClass.set("kiwi.orbit.compose.generator.icons.RunnerKt")
    classpath = sourceSets.getByName("main").runtimeClasspath
}

tasks.register<JavaExec>("illustrations") {
    mainClass.set("kiwi.orbit.compose.generator.illustrations.RunnerKt")
    classpath = sourceSets.getByName("main").runtimeClasspath
}
