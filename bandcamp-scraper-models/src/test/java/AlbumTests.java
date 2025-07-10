import org.junit.jupiter.params.provider.Arguments;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Stream;

import bandcamp_scraper_models.Album;
import bandcamp_scraper_models.Track;
import bandcamp_scraper_models.HydratableModel.HydrationStatus;

public class AlbumTests extends AbstractSerializationTest<Album>  {

  @Override
  protected Logger provideLogger() {
    return LoggerFactory.getLogger(Album.class);
  }

  @Override
  protected Stream<Arguments> provideTestCases() {
    return Stream.of(
        // https://michaelcerapalin.bandcamp.com/album/i-dont-know-how-to-explain-it : DRY REFS
        Arguments.of(
          Album.builder()
            .title("I Don't Know How To Explain It")
            .price(0)
            .tracks(
              List.of(
                Track.builder().status(HydrationStatus.DRY).origin("https://michaelcerapalin.bandcamp.com/track/portrait-of-a-man-on-a-couch").build(),
                Track.builder().status(HydrationStatus.DRY).origin("https://michaelcerapalin.bandcamp.com/track/southern-comfort").build(),
                Track.builder().status(HydrationStatus.DRY).origin("https://michaelcerapalin.bandcamp.com/track/if-it-makes-you-happy").build(),
                Track.builder().status(HydrationStatus.DRY).origin("https://michaelcerapalin.bandcamp.com/track/admiral").build(),
                Track.builder().status(HydrationStatus.DRY).origin("https://michaelcerapalin.bandcamp.com/track/go-home-play-music-feel-better").build()
              )
            )
            .origin("https://michaelcerapalin.bandcamp.com/album/i-dont-know-how-to-explain-it")
            .status(HydrationStatus.HYDRATED)
            .build(),
          """
          {"status":"HYDRATED","origin":"https://michaelcerapalin.bandcamp.com/album/i-dont-know-how-to-explain-it","title":"I Don't Know How To Explain It","price":0.0,"tracks":[{"status":"DRY","origin":"https://michaelcerapalin.bandcamp.com/track/portrait-of-a-man-on-a-couch","title":null,"number":0,"duration":0},{"status":"DRY","origin":"https://michaelcerapalin.bandcamp.com/track/southern-comfort","title":null,"number":0,"duration":0},{"status":"DRY","origin":"https://michaelcerapalin.bandcamp.com/track/if-it-makes-you-happy","title":null,"number":0,"duration":0},{"status":"DRY","origin":"https://michaelcerapalin.bandcamp.com/track/admiral","title":null,"number":0,"duration":0},{"status":"DRY","origin":"https://michaelcerapalin.bandcamp.com/track/go-home-play-music-feel-better","title":null,"number":0,"duration":0}]}
          """.trim()
        )
    );
  }

}
