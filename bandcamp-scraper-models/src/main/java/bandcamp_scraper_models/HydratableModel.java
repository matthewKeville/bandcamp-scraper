package bandcamp_scraper_models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@Setter
@ToString
public abstract class HydratableModel {

  @Builder.Default
  private HydrationStatus status = HydrationStatus.DRY;
  private String origin;

  public enum HydrationStatus {
    DRY,
    PARTIAL,
    HYDRATED
  }

}
