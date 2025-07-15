package bandcamp_scraper_core.fetcher;

import java.util.List;

import bandcamp_scraper_core.exceptions.fetching.FetchingException;
import bandcamp_scraper_core.extraction.RootModelExtractionContext;
import bandcamp_scraper_core.pages.RootModelPage;
import bandcamp_scraper_core.selenium.DriverContext;
import bandcamp_scraper_models.RootModel;

/**
 * Interface representing a class that fetches RootModels.
 *
 * @param <M> the type of RootModel to fetch
 * @param <P> the type of PageModel
 * @param <B> the type of RootModel Builder (lombok workaround)
 */
public interface RootModelFetcher<M extends RootModel,P extends RootModelPage<M>,B> {

  /**
   * Fetches a single model identified by the given URL.
   *
   * @param extractionContext specifies what data to fetch
   * @param driverContext specifies how to fetch (e.g., browser settings)
   * @param url the URL for the requested data model
   * @return the requested data model
   */
  M fetchModel(RootModelExtractionContext<M,P,B> extractionContext, DriverContext driverContext, String url) throws FetchingException;

  /**
   * Fetches a list of models identified by the given URLs.
   *
   * @param extractionContext specifies what data to fetch
   * @param driverContext specifies how to fetch (e.g., browser settings)
   * @param urls list of URLs for the requested data models
   * @return list of requested data models
   */
  List<M> fetchModels(RootModelExtractionContext<M,P,B> extractionContext, DriverContext driverContext, List<String> urls) throws FetchingException;
}
