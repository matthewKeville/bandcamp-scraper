package bandcamp_scraper_core_test.fixtures;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import bandcamp_scraper_core_test.fixtures.Fixtures.AlbumFixtureFactory;
import bandcamp_scraper_core_test.fixtures.Fixtures.TrackFixtureFactory;
import bandcamp_scraper_models.Album;
import bandcamp_scraper_models.HydratableModel.HydrationStatus;

public class AlbumFixtures {

  private static AlbumFixtureFactory buildAlbumFixtureFactory(String artistURL,String albumURL,String title,float price,List<TrackFixtureFactory> trackFixtureFactories) {

    return AlbumFixtureFactory.builder()
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
  }

  /**
   * Captures a live artist, data will get stale.
   */
  public static final class FEMTANYL {

    //https://femtanyl.bandcamp.com/music
    public static final String ARTIST_URL = "https://femtanyl.bandcamp.com/music";

    public static final String REACTOR_URL = "https://femtanyl.bandcamp.com/album/reactor";
    public static final String REACTOR_TITLE = "REACTOR";
    public static final float REACTOR_PRICE = 7.0f;
    public static final List<TrackFixtureFactory> REACTOR_TRACK_FACTORIES = List.of(
       TrackFixtures.FEMTANYL.REACTOR.ITS_TIME_2_FF,
       TrackFixtures.FEMTANYL.REACTOR.WEIGHTLESS_2_FF,
       TrackFixtures.FEMTANYL.REACTOR.DINNER_2_FF,
       TrackFixtures.FEMTANYL.REACTOR.M3_N_MIN3_FEAT_DANNY_BROWN_2_FF,
       TrackFixtures.FEMTANYL.REACTOR.ATTACKING_VERTICAL_2_FF,
       TrackFixtures.FEMTANYL.REACTOR.AND_IM_GONE_2_FF
    );
    public static final AlbumFixtureFactory REACTOR_FF = 
      buildAlbumFixtureFactory(ARTIST_URL, REACTOR_URL, REACTOR_TITLE, REACTOR_PRICE, REACTOR_TRACK_FACTORIES);

    public static final String CHASER_URL = "https://femtanyl.bandcamp.com/album/chaser";
    public static final String CHASER_TITLE = "CHASER";
    public static final float CHASER_PRICE = 7.0f;
    public static final List<TrackFixtureFactory> CHASER_TRACK_FACTORIES = List.of(
       TrackFixtures.FEMTANYL.CHASER.ACT_RIGHT_3_FF,
       TrackFixtures.FEMTANYL.CHASER.P3T_2_FF,
       TrackFixtures.FEMTANYL.CHASER.PUSH_UR_T3MPRR_FF,
       TrackFixtures.FEMTANYL.CHASER.KATAMARI_2_FF,
       TrackFixtures.FEMTANYL.CHASER.MURDER_EVERY_1_U_KNOW_FF,
       TrackFixtures.FEMTANYL.CHASER.GIRL_HELL_1999_FF
    );
    public static final AlbumFixtureFactory CHASER_FF = 
      buildAlbumFixtureFactory(ARTIST_URL, CHASER_URL, CHASER_TITLE, CHASER_PRICE, CHASER_TRACK_FACTORIES);

    }

}

