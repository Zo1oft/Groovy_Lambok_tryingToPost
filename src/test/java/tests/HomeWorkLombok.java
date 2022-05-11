package tests;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import tests.models.Morpheus;
import tests.models.MorpheusData;
import tests.models.UserData;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static tests.Specs.MorpheusSpecs.requestSpec;
import static tests.Specs.Specs.request;
import static tests.Specs.Specs.responseSpec;

@Data
public class HomeWorkLombok {
    @Test
  public void lombokListTest() {
       UserData data = given()
                .spec(request)
                .when()
                .get("/api/unknown")
                .then()
                .spec(responseSpec)
                    .log().body()
                    .extract().as(UserData.class);
        assertEquals(2, data.getUser().getId());
    }
    @Test
    public void CreateMorpheus () {
            Morpheus morpheus = findMorpheus();

           given()
                    .spec(requestSpec)
                    .body(morpheus)
                    .when()
                    .post("morpheusdata")
                    .then()
                    .spec(responseSpec)
                    .body(matchesJsonSchemaInClasspath("morpheusdata.json"))
                    .statusCode(200)
                    .extract().as(Morpheus.class);
        }

    @Test
  public void listTestWithGroovy() {
       given()
                .spec(request)
                .when()
                .get("/api/unknown")
                .then()
                .spec(responseSpec)
                .log().body()
                .body("data.findAll{it}.year.flatten()",
                        Matchers.hasItem(2005));
    }
}
