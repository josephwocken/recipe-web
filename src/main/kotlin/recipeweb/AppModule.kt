package recipeweb

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.google.inject.AbstractModule
import com.google.inject.Scopes
import recipeweb.handler.CreateRecipeHandler
import recipeweb.handler.GetAllRecipesHandler
import recipeweb.handler.RecipeHandler

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