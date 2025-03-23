package project;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;

public class UserRegistrationTest {

    User user;
    UserClient userClient;
    UserCredentials userCredentials;
    String accessToken;

    @Before
    public void init() {

        userClient = new UserClient();
        userCredentials = new UserCredentials();
        user = User.getCorrectUser();
        accessToken = UserCredentials.getUserAccessToken(user);
    }

    @After
    public void delete() {

        String accessToken = UserCredentials.getUserAccessToken(user);
        userClient.delete(accessToken);
    }

    @Test
    @DisplayName("Успешная регистрации пользователя")
    public void successRegistrationUser() {

        Response response = userClient.userRegistration(user);
        response.then()
                .assertThat()
                .statusCode(200)
                .and()
                .body("success", equalTo(true));
    }

    @Test
    @DisplayName("Неуспешная регистрации пользователя без обязательного поля 'Name'")
    public void failRegistrationUserWithoutRequiredName() {

        User user = User.getUserWithoutName();
        Response response = userClient.userRegistration(user);
        response.then()
                .assertThat()
                .statusCode(403)
                .and()
                .body("message", equalTo("Email, password and name are required fields"));
    }

    @Test
    @DisplayName("Неуспешная регистрация пользователя без обязательного поля 'Email'")
    public void failRegistrationUserWithoutRequiredEmail() {

        User user = User.getUserWithoutEmail();
        Response response = userClient.userRegistration(user);
        response.then()
                .assertThat()
                .statusCode(403)
                .and()
                .body("message", equalTo("Email, password and name are required fields"));
    }

    @Test
    @DisplayName("Неуспешная регистрация пользователя без обязательного поля 'Password'")
    public void failRegistrationUserWithoutRequiredPassword() {

        User user = User.getUserWithoutPassword();
        Response response = userClient.userRegistration(user);
        response.then()
                .assertThat()
                .statusCode(403)
                .and()
                .body("message", equalTo("Email, password and name are required fields"));
    }

    @Test
    @DisplayName("Неуспешная регистрация уже существующего пользователя")
    public void failRegistrationExistedUser() {

        User user = User.getCorrectUser();
        userClient.userRegistration(user);
        Response response = userClient.userRegistration(user);
        response.then()
                .assertThat()
                .statusCode(403)
                .and()
                .body("success", equalTo(false));
    }
}
