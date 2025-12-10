package bandcamp_scraper_core.fetcher.synchronous;

import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import bandcamp_scraper_core.extraction.RootModelExtractionContext;
import bandcamp_scraper_core.pages.ArtistPage;
import bandcamp_scraper_core.selenium.DriverContext;
import bandcamp_scraper_models.Artist;
import bandcamp_scraper_models.Artist.ArtistBuilder;
import bandcamp_scraper_models.HydratableModel.HydrationStatus;
import bandcamp_scraper_shared.utils.http.UrlUtils;

public class ArtistFetcherSingleThread extends AbstractRootModelFetcherSingleThread<Artist,ArtistPage,Artist.ArtistBuilder> {

  public ArtistFetcherSingleThread(DriverContext driverContext,RootModelExtractionContext<Artist,ArtistPage,Artist.ArtistBuilder> extractionContext) {
    super(driverContext,extractionContext);
  }

  @Override
  protected Logger getLogger() {
    return LoggerFactory.getLogger(ArtistFetcherSingleThread.class);
  }

  @Override
  protected boolean isValidModelUrl(String url) {
    return UrlUtils.isArtistURL(url);
  }

  @Override
  protected ArtistPage getPage(WebDriver driver) {
    return new ArtistPage(driver);
  }

  @Override
  protected ArtistBuilder getBuilder(String url) {
    var builder = Artist.builder();
    builder.origin(url);
    return builder;
  }

  @Override
  protected Artist build(ArtistBuilder builder) {
    builder.status(HydrationStatus.HYDRATED);
    return builder.build();
  }


}
