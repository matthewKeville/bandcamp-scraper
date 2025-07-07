package bandcamp_scraper_core.exceptions.scraping.album;

import bandcamp_scraper_core.exceptions.scraping.ScrapingException;

/**
 *  Base class for all exceptions related to the album scraping process.
 */
public class AlbumScrapingException extends ScrapingException {

    public AlbumScrapingException(String message) {
        super(message);
    }

    public AlbumScrapingException(String message, Throwable cause) {
        super(message, cause);
    }
}
