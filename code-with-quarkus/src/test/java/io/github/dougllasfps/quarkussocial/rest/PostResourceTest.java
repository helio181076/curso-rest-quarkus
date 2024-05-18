package io.github.dougllasfps.quarkussocial.rest;

import io.github.dougllasfps.quarkussocial.domain.model.User;
import io.github.dougllasfps.quarkussocial.domain.repository.UserRepository;
import io.github.dougllasfps.quarkussocial.rest.dto.CreatePostRequest;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import jakarta.inject.Inject;
import jakarta.json.bind.JsonbBuilder;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
@TestHTTPEndpoint(PostResource.class)
class PostResourceTest {
    @Inject
    UserRepository userRepository;
    Long userId;

    @BeforeEach
    @Transactional
    public void setUP(){
        var user = new User();
        user.setAge(30);
        user.setName("Fulano");
        userRepository.persist(user);
        userId = user.getId();
    }
    @Test
    @DisplayName("should create a post for a user")
    public void createPostTest(){
        var createPostRequest = new CreatePostRequest();
        createPostRequest.setText("Some text");


        given()
                .contentType(ContentType.JSON)
                .body(JsonbBuilder.create().toJson(createPostRequest))
                .pathParam("userId", userId)
             .when()
                .post()
             .then()
                .statusCode(201);
    }

    @Test
    @DisplayName("should return 404 when trying to make a post for an inexistent user")
    public void postForAnInexistentUserTest(){
        var createPostRequest = new CreatePostRequest();
        createPostRequest.setText("Some text");

        var inexistentUserId = 999;

        given()
                .contentType(ContentType.JSON)
                .body(JsonbBuilder.create().toJson(createPostRequest))
                .pathParam("userId", inexistentUserId)
             .when()
                .post()
             .then()
                .statusCode(404);
    }

    @Test
    @DisplayName("should return 404 when user doesn´t exist")
    public void listPostUserNotFoundTest(){

    }

    @Test
    @DisplayName("should return 400 when followerId header is not present")
    public void listPostFollowerHeaderNotSendTest(){

    }

    @Test
    @DisplayName("should return 400 when follower doesn´t exist")
    public void listPostFollowerNotFoundTest(){

    }

    @Test
    @DisplayName("should return 403 when follower isn´t a follower")
    public void listPostNotAFollower(){

    }

    @Test
    @DisplayName("should return posts")
    public void listPostsTest(){

    }


}