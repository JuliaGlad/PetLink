plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    id("kotlin-kapt")
}

android {
    namespace = "petlink.android.feature_profile_data_impl"
    compileSdk = 35

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
}

dependencies {
    implementation(project(":feature-profile-data"))
    implementation(project(":feature-profile-domain"))
    implementation(project(":core-data"))
    implementation(project(":core-di"))
    implementation(libs.converter.gson)
    kapt(libs.androidx.room.compiler.v283)
    implementation(libs.androidx.room.ktx.v283)
    implementation(libs.androidx.room.runtime.v283)
    implementation(libs.dagger)
    kapt(libs.dagger.compiler.v255)
    implementation(libs.firebase.auth)
    implementation(platform(libs.firebase.bom.v3440))
    implementation(libs.firebase.firestore)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}