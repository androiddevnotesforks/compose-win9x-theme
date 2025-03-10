plugins {
    id("android.library-conventions")
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.jetbrainsCompose)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(libs.kotlinx.io.core)
        }
        jvmMain.dependencies {
            implementation(compose.desktop.common)
        }
    }
}

android {
    namespace = "nl.ncaj.theme.win9x"
}