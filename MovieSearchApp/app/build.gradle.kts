plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    kotlin("kapt")
}

android {
    namespace = "com.example.moviesearchapp"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.moviesearchapp"
        minSdk = 33
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }

    buildFeatures {
        compose = true
        viewBinding = true
    }
}

dependencies {
    implementation ("androidx.appcompat:appcompat:1.4.0")
    implementation ("androidx.constraintlayout:constraintlayout:2.1.1")

    // Material Components (to fix Theme issues)
    implementation("com.google.android.material:material:1.6.0") // Add Material Components dependency

    // Use a compatible Compose BOM version
    implementation(platform("androidx.compose:compose-bom:2025.03.00"))

    // Core Compose dependencies
    implementation("androidx.compose.ui:ui:1.2.0") // Specify version explicitly
    implementation("androidx.compose.material3:material3:1.0.0")

    // Retrofit & Gson for API calls
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    // Lifecycle (ViewModel & LiveData)
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.6.2")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.2")

    // Activity KTX (for viewModels delegate)
    implementation("androidx.activity:activity-ktx:1.7.0")

    // RecyclerView for displaying lists
    implementation("androidx.recyclerview:recyclerview:1.3.2")

    // Glide for image loading
    implementation("com.github.bumptech.glide:glide:4.16.0")

    // CardView for displaying movie items
    implementation("androidx.cardview:cardview:1.0.0") // Add CardView dependency

    kapt("com.github.bumptech.glide:compiler:4.16.0")

    // Debug-specific tooling for Compose
    debugImplementation("androidx.compose.ui:ui-tooling:1.2.0") // Specify version explicitly

    // Testing dependencies
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:1.2.0") // Make sure this matches the version
    debugImplementation("androidx.compose.ui:ui-tooling-preview:1.2.0")
    debugImplementation("androidx.compose.ui:ui-test-manifest:1.2.0")
}
