package net.combatroll.api;

import net.combatroll.CombatRoll;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.attribute.ClampedEntityAttribute;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;

import java.util.List;

public class EntityAttributes_CombatRoll {

    public static final String distanceName = "distance";
    public static final Identifier distanceId = new Identifier(CombatRoll.MOD_ID + ":" + distanceName);
    public static final EntityAttribute DISTANCE = (new ClampedEntityAttribute("attribute.name.combat_roll." + distanceName, 3.0, 1, 24.0)).setTracked(true);

    public static final String rechargeName = "recharge";
    public static final Identifier rechargeId = new Identifier(CombatRoll.MOD_ID + ":" + rechargeName);
    public static final EntityAttribute RECHARGE = (new ClampedEntityAttribute("attribute.name.combat_roll." + rechargeName, 20, 0.1, 200)).setTracked(true);

    public static final String countName = "count";
    public static final Identifier countId = new Identifier(CombatRoll.MOD_ID + ":" + countName);
    public static final EntityAttribute COUNT = (new ClampedEntityAttribute("attribute.name.combat_roll." + countName, 1, 0, 20.0)).setTracked(true);

    public static final String airRollName = "air_roll";
    public static final Identifier airRollId = new Identifier(CombatRoll.MOD_ID + ":" + airRollName);
    public static final EntityAttribute AIR_ROLL = (new ClampedEntityAttribute(
            "attribute.name.combat_roll." + airRollName,
            (CombatRoll.config != null && CombatRoll.config.allow_rolling_while_airborn_default) ? 1.0 : 0.0,
            0, 1.0)).setTracked(true);

    public static List<EntityAttribute> all;
    static {
        all = List.of(DISTANCE, RECHARGE, COUNT, AIR_ROLL);
    }

    // Helper

    public enum Type {
        DISTANCE, RECHARGE, COUNT, AIR_ROLL
    }

    public static double getAttributeValue(PlayerEntity player, Type type) {
        switch (type) {
            case DISTANCE -> {
                var value = player.getAttributeValue(DISTANCE);
                var level = EnchantmentHelper.getEquipmentLevel(Enchantments_CombatRoll.DISTANCE, player);
                value = Enchantments_CombatRoll.DISTANCE.apply(value, level);
                return value;
            }
            case RECHARGE -> {
                var value = player.getAttributeValue(RECHARGE);
                var chestLevel = EnchantmentHelper.getEquipmentLevel(Enchantments_CombatRoll.RECHARGE, player);
                value = Enchantments_CombatRoll.RECHARGE.apply(value, chestLevel);
                return value;
            }
            case COUNT -> {
                var value = player.getAttributeValue(COUNT);
                var level = EnchantmentHelper.getEquipmentLevel(Enchantments_CombatRoll.COUNT, player);
                value = Enchantments_CombatRoll.COUNT.apply(value, level);
                return value;
            }
            case AIR_ROLL -> {
                return player.getAttributeValue(AIR_ROLL);
            }
        }
        return 1; // Should never happen
    }
}
