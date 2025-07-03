package bandcamp_scraper_core.exceptions.scraping.artist;

import bandcamp_scraper_core.exceptions.scraping.ScrapingException;

/**
 *  Base class for all exceptions related to the artist scraping process.
 */
public class ArtistScrapingException extends ScrapingException {

    public ArtistScrapingException(String message) {
        super(message);
    }

    public ArtistScrapingException(String message, Throwable cause) {
        super(message, cause);
    }
}
