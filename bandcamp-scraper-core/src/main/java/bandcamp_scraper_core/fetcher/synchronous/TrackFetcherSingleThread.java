package bandcamp_scraper_core.fetcher.synchronous;

import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import bandcamp_scraper_core.extraction.RootModelExtractionContext;
import bandcamp_scraper_core.pages.TrackPage;
import bandcamp_scraper_core.selenium.DriverContext;
import bandcamp_scraper_models.HydratableModel.HydrationStatus;
import bandcamp_scraper_models.Track;
import bandcamp_scraper_shared.utils.http.UrlUtils;

public class TrackFetcherSingleThread extends AbstractRootModelFetcherSingleThread<Track,TrackPage,Track.TrackBuilder> {

  public TrackFetcherSingleThread(DriverContext driverContext,RootModelExtractionContext<Track,TrackPage,Track.TrackBuilder> extractionContext) {
    super(driverContext,extractionContext);
  }

  @Override
  protected Logger getLogger() {
    return LoggerFactory.getLogger(TrackFetcherSingleThread.class);
  }

  @Override
  protected boolean isValidModelUrl(String url) {
    return UrlUtils.isTrackURL(url);
  }

  @Override
  protected TrackPage getPage(WebDriver driver) {
    return new TrackPage(driver);
  }

  @Override
  protected Track.TrackBuilder getBuilder(String url) {
    var builder = Track.builder();
    builder.origin(url);
    return builder;
  }

  @Override
  protected Track build(Track.TrackBuilder builder) {
    builder.status(HydrationStatus.HYDRATED);
    return builder.build();
  }
}


