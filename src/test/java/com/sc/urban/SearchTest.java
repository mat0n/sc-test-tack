package com.sc.urban;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.SelenideElement;
import com.sc.urban.page.MainPage;
import com.sc.urban.page.SearchPopup;
import io.qameta.allure.Step;
import org.apache.commons.lang3.RandomStringUtils;
import org.assertj.core.api.SoftAssertions;
import org.testng.annotations.*;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static org.assertj.core.api.Assertions.assertThat;

public class SearchTest {

    private static final String OPPA_URI = "https://www.oppabet.com/";
    private static final String BROWSER = "chrome";

    private MainPage mainPage;

    @BeforeClass
    public void beforeClass() {
        Configuration.browser = BROWSER;
        Configuration.startMaximized = true;
    }

    @BeforeMethod
    public void before() {
        mainPage = open(OPPA_URI, MainPage.class);
    }

    @AfterMethod
    public void after() {
        closeWebDriver();
    }

    @Step("Check search with search string {searchString}")
    @Test(dataProvider = "searchTestDp")
    public void searchTest(String searchString) {
        SearchPopup searchPopup = mainPage.search(searchString);

        searchPopup.getEvents()
                .shouldHave(CollectionCondition.sizeGreaterThan(0));
        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(searchPopup.getSearchPopup().isDisplayed())
                .as("Search popup is displayed")
                .isTrue();
        softly.assertThat(searchPopup.getSearchPopupText().getText())
                .as("'EVENTS FOUND' value")
                .isNotEmpty()
                .isEqualTo(String.valueOf(searchPopup.getEvents().size()));
        softly.assertThat(searchPopup.getSearchField().getValue())
                .as("Search field value")
                .isEqualTo(searchString);
        softly.assertThat(searchPopup.getEvents().filter(and("", visible, not(empty))))
                .as("Events text values")
                .isNotEmpty()
                .extracting(SelenideElement::getText)
                .anySatisfy(text -> assertThat(text).containsIgnoringCase(searchString));
        softly.assertThat(searchPopup.getCoefItems())
                .as("Any coef element is displayed")
                .hasSizeGreaterThan(0)
                .anyMatch(SelenideElement::isDisplayed);
        softly.assertThat(searchPopup.getMic().isDisplayed())
                .as("Microphone button is displayed")
                .isTrue();
        softly.assertThat(searchPopup.clickCloseButton().isDisplayed())
                .as("Close popup button")
                .isFalse();
        softly.assertAll();
    }

    @Step("Negative search check with search string {searchString}")
    @Test(dataProvider = "searchTestNegativeDp")
    public void searchTestNegative(String searchString) {
        SearchPopup searchPopup = mainPage.search(searchString);

        searchPopup.getEvents()
                .shouldHave(CollectionCondition.empty);
        searchPopup.getNothingFind()
                .shouldHave(and("visible and have text", visible, text("No results")));
        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(searchPopup.getSearchPopup().isDisplayed())
                .as("Search popup is displayed")
                .isTrue();
        softly.assertThat(searchPopup.getSearchPopupText().getText())
                .as("'EVENTS FOUND' value")
                .isNotEmpty()
                .isEqualTo(String.valueOf(searchPopup.getEvents().size()));
        softly.assertThat(searchPopup.getSearchField().getValue())
                .as("Search field value")
                .isEqualTo(searchString);
        softly.assertThat(searchPopup.getEvents().filter(visible))
                .as("Events text values")
                .isEmpty();
        softly.assertThat(searchPopup.clickCloseButton().isDisplayed())
                .as("Close popup button")
                .isFalse();
        softly.assertAll();
    }

    @Step("Check search input field with search string {searchString}")
    @Test(dataProvider = "searchTestDp")
    public void searchInputTest(String searchString) {
        SearchPopup searchPopup = mainPage.search();

        searchPopup
                .typeTextInSearchField(searchString)
                .clickSearchButton();

        searchPopup.getEvents()
                .shouldHave(CollectionCondition.sizeGreaterThan(0));
        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(searchPopup.getSearchPopupText().getText())
                .as("'EVENTS FOUND' value")
                .isNotEmpty()
                .isEqualTo(String.valueOf(searchPopup.getEvents().size()));
        softly.assertThat(searchPopup.getSearchField().getValue())
                .as("Search field value")
                .isNotEmpty()
                .isEqualTo(searchString);
        softly.assertAll();

        searchPopup
                .clickSearchClearButton()
                .clickSearchButton();

        searchPopup.getEvents().shouldHaveSize(0);
        softly = new SoftAssertions();
        softly.assertThat(searchPopup.getSearchPopupText().getText())
                .as("'EVENTS FOUND' value")
                .isNotEmpty()
                .isEqualTo(String.valueOf(searchPopup.getEvents().size()));
        softly.assertThat(searchPopup.getSearchField().getValue())
                .as("Search field value")
                .isEmpty();
        softly.assertAll();
    }

    @Step("Check search tabs")
    @Test
    public void searchTabsTest() {
        SearchPopup searchPopup = mainPage.search();

        searchPopup.getTabs()
                .shouldHaveSize(2);
        searchPopup.getTabMatches()
                .shouldHave(attributeMatching("class", ".*search-popup-tabs__tab--active"));
        searchPopup.getTabLeagues()
                .shouldHave(attributeMatching("class", "search-popup-tabs__tab"));

        assertThat(searchPopup.getTabs())
                .as("Search tabs is visible")
                .allSatisfy(tab -> tab.shouldBe(visible));
    }

    @Step("Check search setting checkboxes in tab 'Matches' with search string {searchString}")
    @Test(dataProvider = "searchTestDp")
    public void searchSettingsTestMatches(String searchString) {
        SearchPopup searchPopup = mainPage.search(searchString);

        searchPopup.getEvents()
                .shouldHave(CollectionCondition.sizeGreaterThan(0));
        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(searchPopup.getSearchPopup().isDisplayed())
                .as("Search popup is displayed")
                .isTrue();
        softly.assertThat(searchPopup.getSearchPopupText().getText())
                .as("'EVENTS FOUND' value")
                .isNotEmpty()
                .isEqualTo(String.valueOf(searchPopup.getEvents().size()));
        softly.assertThat(searchPopup.getCheckboxes())
                .as("Search checkboxes")
                .hasSize(2)
                .allSatisfy(element -> assertThat(element.isSelected()));
        softly.assertAll();

        searchPopup.clickLiveCheckbox();

        searchPopup.getEvents()
                .shouldHave(CollectionCondition.sizeGreaterThan(0));
        softly = new SoftAssertions();
        softly.assertThat(searchPopup.getLiveCheckbox().isSelected())
                .as("Search 'Live' checkbox")
                .isFalse();
        softly.assertThat(searchPopup.getSearchPopupText().getText())
                .as("'EVENTS FOUND' value")
                .isEqualTo(String.valueOf(searchPopup.getEvents().size()));
        softly.assertAll();

        searchPopup
                .clickLiveCheckbox()
                .clickSportsCheckbox();

        searchPopup.getEvents()
                .shouldHave(CollectionCondition.sizeGreaterThan(0));
        softly = new SoftAssertions();
        softly.assertThat(searchPopup.getEvents().filter(and("visible and not empty", visible, not(empty))))
                .as("'LIVE' events labels")
                .isNotEmpty()
                .isEqualTo(searchPopup.getEvents().filterBy(Condition.textCaseSensitive("LIVE")));
        softly.assertThat(searchPopup.getSportsCheckbox().isSelected())
                .as("Search 'Sports' checkbox")
                .isFalse();
        softly.assertThat(searchPopup.getSearchPopupText().getText())
                .as("'EVENTS FOUND' value")
                .isEqualTo(String.valueOf(searchPopup.getEvents().size()));
        softly.assertAll();

        searchPopup.clickLiveCheckbox();

        searchPopup.getEvents().shouldHaveSize(0);
        softly = new SoftAssertions();
        softly.assertThat(searchPopup.getLiveCheckbox().isSelected())
                .as("Search 'Live' checkbox")
                .isFalse();
        softly.assertThat(searchPopup.getSportsCheckbox().isSelected())
                .as("Search 'Sports' checkbox")
                .isFalse();
        softly.assertAll();
    }

    @Step("Check search setting checkboxes in tab 'Leagues' with search string {searchString}")
    @Test(dataProvider = "searchTestDp")
    public void searchSettingsTestLeagues(String searchString) {
        SearchPopup searchPopup = mainPage.search(searchString);

        searchPopup
                .clickTabLeagues()
                .getEvents()
                .shouldHave(CollectionCondition.sizeGreaterThan(0));
        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(searchPopup.getSearchPopup().isDisplayed())
                .as("Search popup is displayed")
                .isTrue();
        softly.assertThat(searchPopup.getSearchPopupText().getText())
                .as("'EVENTS FOUND' value")
                .isNotEmpty()
                .isEqualTo(String.valueOf(searchPopup.getEvents().size()));
        softly.assertThat(searchPopup.getCheckboxes())
                .as("Search checkboxes")
                .hasSize(2)
                .allSatisfy(element -> assertThat(element.isSelected()));
        softly.assertAll();

        searchPopup.clickLiveCheckbox();

        searchPopup.getEvents()
                .shouldHave(CollectionCondition.sizeGreaterThan(0));
        softly = new SoftAssertions();
        softly.assertThat(searchPopup.getLiveCheckbox().isSelected())
                .as("Search 'Live' checkbox")
                .isFalse();
        softly.assertThat(searchPopup.getSearchPopupText().getText())
                .as("'EVENTS FOUND' value")
                .isEqualTo(String.valueOf(searchPopup.getEvents().size()));
        softly.assertAll();

        searchPopup
                .clickLiveCheckbox()
                .clickSportsCheckbox();

        searchPopup.getEvents()
                .shouldHave(CollectionCondition.sizeGreaterThan(0));
        softly = new SoftAssertions();
        softly.assertThat(searchPopup.getEvents().filter(and("", visible, not(empty))))
                .as("'LIVE' events labels")
                .isNotEmpty()
                .isEqualTo(searchPopup.getEvents().filterBy(Condition.textCaseSensitive("LIVE")));
        softly.assertThat(searchPopup.getSportsCheckbox().isSelected())
                .as("Search 'Sports' checkbox")
                .isFalse();
        softly.assertThat(searchPopup.getSearchPopupText().getText())
                .as("'EVENTS FOUND' value")
                .isEqualTo(String.valueOf(searchPopup.getEvents().size()));
        softly.assertAll();


        searchPopup.clickLiveCheckbox();

        searchPopup.getEvents().shouldHaveSize(0);
        softly = new SoftAssertions();
        softly.assertThat(searchPopup.getLiveCheckbox().isSelected())
                .as("Search 'Live' checkbox")
                .isFalse();
        softly.assertThat(searchPopup.getSportsCheckbox().isSelected())
                .as("Search 'Sports' checkbox")
                .isFalse();
        softly.assertAll();
    }

    @DataProvider
    public Object[][] searchTestDp() {
        return new Object[][]{
                {"football"},
                {"bask"}
        };
    }

    @DataProvider
    public Object[][] searchTestNegativeDp() {
        return new Object[][]{
                {""},
                {"*"},
                {RandomStringUtils.randomAlphabetic(256)}
        };
    }

}
