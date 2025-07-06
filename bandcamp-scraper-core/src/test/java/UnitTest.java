import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import bandcamp_scraper_core.scraper.artist.ArtistScraper;
import bandcamp_scraper_core.scraper.artist.ArtistScraperSingleThreaded;
import bandcamp_scraper_models.Artist;
import bandcamp_scraper_models.ReleaseItem;
import bandcamp_scraper_models.ReleaseLink;

public class UnitTest {

@Test
void getArtist() {

    //Arrange
    Artist.ArtistBuilder builder = Artist.builder()
      .name("Teenage Halloween")
      .location("Asbury Park, New Jersey");

    Set<ReleaseItem> releases = new HashSet<ReleaseItem>();
    releases.add(new ReleaseLink("/album/till-you-return"));
    releases.add(new ReleaseLink("/album/the-homeless-gospel-choir-teenage-halloween"));
    releases.add(new ReleaseLink("/album/eternal-roast"));
    releases.add(new ReleaseLink("/album/teenage-halloween"));
    builder.releases(releases);
      

    Artist expected = builder.build();
    ArtistScraper artistScraper = new ArtistScraperSingleThreaded();

    //Act
    Artist actual = artistScraper.scrapeArtist("https://teenagehalloween.bandcamp.com/music");

    //Asset
    assertThat(expected).usingRecursiveComparison().isEqualTo(actual);

}

}
