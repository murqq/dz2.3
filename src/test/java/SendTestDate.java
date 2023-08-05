
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import data.DataGenerator;
import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class SendTestDate {

    SelenideElement form = $(".form");

    @BeforeEach
    void setup() {
        open("http://localhost:9999");

    }

    @Test
    @DisplayName("Should successfully login with active registered user")
    void shouldSuccessfulLoginIfRegisteredActiveUser() {

        var registeredUser = DataGenerator.Registration.getRegisteredUser("active");

        form.$("[data-test-id=login] input").setValue(registeredUser.getLogin());
        form.$("[data-test-id=password] input").setValue(registeredUser.getPassword());
        $("[data-test-id=action-login]").click();
        $("[id=root] .heading").shouldHave(exactText("  Личный кабинет"));

    }

    @Test
    @DisplayName("Should get error message if login with not registered user")
    void shouldGetErrorIfNotRegisteredUser() {
        var notRegisteredUser = DataGenerator.Registration.getUser("active");
        form.$("[data-test-id=login] input").setValue(notRegisteredUser.getLogin());
        form.$("[data-test-id=password] input").setValue(notRegisteredUser.getPassword());
        $("[data-test-id=action-login]").click();
        $("[data-test-id=error-notification] .notification__content").shouldHave(exactText("Ошибка! Неверно указан логин или пароль"));

    }

    @Test
    @DisplayName("Should get error message if login with blocked registered user")
    void shouldGetErrorIfBlockedUser() {
        var blockedUser = DataGenerator.Registration.getRegisteredUser("blocked");

        form.$("[data-test-id=login] input").setValue(blockedUser.getLogin());
        form.$("[data-test-id=password] input").setValue(blockedUser.getPassword());
        $("[data-test-id=action-login]").click();
        $("[data-test-id=error-notification] .notification__content").shouldHave(exactText("Ошибка! Пользователь заблокирован"));

    }

    @Test
    @DisplayName("Should get error message if login with wrong login")
    void shouldGetErrorIfWrongLogin() {
        var registeredUser = DataGenerator.Registration.getRegisteredUser("active");
        var wrongLogin = DataGenerator.getRandomLogin();
        form.$("[data-test-id=login] input").setValue(wrongLogin);
        form.$("[data-test-id=password] input").setValue(registeredUser.getPassword());
        $("[data-test-id=action-login]").click();
        $("[data-test-id=error-notification] .notification__content").shouldHave(exactText("Ошибка! Неверно указан логин или пароль"));

    }

    @Test
    @DisplayName("Should get error message if login with wrong password")
    void shouldGetErrorIfWrongPassword() {
        var registeredUser = DataGenerator.Registration.getRegisteredUser("active");
        var wrongPassword = DataGenerator.getRandomPassword();
        form.$("[data-test-id=login] input").setValue(registeredUser.getLogin());
        form.$("[data-test-id=password] input").setValue(wrongPassword);
        $("[data-test-id=action-login]").click();
        $("[data-test-id=error-notification] .notification__content").shouldHave(exactText("Ошибка! Неверно указан логин или пароль"));

    }
}
