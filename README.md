# 🎵 Bandcamp Scraper

**Bandcamp Scraper** is a Java 21+ library and CLI tool for scraping metadata from [Bandcamp](https://bandcamp.com). It extracts structured data for **Artists**, **Albums**, and **Tracks**, and outputs the results as JSON — perfect for automation, archiving, or data analysis.

---

## ✨ Features

- ✅ Scrape metadata from:
  - Artist pages
  - Album pages
  - Track pages (WIP)
- ✅ Models support:
  - JSON serialization via Jackson
  - Hydration/Rehydration for model management
- ✅ Core library designed for Spring Boot integration
- ✅ CLI wrapper exposes the library capabilities to dump metadata to rich JSON format

## ✨ Planned Features

- Scraping support for the 'Search Results Page'
- Scraping support for the 'Selling Right Now' feed
- Track page scraper (currently partially hydrated from Album scraper)
  - Lyrics
  - Unit Price
- Artist Recommendations tree expansion

##   Sample Usage

> Scrape Artist Page for Slime Girls

```sh
launch.sh scrape -u=https://slimegirls.bandcamp.com/music
```

```json
{
  "status": "HYDRATED",
  "origin": "https://slimegirls.bandcamp.com/music",
  "name": "Slime Girls",
  "location": "Los Angeles, California",
  "releases": [
    {
      "track": null,
      "album": {
        "status": "DRY",
        "origin": "https://slimegirls.bandcamp.com/album/heart-on-wave",
        "title": null,
        "price": 0,
        "tracks": null
      },
      "presentType": "bandcamp_scraper_models.Album"
    },
    {
      "track": null,
      "album": {
        "status": "DRY",
        "origin": "https://slimegirls.bandcamp.com/album/vacation-wasteland-ep",
        "title": null,
        "price": 0,
        "tracks": null
      },
      "presentType": "bandcamp_scraper_models.Album"
    },
    {
      "track": null,
      "album": {
        "status": "DRY",
        "origin": "https://slimegirls.bandcamp.com/album/sketchbook-vol-1-12-17",
        "title": null,
        "price": 0,
        "tracks": null
      },
      "presentType": "bandcamp_scraper_models.Album"
    },
    {
      "track": null,
      "album": {
        "status": "DRY",
        "origin": "https://slimegirls.bandcamp.com/album/no-summer-no-cry",
        "title": null,
        "price": 0,
        "tracks": null
      },
      "presentType": "bandcamp_scraper_models.Album"
    },
    {
      "track": null,
      "album": {
        "status": "DRY",
        "origin": "https://slimegirls.bandcamp.com/album/dont-forget",
        "title": null,
        "price": 0,
        "tracks": null
      },
      "presentType": "bandcamp_scraper_models.Album"
    },
    {
      "track": {
        "status": "DRY",
        "origin": "https://slimegirls.bandcamp.com/track/baby-baby",
        "title": null,
        "number": 0,
        "duration": 0
      },
      "album": null,
      "presentType": "bandcamp_scraper_models.Track"
    },
    {
      "track": null,
      "album": {
        "status": "DRY",
        "origin": "https://slimegirls.bandcamp.com/album/tapioca-ost",
        "title": null,
        "price": 0,
        "tracks": null
      },
      "presentType": "bandcamp_scraper_models.Album"
    },
    {
      "track": null,
      "album": {
        "status": "DRY",
        "origin": "https://slimegirls.bandcamp.com/album/as-if-youre-never-hurt",
        "title": null,
        "price": 0,
        "tracks": null
      },
      "presentType": "bandcamp_scraper_models.Album"
    },
    {
      "track": null,
      "album": {
        "status": "DRY",
        "origin": "https://slimegirls.bandcamp.com/album/yumemi-lonely-planet-girl",
        "title": null,
        "price": 0,
        "tracks": null
      },
      "presentType": "bandcamp_scraper_models.Album"
    }
  ]
}

```

> Note : Referenced models require a separate scrape. Future versions
> will support full hydration.

---

> Scrape Album Page for Slime Girls : Vacation Wasteland EP

```sh
launch.sh scrape -u=https://slimegirls.bandcamp.com/album/vacation-wasteland-ep
```

```json
{
  "status": "HYDRATED",
  "origin": "https://slimegirls.bandcamp.com/album/vacation-wasteland-ep",
  "title": "Vacation Wasteland EP",
  "price": 5,
  "tracks": [
    {
      "status": "PARTIAL",
      "origin": "https://slimegirls.bandcamp.com/track/intro",
      "title": "Intro",
      "number": 1,
      "duration": 79
    },
    {
      "status": "PARTIAL",
      "origin": "https://slimegirls.bandcamp.com/track/vacation-wasteland",
      "title": "Vacation Wasteland",
      "number": 2,
      "duration": 218
    },
    {
      "status": "PARTIAL",
      "origin": "https://slimegirls.bandcamp.com/track/summer-is-gone",
      "title": "Summer Is Gone",
      "number": 3,
      "duration": 197
    },
    {
      "status": "PARTIAL",
      "origin": "https://slimegirls.bandcamp.com/track/splash-nebula",
      "title": "Splash Nebula",
      "number": 4,
      "duration": 207
    },
    {
      "status": "PARTIAL",
      "origin": "https://slimegirls.bandcamp.com/track/neo-tokyo-sunset",
      "title": "Neo-Tokyo Sunset",
      "number": 5,
      "duration": 192
    },
    {
      "status": "PARTIAL",
      "origin": "https://slimegirls.bandcamp.com/track/time-travel-lament",
      "title": "Time Travel Lament",
      "number": 6,
      "duration": 168
    },
    {
      "status": "PARTIAL",
      "origin": "https://slimegirls.bandcamp.com/track/starbolt",
      "title": "Starbolt",
      "number": 7,
      "duration": 175
    },
    {
      "status": "PARTIAL",
      "origin": "https://slimegirls.bandcamp.com/track/spring-break-199x",
      "title": "Spring Break 199X",
      "number": 8,
      "duration": 240
    },
    {
      "status": "PARTIAL",
      "origin": "https://slimegirls.bandcamp.com/track/starbolt-reprise",
      "title": "Starbolt (Reprise)",
      "number": 9,
      "duration": 80
    },
    {
      "status": "PARTIAL",
      "origin": "https://slimegirls.bandcamp.com/track/a-cruel-angel-thesis-ayanami-reggae-ver",
      "title": "A Cruel Angel Thesis (Ayanami Reggae Ver.)",
      "number": 10,
      "duration": 213
    }
  ]
}

```

---

## 📦 Project Structure

- **`bandcamp-scraper-models`** – Java library containing domain models that support serialization via Jackson.
- **`bandcamp-scraper-core`** – Java library containing scraping logic and utilities.
- **`bandcamp-scraper-cli`** – CLI wrapper for executing scrapes from the command line and dumping them into JSON.
---

## 📐 Design & Architecture

Explore the [v0.2.0 Design Documentation](docs/design.md) to understand the intended architecture and vision of the core library.

---

## 🚀 Getting Started

### ⚠️ Requirements

- **Java 21+**
- **[Google Chrome](https://www.google.com/chrome/)** (required at runtime)

### 📥 Download

1. Visit the [Releases](https://github.com/matthewKeville/bandcamp-scraper/releases) page.
2. Download the latest `bandcamp-scraper-cli-<version>.zip`.
3. Unzip it and navigate into the directory.

### 🖥️ Run CLI

```bash
### Linux/macOS
./launch.sh scrape -u=https://artistname.bandcamp.com/music
### Windows
./launch.bat scrape -u=https://artistname.bandcamp.com/music

