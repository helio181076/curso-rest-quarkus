package io.github.dougllasfps.quarkussocial.rest;

import io.github.dougllasfps.quarkussocial.rest.dto.CreateUserRequest;
import io.github.dougllasfps.quarkussocial.rest.dto.ResponseError;
import io.quarkus.test.common.http.TestHTTPResource;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import jakarta.json.bind.JsonbBuilder;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.net.URL;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
class UserResourceTest {

    @TestHTTPResource("/users")
    URL apiURL;

    @Test
    @DisplayName("Should create an user successfully.")
    public void createUserTest(){
        var createUserRequest = new CreateUserRequest();
        createUserRequest.setName("Fulano");
        createUserRequest.setAge(30);

        var response = given().contentType(ContentType.JSON)
                .body(JsonbBuilder.create().toJson(createUserRequest))
             .when()
                .post(apiURL)
             .then()
                .extract().response();

        assertEquals(201, response.statusCode());
        assertNotNull(response.jsonPath().getString("id"));

    }

    @Test
    @DisplayName("Should return error when json is not valid.")
    public void createUserValidationErrorTest(){
        var createUserRequest = new CreateUserRequest();
        createUserRequest.setName(null);
        createUserRequest.setAge(null);

        var response = given().contentType(ContentType.JSON)
                .body(JsonbBuilder.create().toJson(createUserRequest))
                .when()
                .post(apiURL)
                .then()
                .extract().response();

        assertEquals(ResponseError.UNPROCESSABLE_ENTITY_STATUS, response.statusCode());
        assertEquals("Validation Error", response.jsonPath().getString("message"));

        List<Map<String, String>> errors = response.jsonPath().getList("errors");
        assertNotNull(errors.get(0).get("message"));
        assertNotNull(errors.get(1).get("message"));
//        assertEquals("Age is Required", errors.get(0).get("message"));
//        assertEquals("Name is Required", errors.get(1).get("message"));
    }

    @Test
    @DisplayName("Should list all users.")
    public void listAllUsersTest(){
        given().contentType(ContentType.JSON)
                .when()
                    .get(apiURL)
                .then()
                .statusCode(200)
                .body("size()", Matchers.is(1));
    }
}