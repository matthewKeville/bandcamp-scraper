package bandcamp_scraper_core.extraction;

import java.util.Optional;
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
      LOG.info("scraping artistName");
      String artistName = page.getArtistName();
      builder.name(artistName);
    });
    addExtractionStep((page, builder) -> {
      LOG.info("scraping releaseItems");
      Set<Release> releaseItems = page.getReleasesItems();
      builder.releases(releaseItems);
    });
    addExtractionStep((page, builder) -> {
      if (page.hasSidebar()) {
        LOG.info("scraping location");
        Optional<String> optLocation = page.getBandNameLocation();
        optLocation.ifPresent( loc -> builder.location(loc));
      }
    });
  }

}
