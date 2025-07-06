package bandcamp_scraper_models;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@EqualsAndHashCode
@ToString
@AllArgsConstructor
public final class ReleaseLink extends ReleaseItem {
  public String Url;
}
