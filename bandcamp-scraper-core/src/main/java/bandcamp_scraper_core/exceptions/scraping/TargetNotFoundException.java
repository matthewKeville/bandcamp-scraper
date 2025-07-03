package bandcamp_scraper_core.exceptions.scraping;

/**
 * Base class for all exceptions related to the scraping process.
 */
public class TargetNotFoundException extends ScrapingException {

    public TargetNotFoundException(String message) {
        super(message);
    }

    public TargetNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
