import controllers.HomeController
import models.DBAccess
import org.mockito.Mockito._
import org.scalatest.mockito.MockitoSugar
import org.scalatestplus.play._
import play.api.libs.json.Json
import play.api.test._
import play.api.test.Helpers._


/**
 * Add your spec here.
 * You can mock out a whole application including requests, plugins etc.
 * For more information, consult the wiki.
 */
class ApplicationSpec extends PlaySpec with OneAppPerTest with MockitoSugar {

  "Routes" should {

    "send 404 on a bad request" in  {
      route(app, FakeRequest(GET, "/boum")).map(status(_)) mustBe Some(NOT_FOUND)
    }

  }

  "HomeController" should {

    "render the index page" in {
      val home = route(app, FakeRequest(GET, "/")).get

      status(home) mustBe OK
      contentType(home) mustBe Some("text/plain") // ←なんのテストかわからん
      contentAsString(home) must include ("Welcome to sabran")
    }

    "hoge" in {
      val name = "test3"
      val password = "test"
//      val admin = true
//      val input =
//        s"""{
//           |  "name":"$name",
//           |  "password":"$password",
//           |  "admin":$admin
//           |}""".stripMargin
//      val json = Json.parse(input)

//      val req = route(app, FakeRequest(POST, "/register").withJsonBody(json)).get
//      println("bbbbbbbbbbbb")

      //TODO DBAccessもDatabaseをInjectしてるので，使用するDBもMockに差し替える
      val m = mock[DBAccess]
      when(m.exists(name)).thenReturn(true)
      val input =
        s"""{
           |  "name":"$name",
           |  "password":"$password"
           |}""".stripMargin
      val json = Json.parse(input)

      val controller = new HomeController(m)

      val request = FakeRequest(POST, "/register").withJsonBody(json)
      val actual = controller.register.apply(request)

      status(actual) mustBe OK

//      req onComplete  {
//        case Success(s) => {
//          println("aaa")
//          status(req) mustBe CREATED
//          contentAsJson(req) mustBe
//            s"""{
//               |  "message": "registered $name"
//               |}""".stripMargin
//        }
//        case Failure(t) => {
//          println("asss")
//          status(req) mustBe CONFLICT
//          contentAsJson(req) mustBe
//            s"""{
//               |  "code": 409, "message":
//               |  "$name is already registered"
//               |}""".stripMargin
//        }
//      }
    }
  }
}
