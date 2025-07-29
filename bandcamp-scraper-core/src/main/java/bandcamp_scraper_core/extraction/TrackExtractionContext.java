package bandcamp_scraper_core.extraction;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import bandcamp_scraper_core.pages.TrackPage;
import bandcamp_scraper_models.RootModelRef;
import bandcamp_scraper_models.Track;
import bandcamp_scraper_shared.enums.RootModelType;

public class TrackExtractionContext extends RootModelExtractionContext<Track,TrackPage,Track.TrackBuilder> {

  public static final Logger LOG = LoggerFactory.getLogger(TrackExtractionContext.class);

   public TrackExtractionContext() {
    addExtractionStep((page, builder) -> {
      String trackTitle = page.getTrackTitle();
      builder.title(trackTitle);
    });
    addExtractionStep((page, builder) -> {
      Optional<Integer> trackDuration = page.getTrackTime();
      trackDuration.ifPresent( t -> builder.duration(t));
    });
    addExtractionStep((page, builder) -> {
      Optional<String> albumUrl = page.getAlbumUrl();
      albumUrl.ifPresent( 
          t -> builder.album(Optional.of(new RootModelRef(RootModelType.ALBUM, t))));
    });
    addExtractionStep((page, builder) -> {
      Optional<String> artistUrl = page.getArtistUrl();
      artistUrl.ifPresent( 
          t -> builder.artist(Optional.of(new RootModelRef(RootModelType.ARTIST, t))));
    });
  }

}
