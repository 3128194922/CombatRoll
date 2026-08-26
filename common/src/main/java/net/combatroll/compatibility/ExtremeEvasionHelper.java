package net.combatroll.compatibility;

import net.combatroll.Platform;
import net.minecraft.server.network.ServerPlayerEntity;

import java.lang.reflect.Method;

/**
 * Proactive compatibility with the ExtremeEvasion mod.
 *
 * ExtremeEvasion (Forge-only, MCP mappings) exposes a public static method
 * {@code ExtremeEvasionEvents.startExternalRollWindow(Player)} which opens its
 * "perfect dodge" detection window. Because ExtremeEvasion is compiled with
 * different mappings and is not a compile-time dependency, we call it
 * reflectively at runtime. This is guarded by a mod-loaded check so Combat Roll
 * still works when ExtremeEvasion is absent.
 */
public class ExtremeEvasionHelper {
    private static final String EVENTS_CLASS = "cc.sighs.extremeevasion.ExtremeEvasionEvents";
    private static Method startExternalRollWindow;

    public static void notifyRoll(ServerPlayerEntity player) {
        if (!Platform.isModLoaded("extremeevasion")) {
            return;
        }
        try {
            var method = resolveMethod();
            if (method != null) {
                method.invoke(null, player);
            }
        } catch (Exception ignored) {
            // ExtremeEvasion is optional; compatibility failures must never break rolling.
        }
    }

    private static Method resolveMethod() {
        if (startExternalRollWindow != null) {
            return startExternalRollWindow;
        }
        try {
            Class<?> clazz = Class.forName(EVENTS_CLASS);
            for (Method method : clazz.getMethods()) {
                if ("startExternalRollWindow".equals(method.getName()) && method.getParameterCount() == 1) {
                    startExternalRollWindow = method;
                    return method;
                }
            }
        } catch (ClassNotFoundException ignored) {
        }
        return null;
    }
}