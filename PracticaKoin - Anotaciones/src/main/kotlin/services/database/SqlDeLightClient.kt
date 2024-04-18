package dev.joseluisgs.services.database

import Config
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import database.DatabaseQueries
import dev.javier.database.AlumnoDatabase
import org.koin.core.annotation.Singleton
import org.lighthousegames.logging.logging


private val logger = logging()

@Singleton
class SqlDeLightClient(
    private val config: Config
) {
    val databaseQueries: DatabaseQueries by lazy { initConfig() }
    init {
        logger.debug { "Inicializando el gestor de Bases de Datos con SQLDelight" }
        initConfig()

    }

    private fun initConfig(): DatabaseQueries {
        return if (config.databaseInMemory) {
            logger.debug { "SqlDeLightClient - InMemory" }
            JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY)
        } else {
            logger.debug { "SqlDeLightClient - File: ${config.databaseUrl}" }
            JdbcSqliteDriver(config.databaseUrl)
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