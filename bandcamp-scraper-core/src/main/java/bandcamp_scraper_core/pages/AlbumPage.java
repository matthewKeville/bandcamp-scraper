package bandcamp_scraper_core.pages;


import java.text.ParseException;
import java.util.Optional;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By.ByLinkText;
import org.openqa.selenium.support.locators.RelativeLocator;
import org.openqa.selenium.support.pagefactory.ByChained;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import bandcamp_scraper_core.utils.selenium.DriverUtils;

public class AlbumPage {

  private WebDriver driver;
  private Logger LOG = LoggerFactory.getLogger(AlbumPage.class);

  private By trackTitleLocator= new ByChained(By.id("name-section"),By.className("trackTitle"));
  //May be able to rework with RelativeLocator, but I threw my hands up with it.
  private By digitalAlbumPriceSpanLocator = By.xpath("//button[contains(text(), 'Buy Digital Album')]/following-sibling::span[1]");

  public AlbumPage(WebDriver driver) {
    this.driver = driver;
  }

  //TODO : probably should throw if albumName is null
  public String getAlbumName() throws NoSuchElementException {
    WebElement elmTrackTitle = driver.findElement(trackTitleLocator);
    String albumName = elmTrackTitle.getText();
    return albumName;
  }

  public Optional<Float> getDigitalAlbumPrice() {

    var ecp = DriverUtils.findElmCountPair(driver, digitalAlbumPriceSpanLocator);
    if ( ecp.getCount() == 0 ) {
      return Optional.empty();
    }

    WebElement elmBuyItemDigitalSpan = ecp.getElm();
    if (elmBuyItemDigitalSpan.getText().equals("name your price")) {
      return Optional.of(0f);
    }

    //price should be the first <span> within this <span> if not "name your price"
    try {
      WebElement priceSpan = elmBuyItemDigitalSpan.findElement(By.tagName("span"));
      String priceText = priceSpan.getText();
      return Optional.of(Float.parseFloat(priceText.substring(1)));
      
    } catch (NoSuchElementException | NumberFormatException | IndexOutOfBoundsException ex ) {
        LOG.warn("buyItemDigitalSpan found, but unable to parse price");
        LOG.warn("threw " + ex.getMessage());
        return Optional.empty();
    }


  }


}
