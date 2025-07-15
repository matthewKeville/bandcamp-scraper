package bandcamp_scraper_core.fetcher;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import bandcamp_scraper_core.exceptions.driver.DriverFactoryException;
import bandcamp_scraper_core.exceptions.fetching.FetchingException;
import bandcamp_scraper_core.exceptions.http.InvalidResourceUrlException;
import bandcamp_scraper_core.extraction.RootModelExtractionContext;
import bandcamp_scraper_core.pages.AlbumPage;
import bandcamp_scraper_core.selenium.DriverContext;
import bandcamp_scraper_core.utils.http.UrlUtils;
import bandcamp_scraper_models.Album;
import bandcamp_scraper_models.HydratableModel.HydrationStatus;

public class AlbumFetcherSingleThread implements RootModelFetcher<Album,AlbumPage,Album.AlbumBuilder> {

  private Logger LOG = LoggerFactory.getLogger(AlbumFetcherSingleThread.class);

  @Override
  public List<Album> fetchModels(
      RootModelExtractionContext<Album, AlbumPage, Album.AlbumBuilder> extractionContext,
      DriverContext driverContext, List<String> urls) throws FetchingException {
    throw new UnsupportedOperationException("Unimplemented method 'fetchModels'");
  }

  @Override
  public Album fetchModel(RootModelExtractionContext<Album, AlbumPage, Album.AlbumBuilder> extractionContext,
      DriverContext driverContext, String url) throws FetchingException {

    if (!UrlUtils.isAlbumURL(url)) {
      throw new FetchingException(new InvalidResourceUrlException("URL " + url + " is not a valid album url"));
    }

    WebDriver driver;
    try {
      driver = driverContext.getDriver();
    } catch (DriverFactoryException ex) {
      throw new FetchingException(ex);
    }

    Album.AlbumBuilder builder = Album.builder();
    builder.origin(url);

    try {

      driver.get(url);
      AlbumPage albumPage = new AlbumPage(driver);
      builder.status(HydrationStatus.HYDRATED);
      extractionContext.extract(albumPage, builder);
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

