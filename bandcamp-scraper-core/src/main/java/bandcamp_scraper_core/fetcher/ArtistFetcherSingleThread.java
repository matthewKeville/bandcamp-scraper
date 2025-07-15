package bandcamp_scraper_core.fetcher;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import bandcamp_scraper_core.exceptions.fetching.FetchingException;
import bandcamp_scraper_core.exceptions.http.InvalidResourceUrlException;
import bandcamp_scraper_core.extraction.RootModelExtractionContext;
import bandcamp_scraper_core.pages.ArtistPage;
import bandcamp_scraper_core.selenium.DriverContext;
import bandcamp_scraper_core.utils.http.UrlUtils;
import bandcamp_scraper_models.Artist;
import bandcamp_scraper_models.Artist.ArtistBuilder;
import bandcamp_scraper_models.HydratableModel.HydrationStatus;

public class ArtistFetcherSingleThread implements RootModelFetcher<Artist,ArtistPage,Artist.ArtistBuilder> {

  private Logger LOG = LoggerFactory.getLogger(ArtistFetcherSingleThread.class);

  @Override
  public List<Artist> fetchModels(
      RootModelExtractionContext<Artist, ArtistPage, ArtistBuilder> extractionContext,
      DriverContext driverContext, List<String> urls) throws FetchingException {
    throw new UnsupportedOperationException("Unimplemented method 'fetchModels'");
  }

  @Override
  public Artist fetchModel(RootModelExtractionContext<Artist, ArtistPage, ArtistBuilder> extractionContext,
      DriverContext driverContext, String url) throws FetchingException {

    if (!UrlUtils.isArtistURL(url)) {
      throw new FetchingException(new InvalidResourceUrlException("URL " + url + " is not a valid artist url"));
    }

    // TODO get driver from DriverContext
    WebDriver driver = new ChromeDriver();
    driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));

    Artist.ArtistBuilder builder = Artist.builder();
    builder.origin(url);

    try {

      driver.get(url);
      ArtistPage artistPage = new ArtistPage(driver);
      extractionContext.extract(artistPage, builder);
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
