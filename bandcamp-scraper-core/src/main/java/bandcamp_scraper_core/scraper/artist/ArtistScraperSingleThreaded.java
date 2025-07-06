package bandcamp_scraper_core.scraper.artist;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.stereotype.Component;

import bandcamp_scraper_core.exceptions.scraping.ScrapingException;
import bandcamp_scraper_core.utils.selenium.DriverUtils;
import bandcamp_scraper_models.Artist;
import bandcamp_scraper_models.ReleaseLink;

@Component
public class ArtistScraperSingleThreaded implements ArtistScraper {

  private WebDriver driver;

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
          //LOG WARN : Unexpect number of elements
        }
        WebElement elmMusicGrid = ecp.getElm();
        List<WebElement> elmsReleaseLink = elmMusicGrid.findElements(By.cssSelector("li a"));
        if ( elmsReleaseLink.size() == 0 ) {
          //LOG WARN : No releases found
        }
        builder.releases(elmsReleaseLink.stream().map( elmA -> new ReleaseLink(elmA.getDomAttribute("href"))).collect(Collectors.toSet()));

      }


      ////////////////////
      //side bar exists?

      ecp = DriverUtils.findElmCountPair(driver,By.id("rightColumn"));

      if (ecp.getCount() > 0) {
        if (ecp.getCount() > 1) {
          //LOG WARN : Unexpect number of elements with ID "rightColumn"
        }
        scrapeSidebar(ecp.getElm(),builder);
      } else {
        //LOG WARN : No Sidebar
      }

      //sanity check
      String title = driver.getTitle();
      System.out.println("mtk title is " + title);

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
    //REPLACE WITH SLFJ :  LOG INFO : Sidebar found, scraping sidebar.
    
    //location
    
    var ecp = DriverUtils.findElmCountPair(driver, By.id("band-name-location"));
    if ( ecp.getCount() > 0 ) {
      //REPLACE WITH SLFJ : LOG TRACE  : band-name-location found
      System.out.println("band-name-location found");
      if ( ecp.getCount() > 1 ) {
        //REPLACE WITH SLFJ : LOG WARN  : Unexpected number of elements
        System.out.println("unexpected number of band-name-location");
      }
      WebElement elmBandNameLocation = ecp.getElm();
      ecp = DriverUtils.findElmCountPair(driver, By.cssSelector(".location.secondaryText"));
      if (ecp.getCount() > 0 ) {
        //REPLACE WITH SLFJ : LOG TRACE  : Unexpected number of elements
        System.out.println("location text found");
        if ( ecp.getCount() > 1 ) {
        //REPLACE WITH SLFJ : LOG WARN  : Unexpected number of elements
        System.out.println("unexpected number of .locationSecondaryText");
        }
        String locationText = ecp.getElm().getText();
        builder.location(locationText);
      } else {
        //REPLACE WITH SLFJ : LOG WARN  : No location text
        System.out.println("no location text");
      }
    } else {
      //REPLACE WITH SLFJ : LOG WARN  : No name-location data 
        System.out.println("no location data");
    }


  }

  private void initializeWebDriver() {
    driver = new ChromeDriver();
    driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));
  }

}
