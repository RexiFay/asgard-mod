package com.asgardmod.asgardmod.block;

import com.asgardmod.asgardmod.AsgardMod;
import com.asgardmod.asgardmod.item.ModItems;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, AsgardMod.MOD_ID);

    // GOD'S DOMAIN BLOCKS
    public static final RegistryObject<Block> DIVINE_STONE = registerBlock("divine_stone",
            () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.QUARTZ).requiresCorrectToolForDrops().strength(3.5f, 12.0f).sound(SoundType.CALCITE)));
    public static final RegistryObject<Block> DIVINE_GRASS = registerBlock("divine_grass",
            () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_LIGHT_BLUE).strength(0.6f).sound(SoundType.GRASS)));
    public static final RegistryObject<Block> DIVINE_CLOUD = registerBlock("divine_cloud",
            () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.SNOW).strength(0.3f).sound(SoundType.WOOL).noOcclusion().lightLevel(s -> 4)));
    public static final RegistryObject<Block> CELESTIAL_MARBLE = registerBlock("celestial_marble",
            () -> new CelestialMarbleBlock(BlockBehaviour.Properties.of().mapColor(MapColor.QUARTZ).requiresCorrectToolForDrops().strength(4.0f, 15.0f).sound(SoundType.CALCITE).lightLevel(s -> s.getValue(CelestialMarbleBlock.ACTIVATED) ? 10 : 0)));
    public static final RegistryObject<Block> CELESTIAL_MARBLE_PILLAR = registerBlock("celestial_marble_pillar",
            () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.QUARTZ).requiresCorrectToolForDrops().strength(4.0f, 15.0f).sound(SoundType.CALCITE)));
    public static final RegistryObject<Block> DIVINE_WOOD_LOG = registerBlock("divine_wood_log",
            () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_CYAN).strength(2.0f).sound(SoundType.WOOD)));
    public static final RegistryObject<Block> DIVINE_LEAVES = registerBlock("divine_leaves",
            () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_LIGHT_BLUE).strength(0.2f).sound(SoundType.GRASS).noOcclusion().lightLevel(s -> 3)));

    // ASGARD BLOCKS
    public static final RegistryObject<Block> JADE_STONE = registerBlock("jade_stone",
            () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_GREEN).requiresCorrectToolForDrops().strength(3.0f, 10.0f).sound(SoundType.STONE)));
    public static final RegistryObject<Block> JADE_BLOCK = registerBlock("jade_block",
            () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_GREEN).requiresCorrectToolForDrops().strength(5.0f, 20.0f).sound(SoundType.METAL)));
    public static final RegistryObject<Block> GOLD_STONE = registerBlock("gold_stone",
            () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.GOLD).requiresCorrectToolForDrops().strength(3.0f, 10.0f).sound(SoundType.STONE)));
    public static final RegistryObject<Block> ASGARD_GRASS = registerBlock("asgard_grass",
            () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.GRASS).strength(0.6f).sound(SoundType.GRASS)));
    public static final RegistryObject<Block> ASGARD_DIRT = registerBlock("asgard_dirt",
            () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.DIRT).strength(0.5f).sound(SoundType.GRAVEL)));
    public static final RegistryObject<Block> GOLD_LOG = registerBlock("gold_log",
            () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.GOLD).strength(2.0f).sound(SoundType.WOOD)));
    public static final RegistryObject<Block> GOLD_LEAVES = registerBlock("gold_leaves",
            () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.GOLD).strength(0.2f).sound(SoundType.GRASS).noOcclusion().lightLevel(s -> 2)));
    public static final RegistryObject<Block> JADE_PILLAR = registerBlock("jade_pillar",
            () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_GREEN).requiresCorrectToolForDrops().strength(4.0f, 15.0f).sound(SoundType.STONE)));
    public static final RegistryObject<Block> SKY_PIERCER_CORE = registerBlock("sky_piercer_core",
            () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_GREEN).requiresCorrectToolForDrops().strength(6.0f, 30.0f).sound(SoundType.METAL).lightLevel(s -> 5)));
    public static final RegistryObject<Block> ASGARD_DEEPSTONE = registerBlock("asgard_deepstone",
            () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.DEEPSLATE).requiresCorrectToolForDrops().strength(4.0f, 12.0f).sound(SoundType.DEEPSLATE)));
    public static final RegistryObject<Block> ASGARD_DIAMOND_ORE = registerBlock("asgard_diamond_ore",
            () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_GREEN).requiresCorrectToolForDrops().strength(3.5f).sound(SoundType.STONE).lightLevel(s -> 2)));
    public static final RegistryObject<Block> ASGARD_EMERALD_ORE = registerBlock("asgard_emerald_ore",
            () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_GREEN).requiresCorrectToolForDrops().strength(3.5f).sound(SoundType.STONE).lightLevel(s -> 2)));
    public static final RegistryObject<Block> CONVERGENCE_NODE = registerBlock("convergence_node",
            () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_PURPLE).requiresCorrectToolForDrops().strength(5.0f, 25.0f).sound(SoundType.AMETHYST).lightLevel(s -> 8).noOcclusion()));
    public static final RegistryObject<Block> ANCIENT_IRON = registerBlock("ancient_iron",
            () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.METAL).requiresCorrectToolForDrops().strength(5.0f, 20.0f).sound(SoundType.METAL)));

    // ── PORTAL BLOCKS ──────────────────────────────────────────────────────
    public static final RegistryObject<Block> GODS_DOMAIN_PORTAL = registerBlock("gods_domain_portal",
            () -> new GodsPortalBlock(
                BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_LIGHT_BLUE)
                    .strength(-1.0f, 3600000.0f)
                    .sound(SoundType.GLASS)
                    .noOcclusion()
                    .noCollission()
                    .lightLevel(s -> 11)
            ));

    public static final RegistryObject<Block> ASGARD_PORTAL = registerBlock("asgard_portal",
            () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.GOLD).strength(-1.0f, 3600000.0f).sound(SoundType.GLASS).noOcclusion().lightLevel(s -> 11)));

    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> void registerBlockItem(String name, RegistryObject<T> block) {
        ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
