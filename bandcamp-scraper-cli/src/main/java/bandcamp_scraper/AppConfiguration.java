package bandcamp_scraper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;

import bandcamp_scraper.commands.RootCommand;
import bandcamp_scraper.commands.ScrapeCommand;
import bandcamp_scraper_core.scraper.album.AlbumScraper;
import bandcamp_scraper_core.scraper.album.AlbumScraperSingleThreaded;
import bandcamp_scraper_core.scraper.artist.ArtistScraper;
import bandcamp_scraper_core.scraper.artist.ArtistScraperSingleThreaded;
import bandcamp_scraper_models.BandcampObjectMapper;
import picocli.CommandLine;

@Configuration
public class AppConfiguration {
  @Bean
  public ArtistScraper artistScraper() {
    return new ArtistScraperSingleThreaded();
  }
  @Bean
  public AlbumScraper albumScraper() {
    return new AlbumScraperSingleThreaded();
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

