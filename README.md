# Audio looping library for Kotlin Multiplatform

## Content

This repository contains code for Kotlin Multiplatform library for iOS and Android targets, which
provides Kotlin bindings for C-implementations of SHA-256 hash function.

Build configuration doesn't contains custom gradle tasks or any shell-script. For iOS target
it uses [CKlib gradle plugin](https://github.com/touchlab/cklib) and for Android target it uses regular Android gradle plugin's NDK.
