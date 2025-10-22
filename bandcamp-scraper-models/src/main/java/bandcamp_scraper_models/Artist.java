package bandcamp_scraper_models;

import java.util.Collections;
import java.util.Set;

import lombok.Builder;
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
public class Artist extends RootModel {

  private String name;
  private String location;
  private Set<Release> releases;
  private Set<RootModelRef> recommendations;

}
