package bandcamp_scraper_core.selenium;

import java.time.Duration;

import org.openqa.selenium.WebDriver;

/**
 *  Responsible for providing a DriverFactory, and limitations
 *  about threading.
 */
public class DriverContext {

  private final DriverFactory driverFactory;

  public DriverContext(DriverFactory driverFactory) {
    this.driverFactory = driverFactory;
  }

  public WebDriver getDriver() {
    return driverFactory.createDriver();
  }

  public static DriverContext getDefault() {
    return new DriverContext(new BasicDriverFactory(DriverFactory.BrowserName.Chrome, true,Duration.ofMillis(500)));
  }

  public static DriverContext getHeadful() {
    return new DriverContext(new BasicDriverFactory(DriverFactory.BrowserName.Chrome, false,Duration.ofMillis(500)));
  }

}
