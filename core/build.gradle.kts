import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.library)
    alias(libs.plugins.cklib)
}

android {
    compileSdk = 34

    defaultConfig {
        minSdk = 24
        multiDexEnabled = true
        namespace = "pl.lemanski.pandaloop"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        ndk {
            // Add application binary interfaces for intel devices (emulators)
            abiFilters += listOf("x86", "x86_64", "armeabi-v7a", "arm64-v8a")
        }
    }

    externalNativeBuild {
        cmake {
            path = file("src/androidMain/cpp/CMakeLists.txt")
        }
    }
}

cklib {
    config.kotlinVersion = libs.versions.kotlin.get()
    create("pl_engine") {
        language = co.touchlab.cklib.gradle.CompileToBitcode.Language.C
        srcDirs = project.files(file("native"))
        linkerArgs += listOf(
            "-lpthread",
            "-lm",
            "-framework CoreFoundation",
            "-framework CoreAudio",
            "-framework AudioToolbox"
        )
    }
}

kotlin {
    jvmToolchain(17)

    androidTarget()
    iosX64()
    iosArm64()
    iosSimulatorArm64()

    applyDefaultHierarchyTemplate()
    sourceSets {
        all {
            languageSettings {
                languageVersion = "2.0"
                compilerOptions {
                    freeCompilerArgs.add("-Xexpect-actual-classes")
                }
            }
        }

        commonMain.dependencies { }
        commonTest.dependencies {
            implementation(kotlin("test"))
        }

        androidMain.dependencies {
            implementation(libs.jna.get()) { artifact { type = "aar" } }
        }

        getByName("androidInstrumentedTest").dependencies {
            implementation(libs.androidX.testRunner)
            implementation(libs.test.rules)
        }

        iosMain.dependencies {

        }

        targets.withType<KotlinNativeTarget> {
            val main by compilations.getting

            main.cinterops.create("pl_engine") {
                headers(
                    file("native/audio_player.h"),
                    file("native/audio_recorder.h"),
                    file("native/device_manager.h"),
                    file("native/resource_manager.h"),
                )
            }
        }
    }
}