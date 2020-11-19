import java.lang.StringBuilder

interface Element {
    fun render(builder: StringBuilder, indent: String)
}
class TextElement(val text: String): Element {
    override fun render(builder: StringBuilder, indent: String) {
        builder.append("$indent$text\n")
    }
}

abstract class Tag(val type: String) : Element {
    val attributes = mutableMapOf<String, String>()
    val children = mutableListOf<Element>()

    protected fun <T : Element> addChild(element: T, block: T.() -> Unit): T {
        element.apply(block)
        children.add(element)
        return element
    }

    fun text(text: String) {
        children.add(TextElement(text))
    }
    operator fun String.unaryPlus() = text(this)

    override fun render(builder: StringBuilder,indent: String) {
        builder.append("$indent<$type${renderAttributes()}>\n")
        for (child in children) {
            child.render(builder, "    $indent")
        }
        builder.append("$indent</$type>\n")
    }

    private fun renderAttributes(): String {
        val builder = StringBuilder()
        for ((key, value) in attributes) {
            builder.append(" $key=\"$value\"")
        }
        return builder.toString()
    }

    override fun toString(): String = StringBuilder()
            .also { render(it, "") }
            .toString()
}

class Html : Tag("html") {
    fun head(block: Head.() -> Unit) = addChild(Head(), block)
}
class Head : Tag("head") {
    fun title(block: Title.() -> Unit) = addChild(Title(), block)
}
class Title : Tag("title")

fun html(block: Html.() -> Unit) = Html().apply(block)

fun main() {
    val markup = html {
        head {
            title {
                text("Min kule nettside")
                +"Min kule nettside 2.0"
            }
//            link("http://dummy.io/index.css") {
//                rel = "stylesheet"
//            }
//            script("http://dummy.io/index.js") {
//                type = "application/javascript"
//            }
        }
//        body {
//            h1 {
//                "Hello, world"
//            }
//            p {
//                "Dette er en fin fine side"
//                b {
//                    "Join us"
//                }
//            }
//            a("http://dummy.io/byebye") {
//                "Bye bye"
//            }
//        }
    }


//    Html(
//            head = Head(
//                    title = ""
//            )
//            body = Body(
//
//            )
//    )

    println(markup)
}
