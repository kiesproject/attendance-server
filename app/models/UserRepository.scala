package models

import javax.inject._
import play.api.db._
import anorm._
import anorm.SqlParser.scalar

trait UserRepository {
  def insert(name: String, password: String)
  def exists(name: String, password: String): Boolean
  def exists(name: String): Boolean
}

@Singleton
class UserRepositoryOnPostgres @Inject()(db: Database) extends UserRepository {
  // TODO: insert成功か失敗を返す(Either)
  def insert(name: String, password: String) = {
    db.withConnection { implicit c =>
      SQL("insert into users(name, password, admin) values({name}, {password}, {admin})")
        .on('name -> name, 'password -> password, 'admin -> false)
        .executeInsert()
    }
  }

  def exists(name: String, password: String): Boolean = {
    db.withConnection { implicit c =>
      SQL("select exists(select * from users where name = {name} and password = {password})")
        .on('name -> name, 'password -> password)
        .as(scalar[Boolean].single)
    }
  }

  def exists(name: String): Boolean = {
    db.withConnection { implicit c =>
      SQL("select exists(select * from users where name = {name})")
        .on('name -> name)
        .as(scalar[Boolean].single)
    }
  }
}