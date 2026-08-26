package net.combatroll.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;

@Config(name = "server")
public class ServerConfig implements ConfigData {
    @Comment("The number of game ticks players become invulnerable upon rolling")
    public int invulnerable_ticks_upon_roll = 0;
    @Comment("The duration of the roll ability, expressed as a number of ticks, during which the player cannot use item/attack/jump etc...")
    public int roll_duration = 8;
    @Comment("Allows Vanilla Minecraft auto jump feature to work while rolling")
    public boolean allow_auto_jump_while_rolling = true;
    @Comment("Allows jumping while rolling. WARNING! Setting this to `true` breaks roll distance attribute and enchantment")
    public boolean allow_jump_while_rolling = false;
    @Comment("Allows combat roll while the player has its weapon on cooldown")
    public boolean allow_rolling_while_weapon_cooldown = false;
    @Comment("The default value of the air roll attribute. When `true`, the air roll attribute defaults to 1 (players can roll while airborne) instead of 0. This only sets the players' default; the actual behavior is driven by the air roll attribute which can be modified by other sources.")
    public boolean allow_rolling_while_airborn_default = false;
    @Comment("Automatically cancel the player's current action (using an item, blocking, etc.) and roll anyway. When `false`, rolling is prevented while the player is using an item or blocking.")
    public boolean cancel_current_action_on_roll = true;
    @Comment("The amount of exhaust (hunger) to be added to the player on every roll")
    public float exhaust_on_roll = 0.1F;
    @Comment("The amount of food level above which players can do a roll")
    public float food_level_required = 6;
    @Comment("The cooldown duration of the combat roll ability expressed in seconds")
    public float roll_cooldown = 4F;
    @Comment("Default roll distance attribute is `3`. Settings this to `1` will make it `4`. Warning! Attribute based scaling does not effect this.")
    public float additional_roll_distance = 0;
}
