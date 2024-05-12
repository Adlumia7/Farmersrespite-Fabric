package com.chefsdelights.farmersrespite.core.registry;

import com.chefsdelights.farmersrespite.common.block.*;
import com.chefsdelights.farmersrespite.core.FarmersRespite;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.MapColor;
import vectorwing.farmersdelight.common.block.PieBlock;

import java.util.function.Supplier;
import java.util.function.ToIntFunction;

@SuppressWarnings("unused")
public enum FRBlocks {

    //Blocks

    // Workstations
    KETTLE("kettle", () -> new KettleBlock()),

    //Tea
    TEA_BUSH("tea_bush", () -> new TeaBushBlock(Block.Properties.of().instabreak().sound(SoundType.GRASS).noOcclusion())),
    SMALL_TEA_BUSH("small_tea_bush", () -> new SmallTeaBushBlock(Block.Properties.of().instabreak().sound(SoundType.GRASS).noOcclusion())),
    WILD_TEA_BUSH("wild_tea_bush", () -> new WildTeaBushBlock(Block.Properties.of().instabreak().sound(SoundType.GRASS).noOcclusion())),

    //Coffee
    COFFEE_BUSH("coffee_bush", () -> new CoffeeBushBlock(Block.Properties.of().instabreak().sound(SoundType.GRASS).noOcclusion())),
    COFFEE_STEM("coffee_stem", () -> new CoffeeStemBlock(Block.Properties.of().instabreak().sound(SoundType.GRASS).noOcclusion())),
    COFFEE_BUSH_TOP("coffee_bush_top", () -> new CoffeeBushTopBlock(Block.Properties.of().instabreak().sound(SoundType.GRASS).noOcclusion())),
    COFFEE_STEM_DOUBLE("coffee_stem_double", () -> new CoffeeDoubleStemBlock(Block.Properties.of().instabreak().sound(SoundType.GRASS).noOcclusion())),
    COFFEE_STEM_MIDDLE("coffee_stem_middle", () -> new CoffeeMiddleStemBlock(Block.Properties.of().instabreak().sound(SoundType.GRASS).noOcclusion())),
    WITHER_ROOTS("wither_roots", () -> new WitherRootsBlock(BlockBehaviour.Properties.of().replaceable().noCollission().instabreak().sound(SoundType.GRASS))),
    WITHER_ROOTS_PLANT("wither_roots_plant", () -> new WitherRootsBlock(BlockBehaviour.Properties.of().replaceable().noCollission().instabreak().sound(SoundType.GRASS))),

    //Food
    COFFEE_CAKE("coffee_cake", () -> new CoffeeCakeBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BROWN).strength(0.5F).sound(SoundType.WOOL))),
    ROSE_HIP_PIE("rose_hip_pie", () -> new PieBlock(Block.Properties.copy(Blocks.CAKE), () -> FRItems.ROSE_HIP_PIE_SLICE.get())),
    CANDLE_COFFEE_CAKE("candle_coffee_cake", () -> new CoffeeCandleCakeBlock(Blocks.CANDLE, BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BROWN).strength(0.5F).sound(SoundType.WOOL).lightLevel(litBlockEmission(3)))),
    WHITE_CANDLE_COFFEE_CAKE("white_candle_coffee_cake", () -> new CoffeeCandleCakeBlock(Blocks.WHITE_CANDLE, BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BROWN).strength(0.5F).sound(SoundType.WOOL).lightLevel(litBlockEmission(3)))),
    ORANGE_CANDLE_COFFEE_CAKE("orange_candle_coffee_cake", () -> new CoffeeCandleCakeBlock(Blocks.ORANGE_CANDLE, BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BROWN).strength(0.5F).sound(SoundType.WOOL).lightLevel(litBlockEmission(3)))),
    MAGENTA_CANDLE_COFFEE_CAKE("magenta_candle_coffee_cake", () -> new CoffeeCandleCakeBlock(Blocks.MAGENTA_CANDLE, BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BROWN).strength(0.5F).sound(SoundType.WOOL).lightLevel(litBlockEmission(3)))),
    LIGHT_BLUE_CANDLE_COFFEE_CAKE("light_blue_candle_coffee_cake", () -> new CoffeeCandleCakeBlock(Blocks.LIGHT_BLUE_CANDLE, BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BROWN).strength(0.5F).sound(SoundType.WOOL).lightLevel(litBlockEmission(3)))),
    YELLOW_CANDLE_COFFEE_CAKE("yellow_candle_coffee_cake", () -> new CoffeeCandleCakeBlock(Blocks.YELLOW_CANDLE, BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BROWN).strength(0.5F).sound(SoundType.WOOL).lightLevel(litBlockEmission(3)))),
    LIME_CANDLE_COFFEE_CAKE("lime_candle_coffee_cake", () -> new CoffeeCandleCakeBlock(Blocks.LIME_CANDLE, BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BROWN).strength(0.5F).sound(SoundType.WOOL).lightLevel(litBlockEmission(3)))),
    PINK_CANDLE_COFFEE_CAKE("pink_candle_coffee_cake", () -> new CoffeeCandleCakeBlock(Blocks.PINK_CANDLE, BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BROWN).strength(0.5F).sound(SoundType.WOOL).lightLevel(litBlockEmission(3)))),
    GRAY_CANDLE_COFFEE_CAKE("gray_candle_coffee_cake", () -> new CoffeeCandleCakeBlock(Blocks.GRAY_CANDLE, BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BROWN).strength(0.5F).sound(SoundType.WOOL).lightLevel(litBlockEmission(3)))),
    LIGHT_GRAY_CANDLE_COFFEE_CAKE("light_gray_candle_coffee_cake", () -> new CoffeeCandleCakeBlock(Blocks.LIGHT_GRAY_CANDLE, BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BROWN).strength(0.5F).sound(SoundType.WOOL).lightLevel(litBlockEmission(3)))),
    CYAN_CANDLE_COFFEE_CAKE("cyan_candle_coffee_cake", () -> new CoffeeCandleCakeBlock(Blocks.CYAN_CANDLE, BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BROWN).strength(0.5F).sound(SoundType.WOOL).lightLevel(litBlockEmission(3)))),
    PURPLE_CANDLE_COFFEE_CAKE("purple_candle_coffee_cake", () -> new CoffeeCandleCakeBlock(Blocks.PURPLE_CANDLE, BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BROWN).strength(0.5F).sound(SoundType.WOOL).lightLevel(litBlockEmission(3)))),
    BLUE_CANDLE_COFFEE_CAKE("blue_candle_coffee_cake", () -> new CoffeeCandleCakeBlock(Blocks.BLUE_CANDLE, BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BROWN).strength(0.5F).sound(SoundType.WOOL).lightLevel(litBlockEmission(3)))),
    BROWN_CANDLE_COFFEE_CAKE("brown_candle_coffee_cake", () -> new CoffeeCandleCakeBlock(Blocks.BROWN_CANDLE, BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BROWN).strength(0.5F).sound(SoundType.WOOL).lightLevel(litBlockEmission(3)))),
    GREEN_CANDLE_COFFEE_CAKE("green_candle_coffee_cake", () -> new CoffeeCandleCakeBlock(Blocks.GREEN_CANDLE, BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BROWN).strength(0.5F).sound(SoundType.WOOL).lightLevel(litBlockEmission(3)))),
    RED_CANDLE_COFFEE_CAKE("red_candle_coffee_cake", () -> new CoffeeCandleCakeBlock(Blocks.RED_CANDLE, BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BROWN).strength(0.5F).sound(SoundType.WOOL).lightLevel(litBlockEmission(3)))),
    BLACK_CANDLE_COFFEE_CAKE("black_candle_coffee_cake", () -> new CoffeeCandleCakeBlock(Blocks.BLACK_CANDLE, BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_BROWN).strength(0.5F).sound(SoundType.WOOL).lightLevel(litBlockEmission(3)))),

    //Decoration
    POTTED_TEA_BUSH("potted_tea_bush", () -> new FlowerPotBlock(FRBlocks.SMALL_TEA_BUSH.get(), BlockBehaviour.Properties.of().instabreak().noOcclusion())),
    POTTED_WILD_TEA_BUSH("potted_wild_tea_bush", () -> new FlowerPotBlock(FRBlocks.WILD_TEA_BUSH.get(), BlockBehaviour.Properties.of().instabreak().noOcclusion())),
    POTTED_COFFEE_BUSH("potted_coffee_bush", () -> new FlowerPotBlock(FRBlocks.COFFEE_BUSH.get(), BlockBehaviour.Properties.of().instabreak().noOcclusion()));

    private final String pathName;
    private final Supplier<Block> blockSupplier;
    private final FlammableBlockRegistry.Entry flammableRate;
    private final boolean isCutout;
    private Block block;

    private static ToIntFunction<BlockState> litBlockEmission(int level) {
        return (state) -> state.getValue(BlockStateProperties.LIT) ? level : 0;
    }

    private static FlammableBlockRegistry.Entry flammable(int burnChance, int spreadChance) {
        return new FlammableBlockRegistry.Entry(burnChance, spreadChance);
    }

    private static boolean isValidFlammableEntry(FlammableBlockRegistry.Entry flammableRate) {
        return flammableRate != null && flammableRate.getBurnChance() > 0 && flammableRate.getSpreadChance() > 0;
    }

    FRBlocks(String pathName, Supplier<Block> blockSupplier) {
        this(pathName, blockSupplier, false, new FlammableBlockRegistry.Entry(0, 0));
    }

    FRBlocks(String pathName, Supplier<Block> blockSupplier, boolean isCutout) {
        this(pathName, blockSupplier, isCutout, new FlammableBlockRegistry.Entry(0, 0));
    }

    FRBlocks(String pathName, Supplier<Block> blockSupplier, boolean isCutout, FlammableBlockRegistry.Entry flammableRate) {
        this.pathName = pathName;
        this.blockSupplier = blockSupplier;
        this.flammableRate = flammableRate;
        this.isCutout = isCutout;
    }

    public static void registerAll() {
        for (FRBlocks value : values()) {
            Block block = value.get();
            Registry.register(BuiltInRegistries.BLOCK, FarmersRespite.id(value.pathName), block);
            if (isValidFlammableEntry(value.flammableRate)) {
                FlammableBlockRegistry.getDefaultInstance().add(block, value.flammableRate);
            }
        }

    }

    @Environment(EnvType.CLIENT)
    public static void registerRenderLayer() {
        for (FRBlocks value : values()) {
            if (value.isCutout) {
                BlockRenderLayerMap.INSTANCE.putBlock(value.get(), RenderType.cutout());
            }
        }
    }

    public Block get() {
        if (this.block == null) {
            this.block = this.blockSupplier.get();
        }

        return this.block;
    }

    public String getId() {
        return BuiltInRegistries.BLOCK.getKey(this.get()).toString();
    }
}
