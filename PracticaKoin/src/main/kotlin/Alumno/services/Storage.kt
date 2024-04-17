package Alumno.services

interface Storage<T> {
    fun save(item: T): T
}
