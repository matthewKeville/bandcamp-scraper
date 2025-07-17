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
@ToString(callSuper = true)
public class Track extends RootModel {
  // TODO : RootModelRef should be introduced to represent a 'gapped'
  // private Optional<RootRef<Album>> Album
  private String title;
  // Number Probably shouldn't be here, this an association with an album
  private int number;
  private int duration;
}

