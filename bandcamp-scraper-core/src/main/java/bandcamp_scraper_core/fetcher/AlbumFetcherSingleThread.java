package bandcamp_scraper_core.fetcher;

import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import bandcamp_scraper_core.pages.AlbumPage;
import bandcamp_scraper_models.Album;
import bandcamp_scraper_models.Album.AlbumBuilder;
import bandcamp_scraper_models.HydratableModel.HydrationStatus;
import bandcamp_scraper_shared.utils.http.UrlUtils;

public class AlbumFetcherSingleThread extends AbstractRootModelFetcherSingleThread<Album,AlbumPage,Album.AlbumBuilder> {

  @Override
  protected Logger getLogger() {
    return LoggerFactory.getLogger(AlbumFetcherSingleThread.class);
  }

  @Override
  protected boolean isValidModelUrl(String url) {
    return UrlUtils.isAlbumURL(url);
  }

  @Override
  protected AlbumPage getPage(WebDriver driver) {
    return new AlbumPage(driver);
  }

  @Override
  protected AlbumBuilder getBuilder(String url) {
    var builder = Album.builder();
    builder.origin(url);
    return builder;
  }

  @Override
  protected Album build(AlbumBuilder builder) {
    builder.status(HydrationStatus.HYDRATED);
    return builder.build();
  }
}


