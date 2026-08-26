package net.combatroll.compatibility;

import net.combatroll.Platform;
import net.minecraft.client.MinecraftClient;

/**
 * Compatibility with the shiledattack mod's hold-to-attack feature.
 *
 * shiledattack lets the player keep attacking continuously by holding the left
 * mouse button (auto attack). While doing that, Combat Roll's own "do not roll
 * while the attack key is held" guard would block rolling. This helper detects
 * that situation (mod loaded + attack key held + not currently using an item)
 * so {@code tryRolling()} can allow the roll.
 *
 * The mod is optional and not a compile-time dependency, so only the mod id is
 * checked at runtime. When shiledattack is absent this returns {@code false}
 * and the original behaviour is fully preserved.
 */
public class ShiledAttackHelper {
    private static Boolean loaded;

    private ShiledAttackHelper() {
    }

    public static boolean isLoaded() {
        if (loaded == null) {
            loaded = Platform.isModLoaded("shiledattack");
        }
        return loaded;
    }

    /**
     * True when the player is holding the attack button while the shiledattack
     * auto attack is in play, i.e. the attack key is held and the player is not
     * currently using an item (shiledattack itself disables auto attack while
     * using an item, e.g. blocking with a shield or eating).
     */
    public static boolean isAutoAttackHeld(MinecraftClient client) {
        if (!isLoaded()) {
            return false;
        }
        if (client.player == null) {
            return false;
        }
        if (!client.options.attackKey.isPressed()) {
            return false;
        }
        return !client.player.isUsingItem();
    }
}
