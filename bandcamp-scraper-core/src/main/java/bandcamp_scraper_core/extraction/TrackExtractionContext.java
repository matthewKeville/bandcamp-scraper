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
      builder.duration(page.getTrackTime());
    });

    addExtractionStep((page, builder) -> {
      Optional<String> albumUrl = page.getTrackAlbumUrl();
      if (albumUrl == null) {
          builder.album(null);
          return;
      }
      if ( albumUrl.isPresent() ) {
          builder.album(Optional.of(new RootModelRef(RootModelType.ALBUM, albumUrl.get())));
      } else {
          builder.album(Optional.empty());
      }
    });

    addExtractionStep((page, builder) -> {
      builder.artist(new RootModelRef(RootModelType.ARTIST, page.getArtistUrl()));
    });

  }

}
