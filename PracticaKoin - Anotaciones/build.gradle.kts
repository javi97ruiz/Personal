val koin_version: String by project
val logging_version: String by project
val logback_version: String by project


plugins {
    kotlin("jvm") version "1.9.23"
    // Plugin para la generación de código SQLDelight
    id("app.cash.sqldelight") version "2.0.2"
    // Plugin de Koin KSP
    id("com.google.devtools.ksp") version "1.9.23-1.0.20"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    // Koin, con BOM ya se instalan todas las dependencias necesarias con la versión correcta
    implementation(platform("io.insert-koin:koin-bom:$koin_version"))
    implementation("io.insert-koin:koin-core") // Core
    implementation("io.insert-koin:koin-test") // Para test y usar checkModules
    // Logger
    implementation("org.lighthousegames:logging:$logging_version")
    implementation("ch.qos.logback:logback-classic:$logback_version")
    // SQLDelight para SQLite
    implementation("app.cash.sqldelight:sqlite-driver:2.0.2")
    // Result ROP
    implementation("com.michael-bull.kotlin-result:kotlin-result:2.0.0")
    // Para las anotaciones
    implementation(platform("io.insert-koin:koin-annotations-bom:1.3.1")) // BOM
    implementation("io.insert-koin:koin-annotations") // Annotations
    ksp("io.insert-koin:koin-ksp-compiler:1.3.1") // KSP Compiler, debes poner el mismo que el de las anotaciones
    // Test
    testImplementation(kotlin("test"))
    // Koin
    testImplementation("io.insert-koin:koin-test-junit5") // Para test con JUnit5
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}

// Configuración del plugin de SqlDeLight
sqldelight {
    databases {
        // Nombre de la base de datos
        create("AlumnoDatabase") {
            // Paquete donde se generan las clases
            packageName.set("dev.javier.database")
        }
    }
}