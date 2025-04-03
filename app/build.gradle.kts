plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.example.weatherapp"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.weatherapp"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    buildFeatures{
      viewBinding =true
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
    dependenciesInfo {
        includeInApk = true
        includeInBundle = true
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {
    implementation("com.airbnb.android:lottie:6.6.4") // ✅ Use correct format
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}
dependencies {
    implementation(libs.core.ktx)// Retrofit for making network requests
    implementation("com.squareup.retrofit2:retrofit:2.9.0")

    // Gson Converter for Retrofit to parse JSON responses
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    // Gson for parsing JSON data
    implementation("com.google.code.gson:gson:2.8.8")

    // Android X for AppCompatActivity
    implementation("androidx.appcompat:appcompat:1.3.1")

    // Material Design for SearchView (optional, depending on your design)
    implementation("com.google.android.material:material:1.4.0")

    // ViewBinding support (if you are using ViewBinding)
    implementation("androidx.databinding:viewbinding:7.0.2")

    // You may want to add this for the latest Android SDK features
    implementation("androidx.constraintlayout:constraintlayout:2.1.1")

    // Add this for logging Retrofit requests (optional but useful for debugging)
    implementation("com.squareup.okhttp3:logging-interceptor:4.9.0")

    // Testing (for unit tests, optional)
    testImplementation("junit:junit:4.13.2")

    // Android X Test (for UI tests, optional)
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
    implementation ("com.github.bumptech.glide:glide:4.12.0")
    annotationProcessor ("com.github.bumptech.glide:compiler:4.12.0")
}

