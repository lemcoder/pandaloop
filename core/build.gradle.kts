plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.library)
}

kotlin {
    jvmToolchain(17)

    androidTarget()

    sourceSets {
        commonMain.dependencies { }
        commonTest.dependencies {
            implementation(kotlin("test"))
        }

        androidMain.dependencies {
            implementation(libs.jna.get()) { artifact { type = "aar" } }
        }

        val androidInstrumentedTest by getting {
            dependencies {
                implementation(libs.androidX.testRunner)
            }
        }
    }
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

//    sourceSets {
//        getByName("main") {
//            manifest.srcFile("src/androidMain/AndroidManifest.xml")
//        }
//    }

    externalNativeBuild {
        cmake {
            path = file("src/androidMain/cpp/CMakeLists.txt")
        }
    }
}
