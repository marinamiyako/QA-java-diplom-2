package project;

import io.qameta.allure.Step;
import org.apache.commons.lang3.RandomStringUtils;

public class User extends ApiClient{
    public String email;
    public String password;
    public String name;

    public User(String email, String password, String name) {

        this.email = email;
        this.password = password;
        this.name = name;
    }

    @Step("Создание корректных данных пользователя")
    public static User getCorrectUser() {

        final String email = (RandomStringUtils.randomAlphabetic(10) + "@ya.ru").toLowerCase();
        final String password = RandomStringUtils.randomAlphabetic(10).toLowerCase();
        final String name = RandomStringUtils.randomAlphabetic(10).toLowerCase();

        return new User(email, password, name);
    }

    @Step("Создание пользователя без имени")
    public static User getUserWithoutName() {

        final String email = RandomStringUtils.randomAlphabetic(10) + "@ya.ru";
        final String password = RandomStringUtils.randomAlphabetic(10);

        return new User(email, password, null);
    }

    @Step("Создание пользователя без пароля")
    public static User getUserWithoutPassword() {

        final String email = RandomStringUtils.randomAlphabetic(10) + "@ya.ru";
        final String name = RandomStringUtils.randomAlphabetic(10);

        return new User(email, null, name);
    }

    @Step("Создание пользователя без почты")
    public static User getUserWithoutEmail() {

        final String password = RandomStringUtils.randomAlphabetic(10);
        final String name = RandomStringUtils.randomAlphabetic(10);

        return new User(null, password, name);
    }
}
