package dev.joseluisgs.services.database

import Config
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import database.DatabaseQueries
import dev.Javier.database.AlumnoDatabase


import koin.personas.AlumnosModule
import org.lighthousegames.logging.logging

private val logger = logging()

object SqlDeLightClient {
    lateinit var databaseQueries: DatabaseQueries
    val config = AlumnosModule
    init {
        logger.debug { "Inicializando el gestor de Bases de Datos con SQLDelight" }
        initConfig()

    }

    private fun initConfig() {

        databaseQueries = if (Config().databaseInMemory) {
            logger.debug { "SqlDeLightClient - InMemory" }
            JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY)
        } else {
            logger.debug { "SqlDeLightClient - File: ${Config().databaseUrl}" }
            JdbcSqliteDriver(Config().databaseUrl)
        }.let { driver ->
            // Creamos la base de datos
            logger.debug { "Creando Tablas (si es necesario)" }
            AlumnoDatabase.Schema.create(driver)
            AlumnoDatabase(driver)
        }.databaseQueries

    }




    // limpiamos las tablas
    private fun removeAllData() {
        logger.debug { "SqlDeLightClient.removeAllData()" }
        databaseQueries.transaction {
            databaseQueries.removeAllAlumnos()

        }
    }
}