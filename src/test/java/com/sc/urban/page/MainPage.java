package com.sc.urban.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.support.FindBy;

import static com.codeborne.selenide.Selenide.*;

public class MainPage {

    @FindBy(className = "searchInput")
    private SelenideElement searchBox;

    public SearchPopup search() {
        searchBox.pressEnter();
        return openSearchPopup();
    }

    public SearchPopup search(String query) {
        searchBox.setValue(query).pressEnter();
        return openSearchPopup();
    }

    private SearchPopup openSearchPopup() {
        return waitUntilVisible(page(SearchPopup.class));
    }

    private SearchPopup waitUntilVisible(SearchPopup popup) {
        popup.getSearchPopup().shouldBe(Condition.visible);
        return popup;
    }

}
