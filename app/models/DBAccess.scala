package models
//
//import javax.inject._
//import play.api.mvc._
//import play.api.db._
//import anorm._
//import anorm.SqlParser._
//
//@Singleton
//class DBAccess @Inject()(db: Database) {
//  def insert(name: String, password: String, admin: Boolean): Option[Long] = {
//    db.withConnection { implicit c =>
//      SQL(s"insert into user(name, password, admin) values('$name', '$password', $admin)").executeInsert()
//    }
//  }
//}