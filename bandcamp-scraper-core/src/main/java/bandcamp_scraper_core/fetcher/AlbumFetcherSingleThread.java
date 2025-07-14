package bandcamp_scraper_core.fetcher;

import java.time.Duration;
import java.util.List;
import java.util.Optional;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import bandcamp_scraper_core.exceptions.fetching.FetchingException;
import bandcamp_scraper_core.exceptions.http.InvalidResourceUrlException;
import bandcamp_scraper_core.pages.AlbumPage;
import bandcamp_scraper_core.selenium.DriverContext;
import bandcamp_scraper_core.utils.http.UrlUtils;
import bandcamp_scraper_models.Album;
import bandcamp_scraper_models.Track;
import bandcamp_scraper_models.HydratableModel.HydrationStatus;

public class AlbumFetcherSingleThread implements RootModelFetcher<Album> {

  private Logger LOG = LoggerFactory.getLogger(AlbumFetcherSingleThread.class);

  @Override
  public List<Album> fetchModels(FetchingContext<Album> fetchingContext, DriverContext driverContext,
      List<String> urls) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'fetchModels'");
  }

  @Override
  public Album fetchModel(FetchingContext<Album> fetchingContext, DriverContext driverContext, String url) {

    if (!UrlUtils.isAlbumURL(url)) {
      throw new FetchingException(new InvalidResourceUrlException("URL " + url + " is not a valid album url"));
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
      throw new FetchingException(String.format("scraping failed for target url %s", url), ex);

    }

    finally {
      driver.quit();
    }

  }


}
