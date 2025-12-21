package cn.gtemc.itembridge.api.util;

@FunctionalInterface
public interface ThrowableRunnable {

    void run() throws Throwable;
}
