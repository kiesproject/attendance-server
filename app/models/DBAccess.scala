package models

import javax.inject._
import play.api.db._
import anorm._
import anorm.SqlParser.scalar

// FIXME: SQL injection 対策する
@Singleton
class DBAccess @Inject()(db: Database) {
  // TODO: insert成功か失敗を返す(Either)
  def insert(name: String, password: String, admin: Boolean) = {
    db.withConnection { implicit c =>
      SQL(s"insert into users(name, password, admin) values('$name', '$password', $admin)").executeInsert()
    }
  }

//  def findNameById(id: Long)(implicit connection:Connection):Opiton[String] = {
//    SQL("SELECT * FROM User WHERE id = {id}").on("id"->id).as(SqlParser.str("name").singleOpt)
//  }

  def exists(name: String, password: String): Boolean = {
    db.withConnection { implicit c =>
      SQL("select exists(select * from users where name = {name} and password = {password})")
        .on('name -> name, 'password -> password)
        .as(scalar[Boolean].single)
//        SQL("select * from users where name = {name} AND password = {password}")
//        .on('name -> name, 'password -> password).as(scalar[Boolean].single)
//          .as(SqlParser.str("name").singleOpt)
//      result.
//      val user = List(query.map { row =>
//        (row[String]("name"), row[String]("password"))
//      })
//      if (user.nonEmpty) true else false
    }
//    ???
  }
}