package bandcamp_scraper_core.fetcher;

import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import bandcamp_scraper_core.pages.TrackPage;
import bandcamp_scraper_core.utils.http.UrlUtils;
import bandcamp_scraper_models.HydratableModel.HydrationStatus;
import bandcamp_scraper_models.Track;

public class TrackFetcherSingleThread extends AbstractRootModelFetcherSingleThread<Track,TrackPage,Track.TrackBuilder> {

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


