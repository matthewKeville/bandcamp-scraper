package bandcamp_scraper_core_test.fixtures;

import java.util.function.Supplier;

import bandcamp_scraper_models.Album;
import bandcamp_scraper_models.Artist;
import bandcamp_scraper_models.Track;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

public class Fixtures {

  @Builder
  @Setter
  @Getter
  public static class TrackFixtureFactory {
    @Builder.Default
    public Supplier<Track> dry = () -> {throw new UnsupportedOperationException();};
    @Builder.Default
    public Supplier<Track> partialFromAlbum = () -> {throw new UnsupportedOperationException();};
    @Builder.Default
    public Supplier<Track> hydrated = () -> {throw new UnsupportedOperationException();};
  }


  @Builder
  @Setter
  @Getter
  public static class AlbumFixtureFactory {
    @Builder.Default
    public Supplier<Album> dry = () -> {throw new UnsupportedOperationException();};
    @Builder.Default
    public Supplier<Album> hydrated = () -> {throw new UnsupportedOperationException();};
    @Builder.Default
    public Supplier<Album> hydratedWithTrackHydration = () -> {throw new UnsupportedOperationException();};
  }

  @Builder
  @Setter
  @Getter
  public static class ArtistFixtureFactory {
    @Builder.Default
    public Supplier<Artist> dry = () -> {throw new UnsupportedOperationException();};
    @Builder.Default
    public Supplier<Artist> hydrated = () -> {throw new UnsupportedOperationException();};
  }

}
