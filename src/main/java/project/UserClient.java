package project;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class UserClient extends ApiClient{

    @Step("Регистрация курьера")
    public Response userRegistration(User user) {

        return given()
                .spec(getSpecification())
                .and()
                .body(user)
                .when()
                .post("/auth/register");
    }

    @Step("Логин курьера")
    public Response userLogIn(User user) {

        return given()
                .spec(getSpecification())
                .when()
                .and()
                .body(user)
                .post("/auth/login");
    }

    @Step("Редактирование курьера с указанием токена доступа")
    public Response userEdit(User user, String authentication) {

        return given()
                .headers(
                        "Authorization", "Bearer " + authentication,
                        "Content-Type",
                        ContentType.JSON,
                        "Accept",
                        ContentType.JSON)
                .spec(getSpecification())
                .when()
                .and()
                .body(user)
                .patch("/auth/user");
    }

    @Step("Редактирование курьера без токена доступа")
    public Response userEditWithOutAuth(User user) {

        return given()
                .spec(getSpecification())
                .when()
                .and()
                .body(user)
                .patch("/auth/user");
    }

    @Step("Разлогин курьера")
    public void userLogOut(String token) {

        given()
                .spec(getSpecification())
                .when()
                .and()
                .body(token)
                .post("/auth/logout");
    }

    @Step("Регистрация и логин клиента")
    public void userRegistrationAndLogin(User user) {

        userRegistration(user);
        userLogIn(user);
    }

    @Step("Удаление клиента")
    public void userDelete(String authentication) {

        given()
                .headers(
                        "Authorization", "Bearer " + authentication,
                        "Content-Type",
                        ContentType.JSON,
                        "Accept",
                        ContentType.JSON)
                .spec(getSpecification())
                .when()
                .delete("auth/user")
                .then()
                .statusCode(202);
    }

    public void delete(String accessToken) {
    }
}
