package recipeweb

import com.fasterxml.jackson.databind.ObjectMapper
import ratpack.guice.BindingsSpec
import ratpack.guice.Guice
import ratpack.handling.Chain
import ratpack.server.RatpackServer
import ratpack.server.RatpackServerSpec
import ratpack.server.ServerConfigBuilder
import recipeweb.handler.HealthHandler
import recipeweb.handler.ResponseHeaderHandler
import recipeweb.image.ImageEndpoint
import recipeweb.recipe.RecipeEndpoint

fun main(args: Array<String>) {
    RatpackServer.start { serverSpec: RatpackServerSpec ->
        serverSpec.registry(
                Guice.registry { bindingsSpec: BindingsSpec ->
                    bindingsSpec.module(AppModule::class.java)
                    bindingsSpec.bindInstance(ObjectMapper::class.java, MapperUtil.getObjectMapper())
                }
        )
        serverSpec.serverConfig { serverConfigBuilder: ServerConfigBuilder ->
            serverConfigBuilder.port(5050)
            //NOT sure if this does anything useful
            serverConfigBuilder.configureObjectMapper {
                MapperUtil.getObjectMapper()
            }
        }
        serverSpec.handlers { chain: Chain ->
            chain.all(ResponseHeaderHandler::class.java)
            chain.get("health", HealthHandler::class.java)
            chain.prefix("recipes", RecipeEndpoint::class.java)
            chain.prefix("images", ImageEndpoint::class.java)
        }
    }
}
