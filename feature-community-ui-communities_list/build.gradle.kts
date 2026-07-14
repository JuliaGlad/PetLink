plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    id("kotlin-kapt")
    id("kotlin-parcelize")
}

android {
    namespace = "petlink.android.feature_community_ui_news"
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
    buildFeatures {
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
    implementation(project(":core-ui"))
    implementation(project(":feature-community-ui-create_community"))
    implementation(project(":feature-community-ui-create_question_community"))
    implementation(project(":feature-community-ui-create_chat"))
    implementation(project(":feature-community-ui-create_photo_group"))
    implementation(project(":feature-community-ui-community_details"))
    implementation(project(":feature-community-ui-chat_details"))
    implementation(project(":feature-community-friend_details"))
    implementation(project(":core-di"))
    implementation(project(":core-mvi"))
    implementation(project(":core-navigation"))
    implementation(project(":feature-community-domain"))
    implementation(project(":core-data"))
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.cicerone)
    implementation(libs.firebase.auth)
    implementation(libs.firebase.firestore)
    implementation(platform(libs.firebase.bom))
    implementation(libs.androidx.room.runtime.v283)
    kapt(libs.androidx.room.compiler.v283)
    implementation(libs.androidx.room.ktx.v283)
    implementation(libs.okhttp)
    implementation(libs.retrofit.v2100)
    implementation(libs.dagger)
    kapt(libs.dagger.compiler.v255)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.fragment.ktx)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}