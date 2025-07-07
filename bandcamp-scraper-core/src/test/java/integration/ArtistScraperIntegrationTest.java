package integration;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import bandcamp_scraper_core.scraper.artist.ArtistScraper;
import bandcamp_scraper_core.scraper.artist.ArtistScraperSingleThreaded;
import bandcamp_scraper_models.Artist;
import bandcamp_scraper_models.ReleaseItem;
import bandcamp_scraper_models.ReleaseLink;

public class ArtistScraperIntegrationTest {

private String scrapeArtistTestIntent(String artistName) {
  return "Scraped artist data should match expected data for " + artistName;
}

@Test
void getTeenageHalloween() {

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
    assertThat(expected)
      .as(scrapeArtistTestIntent("Teenage Halloween"))
      .usingRecursiveComparison()
      .isEqualTo(actual);

}

@Test
void getSlimeGirls() {

    //Arrange
    Artist.ArtistBuilder builder = Artist.builder()
      .name("Slime Girls")
      .location("Los Angeles, California");

    Set<ReleaseItem> releases = new HashSet<ReleaseItem>();
    releases.add(new ReleaseLink("/album/as-if-youre-never-hurt"));
    releases.add(new ReleaseLink("/album/sketchbook-vol-1-12-17"));
    releases.add(new ReleaseLink("/track/baby-baby"));
    releases.add(new ReleaseLink("/album/dont-forget"));
    releases.add(new ReleaseLink("/album/tapioca-ost"));
    releases.add(new ReleaseLink("/album/no-summer-no-cry"));
    releases.add(new ReleaseLink("/album/yumemi-lonely-planet-girl"));
    releases.add(new ReleaseLink("/album/heart-on-wave"));
    releases.add(new ReleaseLink("/album/vacation-wasteland-ep"));
    builder.releases(releases);
      

    Artist expected = builder.build();
    ArtistScraper artistScraper = new ArtistScraperSingleThreaded();

    //Act
    Artist actual = artistScraper.scrapeArtist("https://slimegirls.bandcamp.com/music");

    //Asset
    assertThat(expected)
      .usingRecursiveComparison()
      .as(scrapeArtistTestIntent("Slime Girls"))
      .isEqualTo(actual);

}

@Test
void getElephantJake() {

    //Arrange
    Artist.ArtistBuilder builder = Artist.builder()
      .name("Elephant Jake")
      .location("Philadelphia, Pennsylvania");

    Set<ReleaseItem> releases = new HashSet<ReleaseItem>();
    releases.add(new ReleaseLink("/album/goodness-to-honest"));
    releases.add(new ReleaseLink("/album/looking-good-feeling-good-2"));
    releases.add(new ReleaseLink("/album/elephantsharkwave-presents-friday-the-13th-the-made-for-tv-movie-the-official-soundtrack-volume-1-remastered"));
    releases.add(new ReleaseLink("/track/andy-finally-lost-the-war-with-the-disco-beat-2"));
    releases.add(new ReleaseLink("/track/freshman-15-2"));
    releases.add(new ReleaseLink("/track/kjerstin-2"));
    releases.add(new ReleaseLink("/track/mac-sam"));
    releases.add(new ReleaseLink("/track/hardwood-coolwhip"));
    releases.add(new ReleaseLink("/album/classic-2"));
    releases.add(new ReleaseLink("/album/classic"));
    releases.add(new ReleaseLink("/album/were-movies"));
    releases.add(new ReleaseLink("/track/all-i-want-for-christmas-is-you"));
    releases.add(new ReleaseLink("/track/locked-in"));
    releases.add(new ReleaseLink("/track/shipment-only-knifing"));
    releases.add(new ReleaseLink("/album/hey-dude-thanks-for-having-me-demos-2015-2018"));
    releases.add(new ReleaseLink("/album/hey-dude-thanks-for-coming"));
    releases.add(new ReleaseLink("/track/unauthorizeddemo1"));

    builder.releases(releases);
      

    Artist expected = builder.build();
    ArtistScraper artistScraper = new ArtistScraperSingleThreaded();

    //Act
    Artist actual = artistScraper.scrapeArtist("https://elephantjake.bandcamp.com/music");

    //Asset
    assertThat(expected)
      .usingRecursiveComparison()
      .as(scrapeArtistTestIntent("Elephant Jake"))
      .isEqualTo(actual);

}

}
