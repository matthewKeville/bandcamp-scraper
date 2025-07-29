package bandcamp_scraper_models;

import java.util.Optional;

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
  //refs : While Track is within the Aggregate Root of Album or Artist
  //Tracks can be a standalone entity discovered via search or other
  //mechanisms, and in that case the associations are lost unless
  //we embed them here.
  private Optional<RootModelRef> album = Optional.empty();
  //future note : Collabs will link back to a primary artist
  //but the Artist display name on the track may reflect
  //the nature of the collab
  //https://apesofthestate.bandcamp.com/album/they-cant-kill-us-all
  private RootModelRef artist;
  private String title;
  private int duration;
}

