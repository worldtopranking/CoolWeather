plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-kapt")
    id("io.gitlab.arturbosch.detekt").version("1.0.0")
}

apply {
    from("$rootDir/ktlint.gradle.kts")
}

val apiBaseUrl: String by project
val apiKey: String by project
val placesApiKey: String by project

android {
    compileSdkVersion(29)
    defaultConfig {
        minSdkVersion(21)
        targetSdkVersion(29)
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        buildConfigField(
            "String", "OPENWEATHERMAP_URL",
            "\"$apiBaseUrl\""
        )
        buildConfigField(
            "String", "OPENWEATHERMAP_API_KEY",
            "\"$apiKey\""
        )
        buildConfigField(
            "String", "PLACES_API_KEY",
            "\"$placesApiKey\""
        )

    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
}


detekt {
    toolVersion = "1.0.0"
    input = files("src/main/kotlin", "src/main/java")
    filters = ".*/resources/.*,.*/build/.*"
}

dependencies {
    // implementation fileTree(dir: "libs", include: ["*.jar"])
    implementation(project(":domain"))
    implementation(Libs.kotlinStdLib)

    implementation(Libs.coroutinesCore)

    implementation(Libs.dagger)
    kapt(Libs.daggerCompiler)

    implementation(Libs.retrofit)
    implementation(Libs.retrofitMoshi)

    implementation(Libs.moshi)
    kapt(Libs.moshiCodeGen)
}
