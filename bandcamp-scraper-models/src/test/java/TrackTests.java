import org.junit.jupiter.params.provider.Arguments;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.stream.Stream;

import bandcamp_scraper_models.Track;
import bandcamp_scraper_models.HydratableModel.HydrationStatus;

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
            .number(7)
            .duration(143)
            .title("ringtone")
            .status(HydrationStatus.PARTIAL)
            .origin("https://100gecs.bandcamp.com/track/ringtone")
            .build(),
          """
          {"status":"PARTIAL","origin":"https://100gecs.bandcamp.com/track/ringtone","title":"ringtone","number":7,"duration":143}
          """.trim()
        )
    );
  }

}
