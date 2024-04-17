package Alumno.services

import Alumno.models.Alumno
import org.lighthousegames.logging.logging
import java.util.*

private val logger = logging()

class AlumnosStorageDataBase : AlumnosStorage {


    override fun save(item: Alumno): Alumno {
        logger.debug { "AlumnosStorageDataBase.save() --> $item" }
        println("AlumnosStorageDataBase.save() --> $item")
        return item
    }

    override fun toString() = "AlumnoDataBaseStorage"
}
