package com.heart.mod.item;

import com.heart.mod.HeartMod;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModItems {

    public static final Item KEY = registerItem("key", new HeartKeyItem(new Item.Settings().maxCount(1)));
    public static final Item CURSED_KEY = registerItem("cursed_key",
            new CursedKeyItem(new Item.Settings().maxCount(1)));

    public static final Item SUPER_KEY =  registerItem("super_key", new SuperKeyItem(new Item.Settings().maxCount(1)));

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, Identifier.of(HeartMod.MOD_ID,name), item);
    }

    public static void registerModItems(){
        HeartMod.LOGGER.info("Registering Mod Items for "+ HeartMod.MOD_ID);

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(entries ->{
            entries.add(KEY);
            entries.add(CURSED_KEY);
            entries.add(SUPER_KEY);
        });
    }
}
