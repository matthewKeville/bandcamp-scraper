package bandcamp_scraper_core_test.fixtures;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This is a bandage over my first attempt at static data scaffold
 * for testing. Java uses lazy intialization (which I didn't know)
 * so I am forcing the nested static hierarchy to intializae the fixture
 * factories
 */
public class StaticFixtureLoader {

  private static void loadNestedClasses(Class clazz,int depth) {
    if ( depth <= 0 ) {
      throw new IllegalArgumentException("depth must be > 0");
    }

    List<Class> frontier = List.of(clazz);
    List<Class> explored = new LinkedList();
    for ( int i = depth; i > 0; i-- ) {
      explored.addAll(frontier);
      frontier = frontier.stream()
        .flatMap( cz -> Arrays.stream(cz.getDeclaredClasses()))
        .collect(Collectors.toList());
    }
    explored.forEach( cz -> {
      try { 
        Class.forName(cz.getName()); 
        System.out.println("loaded " + cz.getName());
      }
      catch (Exception e) {}
    });
  }

  /** Heavy handed, but gets the job done for now */
  public static void loadFixtures() {
    loadNestedClasses(TrackFixtures.class, 3);
    loadNestedClasses(AlbumFixtures.class, 2);
    loadNestedClasses(ArtistFixtures.class, 2);
  }

}
