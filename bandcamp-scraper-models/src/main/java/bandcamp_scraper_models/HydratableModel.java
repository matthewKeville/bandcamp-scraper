package bandcamp_scraper_models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@Setter
@ToString
public abstract class HydratableModel {

  private HydrationStatus status;
  private String origin;

  public enum HydrationStatus {
    DRY,
    PARTIAL,
    HYDRATED
  }

}
