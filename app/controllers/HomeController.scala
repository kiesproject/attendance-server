package controllers

import javax.inject._
import models.UserRepository
import play.api.Logger
import play.api.libs.functional.syntax._
import play.api.libs.json._
import play.api.mvc._

/**
  * This controller creates an `Action` to handle HTTP requests to the
  * application's home page.
  */
@Singleton
class HomeController @Inject()(userRepository: UserRepository) extends Controller {

  // POSTのbodyをパースする用
//  val rdsRegister: Reads[(String, String, Boolean)] = (
//      (__ \ 'name).read[String] and
//      (__ \ 'password).read[String] and
//      (__ \ 'admin).read[Boolean]
//    ) tupled

  implicit val rds: Reads[(String, String)] = (
      (__ \ 'name).read[String] and
      (__ \ 'password).read[String]
    ) tupled

  val logger = Logger.logger

  /**
    * Create an Action to render an HTML page with a welcome message.
    * The configuration in the `routes` file means that this method
    * will be called when the application receives a `GET` request with
    * a path of `/`.
    */
  def index = Action {
    Ok("Welcome to sabran!!")
  }

  // user登録
  // TODO: SQL Exceptionをキャッチしてエラーの出し分けする．（文字数制限とか，重複とか，）
  def register = Action { request =>
    request.body.asJson.map { json =>
      json.validate.map {
        case (name, password) =>
          if (!userRepository.exists(name)) {
            userRepository.insert(name, password)
            val response = Map("message" -> s"registered $name")
            logger.info("SUCCESS: user created")
            Created(Json.toJson(response))
          } else {
            val response = Map("code" -> JsNumber(409), "message" -> JsString(s"$name is already registered"))
            logger.debug("FAILURE: user not created")
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
    request.body.asJson.map { json =>
      json.validate.map {
        case (name, password) =>
          if (userRepository.exists(name, password)) {
            val response = Map("user" -> name)
            logger.debug("SUCCESS: user login")
            Ok(Json.toJson(response))
          } else {
            val response = Map("code" -> JsNumber(404), "message" -> JsString("email or password is wrong"))
            logger.debug("FAILURE: user not login")
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
