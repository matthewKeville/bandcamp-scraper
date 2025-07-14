
package bandcamp_scraper_core.exceptions.http;

import bandcamp_scraper_core.exceptions.BandcampScraperException;

/**
 * Thrown when a URL does not match expected resource format.
 * Ex. An Artist URL is expected, but the URL is not an Artist URL
 */
public class InvalidResourceUrlException extends BandcampScraperException {

    public InvalidResourceUrlException(String message) {
        super(message);
    }

}
