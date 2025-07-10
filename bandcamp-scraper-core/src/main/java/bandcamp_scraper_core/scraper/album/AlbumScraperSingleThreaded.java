package bandcamp_scraper_core.scraper.album;

import java.time.Duration;
import java.util.List;
import java.util.Optional;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import bandcamp_scraper_core.exceptions.http.InvalidResourceUrlException;
import bandcamp_scraper_core.exceptions.scraping.ScrapingException;
import bandcamp_scraper_core.pages.AlbumPage;
import bandcamp_scraper_core.utils.http.UrlUtils;
import bandcamp_scraper_models.Album;
import bandcamp_scraper_models.Track;
import bandcamp_scraper_models.HydratableModel.HydrationStatus;

public class AlbumScraperSingleThreaded implements AlbumScraper {

  private Logger LOG = LoggerFactory.getLogger(AlbumScraperSingleThreaded.class);

  public Album scrapeAlbum(String url) throws InvalidResourceUrlException {

    if (!UrlUtils.isAlbumURL(url)) {
      throw new InvalidResourceUrlException("URL " + url + " is not a valid album url");
    }

    WebDriver driver = new ChromeDriver();
    driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));

    Album.AlbumBuilder builder = Album.builder();
    builder.origin(url);

    try {

      driver.get(url);
      AlbumPage albumPage = new AlbumPage(driver);

      String albumTitle = albumPage.getAlbumTitle();
      builder.title(albumTitle);

      Optional<Float> albumPrice = albumPage.getDigitalAlbumPrice();
      albumPrice.ifPresent(price -> builder.price(price));

      List<Track> tracks = albumPage.getTracks();
      builder.tracks(tracks);
      builder.status(HydrationStatus.HYDRATED);

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
