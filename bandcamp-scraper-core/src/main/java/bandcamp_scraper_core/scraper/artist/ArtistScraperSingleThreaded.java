package bandcamp_scraper_core.scraper.artist;

import java.time.Duration;
import java.util.Optional;
import java.util.Set;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import bandcamp_scraper_core.exceptions.scraping.ScrapingException;
import bandcamp_scraper_core.pages.ArtistPage;
import bandcamp_scraper_models.Artist;
import bandcamp_scraper_models.ReleaseItem;

public class ArtistScraperSingleThreaded implements ArtistScraper {

  private Logger LOG = LoggerFactory.getLogger(ArtistScraperSingleThreaded.class);

  public Artist scrapeArtist(String url) {

    WebDriver driver = new ChromeDriver();
    driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));

    Artist.ArtistBuilder builder = Artist.builder();

    try {

      driver.get(url);
      ArtistPage artistPage = new ArtistPage(driver);

      LOG.info("scraping artistName");
      String artistName = artistPage.getArtistName();
      builder.name(artistName);

      LOG.info("scraping releaseItems");
      Set<ReleaseItem> releaseItems = artistPage.getReleasesItems();
      builder.releases(releaseItems);

      if (artistPage.hasSidebar()) {
        LOG.info("scraping location");
        Optional<String> optLocation = artistPage.getBandNameLocation();
        optLocation.ifPresent( loc -> builder.location(loc));
      }

      return builder.build();

    } catch (NoSuchElementException ex) {
      if (driver != null) {
        driver.quit();
      }
      throw new ScrapingException(String.format("scraping failed for target url %s", url), ex);
    }

    finally {
      driver.quit();
    }

  }


}
