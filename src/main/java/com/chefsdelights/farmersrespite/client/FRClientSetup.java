package com.chefsdelights.farmersrespite.client;

import com.chefsdelights.farmersrespite.client.gui.KettleScreen;
import com.chefsdelights.farmersrespite.core.registry.FRBlocks;
import com.chefsdelights.farmersrespite.core.registry.FRContainerTypes;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.RenderType;

public class FRClientSetup implements ClientModInitializer {
    public void onInitializeClient() {
        BlockRenderLayerMap.INSTANCE.putBlock(FRBlocks.KETTLE.get(), RenderType.cutout());

        BlockRenderLayerMap.INSTANCE.putBlock(FRBlocks.COFFEE_BUSH.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(FRBlocks.COFFEE_BUSH_TOP.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(FRBlocks.COFFEE_STEM.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(FRBlocks.COFFEE_STEM_DOUBLE.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(FRBlocks.COFFEE_STEM_MIDDLE.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(FRBlocks.SMALL_TEA_BUSH.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(FRBlocks.TEA_BUSH.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(FRBlocks.WILD_TEA_BUSH.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(FRBlocks.WITHER_ROOTS.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(FRBlocks.WITHER_ROOTS_PLANT.get(), RenderType.cutout());

        BlockRenderLayerMap.INSTANCE.putBlock(FRBlocks.POTTED_COFFEE_BUSH.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(FRBlocks.POTTED_TEA_BUSH.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(FRBlocks.POTTED_WILD_TEA_BUSH.get(), RenderType.cutout());

        /*
        ClientSpriteRegistryCallback.event(InventoryMenu.BLOCK_ATLAS).register(
                (atlasTexture, registry) -> registry.register(KettleContainer.EMPTY_CONTAINER_SLOT_BOTTLE));

         */

        MenuScreens.register(FRContainerTypes.KETTLE, KettleScreen::new);
    }
}

