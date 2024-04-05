import org.jetbrains.compose.ExperimentalComposeLibrary
import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsCompose)
    kotlin("plugin.serialization") version "1.9.22"
}

kotlin {
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
    }
    
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true

            export("com.arkivanov.decompose:decompose:2.2.2-compose-experimental")
//            export("com.arkivanov.decompose:extensions-compose-jetbrains:2.2.2-compose-experimental")
            export("com.arkivanov.essenty:lifecycle:1.3.0")
        }
    }
    
    sourceSets {
        androidMain.dependencies {
            implementation(libs.compose.ui.tooling.preview)
            implementation(libs.androidx.activity.compose)
            implementation(libs.ktor.client.android)

            // Navigation
            implementation("com.arkivanov.decompose:decompose:2.2.2-compose-experimental")
            implementation("com.arkivanov.decompose:extensions-compose-jetbrains:2.2.2-compose-experimental")
            // Add the dependency, typically under the commonMain source set

        }
        iosMain.dependencies {
            implementation(libs.ktor.client.darwin)

            api("com.arkivanov.decompose:decompose:2.2.2-compose-experimental")
//            api("com.arkivanov.decompose:extensions-compose-jetbrains:2.2.2-compose-experimental")
            api("com.arkivanov.essenty:lifecycle:1.3.0")
        }
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material)
            implementation(compose.ui)

            @OptIn(ExperimentalComposeLibrary::class)
            implementation(compose.components.resources)
            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.ktor.serialization.kotlinx.json)
            implementation(libs.bundles.ktor)
            implementation(libs.mvvm.core)
            implementation(libs.image.loader)

            // Navigation
            implementation("com.arkivanov.decompose:decompose:2.2.2-compose-experimental")
            implementation("com.arkivanov.decompose:extensions-compose-jetbrains:2.2.2-compose-experimental")
            implementation(compose.material3)
        }
    }
}

android {
    namespace = "org.mohit.kmpcompose"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets["main"].res.srcDirs("src/androidMain/res")
    sourceSets["main"].resources.srcDirs("src/commonMain/resources")

    defaultConfig {
        applicationId = "org.mohit.kmpcompose"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
//        multiDexEnabled = true
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    dependencies {
        debugImplementation(libs.compose.ui.tooling)
//        implementation("com.android.support:multidex:1.0.3")
    }
}
dependencies {
    implementation(libs.androidx.material)
    implementation(libs.androidx.ui.tooling.preview.android)

    debugImplementation(libs.androidx.ui.tooling.v142)
    implementation(libs.androidx.ui.tooling.preview.v142)
}

