package bandcamp_scraper.commands;

import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bandcamp_scraper_core.exceptions.http.InvalidResourceUrlException;
import bandcamp_scraper_core.exceptions.scraping.ScrapingException;
import bandcamp_scraper_core.scraper.album.AlbumScraper;
import bandcamp_scraper_core.scraper.artist.ArtistScraper;
import bandcamp_scraper_core.utils.http.UrlUtils;
import bandcamp_scraper_models.Album;
import bandcamp_scraper_models.Artist;
import bandcamp_scraper_models.Track;

@Component
@Command(
    name = "scrape",
    mixinStandardHelpOptions = true,
    description = "scrape a remote resource from bandcamp.com" +
    "\nsupports Artist and Album"
)
public class ScrapeCommand implements Runnable {

    // TODO : Slot into AppSettings construct through application.yml
    private final String issueTrackerUrl = "https://github.com/matthewKeville/bandcamp-scraper/issues";
    private ArtistScraper artistScraper;
    private AlbumScraper albumScraper;

    public ScrapeCommand(@Autowired ArtistScraper artistScraper,
        @Autowired AlbumScraper albumScraper
        ) {
        this.artistScraper = artistScraper;
        this.albumScraper = albumScraper;
    }


    @Option(names = {"-u", "--url"}, description = "URL of resource to scrape", required = true)
    private String url;

    @Override
    public void run() {
      try {
        var clazz = UrlUtils.resolveResourceModelType(url);
        if (clazz == Artist.class) {
          System.out.print(artistScraper.scrapeArtist(url).toString());
        } else if (clazz == Album.class) {
          System.out.print(albumScraper.scrapeAlbum(url).toString());
        } else if (clazz == Track.class) {
          System.err.println("Track Scraping Not Implemented");
        } else {
          System.err.println("Unsupported Resource : " + clazz.getName());
        }
      } catch (InvalidResourceUrlException ex) {
          System.err.println(String.format("Invalid URL : %s",url));
          System.err.println("- - -");
          System.err.println("Supported Resource URLS");
          System.err.println("Artist : " + UrlUtils.ARTIST_SLUG_URL);
          System.err.println("Album : " + UrlUtils.ALBUM_SLUG_URL);
          System.err.println("Track : " + UrlUtils.TRACK_SLUG_URL);
      }catch (ScrapingException ex) {
          System.err.println(String.format("Encountered an internal error fetching the resource",url));
          System.err.println(String.format("Please create an issue ticket @ ",issueTrackerUrl));
      }

    }
}

