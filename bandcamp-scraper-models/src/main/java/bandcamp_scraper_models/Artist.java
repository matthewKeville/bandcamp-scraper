package bandcamp_scraper_models;

import java.util.List;

public class Artist {

  private String name;
  private String location;
  private List<String> releases;

  public Artist(String name, String location, List<String> releases) {
    this.name = name;
    this.location = location;
    this.releases = releases;
  }

}
