package controllers

import javax.inject._
//import models.DBAccess

import play.api.mvc._
import play.api.libs.json._
import play.api.libs.functional.syntax._

/**
  * This controller creates an `Action` to handle HTTP requests to the
  * application's home page.
  */
@Singleton
//class HomeController @Inject()(db: DBAccess) extends Controller {
class HomeController @Inject() extends Controller {

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
