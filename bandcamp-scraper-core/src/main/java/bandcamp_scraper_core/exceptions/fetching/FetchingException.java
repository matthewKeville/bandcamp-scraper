package bandcamp_scraper_core.exceptions.fetching;

import bandcamp_scraper_core.exceptions.BandcampScraperCoreException;

/**
 * Base class for all exceptions related to Fetchers
 */
public class FetchingException extends BandcampScraperCoreException {

    public FetchingException(String message) {
        super(message);
    }

    public FetchingException(String message, Throwable cause) {
        super(message, cause);
    }

    public FetchingException(Throwable cause) {
        super(cause.getMessage(), cause);
    }
}
