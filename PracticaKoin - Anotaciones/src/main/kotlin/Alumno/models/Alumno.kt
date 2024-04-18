package Alumno.models

class Alumno (
    val id: Long = -1,
    val nombre: String,
    val nota: Double,
){
    override fun toString(): String{
        return "Id: $id ,nombre:$nombre ,nota = $nota "
    }
}


