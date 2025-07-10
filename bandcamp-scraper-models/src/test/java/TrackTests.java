import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.Stream;

import com.fasterxml.jackson.databind.ObjectMapper;

import bandcamp_scraper_models.Track;
import bandcamp_scraper_models.HydratableModel.HydrationStatus;

public class TrackTests {

  public static final Logger LOG = LoggerFactory.getLogger(TrackTests.class);

  static Stream<Arguments> trackProvider() {
    return Stream.of(
        // ringtone
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
        ),
        // money machine
        Arguments.of(
          Track.builder()
            .number(2)
            .duration(114)
            .title("money machine")
            .status(HydrationStatus.PARTIAL)
            .origin("https://100gecs.bandcamp.com/track/money-machine")
            .build(),
          """
          {"status":"PARTIAL","origin":"https://100gecs.bandcamp.com/track/money-machine","title":"money machine","number":2,"duration":114}
          """.trim()
        )
    );
  }

  @ParameterizedTest
  @MethodSource("trackProvider")
  void trackSerializesCorrectly(Track track, String expectedJson) throws Exception {

      //Arrange
      ObjectMapper mapper = new ObjectMapper();

      //Act
      String actualJson = mapper.writeValueAsString(track);

      //Assert
      assertThat(actualJson)
        .as("JSON matches expectation")
        .isEqualTo(expectedJson);

  }


}
