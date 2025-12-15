package cn.gtemc.itembridge.api;

import cn.gtemc.itembridge.api.context.BuildContext;

import java.util.Optional;

public interface Provider<T> {

    String plugin();

    default Optional<T> build(String id) {
        return build(id, BuildContext.empty());
    }

    Optional<T> build(String id, BuildContext context);

    Optional<String> id(T object);

    boolean is(T object);

    boolean has(String id);
}
