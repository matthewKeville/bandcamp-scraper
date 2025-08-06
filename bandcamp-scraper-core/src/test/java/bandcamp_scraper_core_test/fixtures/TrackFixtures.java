package bandcamp_scraper_core_test.fixtures;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.google.common.collect.Streams;

import bandcamp_scraper_core_test.fixtures.Fixtures.TrackFixtureFactory;
import bandcamp_scraper_models.RootModelRef;
import bandcamp_scraper_models.Track;
import bandcamp_scraper_models.HydratableModel.HydrationStatus;
import bandcamp_scraper_shared.enums.RootModelType;

/**
 * I designed this without the knowledge of static polymorphism being absent in Java's design.
 * I wanted a static data scaffold and interfaces to expose the data at different levels.
 * As a bandage, before this gets redefined to not be static classes, each Album should have
 * a getAllFactoryRecords method to expose the fixture factories conveniently. 
 */
public class TrackFixtures {

    public static record TrackFixtureFactoryRecord(TrackFixtureFactory ff, String url){};

    //probably should nix the FI helper and prefer a more explicit construction.
    private static TrackFixtureFactory buildTrackFixtureFactory(List<TrackFixtureFactoryRecord> artistRegistry,List<TrackFixtureFactoryRecord> albumRegistry,String artistURL,Optional<String> optAlbumURL, String trackURL, String title,int duration) {

      record BuilderBaseArgs(String origin,String artistURL,Optional<String> albumURL){};
      var baseArgs = new BuilderBaseArgs(trackURL, artistURL, optAlbumURL);

      Function<BuilderBaseArgs,Track.TrackBuilder> partialBaseOf = 
        (args) ->
        Track.builder()
          .artist(new RootModelRef(RootModelType.ARTIST,args.artistURL))
          .album(args.albumURL().isEmpty() ? 
              Optional.empty() :
              Optional.of(new RootModelRef(RootModelType.ALBUM,args.albumURL.get()))
          )
          .origin(args.origin());

      var tff = TrackFixtureFactory.builder()
      .dry( () -> 
          Track.builder()
          .origin(trackURL)
          .status(HydrationStatus.DRY)
          .build() 
      )
      .partialFromAlbum( () -> 
          (Track)
          partialBaseOf.apply(baseArgs)
          .title(title)
          .duration(duration)
          .status(HydrationStatus.PARTIAL)
          .build()
      )
      .hydrated( () -> 
          (Track)
          partialBaseOf.apply(baseArgs)
          .title(title)
          .duration(duration)
          .status(HydrationStatus.HYDRATED)
          .build()
      )
      .build();

      albumRegistry.add(new TrackFixtureFactoryRecord(tff, trackURL));
      artistRegistry.add(new TrackFixtureFactoryRecord(tff, trackURL));

      return tff;
    }

  /**
   * Captures a live artist, data will get stale.
   */
  public static final class FEMTANYL {

    //https://femtanyl.bandcamp.com/music
    public static final String ARTIST_URL = "https://femtanyl.bandcamp.com/music";
    private static final List<TrackFixtureFactoryRecord> REGISTRY_ARTIST = new ArrayList();
      public static List<TrackFixtureFactoryRecord> getAllFactoryRecords() {
        return REGISTRY_ARTIST;
      }

    //https://femtanyl.bandcamp.com/album/reactor
    public static final class REACTOR {

      public static final String ALBUM_URL = "https://femtanyl.bandcamp.com/album/reactor";
      private static final List<TrackFixtureFactoryRecord> REGISTRY_ALBUM = new ArrayList();
      public static List<TrackFixtureFactoryRecord> getAllFactoryRecords() {
        return REGISTRY_ALBUM;
      }

      //https://femtanyl.bandcamp.com/track/its-time-2
      public static final String ITS_TIME_2_URL = "https://femtanyl.bandcamp.com/track/its-time-2";
      public static final String ITS_TIME_2_TITLE = "ITS TIME";
      public static final int ITS_TIME_2_DURATION = 127;
      public static final TrackFixtureFactory ITS_TIME_2_FF =
        buildTrackFixtureFactory(REGISTRY_ARTIST, REGISTRY_ALBUM, ARTIST_URL, Optional.of(ALBUM_URL), ITS_TIME_2_URL, ITS_TIME_2_TITLE, ITS_TIME_2_DURATION);


      // //https://femtanyl.bandcamp.com/track/weightless-2
      public static final String WEIGHTLESS_2_URL = "https://femtanyl.bandcamp.com/track/weightless-2";
      public static final String WEIGHTLESS_2_TITLE = "WEIGHTLESS"; //intentional error
      public static final int WEIGHTLESS_2_DURATION = 144;
      public static final TrackFixtureFactory WEIGHTLESS_2_FF = 
        buildTrackFixtureFactory(REGISTRY_ARTIST, REGISTRY_ALBUM, ARTIST_URL, Optional.of(ALBUM_URL), WEIGHTLESS_2_URL, WEIGHTLESS_2_TITLE, WEIGHTLESS_2_DURATION);

      //https://femtanyl.bandcamp.com/track/dinner-2
      public static final String DINNER_2_URL = "https://femtanyl.bandcamp.com/track/dinner-2";
      public static final String DINNER_2_TITLE = "DINNER!";
      public static final int DINNER_2_DURATION = 136;
      public static final TrackFixtureFactory DINNER_2_FF = 
        buildTrackFixtureFactory(REGISTRY_ARTIST, REGISTRY_ALBUM, ARTIST_URL, Optional.of(ALBUM_URL), DINNER_2_URL, DINNER_2_TITLE, DINNER_2_DURATION);

      //https://femtanyl.bandcamp.com/track/m3-n-min3-feat-danny-brown-2
      public static final String M3_N_MIN3_FEAT_DANNY_BROWN_2_URL = "https://femtanyl.bandcamp.com/track/m3-n-min3-feat-danny-brown-2";
      public static final String M3_N_MIN3_FEAT_DANNY_BROWN_2_TITLE = "M3 n MIN3 feat. Danny Brown";
      public static final int M3_N_MIN3_FEAT_DANNY_BROWN_2_DURATION = 131;
      public static final TrackFixtureFactory M3_N_MIN3_FEAT_DANNY_BROWN_2_FF = 
        buildTrackFixtureFactory(REGISTRY_ARTIST, REGISTRY_ALBUM, ARTIST_URL, Optional.of(ALBUM_URL), M3_N_MIN3_FEAT_DANNY_BROWN_2_URL, M3_N_MIN3_FEAT_DANNY_BROWN_2_TITLE, M3_N_MIN3_FEAT_DANNY_BROWN_2_DURATION);

      //https://femtanyl.bandcamp.com/track/attacking-vertical-2
      public static final String ATTACKING_VERTICAL_2_URL = "https://femtanyl.bandcamp.com/track/attacking-vertical-2";
      public static final String ATTACKING_VERTICAL_2_TITLE = "ATTACKING VERTICAL";
      public static final int ATTACKING_VERTICAL_2_DURATION = 115;
      public static final TrackFixtureFactory ATTACKING_VERTICAL_2_FF = 
        buildTrackFixtureFactory(REGISTRY_ARTIST, REGISTRY_ALBUM, ARTIST_URL, Optional.of(ALBUM_URL), ATTACKING_VERTICAL_2_URL, ATTACKING_VERTICAL_2_TITLE, ATTACKING_VERTICAL_2_DURATION);

      //https://femtanyl.bandcamp.com/track/and-im-gone-2
      public static final String AND_IM_GONE_2_URL = "https://femtanyl.bandcamp.com/track/and-im-gone-2";
      public static final String AND_IM_GONE_2_TITLE = "AND IM GONE";
      public static final int AND_IM_GONE_2_DURATION = 166;
      public static final TrackFixtureFactory AND_IM_GONE_2_FF = 
        buildTrackFixtureFactory(REGISTRY_ARTIST, REGISTRY_ALBUM, ARTIST_URL, Optional.of(ALBUM_URL), AND_IM_GONE_2_URL, AND_IM_GONE_2_TITLE, AND_IM_GONE_2_DURATION);
     
    }

    // https://femtanyl.bandcamp.com/album/chaser
    public static class CHASER {

      public static final String ALBUM_URL = "https://femtanyl.bandcamp.com/album/chaser";
      private static final List<TrackFixtureFactoryRecord> REGISTRY_ALBUM = new ArrayList();
      public static List<TrackFixtureFactoryRecord> getAllFactoryRecords() {
        return REGISTRY_ALBUM;
      }

      //https://femtanyl.bandcamp.com/track/act-right-3
      public static final String ACT_RIGHT_3_URL = "https://femtanyl.bandcamp.com/track/act-right-3";
      public static final String ACT_RIGHT_3_TITLE = "ACT RIGHT";
      public static final int ACT_RIGHT_3_DURATION = 144;
      public static final TrackFixtureFactory ACT_RIGHT_3_FF =
        buildTrackFixtureFactory(REGISTRY_ARTIST, REGISTRY_ALBUM, ARTIST_URL, Optional.of(ALBUM_URL), ACT_RIGHT_3_URL, ACT_RIGHT_3_TITLE, ACT_RIGHT_3_DURATION);

      
      //https://femtanyl.bandcamp.com/track/p3t-2
      public static final String P3T_2_URL = "https://femtanyl.bandcamp.com/track/p3t-2";
      public static final String P3T_2_TITLE = "P3T";
      public static final int P3T_2_DURATION = 102;
      public static final TrackFixtureFactory P3T_2_FF =
        buildTrackFixtureFactory(REGISTRY_ARTIST, REGISTRY_ALBUM, ARTIST_URL, Optional.of(ALBUM_URL), P3T_2_URL, P3T_2_TITLE, P3T_2_DURATION);
      
      //https://femtanyl.bandcamp.com/track/push-ur-t3mprr
      public static final String PUSH_UR_T3MPRR_URL = "https://femtanyl.bandcamp.com/track/push-ur-t3mprr";
      public static final String PUSH_UR_T3MPRR_TITLE = "PUSH UR T3MPRR";
      public static final int PUSH_UR_T3MPRR_DURATION = 148;
      public static final TrackFixtureFactory PUSH_UR_T3MPRR_FF =
        buildTrackFixtureFactory(REGISTRY_ARTIST, REGISTRY_ALBUM, ARTIST_URL, Optional.of(ALBUM_URL), PUSH_UR_T3MPRR_URL, PUSH_UR_T3MPRR_TITLE, PUSH_UR_T3MPRR_DURATION);

      //https://femtanyl.bandcamp.com/track/katamari-2
      public static final String KATAMARI_2_URL = "https://femtanyl.bandcamp.com/track/katamari-2";
      public static final String KATAMARI_2_TITLE = "KATAMARI";
      public static final int KATAMARI_2_DURATION = 158;
      public static final TrackFixtureFactory KATAMARI_2_FF =
        buildTrackFixtureFactory(REGISTRY_ARTIST, REGISTRY_ALBUM, ARTIST_URL, Optional.of(ALBUM_URL), KATAMARI_2_URL, KATAMARI_2_TITLE, KATAMARI_2_DURATION);

      //https://femtanyl.bandcamp.com/track/murder-every-1-u-know-feat-takihasdied-2
      public static final String MURDER_EVERY_1_U_KNOW_URL = "https://femtanyl.bandcamp.com/track/murder-every-1-u-know-feat-takihasdied-2";
      public static final String MURDER_EVERY_1_U_KNOW_TITLE = "MURDER EVERY 1 U KNOW! (feat. takihasdied)";
      public static final int MURDER_EVERY_1_U_KNOW_DURATION = 125;
      public static final TrackFixtureFactory MURDER_EVERY_1_U_KNOW_FF =
        buildTrackFixtureFactory(REGISTRY_ARTIST, REGISTRY_ALBUM, ARTIST_URL, Optional.of(ALBUM_URL), MURDER_EVERY_1_U_KNOW_URL, MURDER_EVERY_1_U_KNOW_TITLE, MURDER_EVERY_1_U_KNOW_DURATION);

      //https://femtanyl.bandcamp.com/track/girl-hell-1999-2
      public static final String GIRL_HELL_1999_URL = "https://femtanyl.bandcamp.com/track/girl-hell-1999-2";
      public static final String GIRL_HELL_1999_TITLE = "GIRL HELL 1999";
      public static final int GIRL_HELL_1999_DURATION = 145;
      public static final TrackFixtureFactory GIRL_HELL_1999_FF =
        buildTrackFixtureFactory(REGISTRY_ARTIST, REGISTRY_ALBUM, ARTIST_URL, Optional.of(ALBUM_URL), GIRL_HELL_1999_URL, GIRL_HELL_1999_TITLE, GIRL_HELL_1999_DURATION);
    }

    public static class SINGLES {

      private static final List<TrackFixtureFactoryRecord> REGISTRY_ALBUM = new ArrayList();
      public static List<TrackFixtureFactoryRecord> getAllFactoryRecords() {
        return REGISTRY_ALBUM;
      }

      // https://femtanyl.bandcamp.com/track/lottery
      public static final String LOTTERY_URL = "https://femtanyl.bandcamp.com/track/lottery";
      public static final String LOTTERY_TITLE = "LOTTERY";
      public static final int LOTTERY_DURATION = 172;
      public static final TrackFixtureFactory LOTTERY_FF =
        buildTrackFixtureFactory(REGISTRY_ARTIST, REGISTRY_ALBUM, ARTIST_URL, Optional.empty(), LOTTERY_URL, LOTTERY_TITLE, LOTTERY_DURATION);

      // https://femtanyl.bandcamp.com/track/weightless
      public static final String WEIGHTLESS_URL = "https://femtanyl.bandcamp.com/track/weightless";
      public static final String WEIGHTLESS_TITLE = "WEIGHTLESS";
      public static final int WEIGHTLESS_DURATION = 144;
      public static final TrackFixtureFactory WEIGHTLESS_FF =
        buildTrackFixtureFactory(REGISTRY_ARTIST, REGISTRY_ALBUM, ARTIST_URL, Optional.empty(), WEIGHTLESS_URL, WEIGHTLESS_TITLE, WEIGHTLESS_DURATION);

      // https://femtanyl.bandcamp.com/track/attacking-vertical
      public static final String ATTACKING_VERTICAL_URL = "https://femtanyl.bandcamp.com/track/attacking-vertical";
      public static final String ATTACKING_VERTICAL_TITLE = "ATTACKING VERTICAL";
      public static final int ATTACKING_VERTICAL_DURATION = 115;
      public static final TrackFixtureFactory ATTACKING_VERTICAL_FF =
        buildTrackFixtureFactory(REGISTRY_ARTIST, REGISTRY_ALBUM, ARTIST_URL, Optional.empty(), ATTACKING_VERTICAL_URL, ATTACKING_VERTICAL_TITLE, ATTACKING_VERTICAL_DURATION);

      // https://femtanyl.bandcamp.com/track/its-time
      public static final String ITS_TIME_URL = "https://femtanyl.bandcamp.com/track/its-time";
      public static final String ITS_TIME_TITLE = "ITS TIME";
      public static final int ITS_TIME_DURATION = 127;
      public static final TrackFixtureFactory ITS_TIME_FF =
        buildTrackFixtureFactory(REGISTRY_ARTIST, REGISTRY_ALBUM, ARTIST_URL, Optional.empty(), ITS_TIME_URL, ITS_TIME_TITLE, ITS_TIME_DURATION);

      // https://femtanyl.bandcamp.com/track/m3-n-min3-feat-danny-brown
      public static final String M3_N_MIN3_URL = "https://femtanyl.bandcamp.com/track/m3-n-min3-feat-danny-brown";
      public static final String M3_N_MIN3_TITLE = "M3 n MIN3 feat. Danny Brown";
      public static final int M3_N_MIN3_DURATION = 131;
      public static final TrackFixtureFactory M3_N_MIN3_FF =
        buildTrackFixtureFactory(REGISTRY_ARTIST, REGISTRY_ALBUM, ARTIST_URL, Optional.empty(), M3_N_MIN3_URL, M3_N_MIN3_TITLE, M3_N_MIN3_DURATION);

      // https://femtanyl.bandcamp.com/track/dinner
      public static final String DINNER_BANG_URL = "https://femtanyl.bandcamp.com/track/dinner";
      public static final String DINNER_BANG_TITLE = "DINNER!";
      public static final int DINNER_BANG_DURATION = 136;
      public static final TrackFixtureFactory DINNER_BANG_FF =
        buildTrackFixtureFactory(REGISTRY_ARTIST, REGISTRY_ALBUM, ARTIST_URL, Optional.empty(), DINNER_BANG_URL, DINNER_BANG_TITLE, DINNER_BANG_DURATION);

      // https://femtanyl.bandcamp.com/track/lovesick-cannibal-feat-takihasdied
      public static final String LOVESICK_COMMA_CANNIBAL_BANG_URL = "https://femtanyl.bandcamp.com/track/lovesick-cannibal-feat-takihasdied";
      public static final String LOVESICK_COMMA_CANNIBAL_BANG_TITLE = "LOVESICK, CANNIBAL! (feat. takihasdied)";
      public static final int LOVESICK_COMMA_CANNIBAL_BANG_DURATION = 116;
      public static final TrackFixtureFactory LOVESICK_COMMA_CANNIBAL_BANG_FF =
        buildTrackFixtureFactory(REGISTRY_ARTIST, REGISTRY_ALBUM, ARTIST_URL, Optional.empty(), LOVESICK_COMMA_CANNIBAL_BANG_URL, LOVESICK_COMMA_CANNIBAL_BANG_TITLE, LOVESICK_COMMA_CANNIBAL_BANG_DURATION);

      // https://femtanyl.bandcamp.com/track/dogmatica
      public static final String DOGMATICA_URL = "https://femtanyl.bandcamp.com/track/dogmatica";
      public static final String DOGMATICA_TITLE = "DOGMATICA";
      public static final int DOGMATICA_DURATION = 135;
      public static final TrackFixtureFactory DOGMATICA_FF =
        buildTrackFixtureFactory(REGISTRY_ARTIST, REGISTRY_ALBUM, ARTIST_URL, Optional.empty(), DOGMATICA_URL, DOGMATICA_TITLE, DOGMATICA_DURATION);

      // https://femtanyl.bandcamp.com/track/girl-hell-1999
      public static final String GIRL_HELL_1999_URL = "https://femtanyl.bandcamp.com/track/girl-hell-1999";
      public static final String GIRL_HELL_1999_TITLE = "GIRL HELL 1999";
      public static final int GIRL_HELL_1999_DURATION = 145;
      public static final TrackFixtureFactory GIRL_HELL_1999_FF =
        buildTrackFixtureFactory(REGISTRY_ARTIST, REGISTRY_ALBUM, ARTIST_URL, Optional.empty(), GIRL_HELL_1999_URL, GIRL_HELL_1999_TITLE, GIRL_HELL_1999_DURATION);

      // https://femtanyl.bandcamp.com/track/katamari
      public static final String KATAMARI_URL = "https://femtanyl.bandcamp.com/track/katamari";
      public static final String KATAMARI_TITLE = "KATAMARI";
      public static final int KATAMARI_DURATION = 158;
      public static final TrackFixtureFactory KATAMARI_FF =
        buildTrackFixtureFactory(REGISTRY_ARTIST, REGISTRY_ALBUM, ARTIST_URL, Optional.empty(), KATAMARI_URL, KATAMARI_TITLE, KATAMARI_DURATION);

    }

  }

}
