gradle.startParameter.showStacktrace = ShowStacktrace.ALWAYS
logging.captureStandardOutput(LogLevel.INFO)

group = "memes.mememaker"
version = "1.0-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    annotationProcessor("org.apache.logging.log4j:log4j-core:2.17.2")
    implementation("org.apache.logging.log4j:log4j-core:2.17.2")
}

plugins {
    java
}

repositories {
    mavenCentral()
}
