package bandcamp_scraper_core_test.integration;

import java.util.stream.Stream;

import org.junit.jupiter.params.provider.Arguments;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import bandcamp_scraper_core.extraction.ArtistExtractionContext;
import bandcamp_scraper_core.extraction.RootModelExtractionContext;
import bandcamp_scraper_core.fetcher.ArtistFetcherSingleThread;
import bandcamp_scraper_core.fetcher.RootModelFetcher;
import bandcamp_scraper_core.pages.ArtistPage;
import bandcamp_scraper_core_test.fixtures.ArtistFixtures;
import bandcamp_scraper_models.Artist;

public class ArtistFetcherSingleThreadIT extends AbstractRootModelFetcherIT<Artist,ArtistPage,Artist.ArtistBuilder> {

  @Override
  protected RootModelFetcher<Artist,ArtistPage,Artist.ArtistBuilder> getFetcher() {
    return new ArtistFetcherSingleThread();
  }

  @Override
  protected RootModelExtractionContext<Artist,ArtistPage, Artist.ArtistBuilder> getExtractionContext() {
    return new ArtistExtractionContext();
  }

  @Override
  protected Logger provideLogger() {
    return LoggerFactory.getLogger(ArtistFetcherSingleThreadIT.class);
  }

  @Override
  protected Stream<Arguments> provideTestCases() {
    return Stream.of(
      //Arguments.of(ArtistFixtures.FEMTANYL.URL,ArtistFixtures.FEMTANYL.FF.getHydrated().get())
      Arguments.of(ArtistFixtures.OLD_JAW.URL,ArtistFixtures.OLD_JAW.FF.getHydrated().get())
    );
  }

}
