package bandcamp_scraper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;

import bandcamp_scraper.commands.RootCommand;
import bandcamp_scraper.commands.ScrapeCommand;
import bandcamp_scraper_core.fetcher.AlbumFetcherSingleThread;
import bandcamp_scraper_core.fetcher.ArtistFetcherSingleThread;
import bandcamp_scraper_core.fetcher.RootModelFetcher;
import bandcamp_scraper_models.Album;
import bandcamp_scraper_models.Artist;
import bandcamp_scraper_models.BandcampObjectMapper;
import picocli.CommandLine;

@Configuration
public class AppConfiguration {
  @Bean
  public RootModelFetcher<Artist> artistScraper() {
    return new ArtistFetcherSingleThread();
  }
  @Bean
  public RootModelFetcher<Album> albumScraper() {
    return new AlbumFetcherSingleThread();
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

