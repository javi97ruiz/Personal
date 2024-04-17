package koin.personas

import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.binds
import org.koin.core.module.dsl.singleOf
import org.koin.core.qualifier.named
import org.koin.dsl.module
import Alumno.controllers.AlumnosController
import Alumno.repositories.AlumnosRepository
import Alumno.repositories.AlumnosRepositoryImpl
import Alumno.services.AlumnosStorage
import Alumno.services.AlumnosStorageDataBase
import Alumno.services.AlumnosStorageFile
import Config
import SqlDeLightManager



// Modulo que indica cómo son las dependencias.
val AlumnosModule = module {

    // Hay dos formas de repositorio de personas. y le decimos que al principio (para probar)
    // Como trabajamos con interfaces, debemos indicar cuál es la implementación que queremos usar
    // para eso usamos el named
    // llama a PersonasStorageDataBase() o PersonasStorageFile() según se necesite inyectar
    single<AlumnosStorage>(named("DataBaseStorage")) { AlumnosStorageDataBase() }
    single<AlumnosStorage>(named("FileStorage")) { AlumnosStorageFile() }

    // O por defecto, si no se especifica, se usará el DataBaseStorage
    single<AlumnosStorage> { AlumnosStorageDataBase() }

    // Creamos los posibles repositorios de personas, de acuerdo a la implementación de storage
    // Para eso usamos el named
    // llamaremos a PersonasRepositoryImpl() según se necesite inyectar
    single<AlumnosRepository>(named("DataBaseRepository")) { AlumnosRepositoryImpl(get(named("DataBaseStorage"))) }
    single<AlumnosRepository>(named("FileRepository")) { AlumnosRepositoryImpl(get(named("FileStorage"))) }


    // O por defecto, si no se especifica, se usará el DataBaseRepository
    factory<AlumnosRepository> { AlumnosRepositoryImpl(get()) }

    // Creamos el controlador, a demas podemos inyectar cuando queramos cualquier propiedad
    // para saber con que estamos trabajando, por ejemplo, la url del servidor
    // de nuevo usamos el named para indicar cuál es el repositorio que queremos usar
    // llamaremos a PersonasController() según se necesite inyectar
    single(named("AlumnosDataBaseController")) {
        AlumnosController(
            get(named("DataBaseRepository")),
            getProperty("server_url")
        )
    }
    single(named("AlumnosFileController")) {
        AlumnosController(
            get(named("FileRepository")),
            getProperty("server_url")
        )
    }

    // O por defecto
    single { AlumnosController(get(), getProperty("server_url", "no_encontrada")) }

    fun getConfig(): Config {
        return Config()
    }

    fun getSqlDelightManager(): SqlDeLightManager {
        return SqlDeLightManager(
            databaseUrl = getConfig().databaseUrl,
            databaseInMemory = getConfig().databaseInMemory,
            databaseInitData = getConfig().databaseInitData
        )
    }
}

// Otra manera

val AlumnosModule2 = module {
    singleOf(::AlumnosStorageDataBase) {
        // definition options
        named("DataBaseStorage")
        bind<AlumnosStorage>()
        //createdAtStart()
    }
    singleOf(::AlumnosStorageFile) {
        // definition options
        named("FileStorage")
        bind<AlumnosStorage>()
        //createdAtStart()
    }

    singleOf(::AlumnosStorageDataBase) {
        // definition options
        bind<AlumnosStorage>()
    }

    singleOf(::AlumnosRepositoryImpl) {
        // definition options
        named("DataBaseRepository")
        bind<AlumnosRepository>()
        binds(listOf(AlumnosStorageDataBase::class))
        //createdAtStart()
    }

    singleOf(::AlumnosRepositoryImpl) {
        // definition options
        named("FileRepository")
        bind<AlumnosRepository>()
        binds(listOf(AlumnosStorageFile::class))
        //createdAtStart()
    }

    singleOf(::AlumnosRepositoryImpl) {
        // definition options
        bind<AlumnosRepository>()
        binds(listOf(AlumnosStorageDataBase::class))
        //createdAtStart()
    }

    singleOf(::AlumnosController) {
        // definition options
        named("PersonasDataBaseController")
        binds(listOf(AlumnosRepositoryImpl::class))

        //createdAtStart()
    }

    fun getConfig(): Config {
        return Config()
    }

    fun getSqlDelightManager(): SqlDeLightManager {
        return SqlDeLightManager(
            databaseUrl = getConfig().databaseUrl,
            databaseInMemory = getConfig().databaseInMemory,
            databaseInitData = getConfig().databaseInitData
        )
    }



}