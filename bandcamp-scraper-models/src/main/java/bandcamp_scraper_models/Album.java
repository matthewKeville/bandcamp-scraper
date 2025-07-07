package bandcamp_scraper_models;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@EqualsAndHashCode
@ToString
@NoArgsConstructor
@AllArgsConstructor
public final class Album extends ReleaseItem implements Release {

  private String albumTitle;
  private float digitalCost;
  private List<Track> tracks;
  //private Art art (ArtLink/ArtBase64)

}
