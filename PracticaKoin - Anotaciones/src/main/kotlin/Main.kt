package org.example

import koin.personas.AlumnosApp
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.core.context.GlobalContext.startKoin
import org.koin.fileProperties
import org.koin.ksp.generated.defaultModule

fun main() {
    println("Hello Practica Eliminatoria!")

    startKoin {
        // declare used logger
        printLogger()
        // Leemos las propiedades de un fichero
        fileProperties("/config.properties") // Por defecto busca en src/main/resources/koin.properties, pero puede ser otro fichero si se lo pasas como parametro
        // declara modulos de inyección de dependencias, pero lo verificamos antes de inyectarlos
        modules(defaultModule)
    }

    val app = AlumnosApp()
    app.run()
}