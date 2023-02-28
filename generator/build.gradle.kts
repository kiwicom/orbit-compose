@file:Suppress("DSL_SCOPE_VIOLATION")

import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("java")
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.kotlinter)
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

kotlinter {
    reporters = arrayOf("json")
    experimentalRules = true
}

dependencies {
    implementation(libs.android.sdkCommon)
    implementation(libs.kotlin.stdlib)
    implementation(libs.kotlin.reflect)
    implementation(libs.kotlin.serialization.json)
    implementation(libs.square.kotlinPoet)
    implementation(libs.javaJq)
}

tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = "11"
    }
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
