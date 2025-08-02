package bandcamp_scraper_core_test.fixtures;

import java.util.function.Supplier;

import bandcamp_scraper_models.Album;
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

    //   Arguments.of(
    //     "https://slimegirls.bandcamp.com/music",
    //     Artist.builder()
    //       .name("Slime Girls")
    //       .location("Los Angeles, California")
    //       .status(HydrationStatus.HYDRATED)
    //       .origin("https://slimegirls.bandcamp.com/music")
    //       .releases(new HashSet<>(Set.of(
    //         Release.createFromHref("https://slimegirls.bandcamp.com/album/as-if-youre-never-hurt"),
    //         Release.createFromHref("https://slimegirls.bandcamp.com/album/sketchbook-vol-1-12-17"),
    //         Release.createFromHref("https://slimegirls.bandcamp.com/track/baby-baby"),
    //         Release.createFromHref("https://slimegirls.bandcamp.com/album/dont-forget"),
    //         Release.createFromHref("https://slimegirls.bandcamp.com/album/tapioca-ost"),
    //         Release.createFromHref("https://slimegirls.bandcamp.com/album/no-summer-no-cry"),
    //         Release.createFromHref("https://slimegirls.bandcamp.com/album/yumemi-lonely-planet-girl"),
    //         Release.createFromHref("https://slimegirls.bandcamp.com/album/heart-on-wave"),
    //         Release.createFromHref("https://slimegirls.bandcamp.com/album/vacation-wasteland-ep")
    //       )))
    //       .build()
    //   )
    //
    // );


}
