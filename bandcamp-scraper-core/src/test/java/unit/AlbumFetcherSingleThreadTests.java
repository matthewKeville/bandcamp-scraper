package unit;

import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import bandcamp_scraper_core.extraction.AlbumExtractionContext;
import bandcamp_scraper_core.fetcher.RootModelFetcher;
import bandcamp_scraper_core.fetcher.synchronous.AlbumFetcherSingleThread;
import bandcamp_scraper_core.pages.AlbumPage;
import bandcamp_scraper_models.Album;

public class AlbumFetcherSingleThreadTests extends AbstractRootModelFetcherTests<Album,AlbumPage,Album.AlbumBuilder> {

  @Override
  protected RootModelFetcher<Album> getFetcher() {
    return new AlbumFetcherSingleThread(this.driverContext, new AlbumExtractionContext());
  }

  @Override
  protected Logger provideLogger() {
    return LoggerFactory.getLogger(AlbumFetcherSingleThreadTests.class);
  }

  @Override
  protected Stream<String> provideBadUrls() {
    return Stream.of(
      "https://teenagehalloween.bandcamp.com/music"
    );
  }

}
