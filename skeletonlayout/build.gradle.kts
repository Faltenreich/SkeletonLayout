plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin)
    alias(libs.plugins.maven.publish)
}

android {
    compileSdk = 35
    namespace = "com.faltenreich.skeletonlayout"

    defaultConfig {
        minSdk = 21
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
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

mavenPublishing {
    publishToMavenCentral("S01")
    signAllPublications()
}

dependencies {
    testImplementation(libs.junit)
    androidTestImplementation(libs.espresso)
    androidTestImplementation(libs.junit.androidx)

    api(libs.recyclerview)
    api(libs.viewpager2)
}