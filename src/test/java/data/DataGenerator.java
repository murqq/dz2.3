package data;
import com.github.javafaker.Faker;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.specification.RequestSpecification;
import lombok.Value;

import static io.restassured.RestAssured.given;

import java.util.ArrayList;
import java.util.List;

import io.restassured.http.ContentType;

public class DataGenerator {
    static Faker faker = new Faker();

    private DataGenerator() {
    }

    private static final RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBaseUri("http://localhost")
            .setPort(9999)
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();


    static void sendRequest(RegistrationDto user) {
        given()
                .spec(requestSpec)
                .body(user)
                .when()
                .post("/api/system/users")
                .then()
                .statusCode(200);
    }

    public static String getRandomLogin() {

        String login = String.valueOf(faker.name().firstName());
        return login;
    }

    public static String getRandomPassword() {

        String password = String.valueOf(faker.internet().password());
        return password;
    }

    public static class Registration {
        private Registration() {
        }

        public static RegistrationDto getUser(String status) {

            RegistrationDto user = new RegistrationDto(getRandomLogin(), getRandomPassword(), status);
            return user;
        }

        public static RegistrationDto getRegisteredUser(String status) {

            RegistrationDto registeredUser = getUser(status);
            sendRequest(registeredUser);

            return registeredUser;
        }
    }

    @Value
    public static class RegistrationDto {
        String login;
        String password;
        String status;

    }

}