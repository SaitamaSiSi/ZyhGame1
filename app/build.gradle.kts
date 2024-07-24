plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
}

android {
    namespace = "com.zyh.ZyhG1"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.zyh.ZyhG1"
        minSdk = 30
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
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
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    buildFeatures {
        prefab = true
        compose = true
    }
    externalNativeBuild {
        cmake {
            path = file("src/main/cpp/CMakeLists.txt")
            version = "3.22.1"
        }
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.games.activity)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.lifecycle.runtime.ktx)
    implementation(libs.activity.compose)
    implementation(platform(libs.compose.bom))
    implementation(libs.ui)
    implementation(libs.ui.graphics)
    implementation(libs.ui.tooling.preview)
    implementation(libs.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    androidTestImplementation(platform(libs.compose.bom))
    androidTestImplementation(libs.ui.test.junit4)
    debugImplementation(libs.ui.tooling)
    debugImplementation(libs.ui.test.manifest)

    /** exifinterface **/
    implementation("androidx.exifinterface:exifinterface:1.3.6")

    /** OkHttp **/
    // implementation("com.squareup.okhttp3:okhttp:4.12.0")
    /** json **/
    // implementation(libs.gson) // 请检查并使用最新版本
    /** Retrofit,是基于OkHttp开发，故会自动引入OkHttp **/
    implementation("com.squareup.retrofit2:retrofit:2.11.0")
    /** Retrofit转换库,会自动引入gson **/
    implementation("com.squareup.retrofit2:converter-gson:2.11.0")

    /** Material库,是基于Material Design的要求来设计 **/
    implementation("com.google.android.material:material:1.1.0")
    /** 开源项目CircleImageView,实现图片圆形化 **/
    implementation("de.hdodenhof:circleimageview:3.0.1")
}