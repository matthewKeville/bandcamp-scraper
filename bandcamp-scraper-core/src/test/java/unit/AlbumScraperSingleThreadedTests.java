package unit;

import bandcamp_scraper_core.scraper.album.AlbumScraper;
import bandcamp_scraper_core.scraper.album.AlbumScraperSingleThreaded;

public class AlbumScraperSingleThreadedTests extends AbstractAlbumScraperTests {

  @Override
  protected AlbumScraper getScraper() {
    return new AlbumScraperSingleThreaded();
  }

}
