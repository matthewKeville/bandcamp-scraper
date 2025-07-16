package bandcamp_scraper_core.selenium;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;

import bandcamp_scraper_core.exceptions.driver.DriverFactoryException;

/**
 * Encapsulates WebDriver creation logic, built for DriverContext.
 */
public abstract class DriverFactory {

  public static enum BrowserName {
    Firefox,
    Chrome
  }

  /**
   * Actually creates a WebDriver instance, implemented by subclasses.
   * @throws WebDriverException if the Selenium driver fails to initialize
   * @throws IllegalArgumentException if browser configuration is invalid
   */
  protected abstract WebDriver makeDriver() throws WebDriverException,IllegalArgumentException;

  /**
   * Return a WebDriver
   * @throws DriverFactoryException
   */
  public WebDriver createDriver() throws DriverFactoryException {
    try {
      return makeDriver();
    } catch (WebDriverException | IllegalArgumentException ex) {
      throw new DriverFactoryException(ex);
    }
  }

}
