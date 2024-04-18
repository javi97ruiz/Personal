
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import database.DatabaseQueries
import dev.javier.database.AlumnoDatabase
import org.lighthousegames.logging.logging

private val logger = logging()

class SqlDeLightManager(
    private val databaseUrl: String,
    private val databaseInMemory: Boolean,
    private val databaseInitData: Boolean,
) {
    val databaseQueries: DatabaseQueries by lazy { initQueries() }

    init {
        logger.debug { "Inicializando el gestor de Bases de Datos con SQLDelight" }
        // Inicializamos datos de ejemplo, si se ha configurado
        initialize()
    }

    private fun initQueries(): DatabaseQueries {

        return if (databaseInMemory) {
            logger.debug { "SqlDeLightClient - InMemory" }
            JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY)
        } else {
            logger.debug { "SqlDeLightClient - File: ${databaseUrl}" }
            JdbcSqliteDriver(databaseUrl)
        }.let { driver ->
            // Creamos la base de datos
            logger.debug { "Creando Tablas (si es necesario)" }
            AlumnoDatabase.Schema.create(driver)
            AlumnoDatabase(driver)
        }.databaseQueries

    }

    fun initialize() {
        if (databaseInitData) {
            removeAllData()
        }
    }

    // limpiamos las tablas
    private fun removeAllData() {
        logger.debug { "SqlDeLightClient.removeAllData()" }
        databaseQueries.transaction {
            databaseQueries.removeAllAlumnos()
        }
    }
}