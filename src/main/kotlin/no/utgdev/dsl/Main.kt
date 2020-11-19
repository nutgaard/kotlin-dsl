import DataSource.Companion.dataSource

interface DataSourceConfig<CONFIG, TYPE> {
    fun initialize(block: CONFIG.() -> Unit): TYPE
}

interface DataSource {
    fun sql(query: String): List<String>

    companion object {
        fun <CONFIG, TYPE> dataSource(type: DataSourceConfig<CONFIG, TYPE>, block: CONFIG.() -> Unit): TYPE {
            return type.initialize(block)
        }
    }
}


class Database(val config: Config) : DataSource {
    override fun sql(query: String): List<String> {
        return listOf(requireNotNull(config.url))
    }

    class Config(var url: String? = null)
    companion object Source : DataSourceConfig<Config, Database> {
        override fun initialize(block: Config.() -> Unit) = Database(Config().apply(block))
    }
}

class Mock(val config: Config) : DataSource {
    override fun sql(query: String) = requireNotNull(config.data)

    class Config(var data: List<String>? = null)
    companion object Source : DataSourceConfig<Config, Mock> {
        override fun initialize(block: Config.() -> Unit) = Mock(Config().apply(block))
    }
}

fun main() {
    val dbSource = dataSource(Database) {
        url = "jdbc://url.til.databasen"
    }

    val mockSource = dataSource(Mock) {
        data = listOf("Hei", "på", "deg")
    }

    println(dbSource.sql("SELECT * FROM table"))
    println(mockSource.sql("SELECT * FROM table"))
}

/*
Features in use for DSLs;
Extension function
Infix call
Operator overloading
Lambdas outside of paranetheses
Lambdas with receivers

 Videre lesing;
 - https://github.com/zsmb13/VillageDSL
 - https://www.grokkingandroid.com/creating-kotlin-dsls/
 - https://medium.com/@antonarhipov/awesome-kotlin-domain-specific-languages-f1870be41b0
 - https://www.ximedes.com/2020-04-21/kotlin-dsl-tutorial/



 */
