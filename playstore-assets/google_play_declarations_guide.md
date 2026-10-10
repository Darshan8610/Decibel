# Google Play Console Step-by-Step Policy Declarations Guide

Use this guide when filling out the mandatory declarations in the **Google Play Console** under **App content** (`Policy and programs`):

---

### 1. Privacy Policy
- **URL**: `https://darshan8610.github.io/Decibel/privacy-policy.html`

---

### 2. App Access
- Select: **"All functionality is available without special access"** (or specify credentials if restricting access).

---

### 3. Ads
- Select: **"No, my app does not contain ads"**.

---

### 4. Content Rating (IARC Questionnaire)
- **Category**: Consumer / Utility / Music Streaming
- Violence / Sexual Content / Offensive Language: **No**
- Controlled substances / Gambling / Physical injury: **No**
- Does the app share user location?: **No**
- Does the app allow users to purchase digital goods?: **No**
- Result: **Everyone / PEGI 3 / General**

---

### 5. Target Audience and Content
- **Target Age Groups**: Select `13-15`, `16-17`, and `18 and over`.
- Appeal to Children: Select **"No"**.

---

### 6. News / Financial / Health Declarations
- Is your app a news app?: **No**
- COVID-19 contact tracing / status: **No**
- Financial features / Govt apps: **No**

---

### 7. Data Safety Questionnaire
- **Does your app collect or share any user data?**
  - Select **"Yes"** (due to anonymized crash diagnostics and local audio playback).
- **Data Types**:
  1. **Audio**:
     - *Type*: Music / Audio files
     - *Collected?*: **No** (Processed locally on device)
     - *Shared?*: **No**
     - *Purpose*: App functionality
  2. **App Info and Performance**:
     - *Type*: Crash logs / Diagnostics
     - *Collected?*: **Yes** (if Sentry enabled)
     - *Shared?*: **No**
     - *Ephemeral / Anonymous?*: **Yes**
     - *Purpose*: Analytics, Bug fixes & App functionality
- **Security Practices**:
  - Is data encrypted in transit?: **Yes** (HTTPS/TLS)
  - Do you provide a way for users to request data deletion?: **Yes**

---

### 8. Foreground Services Declaration (Android 14+ Policy)
Google Play requires explaining your foreground service use cases:
- **`FOREGROUND_SERVICE_MEDIA_PLAYBACK`**:
  - *User Facing Purpose*: Continuous audio playback when the app is in the background or screen is off.
  - *Description to enter*: "Decibel is an audio player and streaming application. The foreground service is essential to maintain continuous, uninterrupted music playback and display media controls in the system notification area and Android Auto while users navigate other apps or turn off their screen."
- **`FOREGROUND_SERVICE_DATA_SYNC`**:
  - *User Facing Purpose*: Offline caching/downloading of user-requested tracks.
  - *Description to enter*: "Used exclusively when the user initiates a song download or cache operation to ensure the audio file completes transfer reliably in the background."

---

### 9. Keystore Information (Keep Secure!)
- **Keystore File**: `keystore/release-key.jks`
- **Key Alias**: `decibel-release-key`
- **Password**: `DecibelReleasePass2026!`
- **Enrolled in Google Play App Signing**: Yes (Google manages production signing keys; this upload key signs your `.aab`).
