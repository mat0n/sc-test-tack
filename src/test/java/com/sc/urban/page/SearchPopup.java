package com.sc.urban.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class SearchPopup {

    private static final String SEARCH_POPUP = ".search-popup";
    private static final String SEARCH_POPUP_TEXT = SEARCH_POPUP + "__text";
    private static final String SEARCH_IN_POPUP = "#search-in-popup";
    private static final String SEARCH_POPUP_TABS = ".search-popup-tabs__tab";
    private static final String SEARCH_POPUP_BUTTON = SEARCH_POPUP + "__button";
    private static final String SEARCH_POPUP_CLEAR = SEARCH_POPUP + "__clear";
    private static final String SEARCH_POPUP_SETTING = ".search-popup__setting";
    private static final String SEARCH_POPUP_SETTING_CHECKBOXES = SEARCH_POPUP_SETTING + " .c-checkbox__input";
    private static final String SEARCH_POPUP_SETTING_CHECKBOX_LIVE = SEARCH_POPUP_SETTING + " #checkbox_1";
    private static final String SEARCH_POPUP_SETTING_CHECKBOX_SPORTS = SEARCH_POPUP_SETTING + " #checkbox_2";
    private static final String SEARCH_POPUP_CLOSE = SEARCH_POPUP + "__close";
    private static final String SEARCH_POPUP_EVENTS = ".search-popup-events__item";
    private static final String SEARCH_POPUP_MIC = SEARCH_POPUP + "__mic";
    private static final String SEARCH_POPUP_NOTHING_FIND = SEARCH_POPUP + "__nothing-find";
    private static final String SEARCH_POPUP_COEF_ITEMS = ".search-popup-coef";

    public SearchPopup clickCloseButton() {
        getCloseButton().click();
        return this;
    }

    public SearchPopup clickLiveCheckbox() {
        getLiveCheckbox().click();
        return this;
    }

    public SearchPopup clickSportsCheckbox() {
        getSportsCheckbox().click();
        return this;
    }

    public SearchPopup clickTabMatches() {
        getTabMatches().click();
        return this;
    }

    public SearchPopup clickTabLeagues() {
        getTabLeagues().click();
        return this;
    }

    public SearchPopup typeTextInSearchField(String text) {
        getSearchField().setValue(text);
        return this;
    }

    public SearchPopup clickSearchButton() {
        getSearchButton().click();
        return this;
    }

    public SearchPopup clickSearchClearButton() {
        getSearchClearButton().click();
        return this;
    }

    public boolean isDisplayed() {
        return getSearchPopup().isDisplayed();
    }

    public SelenideElement getSearchPopup() {
        return $(SEARCH_POPUP);
    }

    public SelenideElement getSearchPopupText() {
        return $(SEARCH_POPUP_TEXT);
    }

    public SelenideElement getSearchField() {
        return $(SEARCH_IN_POPUP);
    }

    public ElementsCollection getTabs() {
        return $$(SEARCH_POPUP_TABS);
    }

    public SelenideElement getTabMatches() {
        return getTabs().find(Condition.text("Matches"));
    }

    public SelenideElement getTabLeagues() {
        return getTabs().find(Condition.text("Leagues"));
    }

    public SelenideElement getSearchButton() {
        return $(SEARCH_POPUP_BUTTON);
    }

    public SelenideElement getSearchClearButton() {
        return $(SEARCH_POPUP_CLEAR);
    }

    public ElementsCollection getCheckboxes() {
        return $$(SEARCH_POPUP_SETTING_CHECKBOXES);
    }

    public SelenideElement getLiveCheckbox() {
        return $(SEARCH_POPUP_SETTING_CHECKBOX_LIVE);
    }

    public SelenideElement getSportsCheckbox() {
        return $(SEARCH_POPUP_SETTING_CHECKBOX_SPORTS);
    }

    public SelenideElement getCloseButton() {
        return $(SEARCH_POPUP_CLOSE);
    }

    public ElementsCollection getEvents() {
        return $$(SEARCH_POPUP_EVENTS);
    }

    public SelenideElement getMic() {
        return $(SEARCH_POPUP_MIC);
    }

    public SelenideElement getNothingFind() {
        return $(SEARCH_POPUP_NOTHING_FIND);
    }

    public ElementsCollection getCoefItems() {
        return $$(SEARCH_POPUP_COEF_ITEMS);
    }

}
