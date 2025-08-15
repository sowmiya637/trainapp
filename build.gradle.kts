plugins {
    alias(libs.plugins.android.application) apply false
    // Add the Google Services Gradle plugin
    id("com.google.gms.google-services") version "4.4.0" apply false // Use the latest version
}
