<p align="center"><strong>Moonlight Android Fork for Homatic Box 4K Pro V2</strong></p>

# Moonlight Android Fork for Mi TV Stick 4K Gen 2 and Mi TV Box S 3rd Gen & Homatic Box 4K Pro V2

![Example stream output](docs/images/example-stream.jpg)

Russian version: [README.ru.md](README.ru.md)

This repository is a fork of **Moonlight Android** with device-focused fixes for **Mi TV Stick 4K Gen 2** and **Mi TV Box S 3rd Gen** & Homatic Box 4K Pro V2, mainly for:

- HEVC streaming stability/compatibility (including slideshow fix during HEVC streaming)
- GPU composition behavior on Android TV

## What is changed in this fork

The fork includes patches around:

- HEVC low-latency decoder option handling on affected Xiaomi/Amlogic devices 
- HEVC decoder/RFI behavior tuning for Android TV
- **Slideshow (image freezing) fix during HEVC streaming on Xiaomi Mi TV Stick Gen 2 and Mi TV Box S 3rd Gen running Android TV 14**
- GPU composition forcing logic to reduce stutter scenarios on TV sticks
- Additional renderer/frame-pacing enhancements 

## Download

You can download the app from the [Releases page](https://github.com/farnsworth3010/moonlight-android-mi-tv-stick-gen-2/releases).

The binary APK is built inside the GitHub Actions workflow, so the build process is transparent and reproducible.

## Build instructions

1. Install prerequisites:
   - Android Studio
   - Android NDK
2. Clone this repository.
3. Initialize submodules:
   - `git submodule update --init --recursive`
4. Create `local.properties` in repo root and set your NDK path:
   - `ndk.dir=...`
5. Open the project in Android Studio (or use Gradle).
6. Build APK:
   - Android Studio: **Build > Build APK(s)**
   - or CLI: `./gradlew :app:assembleDebug` (Windows: `gradlew.bat :app:assembleDebug`)

## Default settings in this fork

Current defaults are tuned for the target device profile:

- Resolution: **Full HD (1920x1080)**
- Codec preference: **HEVC**
- Frame pacing: **Balanced**
- Android TV force GPU composition: **Enabled**

## Tested configuration

Validated on:

- Device: **Homatic Box 4K Pro V2**
- Streaming profile: **4K 60 FPS**
- Frame pacing: **Balanced**

Low-latency mode can cause freezes on some affected configurations.

## Known issues

- Streaming can still hang at random times (root cause is not fully understood).
- Smoothness may degrade when the large performance stats overlay is enabled.

## TV settings recommendations

To reduce latency, it is recommended to:

- Enable **Game Mode** on your TV.
- Disable **Auto Motion Plus** (or equivalent frame interpolation/motion smoothing features).

## Credits

- [Moonlight Android](https://github.com/moonlight-stream/moonlight-android)
- [Sunshine](https://github.com/LizardByte/Sunshine)
- [Vibepollo](https://github.com/Nonary/Vibepollo)
- [Nun-z/moonlight-android](https://github.com/Nun-z/moonlight-android)
- [Viktsolovevwork278/moonlight-android-hevc-fix](https://github.com/Viktsolovevwork278/moonlight-android-hevc-fix)
- [Farnsworth3010/moonlight-android-mi-tv-stick-gen-2](https://github.com/farnsworth3010/moonlight-android-mi-tv-stick-gen-2)

## Development note

Just cha
