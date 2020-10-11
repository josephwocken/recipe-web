package recipeweb

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.google.inject.AbstractModule
import com.google.inject.Scopes
import recipeweb.handler.*
import recipeweb.recipe.RecipeService
import java.sql.Connection
import java.sql.DriverManager

class AppModule: AbstractModule() {

    override fun configure() {
        bind(RecipeService::class.java).`in`(Scopes.SINGLETON)
        bind(GetAllRecipesHandler::class.java).`in`(Scopes.SINGLETON)
        bind(CreateRecipeHandler::class.java).`in`(Scopes.SINGLETON)
        bind(GetRecipeByIdHandler::class.java).`in`(Scopes.SINGLETON)
        bind(RecipeHandler::class.java).`in`(Scopes.SINGLETON)
        bind(ResponseHeaderHandler::class.java).`in`(Scopes.SINGLETON)
        bind(ObjectMapper::class.java).toInstance(objectMapper())
        bind(Connection::class.java).toInstance(connection())
    }

    private fun objectMapper(): ObjectMapper {
        return ObjectMapper()
                .registerModule(KotlinModule())
    }

    private fun connection(): Connection {
        return DriverManager.getConnection(
                "jdbc:postgresql://localhost:5432/postgres",
                "postgres",
                "postgres"
        )
    }

}