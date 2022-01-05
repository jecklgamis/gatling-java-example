package gatling.test.example.simulation;

import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.Simulation;
import io.gatling.javaapi.http.HttpProtocolBuilder;

import java.time.Duration;

import static gatling.test.example.simulation.PerfTestConfig.*;
import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.http;
import static io.gatling.javaapi.http.HttpDsl.status;

public class ExampleSimulation extends Simulation {

    HttpProtocolBuilder httpProtocol = http.baseUrl(BASE_URL)
            .header("Content-Type", "application/json")
            .header("Accept-Encoding", "gzip")
            .check(status().is(200));

    ScenarioBuilder scn = scenario("Root end point calls")
            .exec(http("root end point").post("/").body(StringBody("{}")));

    {
        setUp(scn.injectOpen(constantUsersPerSec(REQUEST_PER_SECOND).during(Duration.ofMinutes(DURATION_MIN))))
                .protocols(httpProtocol)
                .assertions(global().responseTime().percentile3().lt(P95_RESPONSE_TIME_MS),
                        global().successfulRequests().percent().gt(95.0))
        ;
    }
}