package unit;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import bandcamp_scraper_core.exceptions.http.InvalidResourceUrlException;
import bandcamp_scraper_core.scraper.artist.ArtistScraper;

public abstract class AbstractArtistScraperTests {

  protected abstract ArtistScraper getScraper();

@Test
void throwsInvalidResourceURLWhenGarbage() {

    //Arrange
    ArtistScraper artistScraper = getScraper();

    //Act & Assert
    assertThrows(InvalidResourceUrlException.class, () -> {
      artistScraper.scrapeArtist("asdfasdfasd");
    });


}

@Test
void throwsInvalidResourceURLWhenNullUrl() {

    //Arrange
    ArtistScraper artistScraper = getScraper();

    //Act & Assert
    assertThrows(InvalidResourceUrlException.class, () -> {
      artistScraper.scrapeArtist(null);
    });


}

}
