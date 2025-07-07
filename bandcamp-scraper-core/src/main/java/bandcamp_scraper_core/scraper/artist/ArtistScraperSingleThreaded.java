package bandcamp_scraper_core.scraper.artist;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import bandcamp_scraper_core.exceptions.scraping.ScrapingException;
import bandcamp_scraper_core.utils.selenium.DriverUtils;
import bandcamp_scraper_models.Artist;
import bandcamp_scraper_models.ReleaseLink;

public class ArtistScraperSingleThreaded implements ArtistScraper {

  private WebDriver driver;
  private Logger LOG = LoggerFactory.getLogger(ArtistScraperSingleThreaded.class);

  public Artist scrapeArtist(String url) {

    initializeWebDriver();
    Artist.ArtistBuilder builder = Artist.builder();

    try {

      driver.get(url);

      ////////////////////
      //Artist Name

      //Must be found, no tolerance. Dual function as sanity check.
      WebElement elmMetaTagSiteName = driver.findElement(By.xpath("//meta[@property='og:site_name']"));
      String artistName = elmMetaTagSiteName.getDomAttribute("content");
      builder.name(artistName);

      ////////////////////
      //Releases
      var ecp = DriverUtils.findElmCountPair(driver,By.id("music-grid"));
      if ( ecp.getCount() > 0 ) {
        if (ecp.getCount() > 1 ) {
          LOG.warn("found multiple #music-grid elements");
        }
        WebElement elmMusicGrid = ecp.getElm();
        List<WebElement> elmsReleaseLink = elmMusicGrid.findElements(By.cssSelector("li a"));
        if ( elmsReleaseLink.size() == 0 ) {
          LOG.warn("no releases found");
        }
        builder.releases(elmsReleaseLink.stream().map( elmA -> new ReleaseLink(elmA.getDomAttribute("href"))).collect(Collectors.toSet()));

      }


      ////////////////////
      //side bar exists?

      ecp = DriverUtils.findElmCountPair(driver,By.id("rightColumn"));

      if (ecp.getCount() > 0) {
        LOG.info("found sidebar");
        if (ecp.getCount() > 1) {
          LOG.warn("found multiple #rightColumn elements");
        }
        scrapeSidebar(ecp.getElm(),builder);
      } else {
        LOG.info("no side bar present");
      }

      return builder.build();

    } catch (WebDriverException ex) {
      if (driver != null) {
        driver.quit();
      }
      throw new ScrapingException(String.format("scraping failed for target url %s", url), ex);
    }

    finally {
      driver.quit();
    }

  }

  private void scrapeSidebar(WebElement element,Artist.ArtistBuilder builder) {

    //location
    
    var ecp = DriverUtils.findElmCountPair(driver, By.id("band-name-location"));
    if ( ecp.getCount() > 0 ) {
      LOG.debug("#band-name-location element found");
      if ( ecp.getCount() > 1 ) {
        LOG.warn("found multiple #band-name-location elements");
      }
      WebElement elmBandNameLocation = ecp.getElm();
      ecp = DriverUtils.findElmCountPair(driver, By.cssSelector(".location.secondaryText"));
      if (ecp.getCount() > 0 ) {
        LOG.debug(".location found");
        if ( ecp.getCount() > 1 ) {
          LOG.warn("found multiple .location.SecondaryText elements");
        }
        String locationText = ecp.getElm().getText();
        builder.location(locationText);
      } else {
        LOG.warn("no location text present");
      }
    } else {
        LOG.warn("no location data present");
    }


  }

  private void initializeWebDriver() {
    driver = new ChromeDriver();
    driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));
  }

}
