package houzz
import io.gatling.core.Predef._
import io.gatling.http.Predef._
import scala.concurrent.duration._
class Houzz3Simulation extends Simulation {

  // val myCookie = Cookie()

  val httpConf = http
    .baseUrl("https://www.houzz3.com") // Here is the root for all relative URLs
    .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9") // Here are the common headers
    .doNotTrackHeader("1")
    .acceptLanguageHeader("en-US;q=0.7,en;q=0.3")
    .acceptEncodingHeader("gzip, deflate, br")
    .userAgentHeader("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/95.0.4638.54 Safari/537.36")
    .header("Cache-Control", "max-age=0")
    .header("connection", "keep-alive")

  val user1 = scenario("some_common_entries")
    .exec(
      http("home").get("/")
    )
    .pause(500 milliseconds)
    .exec(
      http("ideas").get("/photos/home-design-ideas-phbr0-bp~")
    )
    .pause(500 milliseconds)
    .exec(
      http("products_furniture").get("/products/furniture")
    )
    .pause(500 milliseconds)
    .exec(
      http("pro_architect").get("/professionals/architect")
    )
    .pause(2500 milliseconds)

  setUp(
    user1.inject(constantConcurrentUsers(7).during(10.minutes)).protocols(httpConf)
  )

}
