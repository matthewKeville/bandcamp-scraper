package bandcamp_scraper_core.scraper.artist;

import java.util.Collections;

import org.springframework.stereotype.Component;

import bandcamp_scraper_models.Artist;

@Component
public class ArtistScraperSingleThreaded implements ArtistScraper {

  /** 
   * spoof impl
   */
  public Artist scrapeArtist(String url) {
    Artist artist = new Artist("Teenage Halloween","Asbury Park, NJ",Collections.emptyList());
    return artist;
  }

}
