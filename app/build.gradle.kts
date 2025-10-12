plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.serialization)
    id("androidx.navigation.safeargs.kotlin")
    alias(libs.plugins.ksp)
}

val BASE_URL: String by project

android {
    namespace = "com.barcelona.qurio"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.barcelona.qurio"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        buildConfigField("String", "BASE_URL", "\"${BASE_URL}\"")
    }
    sourceSets {
        getByName("main") {
            res.srcDirs("src/main/res", "src/main/res/components")
        }
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
    buildFeatures {
        dataBinding = true
        buildConfig = true
        viewBinding = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // DataStore
    implementation(libs.androidx.datastore.preferences)

    // Coroutines
    implementation(libs.kotlinx.coroutines.core)

    // SplashScreen
    implementation(libs.androidx.core.splashscreen)
    // Lottie Animations
    implementation(libs.lottie)

    // Retrofit & Okhttp
    implementation(libs.retrofit)
    implementation(libs.okhttp)
    implementation(libs.logging.interceptor)
    implementation(libs.retrofit2.kotlinx.serialization.converter)

    // Serialization
    implementation(libs.kotlinx.serialization.json)

    // Navigation
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)

    // Dagger
    implementation(libs.dagger)

    // Room Database
    implementation(libs.bundles.room)
    ksp(libs.androidx.room.compiler)

    kapt(libs.dagger.compiler)

    // Carbon Blur
    api(libs.carbon)
}