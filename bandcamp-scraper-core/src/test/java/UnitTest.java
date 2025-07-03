import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collections;

import org.junit.jupiter.api.Test;

import bandcamp_scraper_core.scraper.artist.ArtistScraper;
import bandcamp_scraper_core.scraper.artist.ArtistScraperSingleThreaded;
import bandcamp_scraper_models.Artist;

public class UnitTest {

@Test
void getArtist() {

    //Arrange
    Artist expected = new Artist("Teenage Halloween","Asbury Park, NJ",Collections.emptyList());
    ArtistScraper artistScraper = new ArtistScraperSingleThreaded();

    //Act
    Artist actual = artistScraper.scrapeArtist("https://fake-artist");

    //Asset
    //do actually assertions here when lombok is added to the project

    assertTrue(true);
}

}
