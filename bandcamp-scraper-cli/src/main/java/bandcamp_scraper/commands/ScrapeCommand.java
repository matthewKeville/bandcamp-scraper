package bandcamp_scraper.commands;

import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import bandcamp_scraper.logging.ConsoleAwareLogger;
import bandcamp_scraper_core.exceptions.fetching.FetchingException;
import bandcamp_scraper_core.fetcher.FetchingContext;
import bandcamp_scraper_core.fetcher.RootModelFetcher;
import bandcamp_scraper_core.utils.http.UrlUtils;
import bandcamp_scraper_models.Album;
import bandcamp_scraper_models.Artist;
import bandcamp_scraper_core.selenium.DriverContext;

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
    private DriverContext driverContext = new DriverContext();
    private RootModelFetcher<Artist> artistFetcher;
    private FetchingContext<Artist> artistFetcherContext = FetchingContext.dummy();
    private RootModelFetcher<Album> albumFetcher;
    private FetchingContext<Album> albumFetchingContext = FetchingContext.dummy();
    private ObjectMapper mapper;
    public static ConsoleAwareLogger LOG = ConsoleAwareLogger.getLogger(ScrapeCommand.class);

    public ScrapeCommand(@Autowired RootModelFetcher<Artist> artistFetcher,
        @Autowired  RootModelFetcher<Album> albumFetcher,
        @Autowired ObjectMapper objectMapper
        ) {
        this.artistFetcher = artistFetcher;
        this.albumFetcher = albumFetcher;
        this.mapper = objectMapper;
    }

    @Option(names = {"-u", "--url"}, description = "URL of resource to scrape", required = true)
    private String url;

    @Override
    public void run() {
      try {
        if ( UrlUtils.isArtistURL(url) ) {
          Artist artist = artistFetcher.fetchModel(artistFetcherContext,driverContext,url);
          String json = mapper.writeValueAsString(artist);
          LOG.printOut(json);
        } else if ( UrlUtils.isAlbumURL(url)) {
          Album album = albumFetcher.fetchModel(albumFetchingContext,driverContext,url);
          String json = mapper.writeValueAsString(album);
          LOG.printOut(json);
        } else if ( UrlUtils.isTrackURL(url)) {
          LOG.printErr("Track Scraping Not Implemented");
        } else {
          LOG.printErr(String.format("Invalid URL : %s",url));
          LOG.printErr("- - -");
          LOG.printErr("Supported Resource URLS");
          LOG.printErr("Artist : " + UrlUtils.ARTIST_SLUG_URL);
          LOG.printErr("Album : " + UrlUtils.ALBUM_SLUG_URL);
          LOG.printErr("Track : " + UrlUtils.TRACK_SLUG_URL);
        }
      } 
      catch (FetchingException ex) {
          LOG.printErr(String.format("Encountered an internal error fetching the resource",url));
          LOG.printErr(String.format("Please create an issue ticket @ ",issueTrackerUrl));
      } catch (JsonProcessingException ex) {
          LOG.printErr(String.format("Encountered an internal error serializing the resource",url));
          LOG.printErr(String.format("Please create an issue ticket @ ",issueTrackerUrl));
      }

    }
}

