package bandcamp_scraper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import bandcamp_scraper.commands.RootCommand;
import bandcamp_scraper.logging.ConsoleAwareLogger;
import picocli.CommandLine;

@EnableConfigurationProperties
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
    /**
     * Hacky work-around for picocli, subject to change.
     * Let picocli parse the arguments for all commands and sub commands,
     * force the workaround parseFlags to execute on the RootCommands
     * populated options, then execute the targetted subcommand.
     */
    CommandLine.ParseResult parseResult = commandLine.parseArgs(args);
    RootCommand root = parseResult.commandSpec().commandLine().getCommand();
    root.parseFlags();
    parseResult.commandSpec().commandLine().execute(args);
  }


}
