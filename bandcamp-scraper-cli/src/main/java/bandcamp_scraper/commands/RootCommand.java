package bandcamp_scraper.commands;

import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import org.springframework.stereotype.Component;

@Component
@Command(
    name = "",
    mixinStandardHelpOptions = true,
    description = "A CLI for scraping resources from bandcamp.com",
    subcommands = {ScrapeCommand.class}
)
public class RootCommand implements Runnable {

    // @Option(names = {"-n", "--name"}, description = "Name of the user")
    // private String name;

    @Override
    public void run() {
        System.out.println("hi from command");
    }
}

