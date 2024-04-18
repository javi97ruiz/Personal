package Alumno.services

import Alumno.models.Alumno
import org.koin.core.annotation.Named
import org.koin.core.annotation.Singleton
import org.lighthousegames.logging.logging
import java.util.*

private val logger = logging()
@Singleton
@Named("AlumnosStorageFile")
class AlumnosStorageFile : AlumnosStorage {

    override fun save(item: Alumno): Alumno {
        logger.debug { "AlumnosStorageFile.save() --> $item" }
        println("AlumnosStorageFile.save() --> $item")
        return item
    }

    override fun toString() = "PersonaDataBaseStorage"
}
