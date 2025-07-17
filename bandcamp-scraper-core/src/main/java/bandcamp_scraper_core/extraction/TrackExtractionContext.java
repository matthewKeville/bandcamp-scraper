package bandcamp_scraper_core.extraction;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import bandcamp_scraper_core.pages.TrackPage;
import bandcamp_scraper_models.Track;

public class TrackExtractionContext extends RootModelExtractionContext<Track,TrackPage,Track.TrackBuilder> {

  public static final Logger LOG = LoggerFactory.getLogger(TrackExtractionContext.class);

   public TrackExtractionContext() {
    addExtractionStep((page, builder) -> {
      String trackTitle = page.getTrackTitle();
      builder.title(trackTitle);
    });
    addExtractionStep((page, builder) -> {
      Optional<Integer> trackDuration = page.getTrackDuration();
      trackDuration.ifPresent( t -> builder.duration(t));
    });
  }

}
