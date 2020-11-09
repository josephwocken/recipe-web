package recipeweb

import com.fasterxml.jackson.databind.ObjectMapper
import com.google.inject.AbstractModule
import com.google.inject.Provides
import com.google.inject.Scopes
import com.google.inject.Singleton
import com.google.inject.name.Named
import com.google.inject.name.Names
import recipeweb.error.RecipeServerErrorHandler
import recipeweb.handler.*
import recipeweb.image.ImageEndpoint
import recipeweb.image.PostImageHandler
import recipeweb.image.ImageService
import recipeweb.recipe.*
import recipeweb.user.UserDao
import recipeweb.user.UserService
import java.io.FileReader
import java.io.IOException
import java.sql.Connection
import java.sql.DriverManager
import java.util.*

class AppModule: AbstractModule() {

    override fun configure() {
        val path = System.getProperty("app.config", "C:\\tmp\\app.properties")
        try {
            val properties = Properties()
            properties.load(FileReader(path))
            Names.bindProperties(binder(), properties)
        } catch (ex: IOException) {
            println("Failed to read in properties from $path" + ex.message)
            throw ex
        }
        bind(RecipeService::class.java).`in`(Scopes.SINGLETON)
        bind(RecipeEndpoint::class.java).`in`(Scopes.SINGLETON)
        bind(GetAllRecipesHandler::class.java).`in`(Scopes.SINGLETON)
        bind(CreateRecipeHandler::class.java).`in`(Scopes.SINGLETON)
        bind(GetRecipeByIdHandler::class.java).`in`(Scopes.SINGLETON)
        bind(DeleteRecipeHandler::class.java).`in`(Scopes.SINGLETON)
        bind(ResponseHeaderHandler::class.java).`in`(Scopes.SINGLETON)
        bind(HealthHandler::class.java).`in`(Scopes.SINGLETON)
        bind(ObjectMapper::class.java).toInstance(MapperUtil.getObjectMapper())
        bind(UserDao::class.java).`in`(Scopes.SINGLETON)
        bind(UserService::class.java).`in`(Scopes.SINGLETON)
        bind(RecipeServerErrorHandler::class.java).`in`(Scopes.SINGLETON)

        // images
        bind(ImageService::class.java).`in`(Scopes.SINGLETON)
        bind(PostImageHandler::class.java).`in`(Scopes.SINGLETON)
        bind(ImageEndpoint::class.java).`in`(Scopes.SINGLETON)
    }

    @Provides
    @Singleton
    fun connection(
            @Named("dbUrl") dbUrl: String,
            @Named("dbUsername") dbUsername: String,
            @Named("dbPassword") dbPassword: String
    ): Connection {
        try {
            return DriverManager.getConnection(
                    dbUrl,
                    dbUsername,
                    dbPassword
            )
        } catch (ex: Exception) {
            println("Failed to get connection to postgres. " + ex.message)
            throw ex
        }
    }

}