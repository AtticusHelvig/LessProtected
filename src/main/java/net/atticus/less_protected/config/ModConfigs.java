package net.atticus.less_protected.config;

import com.mojang.datafixers.util.Pair;

import net.atticus.less_protected.LessProtected;

public class ModConfigs {
    public static SimpleConfig CONFIG;
    private static ModConfigProvider configs;

    public static boolean MODIFY_NON_PLAYERS;

    public static float ARMOR_TOUGHNESS_MODIFIER;
    public static float ARMOR_MODIFIER;
    public static float PROTECTION_MODIFIER;

    public static void registerConfigs() {
        configs = new ModConfigProvider();
        createConfigs();

        CONFIG = SimpleConfig.of(LessProtected.MOD_ID).provider(configs).request();

        assignConfigs();
    }

    private static void createConfigs() {
        configs.addKeyValuePair(new Pair<>("modify_non_players", false));
        configs.addKeyValuePair(new Pair<>("armorToughnessModifier", 1.0));
        configs.addKeyValuePair(new Pair<>("armorModifier", 0.8));
        configs.addKeyValuePair(new Pair<>("protectionModifier", 0.5));
    }

    private static void assignConfigs() {
        MODIFY_NON_PLAYERS = CONFIG.getOrDefault("modify_non_players", false);
        LessProtected.LOGGER.info("modify_non_players=" + MODIFY_NON_PLAYERS);
        ARMOR_TOUGHNESS_MODIFIER = (float) CONFIG.getOrDefault("armorToughnessModifier", 1.0);
        LessProtected.LOGGER.info("armorToughnessModifer=" + ARMOR_TOUGHNESS_MODIFIER);
        ARMOR_MODIFIER = (float) CONFIG.getOrDefault("armorModifier", 1.0);
        LessProtected.LOGGER.info("armorModifier=" + ARMOR_MODIFIER);
        PROTECTION_MODIFIER = (float) CONFIG.getOrDefault("protectionModifier", 1.0);
        LessProtected.LOGGER.info("protectionModifier=" + PROTECTION_MODIFIER);
    }
}
