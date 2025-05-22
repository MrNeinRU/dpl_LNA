import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpackConfig

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.kotlinSerialization)
}
configurations {
    create("cleanedAnnotations")
    implementation {
        exclude(group = "org.jetbrains", module = "annotations")
    }
}
kotlin {
    androidTarget {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_18)
        }
    }

    jvm("desktop")
    
//    @OptIn(ExperimentalWasmDsl::class)
//    wasmJs {
//        moduleName = "composeApp"
//        browser {
//            val rootDirPath = project.rootDir.path
//            val projectDirPath = project.projectDir.path
//            commonWebpackConfig {
//                outputFileName = "composeApp.js"
//                devServer = (devServer ?: KotlinWebpackConfig.DevServer()).apply {
//                    static = (static ?: mutableListOf()).apply {
//                        // Serve sources to debug inside browser
//                        add(rootDirPath)
//                        add(projectDirPath)
//                    }
//                }
//            }
//        }
//        binaries.executable()
//    }
    
    sourceSets {
        val desktopMain by getting
        
        androidMain.dependencies {
            implementation(compose.preview)
            implementation(libs.androidx.activity.compose)
        }
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.materialIconsExtended)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.img.coil3)
            implementation(libs.img.coil3.svg)
            implementation(libs.androidx.lifecycle.viewmodel)
            implementation(libs.androidx.lifecycle.runtime.compose)

            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.kotlinx.datetime)
//            implementation(libs.apache.pdfbox)

            //ktor client
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.okhttp)
            //ktor client

            //navigation
            implementation(libs.voyager.navigator)
            implementation(libs.voyager.tab.navigator)
            implementation(libs.voyager.screenmodel)
            //navigation

            //map
            implementation(libs.kotlin.graphs)
            implementation(libs.mapcompose.mp)
            //map

            //local database
            implementation(libs.room.runtime)
//            implementation(libs.room.gradle.plugin)
            implementation(libs.room.compiler)
            implementation(libs.room.sqlite)
            //local database
        }
        desktopMain.dependencies {
            implementation(compose.desktop.currentOs)
            implementation(libs.kotlinx.coroutines.swing)

            //EXPERIMENTAL//TODO
//            implementation(libs.pdf.dev.zt64.compose.pdf)
//            implementation(libs.pdf.icepdf)
//            implementation(libs.img.zoomable)
//            implementation(libs.img.coil3)

            //net
//            implementation(libs.net.pcap4j.core)
//            implementation(libs.net.jnetpcap.wrapper)
            //net
        }
    }
}

android {
    namespace = "ru.malygin.anytoany"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "ru.malygin.anytoany"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 3
        versionName = "1.0"
    }
    applicationVariants.all {
        outputs.all {
            this as com.android.build.gradle.internal.api.ApkVariantOutputImpl
            val buildName = buildType.name
            val apkName = "DPL_" + defaultConfig.versionName + "_" + defaultConfig.versionCode + "_" + buildName + ".apk"

            outputFileName = apkName
        }
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
            excludes += "/META-INF/DEPENDENCIES"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "pro_guard.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_18
        targetCompatibility = JavaVersion.VERSION_18
    }
}

dependencies {
    debugImplementation(compose.uiTooling)
}

compose.desktop {
    application {
        mainClass = "ru.malygin.anytoany.MainKt"
        buildTypes.release.proguard{
            version.set("7.7.0")
            configurationFiles.from(
                project.file("pro_guard.pro")
            )
            obfuscate.set(false)
            optimize.set(false)
        }

        nativeDistributions {
            targetFormats(TargetFormat.Msi,TargetFormat.Exe, TargetFormat.Deb, TargetFormat.Rpm)
            packageName = "ru.malygin.anytoany"
            packageVersion = "1.0.0"
            description = "Приложение для дипломного проекта"

            outputBaseDir.set(
                project.file("output")
            )

            windows{
                packageName = "DPL_windows"
                iconFile.set(
                    project.file("icons/icon.ico")
                )
                exePackageVersion = "1.0.0"
                dirChooser = true // предоставляет возможность выбрать папку для установки
            }
            linux{
                packageName = "DPL_linux"
                iconFile.set(
                    project.file("icons/icon.png")
                )
                rpmPackageVersion = "1.0.0"
            }
        }
    }
}
compose.resources{
    this.publicResClass = true
}
