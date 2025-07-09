package unit;

import bandcamp_scraper_core.scraper.artist.ArtistScraper;
import bandcamp_scraper_core.scraper.artist.ArtistScraperSingleThreaded;

public class ArtistScraperSingleThreadedTests extends AbstractArtistScraperTests {

  @Override
  protected ArtistScraper getScraper() {
    return new ArtistScraperSingleThreaded();
  }

}
