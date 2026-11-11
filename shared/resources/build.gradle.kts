plugins {
    alias(libs.plugins.kotlinMultiplatformLibrary)
    alias(libs.plugins.composeMultiplatformPlugin)
}

compose {
    resources {
        publicResClass = true // generates Res.*
        // Pinned so the Res package does not change with rootProject.name.
        packageOfResClass = "ke.don.resources.generated"
    }
}

kotlin{
    sourceSets{
        commonMain.dependencies {
            implementation(compose.components.resources)
        }
    }
}