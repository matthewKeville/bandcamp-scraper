package bandcamp_scraper_core.selenium;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public class BasicDriverFactory extends DriverFactory {

  private final BrowserName browserName;
  private final boolean headless;
  private Duration implicitWait;

  public BasicDriverFactory (BrowserName browserName,boolean headless) {
    this.browserName = browserName;
    this.headless = headless;
  }

  public BasicDriverFactory (BrowserName browserName,boolean headless,Duration implicitWait) {
    this.browserName = browserName;
    this.headless = headless;
    this.implicitWait = implicitWait;
  }
  
  protected WebDriver makeDriver() throws WebDriverException, IllegalArgumentException {
    if (browserName == null) throw new IllegalArgumentException("browserName is null");
    switch (browserName) {

      case Firefox: {
        FirefoxOptions options = new FirefoxOptions();
        if ( headless ) {
          options.addArguments("-headless");
        }
        if ( implicitWait != null ) {
          options.setImplicitWaitTimeout(implicitWait);
        }
        return new FirefoxDriver(options);
      }

      case Chrome:  {
        ChromeOptions options = new ChromeOptions();
        if ( headless ) {
          options.addArguments("--headless=new");
        }
        if ( implicitWait != null ) {
          options.setImplicitWaitTimeout(implicitWait);
        }
        return new ChromeDriver(options);
      }

      default :
        throw new IllegalArgumentException(String.format("browserName %s is unexpected and unsupported",browserName));

    }

  }

}
