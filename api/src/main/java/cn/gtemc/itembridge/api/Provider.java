package cn.gtemc.itembridge.api;

import cn.gtemc.itembridge.api.context.BuildContext;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public interface Provider<T> {

    String plugin();

    default Optional<T> build(@NotNull String id) {
        return build(id, BuildContext.empty());
    }

    Optional<T> build(String id, @NotNull BuildContext context);

    Optional<String> id(@NotNull T object);

    boolean is(@NotNull T object);

    boolean has(@NotNull String id);
}
