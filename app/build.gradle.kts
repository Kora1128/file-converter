plugins {
    kotlin("jvm") version "1.9.23"
}

repositories {
    mavenCentral()
}

kotlin {
    jvmToolchain(21) // Set this to match your Java version
}

dependencies {
    implementation("com.itextpdf:itext7-core:8.0.5")

    implementation("org.apache.pdfbox:pdfbox:2.0.29")
    implementation("org.apache.pdfbox:pdfbox-tools:2.0.29")

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.10.3")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.10.3")
}

tasks.test {
    useJUnitPlatform()
}
