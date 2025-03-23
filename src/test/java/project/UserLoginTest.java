package project;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;

public class UserLoginTest {

    UserClient userClient;
    User user, incorrectUser;
    UserCredentials userCredentials;
    String accessToken;

    @Before
    public void init() {

        userClient = new UserClient();
        userCredentials = new UserCredentials();
        user = User.getCorrectUser();
        userClient.userRegistration(user);
        accessToken = UserCredentials.getUserAccessToken(user);
    }

    @After
    public void delete() {

        userClient.delete(accessToken);
    }

    @Test
    @DisplayName("Логина курьера")
    public void successLoginUser() {

        Response response = userClient.userLogIn(user);
        response.then()
                .assertThat()
                .statusCode(200)
                .and()
                .body("success", equalTo(true));
    }

    @Test
    @DisplayName("Неуспешный логина курьера")
    public void registrationIncorrectUser() {

        incorrectUser = User.getCorrectUser();

        Response response = userClient.userLogIn(incorrectUser);
        response.then()
                .assertThat()
                .statusCode(401)
                .and()
                .body("success", equalTo(false));
    }
}