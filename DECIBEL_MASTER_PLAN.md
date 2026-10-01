# Decibel Music Application — Master Architecture & Implementation Plan

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** Build "Decibel" from the ground up as an independent, high-performance, ad-free music streaming application for Android and Desktop (Compose Multiplatform), featuring pitch-black AMOLED UI, triple dynamic skins (Decibel Originals, Spotify, Apple Music), 120Hz fluid rendering, dual-engine crossfade audio playback, custom YouTube Music Innertube API client with stream cipher and anti-bot token handling, public Spotify playlist import, multi-source synced lyrics, and Android Auto integration.

**Architecture:** Strict Clean Architecture (Presentation -> Domain -> Data -> Media & Services) across modular Kotlin Multiplatform modules, using MVI/MVVM with unidirectional data flow, Koin dependency injection, Room SQLite for offline persistence, AndroidX Media3 (ExoPlayer) with custom Crossfade Audio Processor on Android, and libmpv via JNA on Desktop.

**Tech Stack:** Kotlin 2.4.10, Compose Multiplatform 1.11.1, Android Gradle Plugin 9.2.1, AndroidX Media3 1.10.1, Room 2.8.4, Koin 4.2.2, Ktor 3.5.2, Coil3 3.5.0, Haze 1.7.2, Roborazzi 1.46.0.

**Base Namespace / Package:** `com.decibel.music` (zero legacy naming, pure Decibel identity).

---

## Global Constraints

- Root package namespace across all modules is strictly `com.decibel.music`.
- Android Application ID is `com.decibel.music`.
- UI must strictly conform to 8.33ms per frame budget (120Hz AMOLED fluidity) with zero allocations during scroll passes and stable `@Immutable` list keys.
- Media playback must support continuous background streaming, lock-screen controls, media session metadata, and seamless DJ crossfade transitions between tracks.
- Clean separation of platform capabilities: FOSS-compliant modular core with optional service modules.

---

## Review Focus

1. **LazyColumn key stability**: Verify every list item uses stable string/long domain identifiers instead of object hashes to prevent frame drops on 120Hz displays.
2. **Route serialization integrity**: Ensure all navigation routes and destination classes are concrete `@Serializable` instances to prevent navigation crashes.
3. **Media session state synchronization**: Verify dual-player crossfade accurately swaps active delegates and audio focus without stutter or volume pop.
4. **Spotify embed parser resilience**: Defensive fallback traversal for varying `__NEXT_DATA__` JSON tree formats on public Spotify playlists.
5. **Desktop memory footprint**: Proper disposal of libmpv JNA native instances and frame buffers when playback pauses or stops.

---

## System Architecture & Module Map

```
D:\new decibel\
├── androidApp/                                   # Android application shell, manifest, automotive service
├── composeApp/                                   # Shared Compose Multiplatform UI, ViewModels, Themes, Skins
│   ├── commonMain/                               # Shared presentation, navigation, skins, components
│   ├── androidMain/                              # Android platform hooks, haptics, orientation
│   └── jvmMain/                                  # Desktop JVM entry point, title bar, mini player window
├── core/
│   ├── common/                                   # Utilities, Dispatchers, Logging, Constants
│   ├── domain/                                   # Pure Domain: Entities, Repositories, Use Cases
│   ├── data/                                     # Data layer: Room DB, DataStore, Repository Impls, Cache
│   ├── media/
│   │   ├── media3/                               # Android Media3 ExoPlayer, Crossfade Engine, Download Service
│   │   ├── media3-ui/                            # Android media UI & notification handlers
│   │   ├── media-jvm/                            # Desktop libmpv audio & video frame engine (JNA)
│   │   └── media-jvm-ui/                         # Desktop Compose video surface
│   └── service/
│       ├── ytmusic/                              # Custom YouTube Music Innertube API, cipher & PoToken
│       ├── spotify/                              # Public playlist importer & Canvas client
│       ├── lyrics/                               # Multi-source synced lyrics (LRC parser, LRCLIB, translations)
│       ├── ai/                                   # AI-driven real-time lyric translations (Gemini/OpenAI)
│       ├── kizzy/                                # Discord Rich Presence RPC for Desktop
│       └── ktorExt/                              # Network optimizations (Brotli, curl logger, HMAC)
└── gradle/                                       # Version catalogs & wrapper configuration
```

---

## Deep Technical Specifications & Working Mechanisms

### 1. Custom YouTube Music API & Scraper Engine (`core/service/ytmusic`)
- **Innertube Client Configurations**:
  - Context emulation: `WEB_REMIX` (YouTube Music web client version), `WEB` (desktop web client for video info/transcripts), and `TVHTML5` (TV client for raw stream access).
  - Dynamic request headers: `x-youtube-client-name`, `x-youtube-client-version`, `x-goog-visitor-id`, `accept-encoding: gzip, deflate, br`.
- **Innertube Scraper API Methods**:
  - `getSearchSuggestions(query)`: Instant autocomplete query candidates via `/youtubei/v1/music/get_search_suggestions`.
  - `search(query, filter)`: Scrapes structured search summaries with tabs: Songs, Videos, Albums, Playlists, Artists via `/youtubei/v1/search`.
  - `getBrowse(browseId, params)`: Home explore feed, Charts, Mood categories, New Releases via `/youtubei/v1/browse`.
  - `getPlayer(videoId)`: Audio format streaming descriptors, itags, adaptive stream manifests via `/youtubei/v1/player`.
  - `getNext(videoId, playlistId)`: Up-next queue continuations, auto-mix radio recommendations via `/youtubei/v1/next`.
  - `getQueue(playlistId)`: Full tracklists for user and system playlists via `/youtubei/v1/music/get_queue`.
  - `getTranscript(videoId)`: Synced captions and timecoded transcripts.
  - User library operations: `like()`, `dislike()`, `createPlaylist()`, `editPlaylist()`.
- **Stream Cipher & Signature Solving**:
  - Automatic extraction and parsing of YouTube player base JavaScript (`base.js`).
  - Decrypts `s` parameter signatures using dynamic transformation functions (reverse, splice, swap).
  - Solves `n` parameter throttling challenge transforms to prevent audio download speed caps.
- **PoToken (Proof of Origin) Engine**:
  - Headless challenge-response solver generating valid visitor tokens and proof-of-origin tokens (`po_token.html`).
  - Prevents YouTube anti-bot IP throttling, CAPTCHAs, and 403 HTTP errors.
- **Third-Party Open-Source Integrations**:
  - **SponsorBlock**: Automatic crowd-sourced skipping of non-music intro/outro/sponsor segments.
  - **ReturnYouTubeDislike**: Real-time dislike count retrieval.
  - **Extractor Fallbacks**: Integrated `NewPipeExtractor` and `BravePipeExtractor` when direct Innertube streams are rate-limited.
  - **Chunked Parallel Downloader**: Multi-threaded 1MB chunk downloader for offline music caching with ID3 tag writing.

### 2. Dual-Engine Crossfade & Playback Core (`core/media`)
- **Android Media3 Crossfade Architecture**:
  - `CrossfadeExoPlayerAdapter`: Multi-player instance model allocating distinct ExoPlayer instances per track rather than standard playlist queues.
  - `CrossfadeFilterAudioProcessor` & `DecibelBiquadFilter`: Dual-buffer 16-bit PCM processor executing logarithmic volume transitions between outgoing and incoming tracks.
  - Pre-caching mechanism: Automatically initiates stream buffer allocation for the next track 15 seconds before the current track finishes.
  - Dynamic Parametric Equalizer: 5-band / 10-band biquad filter with presets (Bass Boost, Vocal, Electronic, Rock, Acoustic) and audio normalization.
- **Desktop libmpv Audio & Canvas Engine**:
  - `MpvPlayerAdapter`: Direct native C API bindings via JNA to `mpv-2.dll` / `libmpv.so` / `libmpv.dylib`.
  - High-performance audio streaming with gapless playback.
  - Video Frame Surface (`MpvVideoFrameSource`): Decodes and renders high-frame-rate Spotify Canvas loops and music videos directly onto Compose Canvas.
  - Memory Trimmer: Aggressive garbage collection and native handle deallocation on track transitions.
- **Android Auto Integration**:
  - `DecibelCarAppService`: Automotive MediaBrowser service compliant with Android Auto DHU standards.
  - Dedicated automotive screens: LibraryTab, MediaList, SearchCar, QueueCar, and NowPlayingCar.
- **Media Session & System Integration**:
  - MediaSessionCompat callback handling with full metadata (title, artist, album, high-res cover art).
  - Background playback foreground service with lock-screen notification and seek bar.
  - Sleep timer with smooth volume fade-out.

### 3. Comprehensive Presentation & Multi-Skin UI System (`composeApp`)
- **Pitch-Black AMOLED Theming**:
  - True pure black (`#000000`) surfaces for battery efficiency and maximum OLED contrast.
  - Ambient mesh gradients dynamically derived from active album cover art via `KMPalette`.
  - 120Hz display optimization: zero memory allocations inside lazy list composition passes, stable `@Immutable` keys, and GPU-cached `Haze` blur effects.
- **Dynamic Skin Architecture (`SkinUiProvider`)**:
  - **Decibel Originals**: Dark minimalist aesthetic with optional floating `LiquidGlass` translucent navigation dock.
  - **Spotify Skin**: Strict reproduction of Spotify Mobile UI: Spotify green accent (`#1DB954`), rounded pill buttons, greeting headers ("Good morning / afternoon / evening"), Spotify bottom navigation bar, and compact card tiles.
  - **Apple Music Skin**: Strict reproduction of Apple Music iOS design: Bold navigation headers, red accent (`#FA2D48`), ultra-thin glassmorphic materials, Apple Music mini player with floating elevation, and tab sections (Listen Now, Browse, Radio, Library, Search).
- **Core Screen Catalog**:
  - **HomeScreen**: Mood chips, Quick Picks carousel, Listen Again, DecibelCharts (Top Songs, Top Artists), New Releases, Forgotten Favorites.
  - **SearchScreen**: Debounced autocomplete suggestions, voice search, recent search chips, categorized result tabs (Songs, Videos, Albums, Playlists, Artists).
  - **LibraryScreen**: Filter chips (Playlists, Songs, Albums, Artists, Downloaded), local playlists, cloud playlists, and public Spotify playlist import tool.
  - **ArtistScreen**: Parallax hero banner, artist avatar, follow toggle, top popular tracks, albums, singles, featured playlists, and similar artists.
  - **AlbumScreen**: Dynamic palette backdrop, release metadata, track count, total duration, full tracklist, and one-tap album download.
  - **PlaylistScreen**: Custom cover mosaic, drag-and-drop track reordering, batch selection (delete, download, queue).
  - **NowPlayingScreen**: Fullscreen player with 3 display modes (Cover Art, Spotify Canvas video, or Music Video), scrubber, transport controls, audio quality badges, speed/pitch controls.
  - **Synced Lyrics View**: Word-by-word active karaoke highlighting, line-by-line autoscroll, click-to-seek, and AI real-time translation toggle (Gemini / OpenAI).
  - **Up Next Queue Sheet**: Reorderable queue, swipe-to-remove, clear queue, auto-play radio recommendations toggle.
  - **Equalizer Sheet**: Sliders for parametric EQ frequency bands, bass boost, and audio normalization.
  - **MiniPlayer**: Persistent floating bar across all screens with progress indicator, ticker marquee, play/pause, skip, and swipe gestures.
  - **Desktop Mini Player Window**: Detachable always-on-top compact desktop widget.
  - **SettingsScreen**: Appearance & Skins, Playback & Crossfade, Cache & Downloads, Integrations (Discord RPC, Last.fm, Spotify Canvas), About.

### 4. Advanced Services & Ecosystem Features
- **Public Spotify Playlist Importer**:
  - `SpotifyPlaylistUrl`: Normalizes share links, embed URLs, localized paths, and URI schemes.
  - `SpotifyPublicParser`: Parses public embed HTML `__NEXT_DATA__` JSON without needing Spotify developer API credentials.
  - `SpotifyTrackMatcher`: Cleans soundtrack annotations, queries Innertube, and matches songs by title, artist, and duration tolerance (+/- 5s).
- **Multi-Source Synced Lyrics Engine**:
  - `LrcParser`: Parses LRC timestamp formats (`[mm:ss.xx]` and word-level tags).
  - Fallback pipeline: Local Cache -> Decibel Lyrics -> LRCLIB -> Spotify Lyrics -> Apple Music -> YouTube Captions.
- **Discord Rich Presence (`kizzy`)**:
  - Real-time desktop scrobbling via Discord IPC / WebSocket gateway showing current song, artist, duration, and elapsed time.
- **Last.fm Scrobbling**:
  - Web OAuth authentication flow, scrobbling at 50% song completion, and Now Playing status updates.

---

## Multi-Phased Implementation Workflows

### Phase 1: Foundation, Build System & Infrastructure

#### Task 1.1: Root Gradle & Version Catalog Scaffolding
**Files:**
- Create: `settings.gradle.kts`
- Create: `build.gradle.kts`
- Create: `gradle.properties`
- Create: `gradle/libs.versions.toml`
- Create: `gradle/wrapper/gradle-wrapper.properties`
- Create: `gradlew` & `gradlew.bat`

**Interfaces:**
- Produces: Type-safe project accessors for all root modules.

- [ ] **Step 1: Write `gradle/libs.versions.toml`**
  Declare Kotlin 2.4.10, AGP 9.2.1, Compose Multiplatform 1.11.1, Media3 1.10.1, Room 2.8.4, Koin 4.2.2, Ktor 3.5.2, Coil3 3.5.0, Haze 1.7.2.
- [ ] **Step 2: Configure `settings.gradle.kts`**
  Register modules `:composeApp`, `:androidApp`, `:desktopApp`, `:core:domain`, `:core:data`, `:core:media:media3`, `:core:media:media-jvm`, `:core:service:ytmusic`, `:core:service:spotify`, `:core:service:lyrics`, `:core:service:kizzy`, `:core:service:ktorExt`.
- [ ] **Step 3: Setup `gradle.properties`**
  Configure JVM heap memory (`-Xmx4g`), parallel execution, and AndroidX flags.
- [ ] **Step 4: Verify project initialization**
  Run: `./gradlew help` to verify configuration resolution.

---

### Phase 2: Core Domain & Data Layer

#### Task 2.1: Domain Models & Repository Contracts
**Files:**
- Create: `core/domain/src/commonMain/kotlin/com/decibel/music/domain/model/Track.kt`
- Create: `core/domain/src/commonMain/kotlin/com/decibel/music/domain/model/Album.kt`
- Create: `core/domain/src/commonMain/kotlin/com/decibel/music/domain/model/Artist.kt`
- Create: `core/domain/src/commonMain/kotlin/com/decibel/music/domain/model/Playlist.kt`
- Create: `core/domain/src/commonMain/kotlin/com/decibel/music/domain/model/Lyrics.kt`
- Create: `core/domain/src/commonMain/kotlin/com/decibel/music/domain/repository/MediaRepository.kt`
- Create: `core/domain/src/commonMain/kotlin/com/decibel/music/domain/repository/StreamRepository.kt`
- Create: `core/domain/src/commonMain/kotlin/com/decibel/music/domain/repository/LocalPlaylistRepository.kt`
- Test: `core/domain/src/commonTest/kotlin/com/decibel/music/domain/DomainModelTest.kt`

**Interfaces:**
- Produces: Immutable domain entities and clean architecture repository interfaces.

- [ ] **Step 1: Write Domain Model Tests**
  Verify model serialization, equality, and mapping.
- [ ] **Step 2: Implement Track, Album, Artist, Playlist models**
  Create Kotlin `@Serializable` data classes with stable IDs, title, thumbnail URLs, durations, and audio stream descriptors.
- [ ] **Step 3: Define Repository interfaces**
  Define contracts for searching, browsing, stream resolution, and playlist operations.
- [ ] **Step 4: Run domain unit tests**
  Execute `./gradlew :core:domain:test` to verify pass.

#### Task 2.2: Local Database (Room) & User Preferences (DataStore)
**Files:**
- Create: `core/data/src/commonMain/kotlin/com/decibel/music/data/db/DecibelDatabase.kt`
- Create: `core/data/src/commonMain/kotlin/com/decibel/music/data/db/entity/TrackEntity.kt`
- Create: `core/data/src/commonMain/kotlin/com/decibel/music/data/db/entity/PlaylistEntity.kt`
- Create: `core/data/src/commonMain/kotlin/com/decibel/music/data/db/dao/TrackDao.kt`
- Create: `core/data/src/commonMain/kotlin/com/decibel/music/data/db/dao/PlaylistDao.kt`
- Create: `core/data/src/commonMain/kotlin/com/decibel/music/data/datastore/DecibelPreferences.kt`
- Create: `core/data/src/commonMain/kotlin/com/decibel/music/data/repository/LocalPlaylistRepositoryImpl.kt`

**Interfaces:**
- Consumes: Domain models from Task 2.1.
- Produces: Offline database caching, favorite tracks, playback history, and user settings.

- [ ] **Step 1: Define Room entities and DAOs**
  Create tables for songs, playlists, playlist-song cross-references, history, and downloads.
- [ ] **Step 2: Implement DataStore manager**
  Manage settings: active skin (`DECIBEL_ORIGINALS`, `SPOTIFY`, `APPLE_MUSIC`), crossfade duration (0-15s), stream quality (high/low), SponsorBlock categories.
- [ ] **Step 3: Implement LocalPlaylistRepositoryImpl**
  Wire SQLite CRUD operations and flow observables.

---

### Phase 3: Custom YouTube Music Scraper & Remote Services

#### Task 3.1: Innertube Client, Cipher & Stream Resolver
**Files:**
- Create: `core/service/ytmusic/src/commonMain/kotlin/com/decibel/music/ytmusic/DecibelYtClient.kt`
- Create: `core/service/ytmusic/src/commonMain/kotlin/com/decibel/music/ytmusic/parser/InnertubeParser.kt`
- Create: `core/service/ytmusic/src/commonMain/kotlin/com/decibel/music/ytmusic/stream/StreamResolver.kt`
- Create: `core/service/ytmusic/src/commonMain/kotlin/com/decibel/music/ytmusic/potoken/PoTokenChallenge.kt`
- Create: `core/service/ytmusic/src/commonMain/kotlin/com/decibel/music/ytmusic/sponsorblock/SponsorBlockClient.kt`
- Create: `core/service/ytmusic/src/commonMain/kotlin/com/decibel/music/ytmusic/dislike/ReturnDislikeClient.kt`
- Test: `core/service/ytmusic/src/commonTest/kotlin/com/decibel/music/ytmusic/InnertubeParserTest.kt`

**Interfaces:**
- Produces: Real-time search, browse feeds, album/artist data, stream URLs (Opus/AAC), and anti-bot token negotiation.

- [ ] **Step 1: Implement Innertube HTTP Engine**
  Configure Ktor client with Brotli compression, dynamic headers, and visitor token tracking.
- [ ] **Step 2: Implement Stream Cipher & Signature Solver**
  Extract player JS, decrypt `s` parameter signatures, and compute `n` parameter transforms.
- [ ] **Step 3: Implement PoToken Challenge Resolver**
  Generate Proof-of-Origin tokens to ensure consistent high-bitrate streaming without 403 blocks.
- [ ] **Step 4: Implement SponsorBlock & ReturnYouTubeDislike clients**
  Fetch skip segments and community dislike counts.
- [ ] **Step 5: Run scraper parser unit tests**
  Execute `./gradlew :core:service:ytmusic:test` to verify response parsers.

#### Task 3.2: Spotify Public Playlist Importer
**Files:**
- Create: `core/service/spotify/src/commonMain/kotlin/com/decibel/music/spotify/SpotifyPlaylistUrl.kt`
- Create: `core/service/spotify/src/commonMain/kotlin/com/decibel/music/spotify/SpotifyPublicParser.kt`
- Create: `core/service/spotify/src/commonMain/kotlin/com/decibel/music/spotify/SpotifyTrackMatcher.kt`
- Create: `core/service/spotify/src/commonMain/kotlin/com/decibel/music/spotify/SpotifyCanvasClient.kt`
- Test: `core/service/spotify/src/commonTest/kotlin/com/decibel/music/spotify/SpotifyImportTest.kt`

**Interfaces:**
- Produces: Seamless importing of public Spotify playlists into Decibel and Spotify Canvas video loops.

- [ ] **Step 1: Implement `SpotifyPlaylistUrl`**
  Support extraction from plain URLs, embed URLs, internationalized paths, and URI strings.
- [ ] **Step 2: Implement `SpotifyPublicParser`**
  Extract `__NEXT_DATA__` JSON payload from Spotify embed HTML and parse tracklist array.
- [ ] **Step 3: Implement `SpotifyTrackMatcher`**
  Clean track titles, search YouTube Music, and match candidates within duration tolerance.
- [ ] **Step 4: Run Spotify importer tests**
  Execute `./gradlew :core:service:spotify:test`.

#### Task 3.3: Synced Lyrics Multi-Source Engine
**Files:**
- Create: `core/service/lyrics/src/commonMain/kotlin/com/decibel/music/lyrics/LrcParser.kt`
- Create: `core/service/lyrics/src/commonMain/kotlin/com/decibel/music/lyrics/LyricsAggregator.kt`
- Create: `core/service/lyrics/src/commonMain/kotlin/com/decibel/music/lyrics/client/LrclibClient.kt`
- Create: `core/service/ai/src/commonMain/kotlin/com/decibel/music/ai/LyricsTranslator.kt`
- Test: `core/service/lyrics/src/commonTest/kotlin/com/decibel/music/lyrics/LrcParserTest.kt`

**Interfaces:**
- Produces: Synced line-by-line and word-by-word timestamped lyrics with AI translation fallback.

- [ ] **Step 1: Implement `LrcParser`**
  Parse standard `[mm:ss.xx]` and enhanced word timestamps into structured lyrics models.
- [ ] **Step 2: Implement LRCLIB and YouTube Captions fallback**
  Chain lyric providers: Local Cache -> Decibel Lyrics -> LRCLIB -> YouTube Captions.
- [ ] **Step 3: Implement Gemini / OpenAI real-time translator**
  Translate lyric lines while preserving timestamps.

---

### Phase 4: Audio Playback Engine & Crossfade Architecture

#### Task 4.1: Android Media3 Dual-Player Crossfade Engine
**Files:**
- Create: `core/media/media3/src/main/java/com/decibel/music/media3/DecibelMediaService.kt`
- Create: `core/media/media3/src/main/java/com/decibel/music/media3/player/CrossfadePlayerAdapter.kt`
- Create: `core/media/media3/src/main/java/com/decibel/music/media3/audio/CrossfadeAudioProcessor.kt`
- Create: `core/media/media3/src/main/java/com/decibel/music/media3/audio/DecibelBiquadFilter.kt`
- Create: `core/media/media3/src/main/java/com/decibel/music/media3/session/DecibelSessionCallback.kt`
- Create: `core/media/media3/src/main/java/com/decibel/music/media3/carapp/DecibelCarAppService.kt`
- Create: `core/media/media3/src/main/java/com/decibel/music/media3/download/MusicDownloadService.kt`

**Interfaces:**
- Produces: Low-latency background playback, DJ crossfade, parametric EQ, Android Auto integration, and offline song downloads.

- [ ] **Step 1: Implement `CrossfadeAudioProcessor` & `DecibelBiquadFilter`**
  Process PCM audio buffers to apply smooth volume fades and 5-band / 10-band equalization.
- [ ] **Step 2: Implement `CrossfadePlayerAdapter`**
  Coordinate dual ExoPlayer instances: trigger secondary player pre-caching 15s before track end, execute crossfade curve, and swap active listeners.
- [ ] **Step 3: Implement `DecibelMediaService` & `DecibelSessionCallback`**
  Expose MediaSession, foreground notification with playback controls, and seek tracking.
- [ ] **Step 4: Implement `DecibelCarAppService`**
  Provide automotive templates: Root, LibraryTab, MediaList, SearchCar, and NowPlayingCar.
- [ ] **Step 5: Implement `MusicDownloadService`**
  Manage background downloading, tagging ID3 metadata, and embedding cover art.

#### Task 4.2: Desktop libmpv Playback Engine
**Files:**
- Create: `core/media/media-jvm/src/main/kotlin/com/decibel/music/media/jvm/MpvAudioPlayer.kt`
- Create: `core/media/media-jvm/src/main/kotlin/com/decibel/music/media/jvm/MpvNativeBindings.kt`
- Create: `core/media/media-jvm/src/main/kotlin/com/decibel/music/media/jvm/MpvVideoFrameSource.kt`
- Create: `core/media/media-jvm/src/main/kotlin/com/decibel/music/media/jvm/MemoryTrimmer.kt`

**Interfaces:**
- Produces: Native audio and video playback on Windows, macOS, and Linux via libmpv.

- [ ] **Step 1: Load native libmpv libraries via JNA**
  Dynamically bind libmpv native binaries across platforms.
- [ ] **Step 2: Implement playback controller & event listener**
  Manage playback state, seeking, volume, and track transitions.
- [ ] **Step 3: Implement video frame provider**
  Extract raw video frames for Spotify Canvas rendering in Compose for Desktop.
- [ ] **Step 4: Implement MemoryTrimmer**
  Free unreferenced native memory during idle states.

---

### Phase 5: Presentation Layer, Theming & Multi-Skin UI

#### Task 5.1: AMOLED Theme & Skin Provider Architecture
**Files:**
- Create: `composeApp/src/commonMain/kotlin/com/decibel/music/ui/theme/DecibelTheme.kt`
- Create: `composeApp/src/commonMain/kotlin/com/decibel/music/ui/skin/AppSkin.kt`
- Create: `composeApp/src/commonMain/kotlin/com/decibel/music/ui/skin/SkinUiProvider.kt`
- Create: `composeApp/src/commonMain/kotlin/com/decibel/music/ui/skin/DecibelOriginalsSkinProvider.kt`
- Create: `composeApp/src/commonMain/kotlin/com/decibel/music/ui/skin/SpotifySkinProvider.kt`
- Create: `composeApp/src/commonMain/kotlin/com/decibel/music/ui/skin/AppleMusicSkinProvider.kt`
- Create: `composeApp/src/commonMain/kotlin/com/decibel/music/ui/skin/SpotifyComponents.kt`
- Create: `composeApp/src/commonMain/kotlin/com/decibel/music/ui/skin/AppleMusicComponents.kt`

**Interfaces:**
- Produces: Dynamic skin switching between Decibel Originals, Spotify, and Apple Music layouts.

- [ ] **Step 1: Define `SkinUiProvider` interface**
  Establish contracts for `HomeScreen`, `SearchScreen`, `LibraryScreen`, `AlbumDetailScreen`, `ArtistDetailScreen`, `MiniPlayer`, and `BottomNavigationBar`.
- [ ] **Step 2: Implement Pitch-Black AMOLED Palette**
  Surface `#000000`, card surfaces `#121212`, high-contrast typography, and accent highlights.
- [ ] **Step 3: Implement `DecibelOriginalsSkinProvider`**
  Original layout featuring modern glassmorphic tab bars and carousel song cards.
- [ ] **Step 4: Implement `SpotifySkinProvider`**
  Exact structural recreation: Spotify grid cards, rounded mini player with progress pill, and Spotify library filters.
- [ ] **Step 5: Implement `AppleMusicSkinProvider`**
  Exact structural recreation: Listen Now, Browse, Radio navigation, large header typography, card glassmorphism.

#### Task 5.2: Screens, ViewModels & 120Hz Optimizations
**Files:**
- Create: `composeApp/src/commonMain/kotlin/com/decibel/music/ui/screen/home/HomeScreen.kt`
- Create: `composeApp/src/commonMain/kotlin/com/decibel/music/ui/screen/search/SearchScreen.kt`
- Create: `composeApp/src/commonMain/kotlin/com/decibel/music/ui/screen/library/LibraryScreen.kt`
- Create: `composeApp/src/commonMain/kotlin/com/decibel/music/ui/screen/player/NowPlayingScreen.kt`
- Create: `composeApp/src/commonMain/kotlin/com/decibel/music/ui/screen/other/ArtistScreen.kt`
- Create: `composeApp/src/commonMain/kotlin/com/decibel/music/ui/screen/other/AlbumScreen.kt`
- Create: `composeApp/src/commonMain/kotlin/com/decibel/music/ui/screen/other/PlaylistScreen.kt`
- Create: `composeApp/src/commonMain/kotlin/com/decibel/music/ui/screen/home/SettingScreen.kt`
- Create: `composeApp/src/commonMain/kotlin/com/decibel/music/ui/navigation/AppNavigationGraph.kt`
- Create: `composeApp/src/commonMain/kotlin/com/decibel/music/viewModel/SharedViewModel.kt`

**Interfaces:**
- Consumes: Domain use cases and Player service.
- Produces: Fluid 120Hz screens with stable keys, zero allocations in scroll passes, and type-safe navigation.

- [ ] **Step 1: Implement `SharedViewModel`**
  Centralize player state, active track, queue, playback progress, and lyrics timeline.
- [ ] **Step 2: Build screens with 120Hz display pacing**
  Pin every LazyColumn item with `key = { it.id }`, pre-calculate styles outside composition, and throttle blur operations.
- [ ] **Step 3: Implement concrete type-safe navigation**
  Register `@Serializable` route classes with typed parameters to ensure crash-free tab switching.

---

### Phase 6: Automated Testing & Verification Suite

#### Task 6.1: Snapshot Tests & Unit Verification
**Files:**
- Create: `composeApp/src/androidHostTest/kotlin/com/decibel/music/ui/skin/SkinSnapshotTest.kt`
- Create: `composeApp/src/androidHostTest/kotlin/com/decibel/music/ui/skin/SkinDelegationTest.kt`
- Create: `composeApp/src/androidHostTest/kotlin/com/decibel/music/ui/navigation/NavigationTest.kt`
- Create: `core/service/spotify/src/commonTest/kotlin/com/decibel/music/spotify/SpotifyTest.kt`
- Create: `core/service/ytmusic/src/commonTest/kotlin/com/decibel/music/ytmusic/CipherTest.kt`

- [ ] **Step 1: Implement Roborazzi screenshot test (`SkinSnapshotTest`)**
  Capture baseline golden images for Home, Search, and Library across all three skins.
- [ ] **Step 2: Implement `SkinDelegationTest`**
  Static source check ensuring zero unqualified self-recursive composable calls.
- [ ] **Step 3: Implement navigation and cipher tests**
  Validate concrete destination matching and signature descrambling.
- [ ] **Step 4: Execute entire test suite**
  Run: `./gradlew test` to ensure all tests pass across all modules.

---

## Deliverables & Acceptance Checklist

1. **Clean Project Identity**: 100% of code in `D:\new decibel` under package `com.decibel.music`. Zero legacy references to old branding or authors.
2. **Reverse-Engineered YouTube Music API**: Fully functional Innertube client with stream cipher decryption, PoToken anti-bot solver, SponsorBlock, and ReturnYouTubeDislike.
3. **Dual-Engine Playback**: Seamless DJ crossfade on Android via Media3 and low-latency audio/video on Desktop via libmpv.
4. **Android Auto Integration**: Full automotive media browsing and playback capabilities.
5. **Triple-Skin AMOLED UI**: Native structural layouts for Decibel Originals, Spotify, and Apple Music with 120Hz fluid scroll pacing.
6. **Public Spotify Playlist Import**: One-click URL import with fuzzy track matching.
7. **Synced Lyrics with AI Translations**: LRCLIB integration, word-level highlights, and real-time AI translations.
8. **Automated Verification**: Complete suite of unit tests and Roborazzi visual regression tests.
