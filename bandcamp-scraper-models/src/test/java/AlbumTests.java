import org.junit.jupiter.params.provider.Arguments;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Stream;

import bandcamp_scraper_models.Album;
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
            .price(0f)
            .tracks(
              List.of(
                Album.AlbumTrack.builder()
                  .number(1)
                  .build(),

                Album.AlbumTrack.builder()
                  .number(2)
                  .build(),

                Album.AlbumTrack.builder()
                  .number(3)
                  .build(),

                Album.AlbumTrack.builder()
                  .number(4)
                  .build(),

                Album.AlbumTrack.builder()
                  .number(5)
                  .build()
               ))
            .origin("https://michaelcerapalin.bandcamp.com/album/i-dont-know-how-to-explain-it")
            .status(HydrationStatus.HYDRATED)
            .build(),
          """
            {"status":"HYDRATED","origin":"https://michaelcerapalin.bandcamp.com/album/i-dont-know-how-to-explain-it","title":"I Don't Know How To Explain It","price":0.0,"tracks":[{"track":null,"number":1},{"track":null,"number":2},{"track":null,"number":3},{"track":null,"number":4},{"track":null,"number":5}]}
          """.trim()
        )

    );
  }

}
