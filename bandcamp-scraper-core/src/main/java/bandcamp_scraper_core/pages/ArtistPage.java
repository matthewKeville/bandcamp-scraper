package bandcamp_scraper_core.pages;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import bandcamp_scraper_core.exceptions.http.InvalidResourceUrlException;
import bandcamp_scraper_core.utils.http.UrlUtils;
import bandcamp_scraper_core.utils.selenium.DriverUtils;
import bandcamp_scraper_models.Artist;
import bandcamp_scraper_models.Release;

public class ArtistPage implements RootModelPage<Artist> {

  private WebDriver driver;
  private Logger LOG = LoggerFactory.getLogger(ArtistPage.class);

  private By elmMetaTagSiteNameLocator = By.xpath("//meta[@property='og:site_name']");
  private By musicGridLocator = By.id("music-grid");
  private By rightColumnLocator = By.id("rightColumn");
  private By bandNameLocationLocator = By.id("band-name-location");

  public ArtistPage(WebDriver driver) {
    this.driver = driver;
  }

  //TODO : probably should throw if artistName is null
  public String getArtistName() throws NoSuchElementException {
    WebElement elmMetaTagSiteName = driver.findElement(elmMetaTagSiteNameLocator);
    String artistName = elmMetaTagSiteName.getDomAttribute("content");
    return artistName;
  }

  public Set<Release> getReleasesItems() {

    try {

      var ecp = DriverUtils.findElmCountPair(driver,musicGridLocator);

      if ( ecp.getCount() == 0 ) {
        return Collections.emptySet();
      }

      if (ecp.getCount() > 1 ) {
        LOG.warn("found multiple #music-grid elements");
      }

      WebElement elmMusicGrid = ecp.getElm();
      List<WebElement> elmsReleaseLink = elmMusicGrid.findElements(By.cssSelector("li a"));

      String artistBaseUrl = UrlUtils.getArtistBaseUrl(driver.getCurrentUrl());

      Set<Release> releaseItems = elmsReleaseLink.stream()
        .map( elmA -> elmA.getDomAttribute("href"))
        .filter( href -> href != null )
        .map(href -> artistBaseUrl + href)
        .map(href -> Release.tryCreateFromHref(href))
        .filter(opt -> opt.isPresent())
        .map( opt -> opt.get())
        .collect(Collectors.toSet());

      return releaseItems;

    } catch (NoSuchElementException | InvalidResourceUrlException ex ) {
        return Collections.emptySet();
    }

  }

  public boolean hasSidebar() {

      var ecp = DriverUtils.findElmCountPair(driver,rightColumnLocator);

      if (ecp.getCount() > 1) {
        LOG.warn("found multiple #rightColumn elements");
      }

      return ecp.getCount() != 0;
  }

  public Optional<String> getBandNameLocation() {

    var ecp = DriverUtils.findElmCountPair(driver, bandNameLocationLocator);

    if ( ecp.getCount() == 0 ) {
      LOG.warn("no bandNameLocation");
      return Optional.empty();
    }

    if ( ecp.getCount() > 1 ) {
      LOG.warn("found multiple #band-name-location elements");
    }

    WebElement elmBandNameLocation = ecp.getElm();
    ecp = DriverUtils.findElmCountPair(driver, By.cssSelector(".location.secondaryText"));
    if (ecp.getCount() == 0 ) {
      LOG.warn("no location text present");
      return Optional.empty();
    }
    if ( ecp.getCount() > 1 ) {
      LOG.warn("found multiple .location.SecondaryText elements");
    }

    String text = ecp.getElm().getText();
    return (text == null || text.isEmpty()) ? 
      Optional.empty() 
      : Optional.of(text);


  } 


}
