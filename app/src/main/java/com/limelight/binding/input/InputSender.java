package com.limelight.binding.input;

import android.os.Handler;
import android.os.HandlerThread;

/**
 * Optional worker thread for outgoing input packets (concept borrowed from the
 * derflacco/Artemis "Snappy Input" work, reimplemented for this fork).
 *
 * When enabled, controller input sends are posted to a dedicated HandlerThread
 * instead of being executed on the calling (usually UI) thread. This keeps the
 * UI thread responsive even if the native send path ever blocks, so the user
 * can still quit the stream. A single HandlerThread preserves the exact send
 * ordering of the synchronous path.
 *
 * When disabled, submit() runs the task inline, which is byte-for-byte the
 * previous behavior.
 */
public final class InputSender {
    private final boolean async;
    private final HandlerThread thread;
    private final Handler handler;
    private volatile boolean stopped = false;

    public InputSender(boolean async) {
        this.async = async;
        if (async) {
            thread = new HandlerThread("InputSender");
            thread.start();
            handler = new Handler(thread.getLooper());
        }
        else {
            thread = null;
            handler = null;
        }
    }

    public void submit(Runnable r) {
        if (r == null) {
            return;
        }
        if (!async || stopped) {
            r.run();
            return;
        }
        // If the looper is already quitting, fall back to inline execution
        // so teardown packets (like controller zeroing) are never lost.
        if (!handler.post(r)) {
            r.run();
        }
    }

    public void shutdown() {
        stopped = true;
        if (thread != null) {
            // quitSafely() still processes already-queued sends before exiting.
            thread.quitSafely();
        }
    }
}
