package project;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.apache.commons.lang3.RandomUtils.nextInt;

public class Fillings extends ApiClient{

    public ArrayList<String> fillings;

    private static final String INGREDIENTS_PATH = "ingredients";

    public Fillings(ArrayList<String> ingredients) {

        this.fillings = ingredients;
    }


    @Step("Создание бургера с начинками")
    public static Fillings getRandomBurger() {

        ValidatableResponse response =
                given()
                .spec(getSpecification())
                .when()
                .get(INGREDIENTS_PATH)
                .then()
                .statusCode(200);

        ArrayList<String> fillings = new ArrayList<>();

        List<String> bunIngredient = response.extract().jsonPath().getList("data.findAll{it.type =='bun'}._id");
        List<String> sauceIngredient = response.extract().jsonPath().getList("data.findAll{it.type =='sauce'}._id");
        List<String> mainIngredient = response.extract().jsonPath().getList("data.findAll{it.type =='main'}._id");

        fillings.add(bunIngredient.get(nextInt(0,bunIngredient.size())));
        fillings.add(sauceIngredient.get(nextInt(0,sauceIngredient.size())));
        fillings.add(mainIngredient.get(nextInt(0,mainIngredient.size())));

        return new Fillings(fillings);
    }

    @Step("Создание бургера без начинок")
    public static Fillings getEmptyBurger() {

        ArrayList<String> fillings = new ArrayList<>();

        return new Fillings(fillings);
    }

    @Step("Создать бургер с рандомной начинокой")
    public static Fillings getIncorrectBurger() {

        ArrayList<String> ingredients = new ArrayList<>();
        String someIngredient = (RandomStringUtils.randomAlphabetic(3));

        ingredients.add(someIngredient);

        return new Fillings(ingredients);
    }

}
