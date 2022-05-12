plugins {
    java
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.2")
    testImplementation("org.junit.jupiter:junit-jupiter-engine:5.8.2")
    testImplementation("org.assertj:assertj-core:3.22.0")
}

group = "kz.lakida.javacourse"
version = "1.0-SNAPSHOT"
description = "course-tasks"
java.sourceCompatibility = JavaVersion.VERSION_17

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
}
