plugins {
    java
    `maven-publish`
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.5.2")
    testImplementation("org.junit.jupiter:junit-jupiter-engine:5.5.2")
    testImplementation("org.assertj:assertj-core:3.14.0")
}

group = "kz.lakida.javacourse"
version = "1.0-SNAPSHOT"
description = "course-tasks"
java.sourceCompatibility = JavaVersion.VERSION_1_8

publishing {
    publications.create<MavenPublication>("maven") {
        from(components["java"])
    }
}
