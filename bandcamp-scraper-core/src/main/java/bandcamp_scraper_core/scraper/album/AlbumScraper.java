package bandcamp_scraper_core.scraper.album;

import bandcamp_scraper_core.exceptions.scraping.ScrapingException;
import bandcamp_scraper_core.exceptions.scraping.album.AlbumScrapingException;
import bandcamp_scraper_models.Album;

public interface AlbumScraper {

  /**
   * @param url the url of the album
   * @return an Album object 
   * @throws ScrapingException if scraping fails
   */
  public Album scrapeAlbum(String url) throws AlbumScrapingException,ScrapingException;

}
