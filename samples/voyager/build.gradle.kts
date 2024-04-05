import config.Config

plugins {
    id("sample-setup")
}

android {
    defaultConfig {
        applicationId = Config.applicationId + ".voyager"
    }
}

kotlin {
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            export(projects.rinku.rinkuCore)
            baseName = "ComposeApp"
            isStatic = true
        }
    }

    sourceSets {
        androidMain.dependencies {
            implementation(libs.compose.ui.tooling.preview)
            implementation(libs.androidx.activity.compose)
        }

        commonMain.dependencies {
            implementation(projects.rinku.rinkuCore)
            implementation(libs.voyager.navigator)
            implementation(libs.voyager.screenModel)
        }

        iosMain.dependencies {
            api(projects.rinku.rinkuCore)
        }
    }
}
