package models

import javax.inject._
import play.api.db._
import anorm._

// FIXME: SQL injection 対策する
@Singleton
class DBAccess @Inject()(db: Database) {
  // TODO: insert成功か失敗を返す(Either)
  def insert(name: String, password: String, admin: Boolean) = {
    db.withConnection { implicit c =>
      SQL(s"insert into users(name, password, admin) values('$name', '$password', $admin)").executeInsert()
    }
  }

  def exists(name: String, password: String) = {
    db.withConnection { implicit c =>
      val result = SQL(s"select * from users where name = $name AND password = $password").executeQuery()
//      result.
//      val user = List(query.map { row =>
//        (row[String]("name"), row[String]("password"))
//      })
//      if (user.nonEmpty) true else false
    }
    ???
  }
}