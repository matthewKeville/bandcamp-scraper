package bandcamp_scraper_core.scraper.album;

import java.time.Duration;
import java.util.List;
import java.util.Optional;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import bandcamp_scraper_core.exceptions.scraping.ScrapingException;
import bandcamp_scraper_core.pages.AlbumPage;
import bandcamp_scraper_models.Album;
import bandcamp_scraper_models.Track;

public class AlbumScraperSingleThreaded implements AlbumScraper {

  private Logger LOG = LoggerFactory.getLogger(AlbumScraperSingleThreaded.class);

  public Album scrapeAlbum(String url) {

    WebDriver driver = new ChromeDriver();
    driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));

    Album.AlbumBuilder builder = Album.builder();

    try {

      driver.get(url);
      AlbumPage albumPage = new AlbumPage(driver);

      String albumTitle = albumPage.getAlbumTitle();
      builder.title(albumTitle);

      Optional<Float> albumPrice = albumPage.getDigitalAlbumPrice();
      albumPrice.ifPresent(price -> builder.price(price));

      List<Track> tracks = albumPage.getTracks();
      builder.tracks(tracks);

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
