plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.taboo"
    compileSdk = 34


    buildFeatures {   /**** for API_KEY  ***/
        compose = true
        buildConfig = true
    }


    defaultConfig {
        minSdk = 30
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        buildConfigField("String", "API_KEY",  "\"${project.properties["API_KEY"]}\"")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )

        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }


    dependencies {

        implementation("com.google.ai.client.generativeai:generativeai:0.7.0")

        // To use CallbackToFutureAdapter
        implementation("androidx.concurrent:concurrent-futures:1.2.0")

        implementation(libs.appcompat)
        implementation(libs.material)
        implementation(libs.activity)
        implementation(libs.constraintlayout)
        implementation(libs.common)
        implementation(libs.generativeai)
        implementation(libs.espresso.web)
        testImplementation(libs.junit)
        androidTestImplementation(libs.ext.junit)
        androidTestImplementation(libs.espresso.core)
        implementation(kotlin("script-runtime"))

        implementation(libs.google.firebase.crashlytics.buildtools)
    }
}
dependencies {
    implementation(kotlin("script-runtime"))
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
}