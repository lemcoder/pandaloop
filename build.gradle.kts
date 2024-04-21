@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.android.library).apply(false)
    alias(libs.plugins.kotlin.android).apply(false)
    alias(libs.plugins.kotlin.multiplatform).apply(false)
    alias(libs.plugins.cklib).apply(false)
    alias(libs.plugins.complete.kotlin).apply(true)
}