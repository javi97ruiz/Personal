package Alumno.controllers

import Alumno.models.Alumno
import org.lighthousegames.logging.logging
import Alumno.repositories.AlumnosRepository
import java.util.*

private val logger = logging()

// Controlador de Alumnos, que se encarga de gestionar los alumnos
class AlumnosController(
    private val alumnoRepository: AlumnosRepository,
    private val serverUrl: String
) {

    fun save(item: Alumno): Alumno {
        logger.debug { "AlumnosController.save() --> $item" }
        println("AlumnosController.save() --> $item")
        return alumnoRepository.save(item)
    }

    override fun toString() =
        "AlumnoController(alumnoRepository=$alumnoRepository,serverUrl='$serverUrl')"
}
