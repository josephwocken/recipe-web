package recipeweb.handler

import ratpack.handling.Context
import ratpack.handling.Handler

class HealthHandler: Handler {
    override fun handle(ctx: Context) {
        ctx.response.status(200).send()
    }
}