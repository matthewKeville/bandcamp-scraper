package unit;

import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import bandcamp_scraper_core.extraction.TrackExtractionContext;
import bandcamp_scraper_core.extraction.RootModelExtractionContext;
import bandcamp_scraper_core.fetcher.TrackFetcherSingleThread;
import bandcamp_scraper_core.fetcher.RootModelFetcher;
import bandcamp_scraper_core.pages.TrackPage;
import bandcamp_scraper_models.Track;

public class TrackFetcherSingleThreadTests extends AbstractRootModelFetcherTests<Track,TrackPage,Track.TrackBuilder> {

  @Override
  protected RootModelFetcher<Track,TrackPage,Track.TrackBuilder> getFetcher() {
    return new TrackFetcherSingleThread();
  }

  @Override
  protected RootModelExtractionContext<Track,TrackPage,Track.TrackBuilder> getExtractionContext() {
    return new TrackExtractionContext();
  }

  @Override
  protected Logger provideLogger() {
    return LoggerFactory.getLogger(TrackFetcherSingleThreadTests.class);
  }

  @Override
  protected Stream<String> provideBadUrls() {
    return Stream.of(
      "https://diybandits.bandcamp.com/album/burn-the-earth-leave-it-behind"
    );
  }

}
