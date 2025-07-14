package integration;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.Stream;

import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.slf4j.Logger;

import bandcamp_scraper_core.fetcher.FetchingContext;
import bandcamp_scraper_core.fetcher.RootModelFetcher;
import bandcamp_scraper_core.selenium.DriverContext;
import bandcamp_scraper_models.RootModel;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class AbstractRootModelFetcherIT<T extends RootModel> {

  protected abstract RootModelFetcher<T> getFetcher();
  protected abstract FetchingContext<T> getFetchingContext();
  protected final DriverContext driverContext = new DriverContext();

  protected Logger LOG = provideLogger();
  protected abstract Logger provideLogger();
  protected abstract Stream<Arguments> provideTestCases();

  @ParameterizedTest
  @MethodSource("provideTestCases")
  void fetchesCorrectModel(String modelUrl, T expectedModel) throws Exception {
    var fetcher = getFetcher();
    var fetchingContext = getFetchingContext();
    T model = fetcher.fetchModel(fetchingContext, driverContext, modelUrl);
    assertThat(expectedModel)
      .usingRecursiveComparison()
      .as(modelUrl  + "fetches correctly")
      .isEqualTo(model);
  }

}
