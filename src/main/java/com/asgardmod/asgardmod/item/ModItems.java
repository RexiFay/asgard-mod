package com.asgardmod.asgardmod.item;

import com.asgardmod.asgardmod.AsgardMod;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, AsgardMod.MOD_ID);

    public static final RegistryObject<Item> GODS_DOMAIN_KEY = ITEMS.register("gods_domain_key",
            () -> new Item(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> ASGARD_KEY = ITEMS.register("asgard_key",
            () -> new Item(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> JADE_GEM = ITEMS.register("jade_gem",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> CONVERGENCE_SHARD = ITEMS.register("convergence_shard",
            () -> new Item(new Item.Properties()));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
