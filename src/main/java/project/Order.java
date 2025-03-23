package project;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class Order extends ApiClient{

    @Step("Создать заказ с токеном в хедере")
    public static Response createOrderWithAuth(Fillings fillings, String accessToken) {

        return given()
                .spec(getSpecification())
                .headers(
                        "Authorization", "Bearer " + accessToken,
                        "Content-Type",
                        ContentType.JSON,
                        "Accept",
                        ContentType.JSON)
                .body(fillings)
                .when()
                .post("/orders");
    }

    @Step("Создать заказ без токена")
    public static Response createOrderWithOutAuth(Fillings fillings) {

        return given()
                .spec(getSpecification())
                .body(fillings)
                .when()
                .post("/orders");
    }

    @Step("Получить список заказов пользователя с токено доутспа в хедере")
    public static Response getOrdersOfUserWithAuth(String authentication) {

        return given()
                .headers(
                        "Authorization", "Bearer " + authentication,
                        "Content-Type",
                        ContentType.JSON,
                        "Accept",
                        ContentType.JSON)
                .spec(getSpecification())
                .when()
                .get("/orders");
    }

    @Step("Получить список заказов пользователя без токена доступа")
    public static Response getOrdersOfUserWithoutAuth() {

        return given()
                .spec(getSpecification())
                .when()
                .get("/orders");
    }
}
