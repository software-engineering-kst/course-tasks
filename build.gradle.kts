plugins {
    java
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.postgresql:postgresql:42.5.0")

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.0")
    testImplementation("org.junit.jupiter:junit-jupiter-engine:5.9.0")
    testImplementation("org.assertj:assertj-core:3.23.1")
}

group = "kz.lakida.javacourse"
version = "1.0-SNAPSHOT"
description = "course-tasks"
java.sourceCompatibility = JavaVersion.VERSION_17

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
}
