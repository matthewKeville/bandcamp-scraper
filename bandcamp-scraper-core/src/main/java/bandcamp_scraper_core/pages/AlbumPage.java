package bandcamp_scraper_core.pages;


import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.pagefactory.ByChained;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import bandcamp_scraper_core.exceptions.http.InvalidResourceUrlException;
import bandcamp_scraper_core.utils.http.UrlUtils;
import bandcamp_scraper_core.utils.selenium.DriverUtils;
import bandcamp_scraper_models.Track;
import bandcamp_scraper_models.Album;
import bandcamp_scraper_models.HydratableModel.HydrationStatus;

public class AlbumPage implements RootModelPage<Album>  {

  private WebDriver driver;
  private Logger LOG = LoggerFactory.getLogger(AlbumPage.class);

  private By trackTitleLocator= new ByChained(By.id("name-section"),By.className("trackTitle"));
  //May be able to rework with RelativeLocator, but I threw my hands up with it.
  private By digitalAlbumPriceSpanLocator = By.xpath("//button[contains(text(), 'Buy Digital Album')]/following-sibling::span[1]");
  private By trackRowViewTableRowsLocator = By.cssSelector("tr.track_row_view");

  public AlbumPage(WebDriver driver) {
    this.driver = driver;
  }

  //TODO : probably should throw if albumTitle is null
  public String getAlbumTitle() throws NoSuchElementException {
    WebElement elmTrackTitle = driver.findElement(trackTitleLocator);
    String albumTitle = elmTrackTitle.getText();
    return albumTitle;
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

  public List<Track> getTracks() {

    List<WebElement> elmsTrackRowViewTableRows = driver.findElements(trackRowViewTableRowsLocator);
    List<Track> tracks = new ArrayList<Track>();
    for (WebElement elm : elmsTrackRowViewTableRows) {

      Track.TrackBuilder builder = Track.builder();

      extractTrackLink(elm, builder);

      boolean gotTrackNumber = extractTrackNumber(elm, builder);
      boolean gotTrackTitle = extractTrackTitle(elm, builder);
      boolean gotTrackTime = extractTrackTime(elm, builder);
      if (gotTrackNumber || gotTrackTime || gotTrackTitle) {
        builder.status(HydrationStatus.PARTIAL);
      }

      tracks.add(builder.build());

    }
    return tracks;
  }

  /** 
   * @return : successful extraction
   */
  private boolean extractTrackLink(WebElement elm,Track.TrackBuilder builder) {

    //<a href=${link.text}>...</a>
    By trackLinkLocator = By.cssSelector("a[href]");

    var ecpTrackLink = DriverUtils.findElmCountPairFromElm(elm, trackLinkLocator);
    if (ecpTrackLink.getCount() == 0 ) {
      return false;
    }
    if (ecpTrackLink.getCount() > 1 ) {
      LOG.warn("unexpected numer of elements for " + trackLinkLocator );
    }
    String trackLinkHref = ecpTrackLink.getElm().getAttribute("href");
    if (trackLinkHref == null || trackLinkHref.isEmpty()) {
      return false;
    }

    try {
      if (!UrlUtils.isTrackURL(trackLinkHref)) {
        LOG.warn("Track href found, but failed to validate");
        LOG.warn("Track href : " + trackLinkHref);
        return false;
      }
      builder.origin(trackLinkHref);
    } catch (InvalidResourceUrlException ex) {
      return false;
    }

    return true;

  }

  /** 
   * @return : successful extraction
   */
  private boolean extractTrackNumber(WebElement elm,Track.TrackBuilder builder) {

    // <div class="track_number secondaryText">${d}.</div>
    By trackNumberDivLocator = By.className("track_number");

    var ecpTrackNumberDiv = DriverUtils.findElmCountPairFromElm(elm, trackNumberDivLocator);
    if (ecpTrackNumberDiv.getCount() == 0 ) {
      LOG.warn("can't find " + trackNumberDivLocator);
      return false;
    }
    if (ecpTrackNumberDiv.getCount() > 1 ) {
      LOG.warn("unexpected numer of elements for " + trackNumberDivLocator );
    }
    String trackNumberText = ecpTrackNumberDiv.getElm().getText();
    try {
      Pattern pattern = Pattern.compile("\\b(\\d{1,2})\\.");
      Matcher matcher = pattern.matcher(trackNumberText);
      if (!matcher.find()) {
        LOG.warn("unexpected trackNumber text " + trackNumberText);
        return false;
      }
      int trackNumber = Integer.parseInt(matcher.group(1));
      builder.number(trackNumber);
      return true;
    } catch (NumberFormatException ex) {
      LOG.warn("number parse exception" + ex.getMessage());
      return false;
    }

  }

  /** 
   * @return : successful extraction
   */
  private boolean extractTrackTitle(WebElement elm,Track.TrackBuilder builder) {

    //<span class="track-title">${Track Title}</span> 
    By trackTitleSpanLocator = By.className("track-title");

    var ecpTrackTitleSpan = DriverUtils.findElmCountPairFromElm(elm, trackTitleSpanLocator);
    if (ecpTrackTitleSpan.getCount() == 0 ) {
      LOG.warn("can't find " + trackTitleSpanLocator);
      return false;
    }
    if (ecpTrackTitleSpan.getCount() > 1 ) {
      LOG.warn("unexpected numer of elements for " + trackTitleSpanLocator );
    }
    String trackTitleText = ecpTrackTitleSpan.getElm().getText();
    if (trackTitleText == null || trackTitleText.isEmpty()) {
      LOG.warn("couldn't get track title text");
      return false;
    }
    builder.title(trackTitleText);
    return true;

  }

  /** 
   * @return : successful extraction
   */
  private boolean extractTrackTime(WebElement elm,Track.TrackBuilder builder) {

    //<div class="title">
    //...
    //<span class="time">${DD}:${DD}</span> 
    //</div>
    By trackTimeSpanLocator = By.cssSelector("div.title span.time");

    var ecpTrackTimeSpan = DriverUtils.findElmCountPairFromElm(elm, trackTimeSpanLocator);

    if (ecpTrackTimeSpan.getCount() == 0 ) {
      LOG.warn("unexpected numer of elements for " + trackTimeSpanLocator );
    }

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("mm:ss");
    try {

      String timeText = ecpTrackTimeSpan.getElm().getText();
      Pattern pattern = Pattern.compile("(\\d+):(\\d+)");
      Matcher matcher = pattern.matcher(timeText);
      if (!matcher.find()) {
        LOG.warn("couldn't locate time match in : " + timeText);
        return false;
      }

      int minutes = Integer.parseInt(matcher.group(1));
      int seconds = Integer.parseInt(matcher.group(2));
      int elapsed = minutes * 60 + seconds;

      builder.duration(elapsed);
      return true;

    } catch (NumberFormatException ex) {
      LOG.warn("time parse exception " + ex.getMessage());
      return false;
    }

  }


}
