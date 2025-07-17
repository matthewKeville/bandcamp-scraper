package bandcamp_scraper.commands;

import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import bandcamp_scraper.AppSettings;
import bandcamp_scraper.logging.ConsoleAwareLogger;
import bandcamp_scraper_core.exceptions.fetching.FetchingException;
import bandcamp_scraper_core.extraction.AlbumExtractionContext;
import bandcamp_scraper_core.extraction.ArtistExtractionContext;
import bandcamp_scraper_core.extraction.TrackExtractionContext;
import bandcamp_scraper_core.fetcher.RootModelFetcher;
import bandcamp_scraper_core.pages.AlbumPage;
import bandcamp_scraper_core.pages.ArtistPage;
import bandcamp_scraper_core.pages.TrackPage;
import bandcamp_scraper_core.utils.http.UrlUtils;
import bandcamp_scraper_models.Album;
import bandcamp_scraper_models.Artist;
import bandcamp_scraper_models.Track;
import bandcamp_scraper_core.selenium.DriverContext;

@Component
@Command(
    name = "scrape",
    mixinStandardHelpOptions = true,
    description = "scrape a remote resource from bandcamp.com" +
    "\nsupports Artist and Album"
)
public class ScrapeCommand implements Runnable {

    private AppSettings appSettings;
    private DriverContext driverContext;
    private RootModelFetcher<Artist,ArtistPage,Artist.ArtistBuilder> artistFetcher;
    private RootModelFetcher<Album,AlbumPage,Album.AlbumBuilder> albumFetcher;
    private RootModelFetcher<Track,TrackPage,Track.TrackBuilder> trackFetcher;
    private ObjectMapper mapper;

    private ArtistExtractionContext artistExtractionContext = new ArtistExtractionContext();
    private AlbumExtractionContext albumExtractionContext = new AlbumExtractionContext();
    private TrackExtractionContext trackExtractionContext = new TrackExtractionContext();
    public static ConsoleAwareLogger LOG = ConsoleAwareLogger.getLogger(ScrapeCommand.class);

    public ScrapeCommand(
        @Autowired RootModelFetcher<Artist,ArtistPage,Artist.ArtistBuilder> artistFetcher,
        @Autowired RootModelFetcher<Album,AlbumPage,Album.AlbumBuilder> albumFetcher,
        @Autowired RootModelFetcher<Track,TrackPage,Track.TrackBuilder> trackFetcher,
        @Autowired ObjectMapper objectMapper,
        @Lazy DriverContext driverContext,
        @Autowired AppSettings appSettings
        ) {
        this.artistFetcher = artistFetcher;
        this.albumFetcher = albumFetcher;
        this.trackFetcher = trackFetcher;
        this.mapper = objectMapper;
        this.driverContext = driverContext;
        this.appSettings = appSettings;
    }

    @Option(names = {"-u", "--url"}, description = "URL of resource to scrape", required = true)
    private String url;

    @Override
    public void run() {
      try {
        if ( UrlUtils.isArtistURL(url) ) {
          Artist artist = artistFetcher.fetchModel(artistExtractionContext,driverContext,url);
          String json = mapper.writeValueAsString(artist);
          LOG.printOut(json);
        } else if ( UrlUtils.isAlbumURL(url)) {
          Album album = albumFetcher.fetchModel(albumExtractionContext,driverContext,url);
          String json = mapper.writeValueAsString(album);
          LOG.printOut(json);
        } else if ( UrlUtils.isTrackURL(url)) {
          Track track = trackFetcher.fetchModel(trackExtractionContext,driverContext,url);
          String json = mapper.writeValueAsString(track);
          LOG.printOut(json);
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
          LOG.printErr(String.format("Please create an issue ticket @ ",appSettings.getIssueTrackerUrl()));
      } catch (JsonProcessingException ex) {
          LOG.printErr(String.format("Encountered an internal error serializing the resource",url));
          LOG.printErr(String.format("Please create an issue ticket @ ",appSettings.getIssueTrackerUrl()));
      }

    }
}

