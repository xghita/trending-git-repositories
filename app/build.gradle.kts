plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("android.extensions")
    kotlin("kapt")
    id("androidx.navigation.safeargs.kotlin")
}


android {

    compileSdk = AndroidSdk.target

    defaultConfig {
        applicationId = "com.test.gittrendingrepo"
        minSdk = AndroidSdk.min
        targetSdk = AndroidSdk.target
        versionCode = 1
        versionName = "1.0.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        multiDexEnabled = true
        buildConfigField("String", "API_URL", "\"https://api.github.com/\"")
    }

    buildFeatures {
        dataBinding = true
    }

    buildTypes {
        getByName("debug") {
            isDebuggable = true
        }
        getByName("release") {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    flavorDimensions.add("organization")
    productFlavors {
        create("staging") {
            applicationId = "com.test.gittrendingrepo.stg"
            dimension = "organization"
            resValue("string", "app_name", "Test STG")
        }
        create("production") {
            applicationId = "com.test.gittrendingrepo"
            dimension = "organization"
            resValue("string", "app_name", "Test")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    packagingOptions {
        resources.excludes.add("META-INF/LICENSE")
        resources.pickFirsts.add("META-INF/kotlinx-io.kotlin_module")
        resources.pickFirsts.add("META-INF/atomicfu.kotlin_module")
        resources.pickFirsts.add("META-INF/kotlinx-coroutines-io.kotlin_module")
        resources.pickFirsts.add("META-INF/kotlinx-coroutines-core.kotlin_module")
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

    lint {
        isCheckReleaseBuilds = false
    }
}

dependencies {

    Libraries.getImplementationDependencies().forEach { implementation(it) }

    Libraries.getAnnotations().forEach { annotationProcessor(it) }

    Libraries.getKaptDependencies().forEach { kapt(it) }

    TestLibraries.getTestImplementationDependencies().forEach { testImplementation(it) }

    TestLibraries.getAndroidTestImplementationDependencies()
        .forEach { androidTestImplementation(it) }
}