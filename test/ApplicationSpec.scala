import org.scalatestplus.play._
import play.api.libs.json.Json
import play.api.test._
import play.api.test.Helpers._

import scala.util.{Failure, Success}
import scala.concurrent.ExecutionContext.Implicits.global

/**
 * Add your spec here.
 * You can mock out a whole application including requests, plugins etc.
 * For more information, consult the wiki.
 */
class ApplicationSpec extends PlaySpec with OneAppPerTest {

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
      val admin = true
      val input =
        s"""{
           |  "name":"$name",
           |  "password":"$password",
           |  "admin":$admin
           |}""".stripMargin
      val json = Json.parse(input)

      val req = route(app, FakeRequest(POST, "/register").withJsonBody(json)).get
      println("bbbbbbbbbbbb")

      req onComplete  {
        case Success(s) => {
          println("aaa")
          status(req) mustBe CREATED
          contentAsJson(req) mustBe
            s"""{
               |  "message": "registered $name"
               |}""".stripMargin
        }
        case Failure(t) => {
          println("asss")
          status(req) mustBe CONFLICT
          contentAsJson(req) mustBe
            s"""{
               |  "code": 409, "message":
               |  "$name is already registered"
               |}""".stripMargin
        }
      }
    }
  }
}

/*req match {
+      case Right(appNexusReq) => {
+        appNexusReq.bcat should contain theSameElementsAs Seq("IAB23-7", "IAB23-5", "IAB23-10", "IAB23-9", "IAB23-1", "IAB7-44", "IAB9-9", "IAB8-18", "IAB8-5")
+      }
+      case Left(appNexusReq) => fail()
+    }*/