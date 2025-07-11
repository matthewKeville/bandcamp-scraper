package bandcamp_scraper.commands;

import picocli.CommandLine.Command;

import org.springframework.stereotype.Component;

@Component
@Command(
    name = "",
    mixinStandardHelpOptions = true,
    description = "A CLI for scraping resources from bandcamp.com"
)
public class RootCommand implements Runnable {

    @Override
    public void run() {
    }
}

