package bandcamp_scraper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import bandcamp_scraper.logging.ConsoleAwareLogger;
import picocli.CommandLine;

@SpringBootApplication
public class BandcampScraperCLI implements CommandLineRunner {

  private CommandLine commandLine;

  public BandcampScraperCLI(@Autowired CommandLine commandLine) {
    this.commandLine = commandLine;
  }

  public static void main(String[] args) {
    SpringApplication.run(BandcampScraperCLI.class, args);
  }

  public void run(String... args) {
    commandLine.execute(args);
  }


}
