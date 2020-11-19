import io.ktor.application.*
import io.ktor.features.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import org.slf4j.event.Level
import java.io.File

fun main() {
    embeddedServer(factory = Netty, port = 8080) {
        install(CallLogging) {
            level = Level.INFO
        }

        install(CORS) {
            anyHost()
            allowCredentials = true
        }

        routing {
            get("/index.html") {
                call.respondFile(File("index.html"))
            }
        }
    }
}
