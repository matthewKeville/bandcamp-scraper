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


  public static final class OLD_JAW {

    //https://femtanyl.bandcamp.com/music
    public static final String ARTIST_URL = "https://oldjaw.bandcamp.com/music";
      private static final List<AlbumFixtureFactoryRecord> REGISTRY= new ArrayList();
      public static List<AlbumFixtureFactoryRecord> getAllFactoryRecords() {
        return REGISTRY;
      }

    public static final String DEMO_2017_URL = "https://oldjaw.bandcamp.com/album/demo-2017";
    public static final String DEMO_2017_TITLE = "Demo 2017";
    public static final float DEMO_2017_PRICE = 0f;
    public static final AlbumFixtureFactory DEMO_2017_FF = 
      buildAlbumFixtureFactory(REGISTRY, ARTIST_URL, DEMO_2017_URL, DEMO_2017_TITLE, DEMO_2017_PRICE, 
          TrackFixtures.OLD_JAW.DEMO_2017.getAllFactoryRecords().stream().map(ffr -> ffr.ff()).toList());

    public static final String OLD_JAW_SELF_TITLED_URL = "https://oldjaw.bandcamp.com/album/old-jaw";
    public static final String OLD_JAW_SELF_TITLED_TITLE = "Old Jaw";
    public static final float OLD_JAW_SELF_TITLED_PRICE = 0f;
    public static final AlbumFixtureFactory OLD_JAW_SELF_TITLED_FF = 
      buildAlbumFixtureFactory(REGISTRY, ARTIST_URL, OLD_JAW_SELF_TITLED_URL, OLD_JAW_SELF_TITLED_TITLE, OLD_JAW_SELF_TITLED_PRICE, 
          TrackFixtures.OLD_JAW.OLD_JAW_SELF_TITLED.getAllFactoryRecords().stream().map(ffr -> ffr.ff()).toList());

    public static final String SOPHIES_SONG_URL = "https://oldjaw.bandcamp.com/album/sophies-song";
    public static final String SOPHIES_SONG_TITLE = "Sophie's Song";
    public static final float SOPHIES_SONG_PRICE = 0f;
    public static final AlbumFixtureFactory SOPHIES_SONG_FF = 
      buildAlbumFixtureFactory(REGISTRY, ARTIST_URL, SOPHIES_SONG_URL, SOPHIES_SONG_TITLE, SOPHIES_SONG_PRICE, 
          TrackFixtures.OLD_JAW.SOPHIES_SONG.getAllFactoryRecords().stream().map(ffr -> ffr.ff()).toList());


    }

}

