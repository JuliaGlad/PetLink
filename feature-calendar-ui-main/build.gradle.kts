plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    id("kotlin-kapt")
}

android {
    namespace = "petlink.android.feature_calendar_ui_main"
    compileSdk = 35

    defaultConfig {
        minSdk = 26

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
    buildFeatures{
        viewBinding = true
    }
}

dependencies {
    implementation(project(":core-di"))
    implementation(project(":core-mvi"))
    implementation(project(":core-ui"))
    implementation(project(":core-data"))
    implementation(project(":core-navigation"))
    implementation(project(":feature-calendar-domain"))
    implementation(project(":feature-calendar-ui-history"))
    implementation(project(":feature-calendar-ui-edit_event"))
    implementation(project(":feature-calendar-ui-add_event"))
    implementation(project(":feature-calendar-ui-calendar_view"))
    implementation(libs.androidx.room.runtime.v283)
    kapt(libs.androidx.room.compiler.v283)
    implementation(libs.cicerone)
    implementation(libs.dagger)
    kapt(libs.dagger.compiler.v255)
    implementation(libs.okhttp)
    implementation(libs.retrofit.v2100)
    implementation(libs.firebase.auth)
    implementation(libs.firebase.firestore)
    implementation(platform(libs.firebase.bom))
    implementation(libs.imagepicker)
    implementation(libs.androidx.fragment.ktx)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}