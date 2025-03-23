package project;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class GetOrderTest {

    private UserClient userClient;

    User user;
    Order order;
    String accessToken;

    @Before
    public void init() {
        UserCredentials userCredentials = new UserCredentials();
        userClient = new UserClient();
        user = User.getCorrectUser();
        order = new Order();
        userClient.userRegistration(user);
        accessToken = UserCredentials.getUserAccessToken(user);
    }

    @After
    public void delete() {
        userClient.delete(accessToken);
    }

    @Test
    @DisplayName("Получение заказов пользователя с авторизацией")
    public void getUserOrdersWithAuth() {

        String token = UserCredentials.getUserAccessToken(user);
        Response response = Order.getOrdersOfUserWithAuth(token);
        response.then()
                .assertThat()
                .statusCode(200)
                .and()
                .body("success", equalTo(true))
                .body("orders.total", notNullValue());
    }

    @Test
    @DisplayName("Получение заказов пользователя без авторизации")
    public void getUserOrdersWithoutAuth() {

        Response response = Order.getOrdersOfUserWithoutAuth();
        response.then()
                .assertThat()
                .statusCode(401)
                .and()
                .body("success", equalTo(false))
                .body("message", equalTo("You should be authorised"));
    }
}
