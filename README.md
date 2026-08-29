<div align="center">

# DECIBEL

**A Premium, High-Performance Music Experience Powered by YouTube Music**

*Built for Android & Desktop using Compose Multiplatform*

---

[![License: GPL v3](https://img.shields.io/badge/License-GPLv3-indigo.svg)](https://www.gnu.org/licenses/gpl-3.0)
[![Platform](https://img.shields.io/badge/Platform-Android%20%7C%20Desktop-black.svg)]()
[![Theme](https://img.shields.io/badge/Design-Pitch--Black%20AMOLED-000000.svg)]()

</div>

---

## ✨ Overview

**Decibel** is a modern, open-source music streaming application designed for audio enthusiasts and power users. Built on top of YouTube Music, Decibel offers an ad-free, high-fidelity listening experience wrapped in a pitch-black dark UI.

---

## 🚀 Highlights & Features

- **Ad-Free Audio Streaming**: Stream from YouTube Music & YouTube without interruptions or tracking.
- **Pitch-Black AMOLED Design**: Dark-first interface crafted with deep contrast, glassmorphism, and responsive micro-animations.
- **High-Fidelity Audio**: Support for up to 256kbps high-quality audio streams.
- **Crossfading & Audio Effects**: DJ-style crossfade transitions between tracks.
- **Synced Lyrics**: Integrated synced lyrics with multi-provider fallbacks (Decibel Lyrics, LRCLIB, Spotify, YouTube).
- **AI-Powered Translations**: Real-time AI lyric translation powered by Gemini / OpenAI.
- **Spotify Canvas Support**: Visual canvas playback integration.
- **Android Auto**: Full-featured automotive UI for safe, immersive listening on the road.
- **SponsorBlock & ReturnYouTubeDislike**: Automatic crowd-sourced sponsor skipping and dis-like count restoration.
- **Personalized Recommendations**: Integrated charts, mood browsing, podcast discovery, and AI track suggestions.
- **Discord Rich Presence**: Live playback status scrobbling on desktop.
- **Last.fm Scrobbling**: Built-in support for Last.fm scrobbling and listening history.

---

## 📱 APK Download & Installation Guide

### 1. Download Pre-built APKs
You can get the latest official APK builds from the [**GitHub Releases**](https://github.com/Darshan8610/Decibel/releases) tab.

| APK Variant | Recommended For | Description |
| :--- | :--- | :--- |
| **`Decibel-v1.0.0-arm64-v8a.apk`** | Modern Android Devices | Optimized for 64-bit ARM devices (smaller download size ~30 MB & better performance). |
| **`Decibel-v1.0.0-Universal.apk`** | All Android Devices & Emulators | Includes all CPU architectures (arm64-v8a, armeabi-v7a, x86, x86_64 ~57 MB). |

### 2. How to Install on Android
1. Download either APK variant to your Android phone.
2. Open your device's **Files / Downloads** app and tap the downloaded `.apk` file.
3. If prompted, grant permission to **"Allow from this source"** or **"Install unknown apps"** in your device Settings.
4. Tap **Install** and launch Decibel.

> **Tip (Fast ADB Install via Terminal):**
> ```bash
> adb install Decibel-v1.0.0-arm64-v8a.apk
> ```

---

## 🔍 How to View & Inspect the APK Internals

If you want to view, analyze, or reverse-engineer the APK package directly:

* **Android Studio APK Analyzer** *(Recommended)*:
  1. Open Android Studio.
  2. Navigate to **Build > Analyze APK...** (or drag and drop the `.apk` directly into Android Studio).
  3. View raw dex files, resource tables, AndroidManifest.xml, certificate fingerprints, and uncompressed component sizes.
* **JADX GUI (Decompiler)**:
  - Download [JADX-GUI](https://github.com/skylot/jadx/releases) and open the APK to browse Kotlin/Java source code and XML assets.
* **Command Line Inspection (Apktool / Zip)**:
  ```bash
  # Extract resources and decompiled Smali code
  apktool d Decibel-v1.0.0-arm64-v8a.apk -o DecibelExtracted

  # Or unzip raw assets directly (APKs are zip archives)
  unzip -l Decibel-v1.0.0-arm64-v8a.apk
  ```

---

## 🔨 Building APK from Source

To compile the Android APK directly on your machine:

```bash
# Clone the repository with submodules
git clone --recursive https://github.com/Darshan8610/Decibel.git
cd Decibel

# Build debug APK
./gradlew :androidApp:assembleDebug

# Build release APK
./gradlew :androidApp:assembleRelease
```

The output APKs will be generated in:
`androidApp/build/outputs/apk/debug/` or `androidApp/build/outputs/apk/release/`

---

## 🛠 Tech Stack & Architecture

Decibel is built with modern Kotlin Multiplatform & Android best practices:

- **UI Framework**: Jetpack Compose / Compose Multiplatform
- **Architecture**: Clean Architecture + MVI / Layered UI-Domain-Data
- **Playback Engine**: AndroidX Media3 (ExoPlayer) on Android, mpv on Desktop
- **Dependency Injection**: Koin
- **Asynchronous Flow**: Kotlin Coroutines & Flow
- **Network & Caching**: Ktor, Coil 3

---

## 📄 License & Attributions

Decibel is licensed under the [GNU General Public License v3.0 (GPL-3.0)](LICENSE).

Decibel builds upon open-source technologies including:
- **InnerTune** (GPL-3.0)
- **SmartTube** (GPL-3.0)
- **NewPipeExtractor / BravePipeExtractor** (GPL-3.0)
- **LRCLIB** (MIT)
- **SponsorBlock** & **ReturnYouTubeDislike**

---

<div align="center">
  <sub>Crafted with ❤️ by the Decibel Team</sub>
</div>
