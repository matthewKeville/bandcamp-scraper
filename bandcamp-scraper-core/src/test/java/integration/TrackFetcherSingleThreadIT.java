package integration;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

import org.junit.jupiter.params.provider.Arguments;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import bandcamp_scraper_core.extraction.TrackExtractionContext;
import bandcamp_scraper_core.extraction.RootModelExtractionContext;
import bandcamp_scraper_core.fetcher.TrackFetcherSingleThread;
import bandcamp_scraper_core.fetcher.RootModelFetcher;
import bandcamp_scraper_core.pages.TrackPage;
import bandcamp_scraper_models.Track;
import bandcamp_scraper_models.HydratableModel.HydrationStatus;

public class TrackFetcherSingleThreadIT extends AbstractRootModelFetcherIT<Track,TrackPage,Track.TrackBuilder> {

  @Override
  protected RootModelFetcher<Track,TrackPage,Track.TrackBuilder> getFetcher() {
    return new TrackFetcherSingleThread();
  }

  @Override
  protected RootModelExtractionContext<Track,TrackPage, Track.TrackBuilder> getExtractionContext() {
    return new TrackExtractionContext();
  }

  @Override
  protected Logger provideLogger() {
    return LoggerFactory.getLogger(TrackFetcherSingleThreadIT.class);
  }

  @Override
  protected Stream<Arguments> provideTestCases() {
    return Stream.of(
      Arguments.of(
        "https://femtanyl.bandcamp.com/track/weightless-2",
        Track.builder()
          .title("WEIGHTLESS")
          .duration(144)
          .status(HydrationStatus.HYDRATED)
          .origin("https://femtanyl.bandcamp.com/track/weightless-2")
          .build()
      )
    );
  }



}
