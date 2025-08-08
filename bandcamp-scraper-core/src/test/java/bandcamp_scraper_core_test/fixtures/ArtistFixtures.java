package bandcamp_scraper_core_test.fixtures;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import bandcamp_scraper_core_test.fixtures.AlbumFixtures.AlbumFixtureFactoryRecord;
import bandcamp_scraper_core_test.fixtures.Fixtures.ArtistFixtureFactory;
import bandcamp_scraper_core_test.fixtures.TrackFixtures.TrackFixtureFactoryRecord;
import bandcamp_scraper_models.Artist;
import bandcamp_scraper_models.HydratableModel.HydrationStatus;
import bandcamp_scraper_models.Release;

public class ArtistFixtures {

  private static ArtistFixtureFactory buildArtistFixtureFactory(String artistURL,String name,String location,List<AlbumFixtureFactoryRecord> albumFixtureFactoryRecords,List<TrackFixtureFactoryRecord> singleFixtureFactoryRecords) {

    Set<Release> releases = new HashSet<>();

    albumFixtureFactoryRecords.stream()
      .map( affr -> Release.tryCreateFromHref(affr.url()).get())
      .forEach( r -> releases.add(r));
    singleFixtureFactoryRecords.stream()
      .map( sffr -> Release.tryCreateFromHref(sffr.url()).get())
      .forEach( r -> releases.add(r));


    var aff = ArtistFixtureFactory.builder()
    .dry( () -> 
        Artist.builder()
        .origin(artistURL)
        .status(HydrationStatus.DRY)
        .build() 
    )
    .hydrated( () ->
        Artist.builder()
        .name(name)
        .releases(releases)
        .location(location)
        .origin(artistURL)
        .status(HydrationStatus.HYDRATED)
        .build() 
    )
    .build();

    return aff;
  }

  public static final class FEMTANYL {
    public static final String URL = "https://femtanyl.bandcamp.com/music";
    public static final String NAME = "Femtanyl";
    public static final String LOCATION = "Toronto, Ontario";
    public static final ArtistFixtureFactory FF = 
      buildArtistFixtureFactory(URL, NAME, LOCATION, 
          AlbumFixtures.FEMTANYL.getAllFactoryRecords(),
          TrackFixtures.FEMTANYL.SINGLES.getAllFactoryRecords());

  }

}
