package com.chefsdelights.farmersrespite.core.event;

import com.chefsdelights.farmersrespite.common.loot.function.FRCopyMealFunction;
import com.chefsdelights.farmersrespite.core.registry.FRItems;
import net.minecraft.world.level.block.ComposterBlock;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctions;

public class FRCommonSetup {
    public static void init() {
        registerCompostables();
        LootItemFunctions.register(FRCopyMealFunction.ID.toString(), new FRCopyMealFunction.Serializer());

    }

    public static void registerCompostables() {
        ComposterBlock.COMPOSTABLES.put(FRItems.GREEN_TEA_LEAVES.get(), 0.3F);
        ComposterBlock.COMPOSTABLES.put(FRItems.YELLOW_TEA_LEAVES.get(), 0.2F);
        ComposterBlock.COMPOSTABLES.put(FRItems.BLACK_TEA_LEAVES.get(), 0.1F);
        ComposterBlock.COMPOSTABLES.put(FRItems.COFFEE_BERRIES.get(), 0.3F);
        ComposterBlock.COMPOSTABLES.put(FRItems.TEA_SEEDS.get(), 0.3F);
        ComposterBlock.COMPOSTABLES.put(FRItems.ROSE_HIPS.get(), 0.3F);

        ComposterBlock.COMPOSTABLES.put(FRItems.GREEN_TEA_COOKIE.get(), 0.85F);
        ComposterBlock.COMPOSTABLES.put(FRItems.WILD_TEA_BUSH.get(), 0.65F);
        ComposterBlock.COMPOSTABLES.put(FRItems.COFFEE_CAKE.get(), 1.0F);
        ComposterBlock.COMPOSTABLES.put(FRItems.ROSE_HIP_PIE.get(), 1.0F);
        ComposterBlock.COMPOSTABLES.put(FRItems.COFFEE_CAKE_SLICE.get(), 0.85F);
        ComposterBlock.COMPOSTABLES.put(FRItems.ROSE_HIP_PIE_SLICE.get(), 0.85F);
    }
}