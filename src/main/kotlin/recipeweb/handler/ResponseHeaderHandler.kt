package recipeweb.handler

import ratpack.handling.Context
import ratpack.handling.Handler

class ResponseHeaderHandler: Handler {

    override fun handle(ctx: Context) {
        ctx.response.headers
                .add("Access-Control-Allow-Origin", "*")
                .add("Access-Control-Allow-Headers", "Content-Type, Accept, Access-Control-Allow-Headers, Authorization, X-Requested-With")
                .add("Access-Control-Allow-Credentials", "true")
        ctx.next()
    }
}