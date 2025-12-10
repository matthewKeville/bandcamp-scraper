package bandcamp_scraper_core.fetcher.synchronous;

import java.util.List;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;

import bandcamp_scraper_shared.exceptions.http.InvalidResourceUrlException;
import bandcamp_scraper_core.exceptions.driver.DriverFactoryException;
import bandcamp_scraper_core.exceptions.fetching.FetchingException;
import bandcamp_scraper_core.extraction.RootModelExtractionContext;
import bandcamp_scraper_core.fetcher.RootModelFetcher;
import bandcamp_scraper_core.pages.RootModelPage;
import bandcamp_scraper_core.selenium.DriverContext;
import bandcamp_scraper_models.RootModel;

/** Note 12/09/2025 
 *
 * This class is more than just a "single-threaded" RootModelFetcher. 
 * It implements a specific workflow for fetching:
 * 1) Using a pluggable ExtractionContext to populate a model from a page,
 * 2) Building the model via a builder pattern. 
 *
 */
public abstract class AbstractRootModelFetcherSingleThread<M extends RootModel, P extends RootModelPage<M>, B>
  implements RootModelFetcher<M> 
  {
  
  private Logger LOG = getLogger();
  private final DriverContext driverContext;
  private final RootModelExtractionContext<M,P,B> extractionContext;

  protected AbstractRootModelFetcherSingleThread(DriverContext driverContext,RootModelExtractionContext extractionContext) {
    this.driverContext = driverContext;
    this.extractionContext = extractionContext;
  }

  protected abstract Logger getLogger();

  /**
   * determine if the url is appropriate for the derived class's model
   */
  protected abstract boolean isValidModelUrl(String url);
  /**
   * retrieve the matching page for the derived model
   */
  protected abstract P getPage(WebDriver driver);

  //kinda hacky to work arounds for Lombok static builders

  /**
   * retrive the builder for derived model
   * must set the origin
   */
  protected abstract B getBuilder(String url);
  /**
   * set hyrdation status to Hydrated
   * return B.build()
   */
  protected abstract M build(B builder);

  @Override
  public List<M> fetchModels(List<String> urls) throws FetchingException {
    throw new UnsupportedOperationException("Unimplemented method 'fetchModels'");
  }

  @Override
  public M fetchModel(String url) throws FetchingException {

    if (!isValidModelUrl(url)) { 
      throw new FetchingException(new InvalidResourceUrlException("URL " + url + " is not a model url")); 
    }

    WebDriver driver;

    try {
      driver = driverContext.getDriver();
    } catch (DriverFactoryException ex) {
      throw new FetchingException(ex);
    }

    B builder = getBuilder(url);

    try {

      driver.get(url);
      P page = getPage(driver);
      extractionContext.extract(page, builder);
      return build(builder);

    } catch (NoSuchElementException ex) {
      if (driver != null) {
        driver.quit();
      }
      throw new FetchingException(String.format("scraping failed for target url %s", url), ex);
    }

    finally {
      driver.quit();
    }

  }

  }

