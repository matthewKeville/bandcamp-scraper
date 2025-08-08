package bandcamp_scraper_core.pages;

import java.util.Optional;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import bandcamp_scraper_core.utils.parsing.ParsingUtils;
import bandcamp_scraper_core.utils.selenium.DriverUtils;
import bandcamp_scraper_core.utils.selenium.ElmCountPair;
import bandcamp_scraper_models.Track;
import bandcamp_scraper_shared.utils.http.UrlUtils;

public class TrackPage implements RootModelPage<Track> {

  private WebDriver driver;
  private Logger LOG = LoggerFactory.getLogger(TrackPage.class);

  private By trackTitleHeaderLocator = By.className("trackTitle");
  private By timeTotalSpanLocator = By.className("time_total");
  private By trackAlbumTitleHeaderLinks = By.cssSelector("h3.albumTitle a");

  public TrackPage(WebDriver driver) {
    this.driver = driver;
  }

  public String getTrackTitle() throws NoSuchElementException {
    WebElement elmTrackTitleHeader = driver.findElement(trackTitleHeaderLocator);
    String trackTitle = elmTrackTitleHeader.getText();
    return trackTitle;
  }

  public Integer getTrackTime() {
    ElmCountPair ecp = DriverUtils.findElmCountPair(driver, timeTotalSpanLocator);
    if (ecp.getCount() == 0 ) {
      LOG.warn("couldn't locate track duration by : " + timeTotalSpanLocator);
      return null;
    }
    if (ecp.getCount() > 1 ) {
      LOG.warn("unexpected number of elements for : " + timeTotalSpanLocator);
    }

    // the <div inline_player one-track> could have a hidden class
    // in such a case the child span is display: none
    // selenium getText() only returns the visible text
    String durationText = ecp.getElm().getText();
    if (durationText.isEmpty()) {
      LOG.warn("time_total span's visible text is empty, checking the DOM directly");
      durationText = (String) ((JavascriptExecutor) driver).executeScript("return arguments[0].textContent;", ecp.getElm());
    }
    Optional<Integer> duration = ParsingUtils.tryParseDurationInSeconds(durationText);
    return duration.isPresent() ? duration.get() : null;

  }

  /**
   * if track is an album track there will be 2 links and the first
   * is the album link
   */
  public Optional<String> getTrackAlbumUrl() {
    var elmsLink = driver.findElements(trackAlbumTitleHeaderLinks);
    if ( elmsLink.size() == 0 ) {
      LOG.warn("unable to attempt getting album url by " + trackAlbumTitleHeaderLinks);
      return Optional.empty();
    }
    //single
    if ( elmsLink.size() != 2 ) {
      return Optional.empty();
    }
    //album release : should by the first link
    String albumLink = elmsLink.get(0).getAttribute("href");
    if ( UrlUtils.isAlbumURL(albumLink) ) {
      return Optional.of(albumLink);
    }
    return Optional.empty();
    
    
  }

  /**
   * if track is an album track there will be 2 links and the second
   * is the artist link, otherwise the only link is the artist link
   */
  public String getArtistUrl() {
    var elmsLink = driver.findElements(trackAlbumTitleHeaderLinks);
    if ( elmsLink.size() == 0 ) {
      LOG.warn("unable to get artist url by " + trackAlbumTitleHeaderLinks);
      return null;
    }

    //single

    String artistLink;
    if ( elmsLink.size() == 1 ) {
      artistLink = elmsLink.get(0).getAttribute("href");

    //album release

    } else {
      if ( elmsLink.size() > 2 ) {
        LOG.warn("unexpected number of links in " + trackAlbumTitleHeaderLinks);
      }
      artistLink = elmsLink.get(1).getAttribute("href");
    }

    //my domain treats /music as cannonical artist page
    artistLink+="music"; 
    return artistLink;

  }

}
