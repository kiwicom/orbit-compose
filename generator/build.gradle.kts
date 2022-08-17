@file:Suppress("DSL_SCOPE_VIOLATION")

plugins {
    id("java")
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.kotlinter)
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

kotlinter {
    reporters = arrayOf("json")
    experimentalRules = true
    disabledRules = arrayOf("trailing-comma") // kotlinter/ktlint does not respect editorconfig
}

dependencies {
    implementation(libs.android.sdkCommon)
    implementation(libs.kotlin.stdlib)
    implementation(libs.kotlin.reflect)
    implementation(libs.kotlin.serialization)
    implementation(libs.square.kotlinPoet)
}

tasks.register<JavaExec>("colors") {
    mainClass.set("kiwi.orbit.compose.generator.colors.MainKt")
    classpath = sourceSets.getByName("main").runtimeClasspath
}

tasks.register<JavaExec>("icons") {
    mainClass.set("kiwi.orbit.compose.generator.icons.MainKt")
    classpath = sourceSets.getByName("main").runtimeClasspath
}

tasks.register<JavaExec>("illustrations") {
    mainClass.set("kiwi.orbit.compose.generator.illustrations.MainKt")
    classpath = sourceSets.getByName("main").runtimeClasspath
}
