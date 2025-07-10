package integration;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;

import bandcamp_scraper_core.scraper.album.AlbumScraper;
import bandcamp_scraper_core.scraper.album.AlbumScraperSingleThreaded;
import bandcamp_scraper_models.Album;
import bandcamp_scraper_models.HydratableModel.HydrationStatus;
import bandcamp_scraper_models.Track;

public class AlbumScraperSingleThreadedIT {

private String scrapeAlbumTestIntent(String albumName) {
  return "Scraped album data should match expected data for " + albumName;
}

@Test
void getBurnTheEarthLeaveItBehind() {

    //Arrange
    final String albumURL = "https://diybandits.bandcamp.com/album/burn-the-earth-leave-it-behind";
    Album.AlbumBuilder builder = Album.builder();
    builder.origin(albumURL)
      .status(HydrationStatus.HYDRATED);
      
    Album expected = builder.title("Burn The Earth Leave It Behind")
      .price(6.66f)
        .tracks(List.of(
          Track.builder().title("Proudhon in Manhattan").number(1).duration(146).status(HydrationStatus.PARTIAL)
            .origin("https://diybandits.bandcamp.com/track/proudhon-in-manhattan").build(),
          Track.builder().title("Never Trust a Man").number(2).duration(137).status(HydrationStatus.PARTIAL)
            .origin("https://diybandits.bandcamp.com/track/never-trust-a-man").build(),
          Track.builder().title("Fuck Shit Up!").number(3).duration(122).status(HydrationStatus.PARTIAL)
            .origin("https://diybandits.bandcamp.com/track/fuck-shit-up").build(),
          Track.builder().title("Fuck Every Cop").number(4).duration(222).status(HydrationStatus.PARTIAL)
            .origin("https://diybandits.bandcamp.com/track/fuck-every-cop").build(),
          Track.builder().title("Urine Speaks Louder than Words").number(5).duration(120).status(HydrationStatus.PARTIAL)
            .origin("https://diybandits.bandcamp.com/track/urine-speaks-louder-than-words").build(),
          Track.builder().title("Picking Sides").number(6).duration(222).status(HydrationStatus.PARTIAL)
            .origin("https://diybandits.bandcamp.com/track/picking-sides").build(),
          Track.builder().title("Jesus Does the Dishes").number(7).duration(177).status(HydrationStatus.PARTIAL)
            .origin("https://diybandits.bandcamp.com/track/jesus-does-the-dishes").build(),
          Track.builder().title("Just Becase I don't say Anything").number(8).duration(101).status(HydrationStatus.PARTIAL)
            .origin("https://diybandits.bandcamp.com/track/just-becase-i-dont-say-anything").build(),
          Track.builder().title("For a Girl in Rhinelander").number(9).duration(99).status(HydrationStatus.PARTIAL)
            .origin("https://diybandits.bandcamp.com/track/for-a-girl-in-rhinelander").build(),
          Track.builder().title("My Idea of Fun").number(10).duration(398).status(HydrationStatus.PARTIAL)
            .origin("https://diybandits.bandcamp.com/track/my-idea-of-fun").build()
        ))
      .build();

    AlbumScraper albumScraper = new AlbumScraperSingleThreaded();

    //Act
    Album actual = albumScraper.scrapeAlbum(albumURL);

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
    final String albumURL = "https://apesofthestate.bandcamp.com/album/all-i-did-this-summer-was-go-to-rehab";
    builder.origin(albumURL)
      .status(HydrationStatus.HYDRATED);

      Album expected = builder.title("All I did this summer was go to rehab")
        .price(0.0f)
        .tracks(List.of(
          Track.builder().title("white and privileged").number(1).duration(142).status(HydrationStatus.PARTIAL)
            .origin("https://apesofthestate.bandcamp.com/track/white-and-privileged").build(),
          Track.builder().title("serenity").number(2).duration(146).status(HydrationStatus.PARTIAL)
            .origin("https://apesofthestate.bandcamp.com/track/serenity").build(),
          Track.builder().title("a song about kool aid").number(3).duration(141).status(HydrationStatus.PARTIAL)
            .origin("https://apesofthestate.bandcamp.com/track/a-song-about-kool-aid").build(),
          Track.builder().title("all I did this summer was go to rehab").number(4).duration(172).status(HydrationStatus.PARTIAL)
            .origin("https://apesofthestate.bandcamp.com/track/all-i-did-this-summer-was-go-to-rehab").build(),
          Track.builder().title("earth people").number(5).duration(172).status(HydrationStatus.PARTIAL)
            .origin("https://apesofthestate.bandcamp.com/track/earth-people").build(),
          Track.builder().title("today I didn't rob my friends").number(6).duration(220).status(HydrationStatus.PARTIAL)
            .origin("https://apesofthestate.bandcamp.com/track/today-i-didnt-rob-my-friends").build(),
          Track.builder().title("not responsible").number(7).duration(114).status(HydrationStatus.PARTIAL)
            .origin("https://apesofthestate.bandcamp.com/track/not-responsible").build(),
          Track.builder().title("runaway").number(8).duration(69).status(HydrationStatus.PARTIAL)
            .origin("https://apesofthestate.bandcamp.com/track/runaway").build(),
          Track.builder().title("heading to new orleans").number(9).duration(136).status(HydrationStatus.PARTIAL)
            .origin("https://apesofthestate.bandcamp.com/track/heading-to-new-orleans").build(),
          Track.builder().title("the puke song").number(10).duration(191).status(HydrationStatus.PARTIAL)
            .origin("https://apesofthestate.bandcamp.com/track/the-puke-song").build(),
          Track.builder().title("connect the dots").number(11).duration(225).status(HydrationStatus.PARTIAL)
            .origin("https://apesofthestate.bandcamp.com/track/connect-the-dots").build(),
          Track.builder().title("something about blue eyes").number(12).duration(262).status(HydrationStatus.PARTIAL)
            .origin("https://apesofthestate.bandcamp.com/track/something-about-blue-eyes").build(),
          Track.builder().title("people are fat").number(13).duration(113).status(HydrationStatus.PARTIAL)
            .origin("https://apesofthestate.bandcamp.com/track/people-are-fat").build()
         ))
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
