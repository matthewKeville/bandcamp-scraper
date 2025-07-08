### Bandcamp Assumptions

#### Pages

Bandcamp artist pages have the following URL structure

`https://teenagehalloween.bandcamp.com/`

Bandcamp artist pages have core sub pages that may or may not be present on there
artist profile. It appears that the artist can set a redirect or default
page when there profile is hit.

`/community`

> Social media aspect of the platform

`/merch`

> Merch offerings

`/music`

> This page is the discography page, seems to be the default
when no override is present.

> It also seems that this page must exist.

#### Sub Pages

/track

> can be a standalone release or part of an album
>
> track price and track lyrics are locked behind this page.

/album

> a listing of tracks and purchase options

#### Known Redirects

I commonly observe Artist pages redirecting to a recent release.

### Praxis

> When we scrape an artist we target `https://$(artistName).bandcamp.com/music`
the discography should present the sidebar, and we can scrape the relevant
metadata from that if it is present.
