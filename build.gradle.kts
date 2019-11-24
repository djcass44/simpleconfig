/*
 *   This Source Code Form is subject to the terms of the Mozilla Public
 *   License, v. 2.0. If a copy of the MPL was not distributed with this
 *   file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

plugins {
    kotlin("jvm") version "1.3.60"
    maven
}

group = "dev.dcas"
version = "0.1"

repositories {
    mavenCentral()
    jcenter()
    maven(url = "https://jitpack.io")
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation("org.jetbrains.kotlin:kotlin-reflect")

    implementation("com.github.djcass44:log2:3.4")
    implementation("com.github.djcass44:castive-utilities:v3")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.10.0")


    val junitVersion = "5.2.0"
    testImplementation("org.junit.jupiter:junit-jupiter-api:$junitVersion")
    testImplementation("org.junit.jupiter:junit-jupiter-params:$junitVersion")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:$junitVersion")

    testImplementation("org.hamcrest:hamcrest:2.2")
    testImplementation("org.mockito:mockito-core:3.0.0")
    testImplementation("org.jetbrains.kotlin:kotlin-test")
}

tasks {
    wrapper {
        gradleVersion = "5.6.4"
        distributionType = Wrapper.DistributionType.ALL
    }
    withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().all {
        kotlinOptions.jvmTarget = "11"
    }
    withType<Test> {
        useJUnitPlatform()
    }
}