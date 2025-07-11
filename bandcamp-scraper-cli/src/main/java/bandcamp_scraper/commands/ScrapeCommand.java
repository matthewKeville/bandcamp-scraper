package bandcamp_scraper.commands;

import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import bandcamp_scraper.logging.ConsoleAwareLogger;
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
    private ObjectMapper mapper;
    public static ConsoleAwareLogger LOG = ConsoleAwareLogger.getLogger(ScrapeCommand.class);

    public ScrapeCommand(@Autowired ArtistScraper artistScraper,
        @Autowired AlbumScraper albumScraper,
        @Autowired ObjectMapper objectMapper
        ) {
        this.artistScraper = artistScraper;
        this.albumScraper = albumScraper;
        this.mapper = objectMapper;
    }


    @Option(names = {"-u", "--url"}, description = "URL of resource to scrape", required = true)
    private String url;

    @Override
    public void run() {
      try {
        var clazz = UrlUtils.resolveResourceModelType(url);
        if (clazz == Artist.class) {
          Artist artist = artistScraper.scrapeArtist(url);
          String json = mapper.writeValueAsString(artist);
          LOG.printOut(json);
        } else if (clazz == Album.class) {
          Album album = albumScraper.scrapeAlbum(url);
          String json = mapper.writeValueAsString(album);
          LOG.printOut(json);
        } else if (clazz == Track.class) {
          LOG.printErr("Track Scraping Not Implemented");
        } else {
          LOG.printErr("Unsupported Resource : " + clazz.getName());
        }
      } catch (InvalidResourceUrlException ex) {
          LOG.printErr(String.format("Invalid URL : %s",url));
          LOG.printErr("- - -");
          LOG.printErr("Supported Resource URLS");
          LOG.printErr("Artist : " + UrlUtils.ARTIST_SLUG_URL);
          LOG.printErr("Album : " + UrlUtils.ALBUM_SLUG_URL);
          LOG.printErr("Track : " + UrlUtils.TRACK_SLUG_URL);
      } catch (ScrapingException ex) {
          LOG.printErr(String.format("Encountered an internal error fetching the resource",url));
          LOG.printErr(String.format("Please create an issue ticket @ ",issueTrackerUrl));
      } catch (JsonProcessingException ex) {
          LOG.printErr(String.format("Encountered an internal error serializing the resource",url));
          LOG.printErr(String.format("Please create an issue ticket @ ",issueTrackerUrl));
      }

    }
}

