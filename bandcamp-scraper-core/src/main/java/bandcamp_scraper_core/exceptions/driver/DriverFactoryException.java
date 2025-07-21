package bandcamp_scraper_core.exceptions.driver;

import bandcamp_scraper_core.exceptions.BandcampScraperCoreException;

public class DriverFactoryException extends BandcampScraperCoreException {

    public DriverFactoryException(String message) {
        super(message);
    }

    public DriverFactoryException(String message, Throwable cause) {
        super(message, cause);
    }

    public DriverFactoryException(Throwable cause) {
        super(cause.getMessage(), cause);
    }
}
