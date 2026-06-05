package cn.gtemc.itembridge.hook;

import cn.gtemc.itembridge.api.Provider;
import cn.gtemc.itembridge.api.context.BuildContext;
import com.badbones69.crazyvouchers.CrazyVouchers;
import com.badbones69.crazyvouchers.api.objects.Voucher;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

final class CrazyVouchersProvider implements Provider<ItemStack, Player> {
    public static final CrazyVouchersProvider INSTANCE = new CrazyVouchersProvider();

    private CrazyVouchersProvider() {}

    @Override
    public String plugin() {
        return "crazyvouchers";
    }

    @Override
    public Optional<ItemStack> build(String id, @Nullable Player player, @NotNull BuildContext context) {
        Voucher voucher = CrazyVouchers.get().getCrazyManager().getVoucher(id);
        if (voucher == null) {
            return Optional.empty();
        }
        return Optional.ofNullable(voucher.buildItem());
    }

    @Override
    public @Nullable ItemStack buildOrNull(String id, @Nullable Player player, @NotNull BuildContext context) {
        Voucher voucher = CrazyVouchers.get().getCrazyManager().getVoucher(id);
        if (voucher == null) {
            return null;
        }
        return voucher.buildItem();
    }

    @Override
    public Optional<String> id(@NotNull ItemStack item) {
        Voucher voucher = CrazyVouchers.get().getCrazyManager().getVoucherFromItem(item);
        if (voucher == null) {
            return Optional.empty();
        }
        return Optional.ofNullable(voucher.getName());
    }

    @Override
    public @Nullable String idOrNull(@NotNull ItemStack item) {
        Voucher voucher = CrazyVouchers.get().getCrazyManager().getVoucherFromItem(item);
        if (voucher == null) {
            return null;
        }
        return voucher.getName();
    }

    @Override
    public boolean is(@NotNull ItemStack item) {
        return CrazyVouchers.get().getCrazyManager().getVoucherFromItem(item) != null;
    }

    @Override
    public boolean has(@NotNull String id) {
        return CrazyVouchers.get().getCrazyManager().getVoucher(id) != null;
    }

    final static class Check {
        static boolean conflictCheck(Plugin plugin) {
            return Utils.classExists("com{}badbones69{}crazyvouchers{}CrazyVouchers");
        }
    }
}
