package me.aaron.base.util;

import java.io.Serializable;

public final class Logger implements Serializable {

    private String tag;

    private Logger() {

    }

    public Logger(Class<?> cls) {
        this.tag = cls.getName();
    }

    public Logger(String tag) {
        this.tag = tag;
    }

    /**
     * Prints the stack trace for the given throwable instance for debug build.
     * @param ex
     */
    public void error(Throwable ex) {
        error(ex, false);
    }

    /**
     * Prints the stack trace for the given throwable instance for debug build.
     * Also, submits the crash report (only for release build) to Fabric if submitCrashReport is true.
     * @param ex
     * @param submitCrashReport
     */
    public void error(Throwable ex, boolean submitCrashReport) {
        LogUtils.error(this.tag, ex.getMessage(), ex);

        /*if (submitCrashReport
                && !BuildConfig.DEBUG
                &&  config.getFabricConfig().isEnabled()) {
            Crashlytics.logException(ex);
        }*/
    }

    public void warn(String log) {
        LogUtils.warn(this.tag, log);
    }

    public void debug(String log) {
        LogUtils.debug(this.tag, log);
    }
}