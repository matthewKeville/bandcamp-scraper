package bandcamp_scraper_core_test.integration;

import java.util.stream.Stream;

import org.junit.jupiter.params.provider.Arguments;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import bandcamp_scraper_core.extraction.TrackExtractionContext;
import bandcamp_scraper_core.fetcher.RootModelFetcher;
import bandcamp_scraper_core.fetcher.synchronous.TrackFetcherSingleThread;
import bandcamp_scraper_core.pages.TrackPage;
import bandcamp_scraper_core_test.fixtures.TrackFixtures;
import bandcamp_scraper_models.Track;

public class TrackFetcherSingleThreadIT extends AbstractRootModelFetcherIT<Track,TrackPage,Track.TrackBuilder> {

  @Override
  protected RootModelFetcher<Track> getFetcher() {
    return new TrackFetcherSingleThread(this.driverContext, new TrackExtractionContext());
  }

  @Override
  protected Logger provideLogger() {
    return LoggerFactory.getLogger(TrackFetcherSingleThreadIT.class);
  }

  @Override
  protected Stream<Arguments> provideTestCases() {
    //return singlesWithHiddenPlayers();
    return allFemtanylTracks();
  }

  private Stream<Arguments> allFemtanylTracks() {
    return TrackFixtures.FEMTANYL.getAllFactoryRecords().stream().map( tfr -> Arguments.of(tfr.url(),tfr.ff().hydrated.get()));
  }

  //subset of all tracks with known hidden track players
  private Stream<Arguments> singlesWithHiddenPlayers() {
    return Stream.of(
      Arguments.of(TrackFixtures.FEMTANYL.SINGLES.ITS_TIME_URL,TrackFixtures.FEMTANYL.SINGLES.ITS_TIME_FF.getHydrated().get()),
      Arguments.of(TrackFixtures.FEMTANYL.SINGLES.ATTACKING_VERTICAL_URL,TrackFixtures.FEMTANYL.SINGLES.ATTACKING_VERTICAL_FF.getHydrated().get()),
      Arguments.of(TrackFixtures.FEMTANYL.SINGLES.LOTTERY_URL,TrackFixtures.FEMTANYL.SINGLES.LOTTERY_FF.getHydrated().get())
    );
  }



}
