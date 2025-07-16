package bandcamp_scraper_core.extraction;

import java.util.ArrayList;
import java.util.List;

import bandcamp_scraper_core.pages.RootModelPage;
import bandcamp_scraper_models.RootModel;

/**
 * Interface representing what data to extract for a RootModel M,
 * by defining Extraction Lambdas over the PageModel P and RootModel Builder B.
 *
 * Separates extraction bussiness logic from the Fetcher, and opens up
 * end consumer to build constructive Extraction Contexts.
 *
 * @param <M> the type of RootModel to fetch
 * @param <P> the type of PageModel
 * @param <B> the type of RootModel Builder (lombok workaround)
 */
public abstract class RootModelExtractionContext<M extends RootModel,P extends RootModelPage<M>,B> {

  private final List<ExtractionStep<P, B>> extractionSteps = new ArrayList<>();

  protected void addExtractionStep(ExtractionStep<P, B> step) {
      extractionSteps.add(step);
  }

  public void extract(P page, B builder) {
      for (ExtractionStep<P, B> step : extractionSteps) {
          step.apply(page, builder);
      }
  }

  @FunctionalInterface
  public static interface ExtractionStep<P,B> {
      void apply(P page, B builder);
  }

}
