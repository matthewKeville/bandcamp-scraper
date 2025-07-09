package bandcamp_scraper.commands;

import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import org.springframework.stereotype.Component;

@Component
@Command(
    name = "scrape",
    mixinStandardHelpOptions = true,
    description = "scrape a remote resource from bandcamp.com"
)
public class ScrapeCommand implements Runnable {

    @Option(names = {"-u", "--url"}, description = "URL of resource to scrape", required = true)
    private String url;

    // @Option(names = {"-n", "--name"}, description = "Name of the user")
    // private String name;

    @Override
    public void run() {
        System.out.println(String.format("would scrape %s", url));
    }
}

