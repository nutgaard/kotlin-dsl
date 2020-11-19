import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.transactions.transaction

object Users : Table() {
    val id = integer("id").autoIncrement()
    val name = varchar("name", length = 50)

    override val primaryKey = PrimaryKey(id, name = "PK_User_ID")
}

fun main() {
    val database = Database.connect("jdbc:h2:mem:test", driver = "org.h2.Driver", user = "root", password = "")
    transaction(database) {
        addLogger(StdOutSqlLogger)
        SchemaUtils.create(Users)

        val userId1: Int = Users.insert { it[name] = "Firstname" } get Users.id
        val userId2: Int = Users.insert { it[name] = "Firstname" } get Users.id
        val userId3: Int = Users.insert { it[name] = "Firstname" } get Users.id
        val userId4: Int = Users.insert { it[name] = "Firstname" } get Users.id

        Users.update({ Users.id eq userId1 }) { it[name] = "Firstname Lastname" }
        Users.update({ Users.id eq userId2 }) { it[name] = "Firstname Lastname" }
        Users.update({ Users.id eq userId3 }) { it[name] = "Firstname Lastname" }
        Users.update({ Users.id eq userId4 }) { it[name] = "Firstname Lastname" }
    }
}
