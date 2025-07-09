package bandcamp_scraper_models;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@ToString
public class Track extends HydratableModel {
  private String title;
  private int number;
  private int duration;
}

