package bandcamp_scraper_core.utils.http;

public class UrlUtils {

  public static String tryGetArtistBaseUrl(String url) {
      // Regex to match scheme + subdomain.bandcamp.com
      String pattern = "^https?://([a-zA-Z0-9_-]+\\.bandcamp\\.com)(/.*)?$";

      if (!url.matches(pattern)) {
          throw new IllegalArgumentException("URL must contain a bandcamp.com subdomain");
      }
      // Extract scheme + subdomain.bandcamp.com (without trailing slash)
      return url.replaceAll(pattern, "https://$1");
  }

}
