package bandcamp_scraper_core.exceptions;

import bandcamp_scraper_shared.exceptions.BandcampScraperException;

/**
 * Base class for all bandcamp_scraper_core exceptions
 */
public class BandcampScraperCoreException extends BandcampScraperException {

    public BandcampScraperCoreException(String message) {
        super(message);
    }

    public BandcampScraperCoreException(String message, Throwable cause) {
        super(message, cause);
    }

    public BandcampScraperCoreException(Throwable cause) {
        super(cause.getMessage(), cause);
    }
}
