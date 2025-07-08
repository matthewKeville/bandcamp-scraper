import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import bandcamp_scraper_core.scraper.album.AlbumScraper;
import bandcamp_scraper_core.scraper.album.AlbumScraperSingleThreaded;
import bandcamp_scraper_models.Album;

public class AlbumScraperIT {

private String scrapeAlbumTestIntent(String albumName) {
  return "Scraped album data should match expected data for " + albumName;
}

@Test
void getBurnTheEarthLeaveItBehind() {

    //Arrange
    Album.AlbumBuilder builder = Album.builder();
      
    Album expected = builder.albumTitle("Burn The Earth Leave It Behind")
      .digitalCost(6.66f)
      .build();
    AlbumScraper albumScraper = new AlbumScraperSingleThreaded();

    //Act
    Album actual = albumScraper.scrapeAlbum("https://diybandits.bandcamp.com/album/burn-the-earth-leave-it-behind");

    //Asset
    assertThat(expected)
      .as(scrapeAlbumTestIntent("Burn The Earth Leave It Behind"))
      .usingRecursiveComparison()
      .isEqualTo(actual);

}

@Test
void getAllIDidThisSummerWasGoToRehab() {

    //Arrange
    Album.AlbumBuilder builder = Album.builder();
      
    Album expected = builder.albumTitle("All I did this summer was go to rehab")
      .digitalCost(0.0f)
      .build();
    AlbumScraper albumScraper = new AlbumScraperSingleThreaded();

    //Act
    Album actual = albumScraper.scrapeAlbum("https://apesofthestate.bandcamp.com/album/all-i-did-this-summer-was-go-to-rehab");

    //Asset
    assertThat(expected)
      .as(scrapeAlbumTestIntent("All I did this summer was go to rehab"))
      .usingRecursiveComparison()
      .isEqualTo(actual);

}

}
