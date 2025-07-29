import org.junit.jupiter.params.provider.Arguments;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;
import java.util.stream.Stream;

import bandcamp_scraper_models.RootModelRef;
import bandcamp_scraper_models.Track;
import bandcamp_scraper_models.HydratableModel.HydrationStatus;
import bandcamp_scraper_shared.enums.RootModelType;

public class TrackTests extends AbstractSerializationTest<Track>  {

  @Override
  protected Logger provideLogger() {
    return LoggerFactory.getLogger(Track.class);
  }

  @Override
  protected Stream<Arguments> provideTestCases() {
    return Stream.of(
        // https://100gecs.bandcamp.com/track/ringtone : ringtone
        Arguments.of(
          Track.builder()
            .duration(143)
            .title("ringtone")
            .artist(new RootModelRef(RootModelType.ARTIST,"https://100gecs.bandcamp.com/music"))
            .album(Optional.of(new RootModelRef(RootModelType.ALBUM, "https://100gecs.bandcamp.com/album/1000-gecs")))
            .status(HydrationStatus.PARTIAL)
            .origin("https://100gecs.bandcamp.com/track/ringtone")
            .build(),
          """
            {"status":"PARTIAL","origin":"https://100gecs.bandcamp.com/track/ringtone","album":{"type":"ALBUM","origin":"https://100gecs.bandcamp.com/album/1000-gecs"},"artist":{"type":"ARTIST","origin":"https://100gecs.bandcamp.com/music"},"title":"ringtone","duration":143}
          """.trim()
        )
    );
  }

}
