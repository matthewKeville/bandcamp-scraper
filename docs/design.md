# 📦 Version 0.2.0 Architecture

This outlines the intended structure of the 0.2.0 release.

## 🧩 Model (Domain)

`Model`  
> A representation of any Bandcamp resource — either root or intermediate (e.g., Release, Track).

`RootModel : Model`  
> A top-level resource from can be fetched.
> Tracks model origin and HydrationStatus.  
> Examples: Artist, Album, Track.

## 🔄 Data Acquisition (POM + Fetchers)

`Page`
> An abstraction over the elements on a webpage and interactions performed on them.  
> (e.g., closeNagBar(), navigateSearchResults())

`RootModelPage<M extends RootModel> : Page`
> A Page where a RootModel can be extracted.

`Fetcher`  
> A general-purpose utility to retrieve remote data not explicitly associated with a `RootModel`.  
> Examples: SearchResultsFetcher, SellingRightNowFeedFetcher

```
AbstractRootModelFetcherSingleThread<
  M extends RootModel,
  P extends RootModelPage<M>,
  B
  > : RootModelFetcher<M,P,B>
```
> An abstract base class for Single Thread implementations of `RootModelFetcher`

`RootModelFetcher<M extends RootModel,P extends RootModelPage<M>,B>`  
> Retrieves a Hydrated RootModel from a remote source (e.g., an album page or artist URL).

`RootModelExtractionContext<M extends RootModel,P extends RootModelPage<M>,B>`  
> A container for pluggable lambda functions, that encapsulate the business logic of building a model from a page.
> Describes what to extract from a RootPage to build a RootModel — such as metadata, tracks, related links, etc.

`DriverContext`  
> Encapsulates Driver Creation and available threading resources.

## ⚙️ Orchestration (Scraping Service)

`ScrapingService`  
> The orchestration layer that uses `Fetchers` to return a Bandcamp meta-data-set.

`ScrapingContext`  
> Defines the rules for expansion, including:  
> - Depth  
> - Recursion strategy  
> - Inclusion/exclusion of related models  
> - Optional modules like live feed or search

