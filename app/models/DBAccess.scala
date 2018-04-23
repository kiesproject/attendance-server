package models
//
////
import javax.inject._
import play.api.mvc._
import play.api.db._
import anorm._
import anorm.SqlParser._
//
////
@Singleton
class DBAccess @Inject()(db: Database) {
  def insert(name: String, password: String, admin: Boolean) = {
    db.withConnection { implicit c =>
      SQL(s"insert into user(name, password, admin) values('$name', '$password', $admin)").executeInsert()
    }
//    val conn = DB.getConnection()
//    try {
//      val stmt = conn.createStatement
////      stmt.executeUpdate("CREATE TABLE IF NOT EXISTS ticks (tick timestamp)")
//      stmt.executeUpdate(s"INSERT INTO users (name, password, admin) VALUES ('$name', '$password', $admin)")
//    } finally {
//      conn.close()
//    }
  }
}
//
////def db = Action {
////  var out = ""
////  val conn = DB.getConnection()
////  try {
////  val stmt = conn.createStatement
////  stmt.executeUpdate("CREATE TABLE IF NOT EXISTS ticks (tick timestamp)")
////  stmt.executeUpdate("INSERT INTO ticks VALUES (now())")
////
////  val rs = stmt.executeQuery("SELECT tick FROM ticks")
////
////  while (rs.next) {
////  out += "Read from DB: " + rs.getTimestamp("tick") + "\n"
////}
////} finally {
////  conn.close()
////}
////  Ok(out)
////}