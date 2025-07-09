package bandcamp_scraper_models;

import java.util.Optional;
import java.util.regex.Pattern;

import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * Discriminated Union for bandcamp release types
 */
@ToString
@EqualsAndHashCode(exclude = "presentType")
public class Release {

  //Regex here as static member
  private static final Pattern ALBUM_PATTERN = Pattern.compile("^https://[a-zA-Z0-9-]+\\.bandcamp\\.com/album/[^/]+/?$");
  private static final Pattern TRACK_PATTERN = Pattern.compile("^https://[a-zA-Z0-9-]+\\.bandcamp\\.com/track/[^/]+/?$");


  private Optional<Track> track = Optional.empty();
  private Optional<Album> album = Optional.empty();
  private Class<?> presentType;

  public Release(Track track) {
    this.track = Optional.of(track);
    this.album = Optional.empty();
    this.presentType = Track.class;
  }

  public Release(Album album) {
    this.album = Optional.of(album);
    this.track = Optional.empty();
    this.presentType = Album.class;
  }

  public void setTrack(Track track) {
    this.track = Optional.of(track);
    this.album = Optional.empty();
    this.presentType = Track.class;
  }

  public Optional<Track> getTrack() {
    return this.track;
  }

  public void setAlbum(Album album) {
    this.album = Optional.of(album);
    this.track = Optional.empty();
    this.presentType = Album.class;
  }

  public Optional<Album> getAlbum() {
    return this.album;
  }

  public Class<?> getPresentType() {
    return this.presentType;
  }

  public static Optional<Release> tryCreateFromHref(String href) {
    try {
      return Optional.of(createFromHref(href));
    } catch (IllegalArgumentException ex) {
      return Optional.empty();
    }
  }

  public static Release createFromHref(String href) throws IllegalArgumentException {
    if (href == null) throw new IllegalArgumentException();

    if (ALBUM_PATTERN.matcher(href).matches()) {
      Album album = Album.builder().origin(href).build();
      return new Release(album);
    } else if (TRACK_PATTERN.matcher(href).matches()) {
      Track track = Track.builder().origin(href).build();
      return new Release(track);
    } else {
      throw new IllegalArgumentException();
    }
  }

}

