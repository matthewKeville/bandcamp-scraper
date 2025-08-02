/*
package bandcamp_scraper_core_test.integration;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

import org.junit.jupiter.params.provider.Arguments;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import bandcamp_scraper_core.extraction.ArtistExtractionContext;
import bandcamp_scraper_core.extraction.RootModelExtractionContext;
import bandcamp_scraper_core.fetcher.ArtistFetcherSingleThread;
import bandcamp_scraper_core.fetcher.RootModelFetcher;
import bandcamp_scraper_core.pages.ArtistPage;
import bandcamp_scraper_models.Artist;
import bandcamp_scraper_models.HydratableModel.HydrationStatus;
import bandcamp_scraper_models.Release;

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
      Arguments.of(
        "https://femtanyl.bandcamp.com/music",
        Artist.builder()
          .name("Femtanyl")
          .location("Toronto, Ontario")
          .status(HydrationStatus.HYDRATED)
          .origin("https://femtanyl.bandcamp.com/music")
          .releases(new HashSet<>(Set.of(
            Release.createFromHref("https://teenagehalloween.bandcamp.com/album/till-you-return"),
            Release.createFromHref("https://teenagehalloween.bandcamp.com/album/the-homeless-gospel-choir-teenage-halloween"),
            Release.createFromHref("https://teenagehalloween.bandcamp.com/album/eternal-roast"),
            Release.createFromHref("https://teenagehalloween.bandcamp.com/album/teenage-halloween")
          )))
          .build()
      )
    );
  }



}
*/
