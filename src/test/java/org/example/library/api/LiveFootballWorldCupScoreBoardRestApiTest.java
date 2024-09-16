package org.example.library.api;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.example.library.api.messages.AddNewGameRequest;
import org.example.library.api.messages.UpdateScoreRequest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;

@QuarkusTest
class LiveFootballWorldCupScoreBoardRestApiTest {

    @Test
    void testAddNewGame_Success() {
        AddNewGameRequest request = new AddNewGameRequest("Team A", "Team B");

        given()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post("/games/add")
                .then()
                .statusCode(200)
                .body(containsString("Game added with ID"));
    }

    @Test
    void testAddNewGame_Failure_InvalidTeamNames() {
        AddNewGameRequest request = new AddNewGameRequest("", "");

        given()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post("/games/add")
                .then()
                .statusCode(400)
                .body(containsString("Invalid team names provided"));
    }

    @Test
    void testUpdateScore_Success() {
        AddNewGameRequest addRequest = new AddNewGameRequest("Team A", "Team B");

        String responseMessage = given()
                .contentType(ContentType.JSON)
                .body(addRequest)
                .when()
                .post("/games/add")
                .then()
                .statusCode(200)
                .extract().path("message");

        String gameId = responseMessage.replaceAll("\\D+", "").trim();

        UpdateScoreRequest updateRequest = new UpdateScoreRequest(2, 3);

        given()
                .contentType(ContentType.JSON)
                .body(updateRequest)
                .when()
                .put("/games/" + gameId + "/update-score")
                .then()
                .statusCode(200)
                .body(containsString("Score updated for game ID: " + gameId));
    }

    @Test
    void testUpdateScore_Failure_GameNotFound() {
        UpdateScoreRequest request = new UpdateScoreRequest(2, 3);

        given()
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .put("/games/999/update-score")
                .then()
                .statusCode(404)
                .body(containsString("Game with ID 999 not found"));
    }

    @Test
    void testFinishGame_Success() {
        given()
                .when()
                .delete("/games/1/finish")
                .then()
                .statusCode(200)
                .body(containsString("Game finished with ID: 1"));
    }

    @Test
    void testFinishGame_Failure_GameNotFound() {
        given()
                .when()
                .delete("/games/999/finish")
                .then()
                .statusCode(404)
                .body(containsString("Game with ID 999 not found"));
    }

    @Test
    void testGetGameSummary_Success() {
        given()
                .when()
                .get("/games/summary")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body(containsString("[]"));
    }

    @Test
    void testAddNewGame_Failure_UnsupportedMediaType() {
        given()
                .contentType(ContentType.TEXT)
                .body("{\"homeTeamName\": \"Team A\", \"awayTeamName\": \"Team B\"}")
                .when()
                .post("/games/add")
                .then()
                .statusCode(415);
    }
}
