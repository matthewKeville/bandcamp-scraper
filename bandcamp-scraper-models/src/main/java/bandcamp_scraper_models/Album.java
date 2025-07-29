package bandcamp_scraper_models;

import java.util.List;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import lombok.Builder;

@Getter
@Setter
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public final class Album extends RootModel {

  private String title;
  private float price;
  private List<AlbumTrack> tracks;

  /**
   * Wrapper Type to represent the association between a track and an album
   */
  @Getter
  @Setter
  @Builder
  @EqualsAndHashCode
  @ToString(callSuper = true)
  public static class AlbumTrack implements Model {
    public Track track;
    public int number;
  }

}
