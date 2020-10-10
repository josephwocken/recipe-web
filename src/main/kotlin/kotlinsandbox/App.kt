package kotlinsandbox

import kotlinsandbox.handler.CreateRecipeHandler
import kotlinsandbox.handler.GetAllRecipesHandler
import kotlinsandbox.handler.RecipeHandler
import ratpack.guice.BindingsSpec
import ratpack.guice.Guice
import ratpack.handling.Chain
import ratpack.server.RatpackServer
import ratpack.server.RatpackServerSpec

fun main(args: Array<String>) {
    RatpackServer.start { serverSpec: RatpackServerSpec ->
        serverSpec.registry(
                Guice.registry { bindingsSpec: BindingsSpec ->
                    bindingsSpec.module(AppModule::class.java)
                }
        )
        serverSpec.handlers { chain: Chain ->
            chain.path("recipes", RecipeHandler::class.java)
        }
    }
}
