package bandcamp_scraper.commands;

import picocli.CommandLine.ArgGroup;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bandcamp_scraper.AppSettings;
import bandcamp_scraper.logging.ConsoleAwareLogger;

@Component
@Command(
    name = "",
    mixinStandardHelpOptions = true,
    description = "A CLI for scraping resources from bandcamp.com"
)
public class RootCommand implements Runnable {

    //let user override driver behaviour
    static class HeadlessExclusive {
      @Option(names = {"-H", "--headful"}, description = "Set webdriver to headful mode")
      boolean headful = false;
      @Option(names = {"-L", "--headless"}, description = "Set webdriver to headless mode")
      boolean headless = false;
    }

    private AppSettings appSettings;
    public static ConsoleAwareLogger LOG = ConsoleAwareLogger.getLogger(RootCommand.class);

    public RootCommand(@Autowired AppSettings appSettings) {
      this.appSettings = appSettings;
    }

    @ArgGroup(exclusive = true, multiplicity = "0..1")
    HeadlessExclusive headlessExclusive;

    @Override
    public void run() {}

    /**
     * Hacky work-around with picocli, when a subcommand is executing
     * the root command doesn't execute, so i'm trying to leverage picoclis flag structure
     * external, see BandcampScraperCLI.run for context.
     */
    public void parseFlags() {
      if (headlessExclusive != null) {
        appSettings.setHeadless(headlessExclusive.headless);
      }
    }
}

