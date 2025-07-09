package integration;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;

import bandcamp_scraper_core.scraper.album.AlbumScraper;
import bandcamp_scraper_core.scraper.album.AlbumScraperSingleThreaded;
import bandcamp_scraper_models.Album;
import bandcamp_scraper_models.Track;

public class AlbumScraperSingleThreadedIT {

private String scrapeAlbumTestIntent(String albumName) {
  return "Scraped album data should match expected data for " + albumName;
}

@Test
void getBurnTheEarthLeaveItBehind() {

    //Arrange
    Album.AlbumBuilder builder = Album.builder();
      
    Album expected = builder.title("Burn The Earth Leave It Behind")
      .price(6.66f)
      .tracks(List.of(
        Track.builder().title("Proudhon in Manhattan").number(1).duration(146).build(),
        Track.builder().title("Never Trust a Man").number(2).duration(137).build(),
        Track.builder().title("Fuck Shit Up!").number(3).duration(122).build(),
        Track.builder().title("Fuck Every Cop").number(4).duration(222).build(),
        Track.builder().title("Urine Speaks Louder than Words").number(5).duration(120).build(),
        Track.builder().title("Picking Sides").number(6).duration(222).build(),
        Track.builder().title("Jesus Does the Dishes").number(7).duration(177).build(),
        Track.builder().title("Just Becase I don't say Anything").number(8).duration(101).build(),
        Track.builder().title("For a Girl in Rhinelander").number(9).duration(99).build(),
        Track.builder().title("My Idea of Fun").number(10).duration(398).build()
      ))
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
      
    Album expected = builder.title("All I did this summer was go to rehab")
      .price(0.0f)
      .tracks(List.of(
        Track.builder().title("white and privileged").number(1).duration(142).build(),
        Track.builder().title("serenity").number(2).duration(146).build(),
        Track.builder().title("a song about kool aid").number(3).duration(141).build(),
        Track.builder().title("all I did this summer was go to rehab").number(4).duration(172).build(),
        Track.builder().title("earth people").number(5).duration(172).build(),
        Track.builder().title("today I didn't rob my friends").number(6).duration(220).build(),
        Track.builder().title("not responsible").number(7).duration(114).build(),
        Track.builder().title("runaway").number(8).duration(69).build(),
        Track.builder().title("heading to new orleans").number(9).duration(136).build(),
        Track.builder().title("the puke song").number(10).duration(191).build(),
        Track.builder().title("connect the dots").number(11).duration(225).build(),
        Track.builder().title("something about blue eyes").number(12).duration(262).build(),
        Track.builder().title("people are fat").number(13).duration(113).build()
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
