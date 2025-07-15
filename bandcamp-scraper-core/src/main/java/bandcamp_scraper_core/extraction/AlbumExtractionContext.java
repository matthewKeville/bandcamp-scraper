
package bandcamp_scraper_core.extraction;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import bandcamp_scraper_core.pages.AlbumPage;
import bandcamp_scraper_models.Album;
import bandcamp_scraper_models.Track;

public class AlbumExtractionContext extends RootModelExtractionContext<Album,AlbumPage,Album.AlbumBuilder> {

  public static final Logger LOG = LoggerFactory.getLogger(ArtistExtractionContext.class);

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
      List<Track> tracks = page.getTracks();
      builder.tracks(tracks);
    });

  }

}
