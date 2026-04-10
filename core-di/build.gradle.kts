plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    id("kotlin-kapt")
}

android {
    namespace = "petlink.android.core_di"
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
    implementation(project(":feature-community-data"))
    implementation(project(":feature-community-data-impl"))
    implementation(project(":feature-community-domain"))
    implementation(project(":feature-community-domain-impl"))
    implementation(project(":feature-profile-data"))
    implementation(project(":feature-profile-data-impl"))
    implementation(project(":feature-profile-domain"))
    implementation(project(":feature-profile-domain-impl"))
    implementation(project(":feature-calendar-data"))
    implementation(project(":feature-calendar-data-impl"))
    implementation(project(":feature-calendar-domain"))
    implementation(project(":feature-calendar-domain-impl"))
    implementation(project(":core-navigation"))
    implementation(project(":core-data"))
    implementation(libs.androidx.room.ktx.v283)
    implementation(libs.androidx.room.runtime.v283)
    kapt(libs.androidx.room.compiler.v283)
    implementation(libs.firebase.auth)
    implementation(libs.dagger)
    kapt(libs.dagger.compiler.v255)
    implementation(libs.cicerone)
    implementation(libs.firebase.firestore)
    implementation(platform(libs.firebase.bom))
    implementation(libs.retrofit.v2100)
    implementation(libs.okhttp)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}