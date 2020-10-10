package kotlinsandbox

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.google.inject.AbstractModule
import com.google.inject.Scopes
import kotlinsandbox.handler.CreateRecipeHandler
import kotlinsandbox.handler.GetAllRecipesHandler
import kotlinsandbox.handler.RecipeHandler

class AppModule: AbstractModule() {

    override fun configure() {
        bind(RecipeService::class.java).`in`(Scopes.SINGLETON)
        bind(GetAllRecipesHandler::class.java).`in`(Scopes.SINGLETON)
        bind(CreateRecipeHandler::class.java).`in`(Scopes.SINGLETON)
        bind(RecipeHandler::class.java).`in`(Scopes.SINGLETON)
        bind(ObjectMapper::class.java).toInstance(objectMapper())
    }

    private fun objectMapper(): ObjectMapper {
        return ObjectMapper()
                .registerModule(KotlinModule())
    }

}