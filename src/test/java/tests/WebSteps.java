package tests;

import com.codeborne.selenide.Condition;
import io.qameta.allure.Step;

import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static org.openqa.selenium.By.linkText;


public class WebSteps {

    @Step("Открываем главную страницу")
    public void openMainPage(){
        open("https://github.com");
    }

    @Step("Ищем репозиторий {repo}")
    public void searchForRepository(String repo){
        $(".search-input").click();
        $("#query-builder-test").sendKeys(repo);
        $("#query-builder-test").pressEnter();
    }

    @Step("Кликаем по ссылке репозитория {repo}")
    public void clickOnRepositoryLink(String repo){
        $(linkText(repo)).click();
    }

    @Step("Открываем таб Issues")
    public void openIssueTab(){
        $(linkText("#issues-tab")).click();
    }

    @Step("Проверяем наличие Issues с номером {issue}")
    public void shouldSeeIssueWithNumber(String issue){
        $(withText(issue)).should(Condition.exist);
    }

}
