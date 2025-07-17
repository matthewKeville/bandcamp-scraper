package bandcamp_scraper;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import com.fasterxml.jackson.databind.ObjectMapper;

import bandcamp_scraper.commands.RootCommand;
import bandcamp_scraper.commands.ScrapeCommand;
import bandcamp_scraper_core.fetcher.AlbumFetcherSingleThread;
import bandcamp_scraper_core.fetcher.ArtistFetcherSingleThread;
import bandcamp_scraper_core.fetcher.RootModelFetcher;
import bandcamp_scraper_core.fetcher.TrackFetcherSingleThread;
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
  public RootModelFetcher<Artist,ArtistPage,Artist.ArtistBuilder> artistScraper() {
    return new ArtistFetcherSingleThread();
  }
  @Bean
  public RootModelFetcher<Album,AlbumPage,Album.AlbumBuilder> albumScraper() {
    return new AlbumFetcherSingleThread();
  }
  @Bean
  public RootModelFetcher<Track,TrackPage,Track.TrackBuilder> trackScraper() {
    return new TrackFetcherSingleThread();
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

