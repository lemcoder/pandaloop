import com.android.build.gradle.internal.tasks.factory.registerTask
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget
import org.jetbrains.kotlin.gradle.plugin.mpp.apple.XCFramework

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.library)
    // alias(libs.plugins.cklib)
    `maven-publish`
}

group = "pl.lemanski.pandaloop"
version = "0.0.1"

android {
    compileSdk = 34

    defaultConfig {
        minSdk = 24
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

//cklib {
//    config.kotlinVersion = libs.versions.kotlin.get()
//    create("pl_engine") {
//        language = co.touchlab.cklib.gradle.CompileToBitcode.Language.C
//        srcDirs = project.files(file("native"))
//        linkerArgs += listOf(
//            "-lpthread",
//            "-lm",
//            "-framework CoreFoundation",
//            "-framework CoreAudio",
//            "-framework AudioToolbox"
//        )
//    }
//}

kotlin {
    jvmToolchain(17)

    val xcFrameworkName = "PandaLoop"
    val xcf = XCFramework(xcFrameworkName)
    val iosTargets = listOf(iosX64(), iosArm64(), iosSimulatorArm64())

    iosTargets.forEach {
        it.binaries.framework {
            baseName = xcFrameworkName
            binaryOption("bundleId", "org.example.${xcFrameworkName}")

            xcf.add(this)
            isStatic = true
        }
    }

    androidTarget {
        publishAllLibraryVariants()
    }

    applyDefaultHierarchyTemplate()
    sourceSets {
        all {
            languageSettings {
                @OptIn(ExperimentalKotlinGradlePluginApi::class)
                compilerOptions {
                    freeCompilerArgs.add("-Xexpect-actual-classes")
                }
            }
        }

        commonMain.dependencies {
        }

        commonTest.dependencies {
            implementation(kotlin("test"))
        }

        androidMain.dependencies {
            implementation(libs.androidX.annotation)
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

tasks.register("moveSWIGFiles") {
    copy {
        from("src/androidMain/cpp/.swig/*.java")
        to("src/androidMain/kotlin/pl/lemanski/pandaloop/jni")
    }

    copy {
        from("src/androidMain/cpp/.swig/*.c")
        to("native/jni")
    }
}