package bandcamp_scraper.commands;

import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import bandcamp_scraper.AppSettings;
import bandcamp_scraper.logging.ConsoleAwareLogger;
import bandcamp_scraper_core.exceptions.fetching.FetchingException;
import bandcamp_scraper_core.fetcher.RootModelFetcher;
import bandcamp_scraper_models.Album;
import bandcamp_scraper_models.Artist;
import bandcamp_scraper_models.Track;
import bandcamp_scraper_shared.utils.http.UrlUtils;
import bandcamp_scraper_shared.enums.RootModelType;

@Component
@Command(
    name = "scrape",
    mixinStandardHelpOptions = true,
    description = "scrape a remote resource from bandcamp.com" +
    "\nsupports Artist and Album"
)
public class ScrapeCommand implements Runnable {

    private AppSettings appSettings;
    private RootModelFetcher<Artist> artistFetcher;
    private RootModelFetcher<Album> albumFetcher;
    private RootModelFetcher<Track> trackFetcher;
    private ObjectMapper mapper;

    public static ConsoleAwareLogger LOG = ConsoleAwareLogger.getLogger(ScrapeCommand.class);

    public ScrapeCommand(
        @Autowired RootModelFetcher<Artist> artistFetcher,
        @Autowired RootModelFetcher<Album> albumFetcher,
        @Autowired RootModelFetcher<Track> trackFetcher,
        @Autowired ObjectMapper objectMapper,
        @Autowired AppSettings appSettings
        ) {
        this.artistFetcher = artistFetcher;
        this.albumFetcher = albumFetcher;
        this.trackFetcher = trackFetcher;
        this.mapper = objectMapper;
        this.appSettings = appSettings;
    }

    @Option(names = {"-u", "--url"}, description = "URL of resource to scrape", required = true)
    private String url;

    @Override
    public void run() {
      try {
        switch ( UrlUtils.resolveResourceModelType(url) ) {
          case RootModelType.ARTIST: {
            Artist artist = artistFetcher.fetchModel(url);
            String json = mapper.writeValueAsString(artist);
            LOG.printOut(json);
            break;
          }
          case RootModelType.ALBUM: {
            Album album = albumFetcher.fetchModel(url);
            String json = mapper.writeValueAsString(album);
            LOG.printOut(json);
            break;
          }
          case RootModelType.TRACK: {
            Track track = trackFetcher.fetchModel(url);
            String json = mapper.writeValueAsString(track);
            LOG.printOut(json);
            break;
          }
          default:
            LOG.printErr(String.format("Invalid URL : %s",url));
            LOG.printErr("- - -");
            LOG.printErr("Supported Resource URLS");
            LOG.printErr("Artist : " + UrlUtils.ARTIST_SLUG_URL);
            LOG.printErr("Album : " + UrlUtils.ALBUM_SLUG_URL);
            LOG.printErr("Track : " + UrlUtils.TRACK_SLUG_URL);
            break;
        }
      } 
      catch (FetchingException ex) {
          LOG.printErr(String.format("Encountered an internal error fetching the resource",url));
          LOG.printErr(String.format("Please create an issue ticket @ ",appSettings.getIssueTrackerUrl()));
      } catch (JsonProcessingException ex) {
          LOG.printErr(String.format("Encountered an internal error serializing the resource",url));
          LOG.printErr(String.format("Please create an issue ticket @ ",appSettings.getIssueTrackerUrl()));
      }

    }
}

