package recipeweb

import recipeweb.handler.RecipeHandler
import ratpack.guice.BindingsSpec
import ratpack.guice.Guice
import ratpack.handling.Chain
import ratpack.server.RatpackServer
import ratpack.server.RatpackServerSpec
import ratpack.server.ServerConfigBuilder
import recipeweb.handler.GetRecipeByIdHandler
import recipeweb.handler.HealthHandler
import recipeweb.handler.ResponseHeaderHandler

fun main(args: Array<String>) {
    RatpackServer.start { serverSpec: RatpackServerSpec ->
        serverSpec.registry(
                Guice.registry { bindingsSpec: BindingsSpec ->
                    bindingsSpec.module(AppModule::class.java)
                }
        )
        serverSpec.serverConfig { serverConfigBuilder: ServerConfigBuilder ->
            serverConfigBuilder.port(5050)
        }
        serverSpec.handlers { chain: Chain ->
            chain.all(ResponseHeaderHandler::class.java)
            chain.get("health", HealthHandler::class.java)
            chain.get("recipes/:id", GetRecipeByIdHandler::class.java)
            chain.path("recipes", RecipeHandler::class.java)
        }
    }
}
