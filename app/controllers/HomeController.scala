package controllers

import javax.inject._
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
  val rdsRegister: Reads[(String, String, Boolean)] = (
      (__ \ 'name).read[String] and
      (__ \ 'password).read[String] and
      (__ \ 'admin).read[Boolean]
    ) tupled

  val rdsLogin: Reads[(String, String)] = (
      (__ \ 'name).read[String] and
      (__ \ 'password).read[String]
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
  // TODO: Logを吐かせましょう
  // TODO: SQL Exceptionをキャッチしてエラーの出し分けする．（文字数制限とか，重複とか，）
  // TODO: nameの重複チェックをして，重複していなかったら登録する，それ以外はエラーを返す
  def register = Action { request =>
    request.body.asJson.map { json =>
      json.validate(rdsRegister).map {
        case (name, password, admin) =>
          if (!db.exists(name)) {
            db.insert(name, password, admin)
            val response = Map("message" -> s"registered $name")
            Created(Json.toJson(response))
          } else {
            val response = Map("code" -> JsNumber(409), "message" -> JsString(s"$name is already registered"))
            Conflict(Json.toJson(response))
          }
      }.recoverTotal {
        e => BadRequest("Detected error:" + JsError.toFlatJson(e))
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
      json.validate(rdsLogin).map {
        case (name, password) =>
          if (db.exists(name, password)) {
            val response = Map("user" -> name)
            Ok(Json.toJson(response))
          } else {
            val response = Map("code" -> JsNumber(404), "message" -> JsString("email or password is wrong"))
            NotFound(Json.toJson(response))
          }
      }.recoverTotal {
        e => BadRequest("Detected error:" + JsError.toFlatJson(e))
      }
    }.getOrElse {
      BadRequest("Expecting Json data")
    }
  }
}
