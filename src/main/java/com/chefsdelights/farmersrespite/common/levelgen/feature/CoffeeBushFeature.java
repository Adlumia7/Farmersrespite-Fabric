package com.chefsdelights.farmersrespite.common.levelgen.feature;

import com.chefsdelights.farmersrespite.common.block.CoffeeBushBlock;
import com.chefsdelights.farmersrespite.common.block.CoffeeBushTopBlock;
import com.chefsdelights.farmersrespite.common.block.CoffeeStemBlock;
import com.chefsdelights.farmersrespite.core.registry.FRBlocks;
import com.mojang.serialization.Codec;

import dev.emi.emi.api.recipe.EmiWorldInteractionRecipe.Builder;
import io.github.fabricators_of_create.porting_lib.tags.Tags;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.nbt.Tag;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.storage.loot.LootParams;

import java.util.HashMap;
import java.util.Map;

public class CoffeeBushFeature extends Feature<NoneFeatureConfiguration> {
    public CoffeeBushFeature(Codec<NoneFeatureConfiguration> config) {
        super(config);
    }

    public static final Direction[] DIRECTIONS = new Direction[] { Direction.NORTH, Direction.SOUTH, Direction.WEST,
            Direction.EAST };

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        WorldGenLevel level = context.level();
        BlockPos pos = context.origin();
        RandomSource rand = level.getRandom();
        BlockState coffeeBushBottom = FRBlocks.COFFEE_BUSH.get().defaultBlockState();
        BlockState coffeeBushTop = FRBlocks.COFFEE_BUSH.get().defaultBlockState().setValue(CoffeeBushBlock.HALF,
                DoubleBlockHalf.UPPER);
        BlockState coffeeStem = FRBlocks.COFFEE_STEM.get().defaultBlockState();
        BlockState coffeeBushTopBottom = FRBlocks.COFFEE_BUSH_TOP.get().defaultBlockState();
        BlockState coffeeBushTopTop = FRBlocks.COFFEE_BUSH_TOP.get().defaultBlockState()
                .setValue(CoffeeBushTopBlock.HALF, DoubleBlockHalf.UPPER);
        HashMap<BlockPos, BlockState> blocks = new HashMap<>();
        int i = 0;
        for (int x = -1; x <= 1; ++x) {
            for (int z = -2; z <= 2; ++z) {
                if (Math.abs(x) < 2 || Math.abs(z) < 2) {
                    for (int y = -3; y <= 3; ++y) {
                        BlockPos blockpos = pos.offset(x, y, z);
                        BlockPos below = blockpos.below();
                        BlockState belowState = level.getBlockState(below);
                        if (canGrowCoffee(belowState)) {
                            if (rand.nextInt(4) > 2) {
                                BlockPos above = blockpos.above();
                                BlockPos evenMoreAbove = blockpos.above(2);
                                if ((level.isEmptyBlock(blockpos) || blockHasLayers(level.getBlockState(blockpos))) && !level.isOutsideBuildHeight(above)
                                        && level.isEmptyBlock(above)) {
                                    if (rand.nextInt(5) > 2 && level.isEmptyBlock(evenMoreAbove)) {
                                        blocks.put(blockpos,
                                                coffeeStem.setValue(CoffeeStemBlock.FACING, DIRECTIONS[rand.nextInt(4)])
                                                        .setValue(CoffeeStemBlock.AGE, rand.nextInt(3)));
                                        blocks.put(above, coffeeBushTopBottom);
                                        blocks.put(evenMoreAbove, coffeeBushTopTop);
                                    } else {
                                        blocks.put(blockpos, coffeeBushBottom);
                                        blocks.put(above, coffeeBushTop);
                                    }
                                    ++i;
                                }
                            }
                        }
                    }
                }
            }
        }

        for (Map.Entry<BlockPos, BlockState> entry : blocks.entrySet()) {
            BlockPos entryPos = entry.getKey();
            BlockState entryState = entry.getValue();
            level.setBlock(entryPos, entryState, 19);
        }
        return i > 0;
    }

    public static boolean canGrowCoffee(BlockState state) {
        return  (state.is(BlockTags.BASE_STONE_NETHER) && !state.is(Blocks.NETHERRACK) && !state.is(Blocks.BLACKSTONE)) || state.is(Blocks.POLISHED_BASALT) || state.is(Blocks.SMOOTH_BASALT)
                || state.is(Blocks.MAGMA_BLOCK);
    }

    public static boolean blockHasLayers(BlockState state) {
        return state.hasProperty(BlockStateProperties.LAYERS);
    }
}