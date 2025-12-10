package bandcamp_scraper;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import com.fasterxml.jackson.databind.ObjectMapper;

import bandcamp_scraper.commands.RootCommand;
import bandcamp_scraper.commands.ScrapeCommand;
import bandcamp_scraper_core.extraction.AlbumExtractionContext;
import bandcamp_scraper_core.extraction.ArtistExtractionContext;
import bandcamp_scraper_core.extraction.RootModelExtractionContext;
import bandcamp_scraper_core.extraction.TrackExtractionContext;
import bandcamp_scraper_core.fetcher.RootModelFetcher;
import bandcamp_scraper_core.fetcher.synchronous.AlbumFetcherSingleThread;
import bandcamp_scraper_core.fetcher.synchronous.ArtistFetcherSingleThread;
import bandcamp_scraper_core.fetcher.synchronous.TrackFetcherSingleThread;
import bandcamp_scraper_core.pages.AlbumPage;
import bandcamp_scraper_core.pages.ArtistPage;
import bandcamp_scraper_core.pages.TrackPage;
import bandcamp_scraper_core.selenium.BasicDriverFactory;
import bandcamp_scraper_core.selenium.DriverContext;
import bandcamp_scraper_models.Album;
import bandcamp_scraper_models.Artist;
import bandcamp_scraper_models.BandcampObjectMapper;
import bandcamp_scraper_models.Track;
import picocli.CommandLine;

@Configuration
public class AppConfiguration {
  /**
   * RootCommand supports optional flags that override global behaviour,
   * like setting headless for the browser, so we mark this as lazy, so root
   * command can modify the AppSettings before this bean is created.
   */
  @Lazy
  @Bean
  public DriverContext driverContext(@Autowired AppSettings appSettings) {
    return new DriverContext(new BasicDriverFactory(appSettings.getBrowserName(),appSettings.isHeadless(),appSettings.getImplicitWait()));
  }

  @Bean 
  public RootModelExtractionContext<Artist,ArtistPage,Artist.ArtistBuilder> artistExtractionContext() {
    return new ArtistExtractionContext();
  }
  @Bean 
  public RootModelExtractionContext<Album,AlbumPage,Album.AlbumBuilder> albumExtractionContext() {
    return new AlbumExtractionContext();
  }
  @Bean 
  public RootModelExtractionContext<Track,TrackPage,Track.TrackBuilder> TrackExtractionContext() {
    return new TrackExtractionContext();
  }

  @Bean
  public RootModelFetcher<Artist> artistFetcher(
      @Autowired DriverContext driverContext,
      @Autowired RootModelExtractionContext<Artist,ArtistPage,Artist.ArtistBuilder> extractionContext) {
    return new ArtistFetcherSingleThread(driverContext,extractionContext);
  }
  @Bean
  public RootModelFetcher<Album> albumFetcher(
      @Autowired DriverContext driverContext,
      @Autowired RootModelExtractionContext<Album,AlbumPage,Album.AlbumBuilder> extractionContext) {
    return new AlbumFetcherSingleThread(driverContext,extractionContext);
  }
  @Bean
  public RootModelFetcher<Track> trackFetcher(
      @Autowired DriverContext driverContext,
      @Autowired RootModelExtractionContext<Track,TrackPage,Track.TrackBuilder> extractionContext) {
    return new TrackFetcherSingleThread(driverContext,extractionContext);
  }

  @Bean 
  public ObjectMapper objectMapper() {
    return BandcampObjectMapper.newInstance();
  }
  @Bean
  public CommandLine commandLine(@Autowired RootCommand rootCommand,
      @Autowired ScrapeCommand scrapeCommand) {
    return new CommandLine(rootCommand).addSubcommand("scrape",scrapeCommand);
  }
}

