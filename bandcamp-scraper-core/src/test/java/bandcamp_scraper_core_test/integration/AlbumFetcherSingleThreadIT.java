package bandcamp_scraper_core_test.integration;

import java.util.stream.Stream;

import org.junit.jupiter.params.provider.Arguments;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import bandcamp_scraper_core.extraction.AlbumExtractionContext;
import bandcamp_scraper_core.extraction.RootModelExtractionContext;
import bandcamp_scraper_core.fetcher.AlbumFetcherSingleThread;
import bandcamp_scraper_core.fetcher.RootModelFetcher;
import bandcamp_scraper_core.pages.AlbumPage;
import bandcamp_scraper_core_test.fixtures.AlbumFixtures;
import bandcamp_scraper_models.Album;

public class AlbumFetcherSingleThreadIT extends AbstractRootModelFetcherIT<Album,AlbumPage,Album.AlbumBuilder> {

  @Override
  protected RootModelFetcher<Album,AlbumPage,Album.AlbumBuilder> getFetcher() {
    return new AlbumFetcherSingleThread();
  }

  @Override
  protected RootModelExtractionContext<Album,AlbumPage,Album.AlbumBuilder> getExtractionContext() {
    return new AlbumExtractionContext();
  }

  @Override
  protected Logger provideLogger() {
    return LoggerFactory.getLogger(AlbumFetcherSingleThreadIT.class);
  }

  @Override
  protected Stream<Arguments> provideTestCases() {
    return AlbumFixtures.FEMTANYL.getAllFactoryRecords().stream().map( afr -> Arguments.of(afr.url(),afr.ff().hydrated.get()));
  }

}
