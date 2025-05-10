import java.time.Year
import org.jetbrains.dokka.base.DokkaBase
import org.jetbrains.dokka.base.DokkaBaseConfiguration

plugins {
    kotlin("jvm") version "1.9.23" apply false
    kotlin("android") version "1.9.23" apply false
    kotlin("multiplatform") version "1.9.23" apply false
    kotlin("plugin.serialization") version "1.9.23" apply false
    id("com.android.library") version "8.2.2" apply false
    id("com.android.test") version "8.2.2" apply false
    id("androidx.baselineprofile") version "1.2.4" apply false
    id("app.cash.paparazzi") version "1.3.2" apply false
    id("com.google.firebase.appdistribution") version "4.2.0" apply false
    id("com.vanniktech.maven.publish") version "0.28.0" apply false
    id("io.gitlab.arturbosch.detekt") version "1.23.8" apply false
    id("org.jmailen.kotlinter") version "4.2.0" apply false
    id("org.jetbrains.dokka") version "1.9.20"
}

buildscript {
    dependencies {
        classpath(libs.kotlin.dokka)
    }
}

tasks.dokkaHtmlMultiModule.configure {
    pluginConfiguration<DokkaBase, DokkaBaseConfiguration> {
        moduleName.set("Orbit Compose")
        customStyleSheets = listOf(file("dokka/orbit-style.css"))
        customAssets = listOf(file("dokka/logo-icon.svg"))
        footerMessage = "© ${Year.now().value} Kiwi.com"
    }
}
