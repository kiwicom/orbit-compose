package kiwi.orbit.compose.buildlogic

import com.vanniktech.maven.publish.MavenPublishBaseExtension
import com.vanniktech.maven.publish.SonatypeHost
import java.time.Year
import java.util.Properties
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.extra
import org.gradle.kotlin.dsl.withType
import org.jetbrains.dokka.base.DokkaBase
import org.jetbrains.dokka.base.DokkaBaseConfiguration
import org.jetbrains.dokka.gradle.DokkaTaskPartial

class PublishPlugin : Plugin<Project> {
    override fun apply(project: Project): Unit = with(project) {
        pluginManager.apply("org.jetbrains.dokka")
        pluginManager.apply("com.vanniktech.maven.publish")

        val signingPropsFile = rootProject.file("release/signing.properties")
        if (signingPropsFile.exists()) {
            val localProperties = Properties()
            with(signingPropsFile.inputStream()) {
                localProperties.load(this)
            }
            localProperties.forEach { key, value ->
                if (key == "signing.secretKeyRingFile") {
                    project.extra.set(key as String, rootProject.file(value).absolutePath)
                } else {
                    project.extra.set(key as String, value)
                }
            }
        }

        extensions.configure<MavenPublishBaseExtension> {
            group = requireNotNull(project.findProperty("GROUP"))
            version = requireNotNull(project.findProperty("VERSION_NAME"))
            publishToMavenCentral(SonatypeHost.S01, automaticRelease = true)
            signAllPublications()
        }

        tasks.withType<DokkaTaskPartial>().configureEach {
            pluginConfiguration<DokkaBase, DokkaBaseConfiguration> {
                customStyleSheets = listOf(file("dokka/orbit-style.css"))
                customAssets = listOf(file("dokka/logo-icon.svg"))
                footerMessage = "© ${Year.now().value} Kiwi.com"
            }
        }
    }
}
