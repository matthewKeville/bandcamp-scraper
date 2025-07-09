package bandcamp_scraper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import bandcamp_scraper.commands.RootCommand;
import picocli.CommandLine;
import picocli.CommandLine.Command;

@SpringBootApplication
public class BandcampScraperCLI {

  public static void main(String[] args) {
    ApplicationContext ctx = SpringApplication.run(BandcampScraperCLI.class, args);
    int exitCode = new CommandLine(ctx.getBean(RootCommand.class)).execute(args);
    System.exit(exitCode);
  }

}
