package bandcamp_scraper_core.scraper.artist;

import bandcamp_scraper_core.exceptions.scraping.ScrapingException;
import bandcamp_scraper_core.exceptions.scraping.artist.ArtistScrapingException;
import bandcamp_scraper_models.Artist;

public interface ArtistScraper {

  /**
   * @param url the url of the artist
   * @return an Artist object 
   * @throws ScrapingException if scraping fails
   */
  public Artist scrapeArtist(String url) throws ArtistScrapingException,ScrapingException;

}
