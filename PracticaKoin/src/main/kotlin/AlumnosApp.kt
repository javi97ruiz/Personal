package koin.personas

import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.qualifier.named
import Alumno.controllers.AlumnosController
import Alumno.models.Alumno

class AlumnosApp : KoinComponent {
    val contRepoStorageBD: AlumnosController by inject(named("AlumnosDataBaseController"))
    val contRepoStorageFile: AlumnosController by inject(named("AlumnosFileController"))
    val contPorDefecto: AlumnosController by inject()


    fun run() {
        println("Alumnos: Model->Controller->Repository->Storage(Database|File)")
        println("===============================================================")
        val p = Alumno(nombre = "Juan", nota = 8.0)
        println(p)
        println()

        println("Alumnos: Model->Controller->Repository->Storage(Database)")
        println(contRepoStorageBD)
        var resBD = contRepoStorageBD.save(p)
        println("Resultado BD: $resBD")
        println()

        println("Alumnos: Model->Controller->Repository->Storage(File)")
        println(contRepoStorageFile)
        resBD = contRepoStorageFile.save(p)
        println("Resultado File: $resBD")
        println()

        println("Alumnos: Model->Controller->Repository->Storage(Por Defecto)")
        println(contPorDefecto)
        val res = contPorDefecto.save(p)
        println("Resultado Por Defecto: $res")
        println()
    }
}
