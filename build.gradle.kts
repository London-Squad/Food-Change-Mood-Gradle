plugins {
    kotlin("jvm") version "2.1.10"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(project.dependencies.platform("io.insert-koin:koin-bom:4.0.3"))
    implementation("io.insert-koin:koin-core")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.13.0-M2")

}


tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(23)
}