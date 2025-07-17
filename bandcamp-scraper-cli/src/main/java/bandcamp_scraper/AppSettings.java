package bandcamp_scraper;

import java.time.Duration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import bandcamp_scraper_core.selenium.DriverFactory;
import bandcamp_scraper_core.selenium.DriverFactory.BrowserName;
import lombok.Getter;
import lombok.Setter;

@Component
@ConfigurationProperties("properties")
@Getter
@Setter
public class AppSettings {

  private DriverFactory.BrowserName browserName = BrowserName.Firefox;
  private boolean headless = true;
  private final String issueTrackerUrl = "https://github.com/matthewKeville/bandcamp-scraper/issues";
  private Duration implicitWait = Duration.ofMillis(300);

}

