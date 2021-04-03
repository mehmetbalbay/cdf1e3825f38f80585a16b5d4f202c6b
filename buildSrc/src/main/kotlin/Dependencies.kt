object Dependencies {

    // Kotlin Libs
    val kotlinStdLib = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:${Versions.kotlinVersion}"
    val coreCoreKtx = "androidx.core:core-ktx:${Versions.coreCoreKtx}"

    // Android Libs
    val appCompat = "androidx.appcompat:appcompat:${Versions.appCompat}"
    val constraintLayout = "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}"
    val recyclerView = "androidx.recyclerview:recyclerview:${Versions.recyclerView}"
    val cardView = "androidx.cardview:cardview:${Versions.cardView}"

    // Koin
    val koinAndroid = "org.koin:koin-android:${Versions.koin}"
    val koinAndroidXScope = "org.koin:koin-androidx-scope:${Versions.koin}"
    val koinAndroidXViewModel = "org.koin:koin-androidx-viewmodel:${Versions.koin}"
    val koinAndroidXFragment = "org.koin:koin-androidx-fragment:${Versions.koin}"
    val koinTest = "org.koin:koin-test:${Versions.koin}"

    // Glide
    val glide = "com.github.bumptech.glide:glide:${Versions.glide}"
    val glideCompiler = "com.github.bumptech.glide:compiler:${Versions.glide}"

    // Room
    val roomRuntime = "androidx.room:room-runtime:${Versions.room}"
    val roomCompiler = "androidx.room:room-compiler:${Versions.room}"

    // Rest Libs
    val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    val retrofitGson = "com.squareup.retrofit2:converter-gson:${Versions.retrofit}"
    val gson = "com.google.code.gson:gson:${Versions.gson}"
    val okhttp = "com.squareup.okhttp3:okhttp:${Versions.okhttp}"

    // Coroutines
    val coroutinesAndroid = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}"
    val coroutineAndroidTest = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}"
    val coroutinesTest = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.coroutines}"

    // ViewModel LiveData
    val archLifecycleViewmodelKtx = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycle}"
    val archLifecycleLivedataKtx = "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.lifecycle}"
    val archLifecycle = "android.arch.lifecycle:extensions:${Versions.lifecycle}"

    // Logging
    val timber = "com.jakewharton.timber:timber:${Versions.timber}"

    // Shared Pref
    val hawk = "com.orhanobut:hawk:${Versions.hawk}"

    // MultiDex
    val multidex = "androidx.multidex:multidex:${Versions.multidex}"

    // Material Design Lib
    var androidMaterial = "com.google.android.material:material:${Versions.androidMaterial}"

    // Navigation Component
    val navigationFragment = "androidx.navigation:navigation-fragment-ktx:${Versions.navigation}"
    val navigationUI = "androidx.navigation:navigation-ui-ktx:${Versions.navigation}"

    // Test IMPL
    val junit = "junit:junit:${Versions.junit}"
    val extJunit = "androidx.test.ext:junit:${Versions.extJunit}"
    val espressoCore = "androidx.test.espresso:espresso-core:${Versions.espressoCore}"

}