// package unit;
//
// import java.util.stream.Stream;
//
// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;
//
// import bandcamp_scraper_core.fetcher.AlbumFetcherSingleThread;
// import bandcamp_scraper_core.fetcher.FetchingContext;
// import bandcamp_scraper_core.fetcher.RootModelFetcher;
// import bandcamp_scraper_models.Album;
//
// public class AlbumFetcherSingleThreadTests extends AbstractRootModelFetcherTests<Album> {
//
//   @Override
//   protected RootModelFetcher<Album> getFetcher() {
//     return new AlbumFetcherSingleThread();
//   }
//
//   @Override
//   protected FetchingContext<Album> getFetchingContext() {
//     return FetchingContext.dummy();
//   }
//
//   @Override
//   protected Logger provideLogger() {
//     return LoggerFactory.getLogger(AlbumFetcherSingleThreadTests.class);
//   }
//
//   @Override
//   protected Stream<String> provideBadUrls() {
//     return Stream.of(
//       "https://teenagehalloween.bandcamp.com/music"
//     );
//   }
//
// }
