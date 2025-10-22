package bandcamp_scraper_core.extraction;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import bandcamp_scraper_core.pages.ArtistPage;
import bandcamp_scraper_models.Artist;
import bandcamp_scraper_models.Release;
import bandcamp_scraper_models.RootModelRef;
import bandcamp_scraper_shared.enums.RootModelType;

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
    addExtractionStep((page, builder) -> {
      if (page.hasRecommendations()) {
        List<String> links = page.getRecomendationPage().getRecomendedLinks();
        var recs = 
            links.stream()
            .map(ln -> new RootModelRef(RootModelType.ARTIST, ln))
            .collect(Collectors.toSet());
        builder.recommendations(recs);
      } else {
        builder.recommendations(Collections.emptySet());
      }
    });
  }

}
