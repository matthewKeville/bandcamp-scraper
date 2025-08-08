package bandcamp_scraper_core_test.integration;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.slf4j.Logger;

import bandcamp_scraper_core.extraction.RootModelExtractionContext;
import bandcamp_scraper_core.fetcher.RootModelFetcher;
import bandcamp_scraper_core.pages.RootModelPage;
import bandcamp_scraper_core.selenium.DriverContext;
import bandcamp_scraper_core_test.fixtures.StaticFixtureLoader;
import bandcamp_scraper_models.RootModel;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class AbstractRootModelFetcherIT<M extends RootModel,P extends RootModelPage<M>,B> {

  protected abstract RootModelFetcher<M,P,B> getFetcher();
  protected abstract RootModelExtractionContext<M,P,B> getExtractionContext();
  protected final DriverContext driverContext = DriverContext.getDefault();

  protected Logger LOG = provideLogger();
  protected abstract Logger provideLogger();
  protected abstract Stream<Arguments> provideTestCases();

  @BeforeAll
  static void loadFixtures() {
    StaticFixtureLoader.loadFixtures();
  }

  @ParameterizedTest(name = "fetches model from {0}")
  @MethodSource("provideTestCases")
  void fetchesCorrectModel(String modelUrl, M expectedModel) throws Exception {
    var fetcher = getFetcher();
    var extractionContext = getExtractionContext();
    M model = fetcher.fetchModel(extractionContext, driverContext, modelUrl);
    assertThat(model)
      .usingRecursiveComparison()
      .as(modelUrl  + "fetches correctly")
      .isEqualTo(expectedModel);
  }

}
