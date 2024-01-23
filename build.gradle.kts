import java.time.Year
import org.jetbrains.dokka.base.DokkaBase
import org.jetbrains.dokka.base.DokkaBaseConfiguration

plugins {
    kotlin("jvm") version "1.9.22" apply false
    kotlin("android") version "1.9.22" apply false
    kotlin("multiplatform") version "1.9.22" apply false
    kotlin("plugin.serialization") version "1.9.22" apply false
    id("com.android.library") version "8.2.1" apply false
    id("com.android.test") version "8.2.1" apply false
    id("androidx.baselineprofile") version "1.2.2" apply false
    id("app.cash.paparazzi") version "1.3.1" apply false
    id("com.google.firebase.appdistribution") version "4.0.1" apply false
    id("com.vanniktech.maven.publish") version "0.27.0" apply false
    id("io.gitlab.arturbosch.detekt") version "1.23.4" apply false
    id("org.jmailen.kotlinter") version "4.1.1" apply false
    id("org.jetbrains.dokka") version "1.9.10"
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
