plugins {
    id("java-library")
    id("org.jetbrains.kotlin.jvm")
    id("com.android.lint")
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

tasks.named<Test>("test") {
    useJUnit()
}

dependencies {
    compileOnly(libs.kotlin.stdlib)
    compileOnly(libs.android.lint.api)
    testImplementation(libs.junit)
    testImplementation(libs.android.lint.impl)
    testImplementation(libs.android.lint.tests)
}
