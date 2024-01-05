plugins {
    id("kiwi.orbit.compose.buildlogic.library")
    id("kiwi.orbit.compose.buildlogic.publish")
    id("androidx.baselineprofile")
    id("org.jmailen.kotlinter")
}

kotlinter {
    reporters = arrayOf("json")
}

baselineProfile {
    baselineProfileOutputDir = "."

    filter {
        include("kiwi.orbit.compose.illustrations.**")
    }
}

dependencies {
    implementation(platform(libs.compose.bom))

    implementation(libs.kotlin.stdlib)
    implementation(libs.androidx.core)
    implementation(libs.compose.runtime)
    implementation(libs.compose.ui)

    baselineProfile(projects.baselineprofile)
}
