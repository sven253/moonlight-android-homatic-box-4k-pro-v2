<p align="center"><strong>Moonlight Android Fork for Homatics Box 4K Pro V2</strong></p>

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
- Decoder stall watchdog that restarts the decoder automatically instead of requiring an app restart
- Selectable Amlogic HEVC low-latency options (see below) for testing after firmware updates
- Optional input worker thread that keeps the UI responsive if the input send path stalls
- Updated moonlight-common-c (Feb 2026 upstream) with fixes for a Sunshine audio assert, two thread leaks and RTSP hardening
- [moonlight-common-c PR #147](https://github.com/moonlight-stream/moonlight-common-c/pull/147) (still open upstream, carried in this fork): requests an IDR frame when the FEC queue reports a loss without RFI. Without it, a single reordered packet freezes the video for ~2 seconds while audio keeps playing — this fork disables RFI on Amlogic, so it always takes that path
- PTS-independent decode time measurement, so decode timings stay correct on decoders that don't echo the input PTS


## Default settings in this fork

Current defaults are tuned for the target device profile:

- Resolution: **Full HD (1920x1080)**
- Codec preference: **HEVC**
- Frame pacing: **Balanced**
- Android TV force GPU composition: **Disabled**
- Amlogic HEVC low-latency options: **No low-latency options (stable)**
- Input worker thread: **Enabled**

## Tested configuration

Validated on:

- Device: **Homatics Box 4K Pro V2**
- Streaming profile: **4K 60 FPS**
- Frame pacing: **Balanced**
- Android TV force GPU composition: **Disabled**
- Sunshine + Moonlight

## Amlogic HEVC low-latency findings (S905X5M-J)

The decoder low-latency options are exposed as a setting (Settings -> Advanced -> *Amlogic HEVC low-latency options*)
so each one can be tested individually. Measurements below are from a Homatics Box 4K Pro V2
(Amlogic S905X5M-J, Android TV 14) streaming HEVC from Sunshine, using the in-app performance overlay:

| Option | Result |
| --- | --- |
| No low-latency options (default) | ~10 ms decode time, stable |
| `KEY_LOW_LATENCY` at `configure()` | No video output at all (black screen / 0 FPS) |
| `KEY_LOW_LATENCY` via `setParameters()` after `start()` | Video works, but stuttering (1 FPS) shortly after start |
| `vdec-lowlatency` | No measurable latency gain, freezes intermittently |
| `vendor.low-latency.enable` | No measurable latency gain, freezes intermittently |
| Both vendor options combined | No measurable latency gain, freezes intermittently |
| All options (stock Moonlight behavior) | No video output at all |

Conclusions:

- `KEY_LOW_LATENCY` is broken in this firmware's `c2.amlogic.hevc.decoder`, both at configure time
  (no video output at all) and at runtime (video degrades to ~1 FPS shortly after start). This is
  why stock Moonlight shows a black screen with HEVC on this device.
- The Amlogic vendor low-latency keys do not reduce decode time measurably and destabilize the stream,
  so they are disabled by default.
- ~10 ms decode time appears to be the floor on current firmware. Getting closer to the 2-5 ms seen on
  working devices requires a vendor BSP fix; it cannot be done from the app side.
- Earlier builds of this fork appeared to show a ~2-3 ms gain from the vendor options. That was a
  measurement artifact of the old PTS-based timing and disappeared once decode time measurement was
  fixed. The non-default options are kept only so they can be re-tested after a firmware update.

If a stream does freeze in one of the non-default modes, the built-in watchdog restarts the decoder
after a few seconds instead of requiring an app restart.

## Known issues

Mi TV Stick / Box (not re-tested against the current build):
- Streaming can still hang at random times.
- Smoothness may degrade when the large performance stats overlay is enabled.

Homatics Box 4K Pro V2:
- Selecting a non-default low-latency option causes freezes or no video; the watchdog recovers after a few seconds.
- Controller input stopped reaching the host after a long session in an older build, while video and audio kept running. The input worker thread and the updated moonlight-common-c input/locking fixes are intended to address this; still under observation.

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
Release version: app icons restored to the Moonlight defaults and support added for the Homatics Box 4K Pro V2.
Later work (decoder stall watchdog, selectable low-latency options, moonlight-common-c update, input worker thread,
decode time measurement fix) was developed with AI assistance and tested on a Homatics Box 4K Pro V2.
