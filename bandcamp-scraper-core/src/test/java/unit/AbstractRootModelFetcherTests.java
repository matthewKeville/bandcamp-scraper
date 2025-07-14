package unit;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.stream.Stream;

import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.slf4j.Logger;

import bandcamp_scraper_core.exceptions.fetching.FetchingException;
import bandcamp_scraper_core.fetcher.FetchingContext;
import bandcamp_scraper_core.fetcher.RootModelFetcher;
import bandcamp_scraper_core.selenium.DriverContext;
import bandcamp_scraper_models.RootModel;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class AbstractRootModelFetcherTests<T extends RootModel> {

  protected abstract RootModelFetcher<T> getFetcher();
  protected abstract FetchingContext<T> getFetchingContext();
  protected final DriverContext driverContext = new DriverContext();

  protected Logger LOG = provideLogger();
  protected abstract Logger provideLogger();
  protected abstract Stream<String> provideBadUrls();

  protected final Stream<String> defaultBadUrls = 
    Stream.of(
        null,
        "asdfasdf",
        "not-a-url",
        "https://youtube.com",
        "file:///etc/shadow"
    )
  ;

  protected final Stream<String> allBadUrls() {
    return Stream.concat(defaultBadUrls, provideBadUrls());
  }


  @ParameterizedTest
  @MethodSource("allBadUrls")
  void throwsFetchingExceptionWhenBadURL(String modelUrl) throws Exception {
    var fetcher = getFetcher();
    var fetchingContext = getFetchingContext();
    assertThrows(FetchingException.class, () -> {
      fetcher.fetchModel(fetchingContext, driverContext, modelUrl);
    });
  }



}
