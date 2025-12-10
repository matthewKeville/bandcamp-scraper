package unit;

import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import bandcamp_scraper_core.extraction.ArtistExtractionContext;
import bandcamp_scraper_core.fetcher.RootModelFetcher;
import bandcamp_scraper_core.fetcher.synchronous.ArtistFetcherSingleThread;
import bandcamp_scraper_core.pages.ArtistPage;
import bandcamp_scraper_models.Artist;

public class ArtistFetcherSingleThreadTests extends AbstractRootModelFetcherTests<Artist,ArtistPage,Artist.ArtistBuilder> {

  @Override
  protected RootModelFetcher<Artist> getFetcher() {
    return new ArtistFetcherSingleThread(this.driverContext, new ArtistExtractionContext());
  }

  @Override
  protected Logger provideLogger() {
    return LoggerFactory.getLogger(ArtistFetcherSingleThreadTests.class);
  }

  @Override
  protected Stream<String> provideBadUrls() {
    return Stream.of(
      "https://diybandits.bandcamp.com/album/burn-the-earth-leave-it-behind"
    );
  }

}
