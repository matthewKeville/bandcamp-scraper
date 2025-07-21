package bandcamp_scraper_shared.utils.http;

import java.util.regex.Pattern;

import bandcamp_scraper_shared.enums.RootModelType;
import bandcamp_scraper_shared.exceptions.http.InvalidResourceUrlException;

public class UrlUtils {

    private static final Pattern TRACK_URL = Pattern.compile("^https?://[a-zA-Z0-9_-]+\\.bandcamp\\.com/track/[^/?#]+/?$");
    private static final Pattern ALBUM_URL = Pattern.compile("^https?://[a-zA-Z0-9_-]+\\.bandcamp\\.com/album/[^/?#]+/?$");
    private static final Pattern ARTIST_URL = Pattern.compile("^https?://[a-zA-Z0-9_-]+\\.bandcamp\\.com(/(music)?)?/?$");

    public static final String TRACK_SLUG_URL = "https://<artist>.bandcamp.com/track/<title>";
    public static final String ALBUM_SLUG_URL = "https://<artist>.bandcamp.com/album/<title>";
    public static final String ARTIST_SLUG_URL = "https://<artist>.bandcamp.com/music>";

    public static String getArtistBaseUrl(String url) throws InvalidResourceUrlException {
        if (url == null) throw new InvalidResourceUrlException("URL must contain a bandcamp.com subdomain");
        String pattern = "^https?://([a-zA-Z0-9_-]+\\.bandcamp\\.com)(/.*)?$";
        if (!url.matches(pattern)) {
            throw new InvalidResourceUrlException("URL must contain a bandcamp.com subdomain");
        }
        return url.replaceAll(pattern, "https://$1");
    }

    public static boolean isArtistURL(String url) {
      if (url == null) return false;
        return ARTIST_URL.matcher(url).matches();
    }

    public static boolean isAlbumURL(String url) {
      if (url == null) return false;
        return ALBUM_URL.matcher(url).matches();
    }

    public static boolean isTrackURL(String url) {
      if (url == null) return false;
        return TRACK_URL.matcher(url).matches();
    }

    public static RootModelType resolveResourceModelType(String url) throws InvalidResourceUrlException {
      if (url != null && !url.isEmpty()) {
        if (isArtistURL(url)) return RootModelType.ARTIST;
        if (isAlbumURL(url)) return RootModelType.ALBUM;
        if (isTrackURL(url)) return RootModelType.TRACK;
      }
      throw new InvalidResourceUrlException("Unable to determine Resource class for " + url);
    }
}
