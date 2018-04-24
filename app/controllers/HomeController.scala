package controllers

import javax.inject._
import play.api.db.DB
import models.DBAccess

import play.api.mvc._
import play.api.libs.json._
import play.api.libs.functional.syntax._

/**
  * This controller creates an `Action` to handle HTTP requests to the
  * application's home page.
  */
@Singleton
class HomeController @Inject()(db: DBAccess) extends Controller {

  // POSTのbodyをパースする用
  implicit val rds: Reads[(String, String, Boolean)] = (
    (__ \ 'name).read[String] and
    (__ \ 'password).read[String] and
    (__ \ 'admin).read[Boolean]
  ) tupled

  /**
    * Create an Action to render an HTML page with a welcome message.
    * The configuration in the `routes` file means that this method
    * will be called when the application receives a `GET` request with
    * a path of `/`.
    */
  def index = Action {
    Ok("Hello~ hello~")
  }

  // user登録
  // TODO: nameの重複チェックをして，重複していなかったら登録する，それ以外はエラーを返す
  def register = Action { request =>
    request.body.asJson.map { json =>
      json.validate[(String, String, Boolean)].map{
        case (name, password, admin) =>
          db.insert(name, password, admin)
          Ok("Hello " + name)
      }.recoverTotal{
        e => BadRequest("Detected error:"+ JsError.toFlatJson(e))
      }
    }.getOrElse {
      BadRequest("Expecting Json data")
    }
  }

  // userログイン
  def login = Action { request =>
    /*bodyからnameを取得
    * nameがDBにあるか調べる
    * あるならログインする
    * ないならログインできない，エラーを返す*/
    request.body.asJson.map { json =>
      json.validate[(String, String, Boolean)].map{
        case (name, password, admin) =>
          if (db.exists(name, password)) {
            Ok(s"login $name")
          } else {
            BadRequest("email or password is wrong")
          }

      }.recoverTotal{
        e => BadRequest("Detected error:"+ JsError.toFlatJson(e))
      }
    }.getOrElse {
      BadRequest("Expecting Json data")
    }
    ???
  }

  //  def insert(name: String, pass: String, admin: Boolean) = Action {
//  def insert = Action { request =>
//    request.body.asJson.map { json =>
//      json.validate[(String, String, Boolean)].map{
//        case (name, password, admin) =>
//          db.insert(name, password, admin)
//          Ok("Hello " + name)
//      }.recoverTotal{
//        e => BadRequest("Detected error:"+ JsError.toFlatJson(e))
//      }
//    }.getOrElse {
//      BadRequest("Expecting Json data")
//    }
//  }
}
