plugins {
    id("java-library")
    alias(libs.plugins.jetbrains.kotlin.jvm)
    kotlin("kapt")
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

kotlin {
    compilerOptions {
        jvmTarget = org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_17
    }
}

dependencies {
    implementation(libs.okhttp)
    compileOnly(libs.auto.service.annotations)
    kapt(libs.auto.service.processor)

    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
