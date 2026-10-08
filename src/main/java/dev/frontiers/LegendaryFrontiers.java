package dev.frontiers;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.*;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.*;

@Mod(LegendaryFrontiers.MODID)
public final class LegendaryFrontiers {
    public static final String MODID = "legendary_frontiers";
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);
    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);
    public static final RegistryObject<Item> EMBER = blade("ember_oath", AbilityBlade.Ability.EMBER, 1200, 4f, -2.6f);
    public static final RegistryObject<Item> FROST = blade("winter_verdict", AbilityBlade.Ability.FROST, 1600, 3f, -2.8f);
    public static final RegistryObject<Item> WIND = blade("sky_splitter", AbilityBlade.Ability.WIND, 850, 2f, -1.9f);
    public static final RegistryObject<Item> LIFE = blade("rootbound_mercy", AbilityBlade.Ability.LIFE, 1000, 2.5f, -2.4f);
    public static final RegistryObject<Item> BOW = ITEMS.register("ranger_bow",
        () -> new BowItem(new Item.Properties().setId(ITEMS.key("ranger_bow")).durability(900)));
    private static RegistryObject<Item> blade(String id, AbilityBlade.Ability ability, int durability, float damage, float speed) {
        return ITEMS.register(id, () -> new AbilityBlade(new Item.Properties().setId(ITEMS.key(id))
            .sword(ToolMaterial.DIAMOND, damage, speed).durability(durability), ability));
    }
    public static final RegistryObject<CreativeModeTab> TAB = TABS.register("frontiers", () -> CreativeModeTab.builder()
        .withTabsBefore(CreativeModeTabs.COMBAT)
        .icon(() -> EMBER.get().getDefaultInstance())
        .displayItems((parameters, output) -> { output.accept(EMBER.get()); output.accept(FROST.get());
            output.accept(WIND.get()); output.accept(LIFE.get()); output.accept(BOW.get()); }).build());
    public LegendaryFrontiers(FMLJavaModLoadingContext context) {
        var bus = context.getModBusGroup(); ITEMS.register(bus); TABS.register(bus);
    }
}
