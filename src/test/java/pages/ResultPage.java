package pages;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.By;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import java.time.Duration;
import java.util.List;

public class ResultPage extends BasePage {

    String searchText;

    @FindBy(className = "listview__item")
    List<MobileElement> listViewItems;

    @FindBy(className = "results__control__sort-by-select")
    MobileElement sortDropdown;

    By nameFieldBy = By.className("listview__name-link");
    By priceTextBy = By.className("price");

    public ResultPage(AppiumDriver appiumDriver, String searchstring) {
        this.appiumDriver = appiumDriver;
        PageFactory.initElements(new AppiumFieldDecorator(appiumDriver, Duration.ofSeconds(20L)), this);
        this.searchText = searchstring;
    }

    public void sortByCheapest() {
        Select dropdown = new Select(sortDropdown);
        dropdown.selectByVisibleText("Preis aufsteigend");
    }

    public Float getCheapestPrice() {
        Float cheapestPriceText = null;
        for (MobileElement mobileElement : listViewItems) {
            if (mobileElement.findElement(nameFieldBy).getText().contains(searchText)) {
                String pricestring = mobileElement.findElement(priceTextBy).getText();
                Float price = Float.parseFloat(pricestring.replace(',', '.').split(" ")[1]);
                if (cheapestPriceText == null || cheapestPriceText > price) {
                    cheapestPriceText = price;
                }
            }
        }
        return cheapestPriceText;
    }
}
