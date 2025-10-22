package bandcamp_scraper_core.pages;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.support.pagefactory.ByChained;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import bandcamp_scraper_core.utils.selenium.DriverUtils;
import bandcamp_scraper_core.utils.selenium.ElmCountPair;
import bandcamp_scraper_models.Artist;
import bandcamp_scraper_models.Release;
import bandcamp_scraper_shared.exceptions.http.InvalidResourceUrlException;
import bandcamp_scraper_shared.utils.http.UrlUtils;

public class ArtistPage implements RootModelPage<Artist> {

  private WebDriver driver;
  private Logger LOG = LoggerFactory.getLogger(ArtistPage.class);

  private By elmMetaTagSiteNameLocator = By.xpath("//meta[@property='og:site_name']");
  private By musicGridLocator = By.id("music-grid");
  private By rightColumnLocator = By.id("rightColumn");
  private By bandNameLocationLocator = By.id("band-name-location");
  private By recommendationPageLinkLocator =  new ByChained(By.id("recommended"),By.cssSelector("a[href]"));
  private RecomendationPage recomendationPage = null;

  private Optional<String> getRecommendationPageLink() {
    ElmCountPair ecp = DriverUtils.findElmCountPair(driver, recommendationPageLinkLocator);
    if ( ecp.getCount() > 0 ) {
      String linkRefText = ecp.getElm().getAttribute("href");
      if (! (linkRefText.isBlank() || linkRefText.isEmpty()) )  {
        return Optional.of(linkRefText);
      }
    }
    return Optional.empty();
  }

  public boolean hasRecommendations() {
    return getRecommendationPageLink().isPresent();
  }

  /** 
   * Return reccomendation sub page, if ! hasRecommendations returns null 
   */
  public RecomendationPage getRecomendationPage() {
    if (!hasRecommendations()) {
      return null;
    }
    if (recomendationPage == null) {
      LOG.debug("Creating Reccomendation Page");
      recomendationPage = new RecomendationPage(driver,getRecommendationPageLink().get());
    }
    return recomendationPage;
  }

  public ArtistPage(WebDriver driver) {
    this.driver = driver;
  }

  public String getArtistName() {
    try { 
      WebElement elmMetaTagSiteName = driver.findElement(elmMetaTagSiteNameLocator);
      String artistName = elmMetaTagSiteName.getDomAttribute("content");
      return artistName;
    } catch (NoSuchElementException ex) {
      LOG.warn("unable to get artist name");
      return null;
    }
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
        LOG.warn("error creating release items for ArtistPage");
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

  public String getBandNameLocation() {

    var ecp = DriverUtils.findElmCountPair(driver, bandNameLocationLocator);

    if ( ecp.getCount() == 0 ) {
      LOG.warn("no bandNameLocation");
      return null;
    }

    if ( ecp.getCount() > 1 ) {
      LOG.warn("found multiple #band-name-location elements");
    }

    WebElement elmBandNameLocation = ecp.getElm();
    ecp = DriverUtils.findElmCountPair(driver, By.cssSelector(".location.secondaryText"));
    if (ecp.getCount() == 0 ) {
      LOG.warn("no location text present");
      return null;
    }
    if ( ecp.getCount() > 1 ) {
      LOG.warn("found multiple .location.SecondaryText elements");
    }

    String text = ecp.getElm().getText();
    return (text == null || text.isEmpty()) ? null : text;


  } 


  //The Artsits recommendation page opened as a new tab
  public static class RecomendationPage {

    private WebDriver driver;
    private String artistPageWindow;
    private String recPageWindow;
    private By recomendedReleaseLink = By.cssSelector("div.recommended-item a.recommended-artist-dl-link-click");
    private Logger LOG = LoggerFactory.getLogger(RecomendationPage.class);

    public RecomendationPage(WebDriver driver,String link) {
      this.driver = driver;
      this.artistPageWindow = driver.getWindowHandle();
      driver.switchTo().newWindow(WindowType.TAB);
      this.recPageWindow = driver.getWindowHandle();
      driver.get(link);
      driver.switchTo().window(artistPageWindow);
    }

    // Convert list of rec items to links to artists of rec items
    public List<String> getRecomendedLinks() {

      this.driver.switchTo().window(this.recPageWindow);

      List<String> recReleaseLinks = driver.findElements(recomendedReleaseLink).stream().map( elm -> elm.getAttribute("href")).collect(Collectors.toList());
      List<String> links = recReleaseLinks.stream()
        .map( ln -> getBandNameFromRecLink(ln))
        .filter(ln -> ln != null)
        .map( ln -> String.format("https://%s.bandcamp.com/music",ln))
        .collect(Collectors.toList());

      this.driver.switchTo().window(this.artistPageWindow);

      return links;

    }

    private String getBandNameFromRecLink(String url) {
      Pattern pattern = Pattern.compile("https://([a-zA-Z0-9\\-]+)\\.bandcamp\\.com");
      Matcher matcher = pattern.matcher(url);
      return (matcher.find()) ? matcher.group(1) : null;
    }




  }

}
