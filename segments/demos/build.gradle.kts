
plugins {
    alias(libs.plugins.segmentConvention)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(libs.composemediaplayer)
            implementation(libs.material3.adaptive.layout)
            implementation(libs.material3.adaptive.navigation)
            implementation(libs.material3.navigationSuite)
        }
    }
}