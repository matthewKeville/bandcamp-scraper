# 📦 Version 1.0.0 Architecture

This outlines the intended structure of the 1.0.0 release.

## 🧩 Model (Domain)

`Model`  
> A representation of any Bandcamp resource — either root or intermediate (e.g., Release, Track).

`RootModel : Model`  
> A top-level resource from which scraping begins.  
> Tracks model origin and HydrationStatus.  
> Examples: Artist, Album, Track.

## 🔄 Data Acquisition (POM + Fetchers)

`Page`
> An abstraction over the elements on a webpage and interactions performed on them.  
> (e.g., closeNagBar(), navigateSearchResults())

`Fetcher`  
> A general-purpose utility to retrieve remote data not tied to a `RootModel`.  
> Examples: SearchResultsFetcher, SellingRightNowFeedFetcher

`ModelFetcher<T : RootModel>`  
> Retrieves a complete RootModel from a remote source (e.g., an album page or artist URL).

`ExtractionContext<T : RootModel>`  
> Describes what to extract from a RootModel — such as metadata, tracks, related links, etc.

`FetchingContext`  
> Encapsulates how to fetch resources, including:  
> - Threading  
> - Browser/driver configuration  
> - Rate limits

## ⚙️ Orchestration (Scraping Service)

`ScrapingService`  
> The orchestration layer that uses `ModelFetchers` to recursively expand and process a set of `RootModels`.

`ScrapingContext`  
> Defines the rules for expansion, including:  
> - Depth  
> - Recursion strategy  
> - Inclusion/exclusion of related models  
> - Optional modules like live feed or search

