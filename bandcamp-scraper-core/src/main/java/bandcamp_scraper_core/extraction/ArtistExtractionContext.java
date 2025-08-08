package bandcamp_scraper_core.extraction;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import bandcamp_scraper_core.pages.ArtistPage;
import bandcamp_scraper_models.Artist;
import bandcamp_scraper_models.Release;

public class ArtistExtractionContext extends RootModelExtractionContext<Artist,ArtistPage,Artist.ArtistBuilder> {

  public static final Logger LOG = LoggerFactory.getLogger(ArtistExtractionContext.class);

   public ArtistExtractionContext() {

    addExtractionStep((page, builder) -> {
      String artistName = page.getArtistName();
      builder.name(artistName);
    });

    addExtractionStep((page, builder) -> {
      Set<Release> releaseItems = page.getReleasesItems();
      builder.releases(releaseItems);
    });

    addExtractionStep((page, builder) -> {
      if (page.hasSidebar()) {
        builder.location(page.getBandNameLocation());
      }
    });
  }

}
