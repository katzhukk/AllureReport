package tests;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.logevents.SelenideLogger.step;
import static org.openqa.selenium.By.linkText;


public class AllureStepTest {

    private static final String REPOSITORY = "eroshenkoam/allure-example";
    private static final String ISSUENAME = "hello";

    @DisplayName("Тест на проверку названия Issue в репозитории чистым Selenide (с Listener)")
    @Test
    public void testIssueSearch(){
        SelenideLogger.addListener("allure", new AllureSelenide());

        open("https://github.com");
        $(".search-input").click();
        $("#query-builder-test").sendKeys("eroshenkoam/allure-example");
        $("#query-builder-test").pressEnter();
        $(linkText("eroshenkoam/allure-example")).click();
        $(linkText("#issues-tab")).click();
        $(withText("hello")).should(Condition.exist);
    }

    @DisplayName("Тест на проверку названия Issue в репозитории Лямбда шагами через step")
    @Test
    public void testLambdaSearch(){
        SelenideLogger.addListener("allure", new AllureSelenide());

        step("Открываем главную страницу", () -> {
            open("https://github.com");
        });
        step("Ищем репозиторий " + REPOSITORY, () -> {
            $(".search-input").click();
            $("#query-builder-test").sendKeys(REPOSITORY);
            $("#query-builder-test").pressEnter();
        } );
        step("Кликаем по ссылке репозитория " + REPOSITORY, () -> {
            $(linkText(REPOSITORY)).click();
        });
        step("Открываем таб Issues", () -> {
            $(linkText("#issues-tab")).click();
        });
        step("Проверяем наличие Issues с именем hello", () -> {
            $(withText(ISSUENAME)).should(Condition.exist);
        });
    }

    @DisplayName("Тест на проверку названия Issue в репозитории шагами с аннотацией @Step")
    @Test
    public void testAnnotatedStep(){
        SelenideLogger.addListener("allure", new AllureSelenide());
        WebSteps steps = new WebSteps();

        steps.openMainPage();
        steps.searchForRepository(REPOSITORY);
        steps.clickOnRepositoryLink(REPOSITORY);
        steps.openIssueTab();
        steps.shouldSeeIssueWithNumber(ISSUENAME);
    }
}
