import controllers.HomeController
import models.UserRepository
import org.mockito.Mockito._
import org.scalatest.mockito.MockitoSugar
import org.scalatestplus.play._
import play.api.libs.json.Json
import play.api.test.Helpers._
import play.api.test._


/**
 * Add your spec here.
 * You can mock out a whole application including requests, plugins etc.
 * For more information, consult the wiki.
 */
class ApplicationSpec extends PlaySpec with OneAppPerTest with MockitoSugar {

  "Routes" should {

    "send 404 on a bad request" in  {
      route(app, FakeRequest(GET, "/boum")).map(status) mustBe Some(NOT_FOUND)
    }

  }

  "HomeController" should {

    "render the index page" in {
      val home = route(app, FakeRequest(GET, "/")).get

      status(home) mustBe OK
      contentType(home) mustBe Some("text/plain") // ←なんのテストかわからん
      contentAsString(home) must include ("Welcome to sabran")
    }

    "login success" in {
      val name = "test"
      val password = "test"

      val mockRepository = mock[UserRepository]
      when(mockRepository.exists(name, password)).thenReturn(true)

      val input =
        s"""{
           |  "name":"$name",
           |  "password":"$password"
           |}""".stripMargin
      val json = Json.parse(input)

      val controller = new HomeController(mockRepository)

      val request = FakeRequest(POST, "/login").withJsonBody(json)
      val actual = controller.login.apply(request)

      status(actual) mustBe OK
    }

    "register success" in {
      val name = "test"
      val password = "test"

      val mockRepository = mock[UserRepository]
      when(mockRepository.exists(name)).thenReturn(false)

      val input =
        s"""{
           |  "name":"$name",
           |  "password":"$password"
           |}""".stripMargin
      val json = Json.parse(input)

      val controller = new HomeController(mockRepository)

      val request = FakeRequest(POST, "/register").withJsonBody(json)
      val actual = controller.register.apply(request)

      status(actual) mustBe CREATED
    }


    "login faild" in {
      val name = "test"
      val password = "test"

      val mockRepository = mock[UserRepository]
      when(mockRepository.exists(name, password)).thenReturn(false)

      val input =
        s"""{
           |  "name":"$name",
           |  "password":"$password"
           |}""".stripMargin
      val json = Json.parse(input)

      val controller = new HomeController(mockRepository)

      val request = FakeRequest(POST, "/login").withJsonBody(json)
      val actual = controller.login.apply(request)

      status(actual) mustBe NOT_FOUND
    }

    "register faild" in {
      val name = "test"
      val password = "test"

      val mockRepository = mock[UserRepository]
      when(mockRepository.exists(name)).thenReturn(true)

      val input =
        s"""{
           |  "name":"$name",
           |  "password":"$password"
           |}""".stripMargin
      val json = Json.parse(input)

      val controller = new HomeController(mockRepository)

      val request = FakeRequest(POST, "/register").withJsonBody(json)
      val actual = controller.register.apply(request)

      status(actual) mustBe CONFLICT
    }
  }
}
