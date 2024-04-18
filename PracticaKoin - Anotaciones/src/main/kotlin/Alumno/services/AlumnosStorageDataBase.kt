package Alumno.services

import Alumno.models.Alumno
import org.koin.core.annotation.Named
import org.koin.core.annotation.Singleton
import org.lighthousegames.logging.logging
import java.util.*

private val logger = logging()

@Singleton
@Named("AlumnosStorageDataBase")
class AlumnosStorageDataBase : AlumnosStorage {


    override fun save(item: Alumno): Alumno {
        logger.debug { "AlumnosStorageDataBase.save() --> $item" }
        println("AlumnosStorageDataBase.save() --> $item")
        return item
    }

    override fun toString() = "AlumnoDataBaseStorage"
}
