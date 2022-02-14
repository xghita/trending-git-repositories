const val kotlinVersion = "1.3.71"
const val kotlinStbLibVersion = "1.4.31"

object BuildPlugins {

    object Versions {
        const val buildToolsVersion = "7.0.0"
    }

    private const val googleServicesVersion = "4.3.2"

    private const val androidGradlePlugin =
        "com.android.tools.build:gradle:${Versions.buildToolsVersion}"
    private const val kotlinGradlePlugin =
        "org.jetbrains.kotlin:kotlin-gradle-plugin:$kotlinVersion"
    private const val kotlinStdLib = "org.jetbrains.kotlin:kotlin-stdlib:$kotlinStbLibVersion"
    private const val navigationSafeArgs =
        "androidx.navigation:navigation-safe-args-gradle-plugin:${Navigation.VERSION}"
    private const val googleServices =
        "com.google.gms:google-services:$googleServicesVersion"
    private const val koinPlugin = "io.insert-koin:koin-gradle-plugin:${Koin.VERSION}"

    fun getClassPaths(): List<String> {
        return listOf(
            androidGradlePlugin,
            kotlinGradlePlugin,
            kotlinStdLib,
            navigationSafeArgs,
            googleServices,
            koinPlugin
        )
    }
}

object AndroidSdk {
    const val min = 23
    const val target = 31
}

object Libraries {

    private object Versions {
        const val googlePlayCore = "1.10.0"
        const val jetpack = "1.1.0"
        const val constraintLayout = "1.1.3"
        const val ktx = "1.1.0"
    }

    fun getImplementationDependencies(): List<String> {
        return listOf(
            kotlinStdLib,
            kotlinReflect,
            appCompat,
            constraintLayout,
            ktxCore,
            googlePlayCore
        )
            .asSequence()
            .plus(Navigation.dependencies)
            .plus(Lifecycle.dependencies)
            .plus(Glide.dependencies)
            .plus(Retrofit.dependencies)
            .plus(ReactiveX.dependencies)
            .plus(Koin.dependencies)
            .plus(Room.dependencies)
            .toList()
    }

    fun getKaptDependencies(): List<String> {
        return listOf(DataBinding.compiler, Glide.compiler, Room.compiler)
    }

    fun getAnnotations(): List<String> {
        return listOf(Room.annotation)
    }

    private const val googlePlayCore = "com.google.android.play:core:${Versions.googlePlayCore}"
    private const val kotlinStdLib = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:$kotlinVersion"
    private const val kotlinReflect = "org.jetbrains.kotlin:kotlin-reflect:$kotlinVersion"
    private const val appCompat = "androidx.appcompat:appcompat:${Versions.jetpack}"
    private const val constraintLayout =
        "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}"
    private const val ktxCore = "androidx.core:core-ktx:${Versions.ktx}"
}


object Navigation {

    const val VERSION = "2.3.5"

    val dependencies = listOf(
        "androidx.navigation:navigation-fragment-ktx:$VERSION",
        "androidx.navigation:navigation-runtime-ktx:$VERSION",
        "androidx.navigation:navigation-ui-ktx:$VERSION"
    )
}

object Room {
    private const val VERSION = "2.4.1"

    val dependencies = listOf(
        "androidx.room:room-runtime:$VERSION",
        "androidx.room:room-rxjava2:$VERSION"
    )

    const val annotation = "androidx.room:room-compiler:$VERSION"
    const val compiler = "androidx.room:room-compiler:$VERSION"
}

object Lifecycle {

    private const val VERSION = "2.2.0"

    val dependencies = listOf(
        "androidx.lifecycle:lifecycle-common:$VERSION",
        "androidx.lifecycle:lifecycle-runtime:$VERSION",
        "androidx.lifecycle:lifecycle-extensions:$VERSION",
        "androidx.lifecycle:lifecycle-viewmodel-ktx:$VERSION"
    )
}

object Retrofit {
    private const val RETROFIT_VERSION = "2.4.0"
    private const val GSON_VERSION = "2.8.9"

    val dependencies = listOf(
        "com.squareup.retrofit2:retrofit:$RETROFIT_VERSION",
        "com.squareup.retrofit2:converter-gson:$RETROFIT_VERSION",
        "com.squareup.retrofit2:adapter-rxjava2:$RETROFIT_VERSION",
        "com.google.code.gson:gson:$GSON_VERSION"
    )
}

object ReactiveX {
    val dependencies = listOf(
        "io.reactivex.rxjava2:rxjava:2.2.4",
        "io.reactivex.rxjava2:rxkotlin:2.3.0",
        "io.reactivex.rxjava2:rxandroid:2.1.0"
    )
}

object Glide {

    private const val VERSION = "4.9.0"

    val dependencies = listOf(
        "com.github.bumptech.glide:glide:$VERSION",
        "com.github.bumptech.glide:okhttp3-integration:$VERSION",
        "com.github.bumptech.glide:annotations:$VERSION"
    )

    const val compiler = "com.github.bumptech.glide:compiler:$VERSION"
}

object DataBinding {

    private const val VERSION_COMPILER = "3.1.4"

    const val compiler = "com.android.databinding:compiler:$VERSION_COMPILER"
}


object Koin {
    const val VERSION = "2.2.3"

    val dependencies = listOf(
        "io.insert-koin:koin-core:$VERSION",
        "io.insert-koin:koin-core-ext:$VERSION",
        "io.insert-koin:koin-androidx-scope:$VERSION",
        "io.insert-koin:koin-androidx-viewmodel:$VERSION",
        "io.insert-koin:koin-androidx-ext:$VERSION",
        "io.insert-koin:koin-ktor:$VERSION"
    )
}

object TestLibraries {

    private object Versions {
        const val junit4 = "4.13.2"
        const val testRunner = "1.1.1"
        const val espresso = "3.2.0"
        const val mockk = "1.11.0"
        const val mockito = "3.10.0"
        const val mockito_kotlin = "2.2.0"
        const val core_testing = "2.1.0"
    }

    private const val espresso_core = "androidx.test.espresso:espresso-core:${Versions.espresso}"
    private const val espresso = "androidx.test.espresso:espresso-core:${Versions.espresso}"

    private const val mockk_core = "io.mockk:mockk:${Versions.mockk}"
    private const val mockk_android = "io.mockk:mockk-android:${Versions.mockk}"

    private const val koin_test = "org.koin:koin-test:${Koin.VERSION}"

    private const val junit4 = "junit:junit:${Versions.junit4}"
    private const val testRunner = "androidx.test:runner:${Versions.testRunner}"
    private const val lifecycle_testing = "androidx.arch.core:core-testing:${Versions.core_testing}"

    private const val mockito_android = "org.mockito:mockito-android:${Versions.mockito}"
    private const val mockito_inline = "org.mockito:mockito-inline:${Versions.mockito}"
    private const val mockito_kotlin =
        "com.nhaarman.mockitokotlin2:mockito-kotlin:${Versions.mockito_kotlin}"

    fun getTestImplementationDependencies(): List<String> {
        return listOf(
            junit4, mockk_core, lifecycle_testing, koin_test, mockito_kotlin, mockk_android,
            mockito_inline
        )
    }

    fun getAndroidTestImplementationDependencies(): List<String> {
        return listOf(
            testRunner,
            espresso,
            espresso_core
        )
    }
}