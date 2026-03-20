package cn.gtemc.itembridge.api.context;

public final class ContextKeys {
    private ContextKeys() {}

    public static final ContextKey<Integer> COUNT = ContextKey.of(Integer.class, "count");
}
