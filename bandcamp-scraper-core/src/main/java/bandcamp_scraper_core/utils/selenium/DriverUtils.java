package bandcamp_scraper_core.utils.selenium;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class DriverUtils {

  /**
   *
   * Wrapper Utility around WebDriver.findElements(By by) to return
   * a default first element or null reference and the count of occurrences.
   *
   * @param driver : A WebDriver instance
   * @param by : A By instance
   * @return ElmCountPair : First WebElement reference or null and count of occurences
   */
  public static ElmCountPair findElmCountPair(WebDriver driver,By by) {
    List<WebElement> elms = driver.findElements(by);
    WebElement elm = null;
    if (elms.size() != 0) {
      elm = elms.get(0);
    }
    return new ElmCountPair(elm, elms.size());
  }

}
