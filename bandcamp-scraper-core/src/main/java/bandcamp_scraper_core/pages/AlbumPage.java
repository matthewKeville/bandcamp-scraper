package bandcamp_scraper_core.pages;


import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.pagefactory.ByChained;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import bandcamp_scraper_core.utils.parsing.ParsingUtils;
import bandcamp_scraper_core.utils.selenium.DriverUtils;
import bandcamp_scraper_shared.exceptions.http.InvalidResourceUrlException;
import bandcamp_scraper_shared.utils.http.UrlUtils;
import bandcamp_scraper_models.Album;

public class AlbumPage implements RootModelPage<Album>  {

  private WebDriver driver;
  private Logger LOG = LoggerFactory.getLogger(AlbumPage.class);

  private By trackTitleLocator= new ByChained(By.id("name-section"),By.className("trackTitle"));
  //May be able to rework with RelativeLocator, but I threw my hands up with it.
  private By digitalAlbumPriceSpanLocator = By.xpath("//button[contains(text(), 'Buy Digital Album')]/following-sibling::span[1]");
  private By trackRowViewTableRowsLocator = By.cssSelector("tr.track_row_view");
  //Parametric By to isolate Track of interest
  private Function<Integer,By> trackRowViewTableRowsByNumberLocator = 
    number -> By.cssSelector(String.format("tr.track_row_view[rel=\"tracknum=%d\"]",number));

  public AlbumPage(WebDriver driver) {
    this.driver = driver;
  }

  public String getOrigin() {
    return driver.getCurrentUrl();
  }

  public String getAlbumTitle() {
    try {
      WebElement elmTrackTitle = driver.findElement(trackTitleLocator);
      return elmTrackTitle.getText();
    } catch (NoSuchElementException ex) {
      LOG.warn("Unable to get Album Title with " + trackTitleLocator);
      return null;
    }
  }

  public Float getDigitalAlbumPrice() {

    var ecp = DriverUtils.findElmCountPair(driver, digitalAlbumPriceSpanLocator);
    if ( ecp.getCount() == 0 ) {
      return null;
    }

    WebElement elmBuyItemDigitalSpan = ecp.getElm();
    if (elmBuyItemDigitalSpan.getText().equals("name your price")) {
      return 0f;
    }

    //price should be the first <span> within this <span> if not "name your price"
    try {
      WebElement priceSpan = elmBuyItemDigitalSpan.findElement(By.tagName("span"));
      String priceText = priceSpan.getText();
      return Float.parseFloat(priceText.substring(1));
      
    } catch (NoSuchElementException | NumberFormatException | IndexOutOfBoundsException ex ) {
        LOG.warn("buyItemDigitalSpan found, but unable to parse price");
        LOG.warn("threw " + ex.getMessage());
        return null;
    }


  }

  /** 
   * Get number of tracks for the album
   *
   * Note, doesn't verify the assumption that the WebElement set
   * maps strictly onto tracks, could use internal verification
   * by finding each track's individual number entry.
   */
  public Integer getTrackCount() {
    List<WebElement> elmsTrackRowViewTableRows = driver.findElements(trackRowViewTableRowsLocator);
    return elmsTrackRowViewTableRows.size();
  }

  public String getTrackUrl(int number) {

    Optional<WebElement> optElmTrack = getTrackWebElm(number);
    if (optElmTrack.isEmpty()) {
      return null;
    }

    //<a href=${link.text}>...</a>
    By trackLinkLocator = By.cssSelector("a[href]");
    var ecpTrackLink = DriverUtils.findElmCountPairFromElm(optElmTrack.get(), trackLinkLocator);
    if (ecpTrackLink.getCount() == 0 ) {
      return null;
    }
    if (ecpTrackLink.getCount() > 1 ) {
      LOG.warn("unexpected numer of elements for " + trackLinkLocator );
    }
    String trackLinkHref = ecpTrackLink.getElm().getAttribute("href");
    if (trackLinkHref == null || trackLinkHref.isEmpty()) {
      return null;
    }

    try {
      if (!UrlUtils.isTrackURL(trackLinkHref)) {
        LOG.warn("Track href found, but failed to validate");
        LOG.warn("Track href : " + trackLinkHref);
        return null;
      }
      return trackLinkHref;
    } catch (InvalidResourceUrlException ex) {
      return null;
    }

  }


  public String getTrackTitle(int number) {

    Optional<WebElement> optElmTrack = getTrackWebElm(number);
    if (optElmTrack.isEmpty()) {
      return null;
    }

    //<span class="track-title">${Track Title}</span> 
    By trackTitleSpanLocator = By.className("track-title");

    var ecpTrackTitleSpan = DriverUtils.findElmCountPairFromElm(optElmTrack.get(), trackTitleSpanLocator);
    if (ecpTrackTitleSpan.getCount() == 0 ) {
      LOG.warn("can't find " + trackTitleSpanLocator);
      return null;
    }
    if (ecpTrackTitleSpan.getCount() > 1 ) {
      LOG.warn("unexpected numer of elements for " + trackTitleSpanLocator );
    }
    String trackTitleText = ecpTrackTitleSpan.getElm().getText();
    if (trackTitleText == null || trackTitleText.isEmpty()) {
      LOG.warn("couldn't get track title text");
      return null;
    }
    return trackTitleText;

  }

  public Integer getTrackTime(int number) {

    Optional<WebElement> optElmTrack = getTrackWebElm(number);
    if (optElmTrack.isEmpty()) {
      return null;
    }

    //<div class="title">
    //...
    //<span class="time">${DD}:${DD}</span> 
    //</div>
    By trackTimeSpanLocator = By.cssSelector("div.title span.time");

    var ecpTrackTimeSpan = DriverUtils.findElmCountPairFromElm(optElmTrack.get(), trackTimeSpanLocator);

    if (ecpTrackTimeSpan.getCount() == 0 ) {
      LOG.warn("unexpected numer of elements for " + trackTimeSpanLocator );
    }

    String timeText = ecpTrackTimeSpan.getElm().getText();
    Optional<Integer> elapsed = ParsingUtils.tryParseDurationInSeconds(timeText);
    if (elapsed.isEmpty()) {
      return null;
    }
    return elapsed.get();

  }

  private Optional<WebElement> getTrackWebElm(int number) {
    var ecp = DriverUtils.findElmCountPair(driver,trackRowViewTableRowsByNumberLocator.apply(number));
    if (ecp.getCount() > 0) {
      return Optional.of(ecp.getElm());
    }
    return Optional.empty();
  }

}
