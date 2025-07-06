package bandcamp_scraper_core.utils.selenium;

import org.openqa.selenium.WebElement;

public class ElmCountPair {

  private WebElement elm;
  private int count;

  public ElmCountPair(WebElement elm,int count) {
    this.elm = elm;
    this.count = count;
  }

  public WebElement getElm() {
    return elm;
  }

  public int getCount() {
    return count;
  }


}
