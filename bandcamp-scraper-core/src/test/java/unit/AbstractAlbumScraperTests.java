package unit;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import bandcamp_scraper_core.exceptions.http.InvalidResourceUrlException;
import bandcamp_scraper_core.scraper.album.AlbumScraper;

public abstract class AbstractAlbumScraperTests {

  protected abstract AlbumScraper getScraper();

@Test
void throwsInvalidResourceURLWhenGarbage() {

    //Arrange
    AlbumScraper artistScraper = getScraper();

    //Act & Assert
    assertThrows(InvalidResourceUrlException.class, () -> {
      artistScraper.scrapeAlbum("asdfasdfasd");
    });


}

@Test
void throwsInvalidResourceURLWhenNullUrl() {

    //Arrange
    AlbumScraper albumScraper = getScraper();

    //Act & Assert
    assertThrows(InvalidResourceUrlException.class, () -> {
      albumScraper.scrapeAlbum(null);
    });


}

}
