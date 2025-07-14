
package bandcamp_scraper_core.fetcher;

import bandcamp_scraper_models.Model;

/**
 * Planned interface to specify what data needs to be fetched.
 */
public interface FetchingContext<T extends Model> {
  /**
   * Facade for FetchingContext
   */
  public static class DummyFetchingContext<T extends Model> implements FetchingContext<T> {
  }
  public static <T extends Model> DummyFetchingContext<T> dummy() {
    return new DummyFetchingContext<T>();
  }
}
