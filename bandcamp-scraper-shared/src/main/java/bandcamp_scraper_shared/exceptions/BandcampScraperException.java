package bandcamp_scraper_shared.exceptions;

/**
 * Base class for all bandcamp_scraper_core exceptions
 */
public class BandcampScraperException extends RuntimeException {

    public BandcampScraperException(String message) {
        super(message);
    }

    public BandcampScraperException(String message, Throwable cause) {
        super(message, cause);
    }

    public BandcampScraperException(Throwable cause) {
        super(cause.getMessage(), cause);
    }
}
