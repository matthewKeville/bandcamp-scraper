
package bandcamp_scraper_core.extraction;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import bandcamp_scraper_core.pages.AlbumPage;
import bandcamp_scraper_models.Album;
import bandcamp_scraper_models.RootModelRef;
import bandcamp_scraper_models.Track;
import bandcamp_scraper_models.HydratableModel.HydrationStatus;
import bandcamp_scraper_shared.enums.RootModelType;
import bandcamp_scraper_shared.utils.http.UrlUtils;

public class AlbumExtractionContext extends RootModelExtractionContext<Album,AlbumPage,Album.AlbumBuilder> {

  public static final Logger LOG = LoggerFactory.getLogger(AlbumExtractionContext.class);

   public AlbumExtractionContext() {
    addExtractionStep((page, builder) -> {
      String albumTitle = page.getAlbumTitle();
      builder.title(albumTitle);
    });
    addExtractionStep((page, builder) -> {
      Optional<Float> albumPrice = page.getDigitalAlbumPrice();
      albumPrice.ifPresent(price -> builder.price(price));
    });
    addExtractionStep((page, builder) -> {

      int numTracks = page.extractTrackCount();
      List<Album.AlbumTrack> tracks = new ArrayList<Album.AlbumTrack>();

      // track numbers are 1-based indexing
      for ( int i = 1; i < numTracks+1; i++ ) {

        String origin = page.extractTrackLink(i);
        if ( origin == null || origin.isEmpty() ) {
          continue;
        }

        Track.TrackBuilder trackBuilder = Track.builder();
        trackBuilder.title(page.extractTrackTitle(i));
        trackBuilder.duration(page.extractTrackTime(i));
        trackBuilder.artist(
            new RootModelRef(RootModelType.ARTIST, UrlUtils.getArtistBaseUrl(origin)+"/music")
        );
        trackBuilder.album(
          Optional.of(
            new RootModelRef(RootModelType.ALBUM, page.getOrigin())
          )
        );
        trackBuilder.origin(origin);
        trackBuilder.status(HydrationStatus.PARTIAL);
        tracks.add(
          Album.AlbumTrack.builder()
            .track(trackBuilder.build())
            .number(i)
            .build()
        );

      }

      builder.tracks(tracks);
      
    });
  }

}
