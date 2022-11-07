plugins {
    id("org.springframework.boot") version "2.7.5"
    id("io.spring.dependency-management") version ("1.0.15.RELEASE")
    java
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.postgresql:postgresql:42.5.0")

    testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.0")
    testImplementation("org.junit.jupiter:junit-jupiter-engine:5.9.0")
    testImplementation("org.assertj:assertj-core:3.23.1")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

group = "kz.lakida.javacourse"
version = "1.0-SNAPSHOT"
description = "course-tasks"
java.sourceCompatibility = JavaVersion.VERSION_17

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
}
