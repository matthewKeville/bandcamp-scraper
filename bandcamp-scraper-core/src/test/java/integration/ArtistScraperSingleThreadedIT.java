package integration;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import bandcamp_scraper_core.scraper.artist.ArtistScraper;
import bandcamp_scraper_core.scraper.artist.ArtistScraperSingleThreaded;
import bandcamp_scraper_models.Artist;
import bandcamp_scraper_models.Release;
import bandcamp_scraper_models.HydratableModel.HydrationStatus;

public class ArtistScraperSingleThreadedIT {

private String scrapeArtistTestIntent(String artistName) {
  return "Scraped artist data should match expected data for " + artistName;
}

@Test
void getTeenageHalloween() {

    //Arrange
    final String artistBaseUrl = "https://teenagehalloween.bandcamp.com";
    final String artistUrl = artistBaseUrl + "/music";

    Artist.ArtistBuilder builder = Artist.builder()
      .name("Teenage Halloween")
      .location("Asbury Park, New Jersey")
      .status(HydrationStatus.HYDRATED)
      .origin(artistUrl);

    Set<Release> releases = new HashSet<Release>();
    releases.add(Release.createFromHref(artistBaseUrl+"/album/till-you-return"));
    releases.add(Release.createFromHref(artistBaseUrl+"/album/the-homeless-gospel-choir-teenage-halloween"));
    releases.add(Release.createFromHref(artistBaseUrl+"/album/eternal-roast"));
    releases.add(Release.createFromHref(artistBaseUrl+"/album/teenage-halloween"));
    builder.releases(releases);


    Artist expected = builder.build();
    ArtistScraper artistScraper = new ArtistScraperSingleThreaded();

    //Act
    Artist actual = artistScraper.scrapeArtist(artistUrl);

    //Asset
    assertThat(expected)
      .as(scrapeArtistTestIntent("Teenage Halloween"))
      .usingRecursiveComparison()
      .isEqualTo(actual);

}

@Test
void getSlimeGirls() {

    //Arrange
    final String artistBaseUrl = "https://slimegirls.bandcamp.com";
    final String artistUrl = artistBaseUrl+"/music";
    Artist.ArtistBuilder builder = Artist.builder()
      .name("Slime Girls")
      .location("Los Angeles, California")
      .status(HydrationStatus.HYDRATED)
      .origin(artistUrl);

    Set<Release> releases = new HashSet<>();
    releases.add(Release.createFromHref(artistBaseUrl + "/album/as-if-youre-never-hurt"));
    releases.add(Release.createFromHref(artistBaseUrl + "/album/sketchbook-vol-1-12-17"));
    releases.add(Release.createFromHref(artistBaseUrl + "/track/baby-baby"));
    releases.add(Release.createFromHref(artistBaseUrl + "/album/dont-forget"));
    releases.add(Release.createFromHref(artistBaseUrl + "/album/tapioca-ost"));
    releases.add(Release.createFromHref(artistBaseUrl + "/album/no-summer-no-cry"));
    releases.add(Release.createFromHref(artistBaseUrl + "/album/yumemi-lonely-planet-girl"));
    releases.add(Release.createFromHref(artistBaseUrl + "/album/heart-on-wave"));
    releases.add(Release.createFromHref(artistBaseUrl + "/album/vacation-wasteland-ep"));
    builder.releases(releases);


    Artist expected = builder.build();
    ArtistScraper artistScraper = new ArtistScraperSingleThreaded();

    //Act
    Artist actual = artistScraper.scrapeArtist(artistUrl);

    //Asset
    assertThat(expected)
      .usingRecursiveComparison()
      .as(scrapeArtistTestIntent("Slime Girls"))
      .isEqualTo(actual);

}


@Test
void getElephantJake() {

    //Arrange
    final String artistBaseUrl = "https://elephantjake.bandcamp.com";
    final String artistUrl = artistBaseUrl+"/music";
    Artist.ArtistBuilder builder = Artist.builder()
      .name("Elephant Jake")
      .location("Philadelphia, Pennsylvania")
      .status(HydrationStatus.HYDRATED)
      .origin(artistUrl);

    Set<Release> releases = new HashSet<>();
    releases.add(Release.createFromHref(artistBaseUrl + "/album/goodness-to-honest"));
    releases.add(Release.createFromHref(artistBaseUrl + "/album/looking-good-feeling-good-2"));
    releases.add(Release.createFromHref(artistBaseUrl + "/album/elephantsharkwave-presents-friday-the-13th-the-made-for-tv-movie-the-official-soundtrack-volume-1-remastered"));
    releases.add(Release.createFromHref(artistBaseUrl + "/track/andy-finally-lost-the-war-with-the-disco-beat-2"));
    releases.add(Release.createFromHref(artistBaseUrl + "/track/freshman-15-2"));
    releases.add(Release.createFromHref(artistBaseUrl + "/track/kjerstin-2"));
    releases.add(Release.createFromHref(artistBaseUrl + "/track/mac-sam"));
    releases.add(Release.createFromHref(artistBaseUrl + "/track/hardwood-coolwhip"));
    releases.add(Release.createFromHref(artistBaseUrl + "/album/classic-2"));
    releases.add(Release.createFromHref(artistBaseUrl + "/album/classic"));
    releases.add(Release.createFromHref(artistBaseUrl + "/album/were-movies"));
    releases.add(Release.createFromHref(artistBaseUrl + "/track/all-i-want-for-christmas-is-you"));
    releases.add(Release.createFromHref(artistBaseUrl + "/track/locked-in"));
    releases.add(Release.createFromHref(artistBaseUrl + "/track/shipment-only-knifing"));
    releases.add(Release.createFromHref(artistBaseUrl + "/album/hey-dude-thanks-for-having-me-demos-2015-2018"));
    releases.add(Release.createFromHref(artistBaseUrl + "/album/hey-dude-thanks-for-coming"));
    releases.add(Release.createFromHref(artistBaseUrl + "/track/unauthorizeddemo1"));
    builder.releases(releases);

    Artist expected = builder.build();
    ArtistScraper artistScraper = new ArtistScraperSingleThreaded();

    //Act
    Artist actual = artistScraper.scrapeArtist(artistUrl);

    //Asset
    assertThat(expected)
      .usingRecursiveComparison()
      .as(scrapeArtistTestIntent("Elephant Jake"))
      .isEqualTo(actual);

}

}
