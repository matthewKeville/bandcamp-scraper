package bandcamp_scraper_core_test.fixtures;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import bandcamp_scraper_core_test.fixtures.Fixtures.AlbumFixtureFactory;
import bandcamp_scraper_core_test.fixtures.Fixtures.TrackFixtureFactory;
import bandcamp_scraper_models.Album;
import bandcamp_scraper_models.HydratableModel.HydrationStatus;

public class AlbumFixtures {

  public static record AlbumFixtureFactoryRecord(AlbumFixtureFactory ff, String url){};
  private static AlbumFixtureFactory buildAlbumFixtureFactory(List<AlbumFixtureFactoryRecord> registry, String artistURL,String albumURL,String title,float price,List<TrackFixtureFactory> trackFixtureFactories) {

    var aff = AlbumFixtureFactory.builder()
    .dry( () -> 
        Album.builder()
        .origin(albumURL)
        .status(HydrationStatus.DRY)
        .build() 
    )
    .hydrated( () ->
        Album.builder()
        .title(title)
        .price(price)
        .tracks(
          IntStream.range(0, trackFixtureFactories.size())
          .mapToObj( i -> 
            Album.AlbumTrack.builder()
            .track(
              trackFixtureFactories.get(i).partialFromAlbum.get())
            .number(i+1)
            .build()
          ).collect(Collectors.toList())
        )
        .origin(albumURL)
        .status(HydrationStatus.HYDRATED)
        .build() 
    )
    .build();

    registry.add(new AlbumFixtureFactoryRecord(aff, albumURL));

    return aff;
  }

  /**
   * Captures a live artist, data will get stale.
   */
  public static final class FEMTANYL {

    //https://femtanyl.bandcamp.com/music
    public static final String ARTIST_URL = "https://femtanyl.bandcamp.com/music";
      private static final List<AlbumFixtureFactoryRecord> REGISTRY= new ArrayList();
      public static List<AlbumFixtureFactoryRecord> getAllFactoryRecords() {
        return REGISTRY;
      }

    public static final String REACTOR_URL = "https://femtanyl.bandcamp.com/album/reactor";
    public static final String REACTOR_TITLE = "REACTOR";
    public static final float REACTOR_PRICE = 7.0f;
    public static final AlbumFixtureFactory REACTOR_FF = 
      buildAlbumFixtureFactory(REGISTRY, ARTIST_URL, REACTOR_URL, REACTOR_TITLE, REACTOR_PRICE, 
          TrackFixtures.FEMTANYL.REACTOR.getAllFactoryRecords().stream().map(ffr -> ffr.ff()).toList());

    public static final String CHASER_URL = "https://femtanyl.bandcamp.com/album/chaser";
    public static final String CHASER_TITLE = "CHASER";
    public static final float CHASER_PRICE = 7.0f;
    public static final AlbumFixtureFactory CHASER_FF = 
      buildAlbumFixtureFactory(REGISTRY, ARTIST_URL, CHASER_URL, CHASER_TITLE, CHASER_PRICE, 
          TrackFixtures.FEMTANYL.CHASER.getAllFactoryRecords().stream().map(ffr -> ffr.ff()).toList());

    }

}

