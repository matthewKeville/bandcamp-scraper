package bandcamp_scraper_core.exceptions.scraping;

/**
 * Base class for all exceptions related to the scraping process.
 */
public class ScrapingException extends RuntimeException {

    public ScrapingException(String message) {
        super(message);
    }

    public ScrapingException(String message, Throwable cause) {
        super(message, cause);
    }
}
