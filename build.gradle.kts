plugins {
    kotlin("jvm") version "1.3.72"
    id("com.github.johnrengelman.shadow") version "6.0.0"
}

group = "org.example"
version = "1.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.xenomachina:kotlin-argparser:2.0.7")
    implementation(kotlin("stdlib-jdk8"))
    testImplementation("org.junit.jupiter:junit-jupiter:5.6.2")
}

tasks {
    build {
        dependsOn(shadowJar)
    }
    compileKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
    compileTestKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
}

tasks.shadowJar {
    archiveBaseName.set("MinimaxConnect4")
    archiveClassifier.set("")
    mergeServiceFiles()
    manifest {
        attributes["Main-Class"] = "MainKt"
    }
}

tasks.test {
    useJUnitPlatform()
}