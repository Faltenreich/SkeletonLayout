plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin)
}

android {
    compileSdk = 35
    namespace = "com.faltenreich.skeletonlayout.demo"

    defaultConfig {
        applicationId = "com.faltenreich.skeletonlayout"
        minSdk = 21
        targetSdk = 35
        versionCode = 10
        versionName = "6.0.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        buildFeatures.viewBinding = true
    }

    buildTypes {
        getByName("debug") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
        getByName("release") {
            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation(project(":skeletonlayout"))

    testImplementation(libs.junit)
    androidTestImplementation(libs.espresso)

    implementation(libs.material)
    implementation(libs.constraintlayout)
    implementation(libs.colorslider)
}