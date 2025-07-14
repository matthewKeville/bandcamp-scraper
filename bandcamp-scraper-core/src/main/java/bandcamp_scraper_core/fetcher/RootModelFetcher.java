package bandcamp_scraper_core.fetcher;

import java.util.List;

import bandcamp_scraper_core.exceptions.fetching.FetchingException;
import bandcamp_scraper_core.selenium.DriverContext;
import bandcamp_scraper_models.RootModel;

/**
 * Interface representing a class that fetches RootModels.
 *
 * @param <T> the type of RootModel to fetch
 */
public interface RootModelFetcher<T extends RootModel> {

  /**
   * Fetches a single model identified by the given URL.
   *
   * @param fetchingContext specifies what data to fetch
   * @param driverContext specifies how to fetch (e.g., browser settings)
   * @param url the URL for the requested data model
   * @return the requested data model
   */
  T fetchModel(FetchingContext<T> fetchingContext, DriverContext driverContext, String url) throws FetchingException;

  /**
   * Fetches a list of models identified by the given URLs.
   *
   * @param fetchingContext specifies what data to fetch
   * @param driverContext specifies how to fetch (e.g., browser settings)
   * @param urls list of URLs for the requested data models
   * @return list of requested data models
   */
  List<T> fetchModels(FetchingContext<T> fetchingContext, DriverContext driverContext, List<String> urls) throws FetchingException;
}
