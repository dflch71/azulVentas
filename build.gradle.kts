// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.jetbrainsKotlinAndroid) apply false
    alias(libs.plugins.googleServices) apply false
    alias(libs.plugins.crashlytics) apply false

    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.google.dagger.hilt) apply false

    // Existing plugins
    alias(libs.plugins.compose.compiler) apply false
    alias(libs.plugins.android.test) apply false
    //alias(libs.plugins.baselineprofile) apply false
}