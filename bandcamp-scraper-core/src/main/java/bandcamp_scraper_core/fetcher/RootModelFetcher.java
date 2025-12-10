package bandcamp_scraper_core.fetcher;

import java.util.List;

import bandcamp_scraper_core.exceptions.fetching.FetchingException;
import bandcamp_scraper_models.RootModel;

/**
 * Interface representing a class that fetches RootModels.
 *
 * @param <M> the type of RootModel to fetch
 */
public interface RootModelFetcher<M extends RootModel> extends Fetcher {

  /**
   * Fetches a single model identified by the given URL.
   *
   * @param url the URL for the requested data model
   * @return the requested data model
   */
  M fetchModel(String url) throws FetchingException;

  /**
   * Fetches a list of models identified by the given URLs.
   *
   * @param urls list of URLs for the requested data models
   * @return list of requested data models
   */
  List<M> fetchModels(List<String> urls) throws FetchingException;
}
