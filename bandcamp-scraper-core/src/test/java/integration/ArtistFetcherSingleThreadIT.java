package integration;

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
        "https://teenagehalloween.bandcamp.com/music",
        Artist.builder()
          .name("Teenage Halloween")
          .location("Asbury Park, New Jersey")
          .status(HydrationStatus.HYDRATED)
          .origin("https://teenagehalloween.bandcamp.com/music")
          .releases(new HashSet<>(Set.of(
            Release.createFromHref("https://teenagehalloween.bandcamp.com/album/till-you-return"),
            Release.createFromHref("https://teenagehalloween.bandcamp.com/album/the-homeless-gospel-choir-teenage-halloween"),
            Release.createFromHref("https://teenagehalloween.bandcamp.com/album/eternal-roast"),
            Release.createFromHref("https://teenagehalloween.bandcamp.com/album/teenage-halloween")
          )))
          .build()
      ),
      Arguments.of(
        "https://slimegirls.bandcamp.com/music",
        Artist.builder()
          .name("Slime Girls")
          .location("Los Angeles, California")
          .status(HydrationStatus.HYDRATED)
          .origin("https://slimegirls.bandcamp.com/music")
          .releases(new HashSet<>(Set.of(
            Release.createFromHref("https://slimegirls.bandcamp.com/album/as-if-youre-never-hurt"),
            Release.createFromHref("https://slimegirls.bandcamp.com/album/sketchbook-vol-1-12-17"),
            Release.createFromHref("https://slimegirls.bandcamp.com/track/baby-baby"),
            Release.createFromHref("https://slimegirls.bandcamp.com/album/dont-forget"),
            Release.createFromHref("https://slimegirls.bandcamp.com/album/tapioca-ost"),
            Release.createFromHref("https://slimegirls.bandcamp.com/album/no-summer-no-cry"),
            Release.createFromHref("https://slimegirls.bandcamp.com/album/yumemi-lonely-planet-girl"),
            Release.createFromHref("https://slimegirls.bandcamp.com/album/heart-on-wave"),
            Release.createFromHref("https://slimegirls.bandcamp.com/album/vacation-wasteland-ep")
          )))
          .build()
      )
    );
  }



}
