import Mote.Companion.dato
import Mote.Companion.mote

data class Dato(val ar: Int, val maned: Int, val dag: Int) {
    class Builder(var ar: Int? = null, var maned: Int? = null, var dag: Int? = null)

    companion object {
        fun dato(block: Builder.() -> Unit): Dato {
            val builder = Builder().apply(block)
            return Dato(
                    ar = requireNotNull(builder.ar) { "År må være satt for dato" },
                    maned = requireNotNull(builder.maned),
                    dag = requireNotNull(builder.dag)
            )
        }
    }
}

data class Mote(val tema: String, val dato: Dato) {
    class Builder(var tema: String? = null, var dato: Dato? = null)

    companion object {
        fun mote(block: Builder.() -> Unit): Mote {
            val builder = Builder().apply(block)
            return Mote(
                    requireNotNull(builder.tema),
                    requireNotNull(builder.dato)
            )
        }

        fun Builder.dato(block: Dato.Builder.() -> Unit) {
            this.dato = Dato.dato(block)
        }
    }
}

fun main() {
    val faggruppemote = mote {
        tema = "Kotlin DSLs"
        dato {
            ar = 2020
            maned = 11
            dag = 25
        }
    }

    val fgmote = Mote(
            tema = "Kotlin DSLs",
            dato = Dato(
                    ar = 2020,
                    maned = 11,
                    dag = 25
            )
    )

    println(faggruppemote)
    println(fgmote)
}
