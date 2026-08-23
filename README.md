<p align="center"><strong>Moonlight Android Fork for Homatics Box 4K Pro V2</strong></p>

# Based on Moonlight Android Fork for Mi TV Stick 4K Gen 2 and Mi TV Box S 3rd Gen of [Farnsworth3010](https://github.com/farnsworth3010/moonlight-android-mi-tv-stick-gen-2)


This repository is a fork of **Moonlight Android Fork of Farnsworth3010** with device-focused fixes for **Mi TV Stick 4K Gen 2**, **Mi TV Box S 3rd Gen** and **Homatics Box 4K Pro V2** mainly for:

- HEVC streaming stability/compatibility (including slideshow fix during HEVC streaming)
- GPU composition behavior on Android TV

## What is changed in this fork

The fork includes patches around:

- HEVC low-latency decoder option handling on affected Xiaomi/Amlogic devices 
- HEVC decoder/RFI behavior tuning for Android TV
- **Slideshow (image freezing) fix during HEVC streaming on Xiaomi Mi TV Stick Gen 2, Mi TV Box S 3rd Gen and Homatics Box 4K Pro V2 running Android TV 14**
- GPU composition forcing logic to reduce stutter scenarios on TV sticks
- Additional renderer/frame-pacing enhancements
- Add support for Homatics Box 4K Pro V2
- Original Moonlight Icons


## Default settings in this fork

Current defaults are tuned for the target device profile:

- Resolution: **Full HD (1920x1080)**
- Codec preference: **HEVC**
- Frame pacing: **Balanced**
- Android TV force GPU composition: **Disabled**

## Tested configuration

Validated on:

- Device: **Homatics Box 4K Pro V2**
- Streaming profile: **4K 60 FPS**
- Frame pacing: **Balanced**
- Android TV force GPU composition: **Disabled**
- Sunshine + Moonlight

Low-latency mode can cause freezes on some affected configurations.

## Known issues
Mi TV Stick / Box:
- Streaming can still hang at random times.
- Smoothness may degrade when the large performance stats overlay is enabled.

Homatics:
- Streaming can still hang at random times

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
In the release verison i hust changed AppIcons back to moonlight default and added support for Homatics Box 4K Pro V2.

# Beta:
**I added a watchdog to detect freezes and restart the decoder and added this patch https://github.com/moonlight-stream/moonlight-common-c/pull/147. Coded with Ai. No known issues with beta but still testig!**
