package integration;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.junit.jupiter.params.provider.Arguments;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import bandcamp_scraper_core.extraction.AlbumExtractionContext;
import bandcamp_scraper_core.extraction.RootModelExtractionContext;
import bandcamp_scraper_core.fetcher.AlbumFetcherSingleThread;
import bandcamp_scraper_core.fetcher.RootModelFetcher;
import bandcamp_scraper_core.pages.AlbumPage;
import bandcamp_scraper_models.Album;
import bandcamp_scraper_models.HydratableModel.HydrationStatus;
import bandcamp_scraper_models.RootModelRef;
import bandcamp_scraper_models.Track;
import bandcamp_scraper_shared.enums.RootModelType;

public class AlbumFetcherSingleThreadIT extends AbstractRootModelFetcherIT<Album,AlbumPage,Album.AlbumBuilder> {

  @Override
  protected RootModelFetcher<Album,AlbumPage,Album.AlbumBuilder> getFetcher() {
    return new AlbumFetcherSingleThread();
  }

  @Override
  protected RootModelExtractionContext<Album,AlbumPage,Album.AlbumBuilder> getExtractionContext() {
    return new AlbumExtractionContext();
  }

  @Override
  protected Logger provideLogger() {
    return LoggerFactory.getLogger(AlbumFetcherSingleThreadIT.class);
  }

  @Override
  protected Stream<Arguments> provideTestCases() {
    return Stream.of(

      //"https://diybandits.bandcamp.com/album/burn-the-earth-leave-it-behind",
      Arguments.of(
        "https://diybandits.bandcamp.com/album/burn-the-earth-leave-it-behind",
        Album.builder()
          .title("Burn The Earth Leave It Behind")
          .price(6.66f)
          .tracks(List.of(
            Album.AlbumTrack.builder()
              .number(1)
              .track(
                Track.builder()
                  .title("Proudhon in Manhattan")
                  .duration(146)
                  .status(HydrationStatus.PARTIAL)
                  .origin("https://diybandits.bandcamp.com/track/proudhon-in-manhattan")
                  .artist(new RootModelRef(RootModelType.ARTIST, "https://diybandits.bandcamp.com/music"))
                  .album(Optional.of(new RootModelRef(RootModelType.ALBUM, "https://diybandits.bandcamp.com/album/burn-the-earth-leave-it-behind")))
                  .build()
              )
              .build(),

            Album.AlbumTrack.builder()
              .number(2)
              .track(
                Track.builder()
                  .title("Never Trust a Man")
                  .duration(137)
                  .status(HydrationStatus.PARTIAL)
                  .origin("https://diybandits.bandcamp.com/track/never-trust-a-man")
                  .artist(new RootModelRef(RootModelType.ARTIST, "https://diybandits.bandcamp.com/music"))
                  .album(Optional.of(new RootModelRef(RootModelType.ALBUM, "https://diybandits.bandcamp.com/album/burn-the-earth-leave-it-behind")))
                  .build()
              )
              .build(),

            Album.AlbumTrack.builder()
              .number(3)
              .track(
                Track.builder()
                  .title("Fuck Shit Up!")
                  .duration(122)
                  .status(HydrationStatus.PARTIAL)
                  .origin("https://diybandits.bandcamp.com/track/fuck-shit-up")
                  .artist(new RootModelRef(RootModelType.ARTIST, "https://diybandits.bandcamp.com/music"))
                  .album(Optional.of(new RootModelRef(RootModelType.ALBUM, "https://diybandits.bandcamp.com/album/burn-the-earth-leave-it-behind")))
                  .build()
              )
              .build(),

            Album.AlbumTrack.builder()
              .number(4)
              .track(
                Track.builder()
                  .title("Fuck Every Cop")
                  .duration(222)
                  .status(HydrationStatus.PARTIAL)
                  .origin("https://diybandits.bandcamp.com/track/fuck-every-cop")
                  .artist(new RootModelRef(RootModelType.ARTIST, "https://diybandits.bandcamp.com/music"))
                  .album(Optional.of(new RootModelRef(RootModelType.ALBUM, "https://diybandits.bandcamp.com/album/burn-the-earth-leave-it-behind")))
                  .build()
              )
              .build(),

            Album.AlbumTrack.builder()
              .number(5)
              .track(
                Track.builder()
                  .title("Urine Speaks Louder than Words")
                  .duration(120)
                  .status(HydrationStatus.PARTIAL)
                  .origin("https://diybandits.bandcamp.com/track/urine-speaks-louder-than-words")
                  .artist(new RootModelRef(RootModelType.ARTIST, "https://diybandits.bandcamp.com/music"))
                  .album(Optional.of(new RootModelRef(RootModelType.ALBUM, "https://diybandits.bandcamp.com/album/burn-the-earth-leave-it-behind")))
                  .build()
              )
              .build(),

            Album.AlbumTrack.builder()
              .number(6)
              .track(
                Track.builder()
                  .title("Picking Sides")
                  .duration(222)
                  .status(HydrationStatus.PARTIAL)
                  .origin("https://diybandits.bandcamp.com/track/picking-sides")
                  .artist(new RootModelRef(RootModelType.ARTIST, "https://diybandits.bandcamp.com/music"))
                  .album(Optional.of(new RootModelRef(RootModelType.ALBUM, "https://diybandits.bandcamp.com/album/burn-the-earth-leave-it-behind")))
                  .build()
              )
              .build(),

            Album.AlbumTrack.builder()
              .number(7)
              .track(
                Track.builder()
                  .title("Jesus Does the Dishes")
                  .duration(177)
                  .status(HydrationStatus.PARTIAL)
                  .origin("https://diybandits.bandcamp.com/track/jesus-does-the-dishes")
                  .artist(new RootModelRef(RootModelType.ARTIST, "https://diybandits.bandcamp.com/music"))
                  .album(Optional.of(new RootModelRef(RootModelType.ALBUM, "https://diybandits.bandcamp.com/album/burn-the-earth-leave-it-behind")))
                  .build()
              )
              .build(),

            Album.AlbumTrack.builder()
              .number(8)
              .track(
                Track.builder()
                  .title("Just Becase I don't say Anything")
                  .duration(101)
                  .status(HydrationStatus.PARTIAL)
                  .origin("https://diybandits.bandcamp.com/track/just-becase-i-dont-say-anything")
                  .artist(new RootModelRef(RootModelType.ARTIST, "https://diybandits.bandcamp.com/music"))
                  .album(Optional.of(new RootModelRef(RootModelType.ALBUM, "https://diybandits.bandcamp.com/album/burn-the-earth-leave-it-behind")))
                  .build()
              )
              .build(),

            Album.AlbumTrack.builder()
              .number(9)
              .track(
                Track.builder()
                  .title("For a Girl in Rhinelander")
                  .duration(99)
                  .status(HydrationStatus.PARTIAL)
                  .origin("https://diybandits.bandcamp.com/track/for-a-girl-in-rhinelander")
                  .artist(new RootModelRef(RootModelType.ARTIST, "https://diybandits.bandcamp.com/music"))
                  .album(Optional.of(new RootModelRef(RootModelType.ALBUM, "https://diybandits.bandcamp.com/album/burn-the-earth-leave-it-behind")))
                  .build()
              )
              .build(),

            Album.AlbumTrack.builder()
              .number(10)
              .track(
                Track.builder()
                  .title("My Idea of Fun")
                  .duration(398)
                  .status(HydrationStatus.PARTIAL)
                  .origin("https://diybandits.bandcamp.com/track/my-idea-of-fun")
                  .artist(new RootModelRef(RootModelType.ARTIST, "https://diybandits.bandcamp.com/music"))
                  .album(Optional.of(new RootModelRef(RootModelType.ALBUM, "https://diybandits.bandcamp.com/album/burn-the-earth-leave-it-behind")))
                  .build()
              )
              .build()
          ))
          .origin("https://diybandits.bandcamp.com/album/burn-the-earth-leave-it-behind")
          .status(HydrationStatus.HYDRATED)
          .build()
      ),

      //"https://apesofthestate.bandcamp.com/album/all-i-did-this-summer-was-go-to-rehab",

      // Arguments.of(
      //   "https://apesofthestate.bandcamp.com/album/all-i-did-this-summer-was-go-to-rehab",
      //   Album.builder()
      //     .title("All I did this summer was go to rehab")
      //     .price(0.0f)
      //     .tracks(List.of(
      //       Album.AlbumTrack.builder()
      //         .number(1)
      //         .track(
      //           Track.builder()
      //             .title("white and privileged")
      //             .duration(142)
      //             .status(HydrationStatus.PARTIAL)
      //             .origin("https://apesofthestate.bandcamp.com/track/white-and-privileged")
      //             .build()
      //         )
      //         .build(),
      //       Album.AlbumTrack.builder()
      //         .number(2)
      //         .track(
      //           Track.builder()
      //             .title("serenity")
      //             .duration(146)
      //             .status(HydrationStatus.PARTIAL)
      //             .origin("https://apesofthestate.bandcamp.com/track/serenity")
      //             .build()
      //         )
      //         .build(),
      //       Album.AlbumTrack.builder()
      //         .number(3)
      //         .track(
      //           Track.builder()
      //             .title("a song about kool aid")
      //             .duration(141)
      //             .status(HydrationStatus.PARTIAL)
      //             .origin("https://apesofthestate.bandcamp.com/track/a-song-about-kool-aid")
      //             .build()
      //         )
      //         .build(),
      //       Album.AlbumTrack.builder()
      //         .number(4)
      //         .track(
      //           Track.builder()
      //             .title("all I did this summer was go to rehab")
      //             .duration(172)
      //             .status(HydrationStatus.PARTIAL)
      //             .origin("https://apesofthestate.bandcamp.com/track/all-i-did-this-summer-was-go-to-rehab")
      //             .build()
      //         )
      //         .build(),
      //       Album.AlbumTrack.builder()
      //         .number(5)
      //         .track(
      //           Track.builder()
      //             .title("earth people")
      //             .duration(172)
      //             .status(HydrationStatus.PARTIAL)
      //             .origin("https://apesofthestate.bandcamp.com/track/earth-people")
      //             .build()
      //         )
      //         .build(),
      //       Album.AlbumTrack.builder()
      //         .number(6)
      //         .track(
      //           Track.builder()
      //             .title("today I didn't rob my friends")
      //             .duration(220)
      //             .status(HydrationStatus.PARTIAL)
      //             .origin("https://apesofthestate.bandcamp.com/track/today-i-didnt-rob-my-friends")
      //             .build()
      //         )
      //         .build(),
      //       Album.AlbumTrack.builder()
      //         .number(7)
      //         .track(
      //           Track.builder()
      //             .title("not responsible")
      //             .duration(114)
      //             .status(HydrationStatus.PARTIAL)
      //             .origin("https://apesofthestate.bandcamp.com/track/not-responsible")
      //             .build()
      //         )
      //         .build(),
      //       Album.AlbumTrack.builder()
      //         .number(8)
      //         .track(
      //           Track.builder()
      //             .title("runaway")
      //             .duration(69)
      //             .status(HydrationStatus.PARTIAL)
      //             .origin("https://apesofthestate.bandcamp.com/track/runaway")
      //             .build()
      //         )
      //         .build(),
      //       Album.AlbumTrack.builder()
      //         .number(9)
      //         .track(
      //           Track.builder()
      //             .title("heading to new orleans")
      //             .duration(136)
      //             .status(HydrationStatus.PARTIAL)
      //             .origin("https://apesofthestate.bandcamp.com/track/heading-to-new-orleans")
      //             .build()
      //         )
      //         .build(),
      //       Album.AlbumTrack.builder()
      //         .number(10)
      //         .track(
      //           Track.builder()
      //             .title("the puke song")
      //             .duration(191)
      //             .status(HydrationStatus.PARTIAL)
      //             .origin("https://apesofthestate.bandcamp.com/track/the-puke-song")
      //             .build()
      //         )
      //         .build(),
      //       Album.AlbumTrack.builder()
      //         .number(11)
      //         .track(
      //           Track.builder()
      //             .title("connect the dots")
      //             .duration(225)
      //             .status(HydrationStatus.PARTIAL)
      //             .origin("https://apesofthestate.bandcamp.com/track/connect-the-dots")
      //             .build()
      //         )
      //         .build(),
      //       Album.AlbumTrack.builder()
      //         .number(12)
      //         .track(
      //           Track.builder()
      //             .title("something about blue eyes")
      //             .duration(262)
      //             .status(HydrationStatus.PARTIAL)
      //             .origin("https://apesofthestate.bandcamp.com/track/something-about-blue-eyes")
      //             .build()
      //         )
      //         .build(),
      //       Album.AlbumTrack.builder()
      //         .number(13)
      //         .track(
      //           Track.builder()
      //             .title("people are fat")
      //             .duration(113)
      //             .status(HydrationStatus.PARTIAL)
      //             .origin("https://apesofthestate.bandcamp.com/track/people-are-fat")
      //             .build()
      //         )
      //         .build()
      //     ))
      //     .origin("https://apesofthestate.bandcamp.com/album/all-i-did-this-summer-was-go-to-rehab")
      //     .status(HydrationStatus.HYDRATED)
      //     .build()
      // )

      Arguments.of(
        "https://apesofthestate.bandcamp.com/album/all-i-did-this-summer-was-go-to-rehab",
        Album.builder()
          .title("All I did this summer was go to rehab")
          .price(0.0f)
          .tracks(List.of(
            Album.AlbumTrack.builder()
              .number(1)
              .track(
                Track.builder()
                  .title("white and privileged")
                  .duration(142)
                  .status(HydrationStatus.PARTIAL)
                  .origin("https://apesofthestate.bandcamp.com/track/white-and-privileged")
                  .artist(new RootModelRef(RootModelType.ARTIST, "https://apesofthestate.bandcamp.com/music"))
                  .album(Optional.of(new RootModelRef(RootModelType.ALBUM, "https://apesofthestate.bandcamp.com/album/all-i-did-this-summer-was-go-to-rehab")))
                  .build()
              )
          .build(),
        Album.AlbumTrack.builder()
          .number(2)
          .track(
            Track.builder()
              .title("serenity")
              .duration(146)
              .status(HydrationStatus.PARTIAL)
              .origin("https://apesofthestate.bandcamp.com/track/serenity")
              .artist(new RootModelRef(RootModelType.ARTIST, "https://apesofthestate.bandcamp.com/music"))
              .album(Optional.of(new RootModelRef(RootModelType.ALBUM, "https://apesofthestate.bandcamp.com/album/all-i-did-this-summer-was-go-to-rehab")))
              .build()
          )
          .build(),
        Album.AlbumTrack.builder()
          .number(3)
          .track(
            Track.builder()
              .title("a song about kool aid")
              .duration(141)
              .status(HydrationStatus.PARTIAL)
              .origin("https://apesofthestate.bandcamp.com/track/a-song-about-kool-aid")
              .artist(new RootModelRef(RootModelType.ARTIST, "https://apesofthestate.bandcamp.com/music"))
              .album(Optional.of(new RootModelRef(RootModelType.ALBUM, "https://apesofthestate.bandcamp.com/album/all-i-did-this-summer-was-go-to-rehab")))
              .build()
          )
          .build(),
        Album.AlbumTrack.builder()
          .number(4)
          .track(
            Track.builder()
              .title("all I did this summer was go to rehab")
              .duration(172)
              .status(HydrationStatus.PARTIAL)
              .origin("https://apesofthestate.bandcamp.com/track/all-i-did-this-summer-was-go-to-rehab")
              .artist(new RootModelRef(RootModelType.ARTIST, "https://apesofthestate.bandcamp.com/music"))
              .album(Optional.of(new RootModelRef(RootModelType.ALBUM, "https://apesofthestate.bandcamp.com/album/all-i-did-this-summer-was-go-to-rehab")))
              .build()
          )
          .build(),
        Album.AlbumTrack.builder()
          .number(5)
          .track(
            Track.builder()
              .title("earth people")
              .duration(172)
              .status(HydrationStatus.PARTIAL)
              .origin("https://apesofthestate.bandcamp.com/track/earth-people")
              .artist(new RootModelRef(RootModelType.ARTIST, "https://apesofthestate.bandcamp.com/music"))
              .album(Optional.of(new RootModelRef(RootModelType.ALBUM, "https://apesofthestate.bandcamp.com/album/all-i-did-this-summer-was-go-to-rehab")))
              .build()
          )
          .build(),
        Album.AlbumTrack.builder()
          .number(6)
          .track(
            Track.builder()
              .title("today I didn't rob my friends")
              .duration(220)
              .status(HydrationStatus.PARTIAL)
              .origin("https://apesofthestate.bandcamp.com/track/today-i-didnt-rob-my-friends")
              .artist(new RootModelRef(RootModelType.ARTIST, "https://apesofthestate.bandcamp.com/music"))
              .album(Optional.of(new RootModelRef(RootModelType.ALBUM, "https://apesofthestate.bandcamp.com/album/all-i-did-this-summer-was-go-to-rehab")))
              .build()
          )
          .build(),
        Album.AlbumTrack.builder()
          .number(7)
          .track(
            Track.builder()
              .title("not responsible")
              .duration(114)
              .status(HydrationStatus.PARTIAL)
              .origin("https://apesofthestate.bandcamp.com/track/not-responsible")
              .artist(new RootModelRef(RootModelType.ARTIST, "https://apesofthestate.bandcamp.com/music"))
              .album(Optional.of(new RootModelRef(RootModelType.ALBUM, "https://apesofthestate.bandcamp.com/album/all-i-did-this-summer-was-go-to-rehab")))
              .build()
          )
          .build(),
        Album.AlbumTrack.builder()
          .number(8)
          .track(
            Track.builder()
              .title("runaway")
              .duration(69)
              .status(HydrationStatus.PARTIAL)
              .origin("https://apesofthestate.bandcamp.com/track/runaway")
              .artist(new RootModelRef(RootModelType.ARTIST, "https://apesofthestate.bandcamp.com/music"))
              .album(Optional.of(new RootModelRef(RootModelType.ALBUM, "https://apesofthestate.bandcamp.com/album/all-i-did-this-summer-was-go-to-rehab")))
              .build()
          )
          .build(),
        Album.AlbumTrack.builder()
          .number(9)
          .track(
            Track.builder()
              .title("heading to new orleans")
              .duration(136)
              .status(HydrationStatus.PARTIAL)
              .origin("https://apesofthestate.bandcamp.com/track/heading-to-new-orleans")
              .artist(new RootModelRef(RootModelType.ARTIST, "https://apesofthestate.bandcamp.com/music"))
              .album(Optional.of(new RootModelRef(RootModelType.ALBUM, "https://apesofthestate.bandcamp.com/album/all-i-did-this-summer-was-go-to-rehab")))
              .build()
          )
          .build(),
        Album.AlbumTrack.builder()
          .number(10)
          .track(
            Track.builder()
              .title("the puke song")
              .duration(191)
              .status(HydrationStatus.PARTIAL)
              .origin("https://apesofthestate.bandcamp.com/track/the-puke-song")
              .artist(new RootModelRef(RootModelType.ARTIST, "https://apesofthestate.bandcamp.com/music"))
              .album(Optional.of(new RootModelRef(RootModelType.ALBUM, "https://apesofthestate.bandcamp.com/album/all-i-did-this-summer-was-go-to-rehab")))
              .build()
          )
          .build(),
        Album.AlbumTrack.builder()
          .number(11)
          .track(
            Track.builder()
              .title("connect the dots")
              .duration(225)
              .status(HydrationStatus.PARTIAL)
              .origin("https://apesofthestate.bandcamp.com/track/connect-the-dots")
              .artist(new RootModelRef(RootModelType.ARTIST, "https://apesofthestate.bandcamp.com/music"))
              .album(Optional.of(new RootModelRef(RootModelType.ALBUM, "https://apesofthestate.bandcamp.com/album/all-i-did-this-summer-was-go-to-rehab")))
              .build()
          )
          .build(),
        Album.AlbumTrack.builder()
          .number(12)
          .track(
            Track.builder()
              .title("something about blue eyes")
              .duration(262)
              .status(HydrationStatus.PARTIAL)
              .origin("https://apesofthestate.bandcamp.com/track/something-about-blue-eyes")
              .artist(new RootModelRef(RootModelType.ARTIST, "https://apesofthestate.bandcamp.com/music"))
              .album(Optional.of(new RootModelRef(RootModelType.ALBUM, "https://apesofthestate.bandcamp.com/album/all-i-did-this-summer-was-go-to-rehab")))
              .build()
          )
          .build(),
        Album.AlbumTrack.builder()
          .number(13)
          .track(
            Track.builder()
              .title("people are fat")
              .duration(113)
              .status(HydrationStatus.PARTIAL)
              .origin("https://apesofthestate.bandcamp.com/track/people-are-fat")
              .artist(new RootModelRef(RootModelType.ARTIST, "https://apesofthestate.bandcamp.com/music"))
              .album(Optional.of(new RootModelRef(RootModelType.ALBUM, "https://apesofthestate.bandcamp.com/album/all-i-did-this-summer-was-go-to-rehab")))
              .build()
          )
          .build()
      ))
      .origin("https://apesofthestate.bandcamp.com/album/all-i-did-this-summer-was-go-to-rehab")
      .status(HydrationStatus.HYDRATED)
      .build()
    )
    );

  }

}
