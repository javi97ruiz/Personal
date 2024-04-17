package Alumno.repositories

import Alumno.models.Alumno
import org.lighthousegames.logging.logging
import Alumno.services.AlumnosStorage
import java.util.*

private val logger = logging()

class AlumnosRepositoryImpl(
    private val storage: AlumnosStorage
) : AlumnosRepository {


    override fun save(entity: Alumno): Alumno {
        logger.debug { "AlumnosRepository.save() -->$entity" }
        println("AlumnosRepository.save() -->$entity")
        return storage.save(entity)
    }

    override fun toString() = "AlumnosRepository(storage=$storage)"

}
