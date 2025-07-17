package bandcamp_scraper_core.utils.parsing;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ParsingUtils {
  
  public static Logger LOG = LoggerFactory.getLogger(ParsingUtils.class);

  /**
   * Given a string representing a duration of time in the format
   * mm:ss, try to return that duration in seconds as an Integer
   *
   * @param timeText the duration string to be parsed mm:ss
   * @return Optional.empty() if parsing fails or,
   *         Optional.of(<DurationSeconds>)
   */
  public static Optional<Integer> tryParseDurationInSeconds(String timeText) {
    try {
      final Pattern pattern = Pattern.compile("(\\d+):(\\d+)");
      Matcher matcher = pattern.matcher(timeText);
      if (!matcher.find()) {
        LOG.warn("couldn't locate time match in : " + timeText);
        return Optional.empty();
      }

      int minutes = Integer.parseInt(matcher.group(1));
      int seconds = Integer.parseInt(matcher.group(2));
      int elapsed = minutes * 60 + seconds;

      return Optional.of(elapsed);

    } catch (NumberFormatException ex) {
      LOG.warn("time parse exception " + ex.getMessage());
      return Optional.empty();
    }

  }


}
