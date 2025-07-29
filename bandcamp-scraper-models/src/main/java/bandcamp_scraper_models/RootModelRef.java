package bandcamp_scraper_models;

import com.fasterxml.jackson.annotation.JsonProperty;

import bandcamp_scraper_shared.enums.RootModelType;
import bandcamp_scraper_shared.exceptions.http.InvalidResourceUrlException;
import bandcamp_scraper_shared.utils.http.UrlUtils;

public class RootModelRef {

  private final String origin;
  private final RootModelType type;

  public RootModelRef(@JsonProperty("type") RootModelType type, @JsonProperty("origin") String origin) 
      throws IllegalArgumentException, InvalidResourceUrlException{
    switch (type) {
      case ARTIST:
        if (!UrlUtils.isArtistURL(origin)) {
          throw new InvalidResourceUrlException(origin + " is not a Artist URL");
        }
        break;
      case ALBUM:
        if (!UrlUtils.isAlbumURL(origin)) {
          throw new InvalidResourceUrlException(origin + " is not a Album URL");
        }
        break;
      case TRACK:
        if (!UrlUtils.isTrackURL(origin)) {
          throw new InvalidResourceUrlException(origin + " is not a Track URL");
        }
        break;
      default:
        throw new IllegalArgumentException();

    }
    this.type = type;
    this.origin = origin;
  }

}

