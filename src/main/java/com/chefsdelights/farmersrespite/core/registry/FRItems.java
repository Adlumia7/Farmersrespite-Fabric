package com.chefsdelights.farmersrespite.core.registry;

import com.chefsdelights.farmersrespite.common.item.PurulentTeaItem;
import com.chefsdelights.farmersrespite.common.item.RoseHipTeaItem;
import com.chefsdelights.farmersrespite.core.FarmersRespite;
import com.chefsdelights.farmersrespite.core.utility.FRFoods;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.registry.FuelRegistry;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import vectorwing.farmersdelight.common.item.ConsumableItem;
import vectorwing.farmersdelight.common.item.DrinkableItem;
import vectorwing.farmersdelight.common.registry.ModItems;

import java.util.Arrays;
import java.util.function.Supplier;



@SuppressWarnings("unused")
public enum FRItems {


    // Items
    KETTLE("kettle", () -> new BlockItem(FRBlocks.KETTLE.get(), new Item.Properties().stacksTo(1))),
    WILD_TEA_BUSH("wild_tea_bush", () -> new BlockItem(FRBlocks.WILD_TEA_BUSH.get(), new Item.Properties())),
    TEA_SEEDS("tea_seeds", () -> new BlockItem(FRBlocks.SMALL_TEA_BUSH.get(), new Item.Properties())),
    COFFEE_BEANS("coffee_beans", () -> new BlockItem(FRBlocks.COFFEE_BUSH.get(), new Item.Properties())),
    GREEN_TEA_LEAVES("green_tea_leaves", () -> new Item(new Item.Properties())),
    YELLOW_TEA_LEAVES("yellow_tea_leaves", () -> new Item(new Item.Properties())),
    BLACK_TEA_LEAVES("black_tea_leaves", () -> new Item(new Item.Properties().food(FRFoods.COFFEE_BERRIES))),
    COFFEE_BERRIES("coffee_berries", () -> new Item(new Item.Properties())),


    GREEN_TEA("green_tea", () -> new DrinkableItem(new Item.Properties().food(FRFoods.GREEN_TEA).craftRemainder(Items.GLASS_BOTTLE).stacksTo(16), true, false)),
    YELLOW_TEA("yellow_tea", () -> new DrinkableItem(new Item.Properties().food(FRFoods.YELLOW_TEA).craftRemainder(Items.GLASS_BOTTLE).stacksTo(16), true, false)),
    BLACK_TEA("black_tea", () -> new DrinkableItem(new Item.Properties().food(FRFoods.BLACK_TEA).craftRemainder(Items.GLASS_BOTTLE).stacksTo(16), true, false)),
    ROSE_HIP_TEA("rose_hip_tea", () -> new RoseHipTeaItem(2, new Item.Properties().craftRemainder(Items.GLASS_BOTTLE).stacksTo(16))),
    DANDELION_TEA("dandelion_tea", () -> new DrinkableItem(new Item.Properties().food(FRFoods.DANDELION_TEA).craftRemainder(Items.GLASS_BOTTLE).stacksTo(16), true, false)),
    PURULENT_TEA("purulent_tea", () -> new PurulentTeaItem(300, new Item.Properties().food(FRFoods.PURULENT_TEA).craftRemainder(Items.GLASS_BOTTLE).stacksTo(16))),
    COFFEE("coffee", () -> new DrinkableItem(new Item.Properties().food(FRFoods.COFFEE).craftRemainder(Items.GLASS_BOTTLE).stacksTo(16), true, false)),
    ROSE_HIPS("rose_hips", () -> new Item(new Item.Properties())),


    LONG_GREEN_TEA("long_green_tea", () -> new DrinkableItem(new Item.Properties().food(FRFoods.LONG_GREEN_TEA).craftRemainder(Items.GLASS_BOTTLE).stacksTo(16), true, false)),
    LONG_YELLOW_TEA("long_yellow_tea", () -> new DrinkableItem(new Item.Properties().food(FRFoods.LONG_YELLOW_TEA).craftRemainder(Items.GLASS_BOTTLE).stacksTo(16), true, false)),
    LONG_BLACK_TEA("long_black_tea", () -> new DrinkableItem(new Item.Properties().food(FRFoods.LONG_BLACK_TEA).craftRemainder(Items.GLASS_BOTTLE).stacksTo(16), true, false)),
    LONG_DANDELION_TEA("long_dandelion_tea", () -> new DrinkableItem(new Item.Properties().food(FRFoods.LONG_DANDELION_TEA).craftRemainder(Items.GLASS_BOTTLE).stacksTo(16), true, false)),
    LONG_COFFEE("long_coffee", () -> new DrinkableItem(new Item.Properties().food(FRFoods.LONG_COFFEE).craftRemainder(Items.GLASS_BOTTLE).stacksTo(16), true, false)),
    LONG_APPLE_CIDER("long_apple_cider", () -> new DrinkableItem(new Item.Properties().food(FRFoods.LONG_APPLE_CIDER).craftRemainder(Items.GLASS_BOTTLE).stacksTo(16), true, false)),

    STRONG_GREEN_TEA("strong_green_tea", () -> new DrinkableItem(new Item.Properties().food(FRFoods.STRONG_GREEN_TEA).craftRemainder(Items.GLASS_BOTTLE).stacksTo(16), true, false)),
    STRONG_YELLOW_TEA("strong_yellow_tea", () -> new DrinkableItem(new Item.Properties().food(FRFoods.STRONG_YELLOW_TEA).craftRemainder(Items.GLASS_BOTTLE).stacksTo(16), true, false)),
    STRONG_BLACK_TEA("strong_black_tea", () -> new DrinkableItem(new Item.Properties().food(FRFoods.STRONG_BLACK_TEA).craftRemainder(Items.GLASS_BOTTLE).stacksTo(16), true, false)),
    STRONG_PURULENT_TEA("strong_purulent_tea", () -> new PurulentTeaItem(600, new Item.Properties().food(FRFoods.STRONG_PURULENT_TEA).craftRemainder(Items.GLASS_BOTTLE).stacksTo(16))),
    STRONG_ROSE_HIP_TEA("strong_rose_hip_tea", () -> new RoseHipTeaItem(4, new Item.Properties().craftRemainder(Items.GLASS_BOTTLE).stacksTo(16))),
    STRONG_COFFEE("strong_coffee", () -> new DrinkableItem(new Item.Properties().food(FRFoods.STRONG_COFFEE).craftRemainder(Items.GLASS_BOTTLE).stacksTo(16), true, false)),
    STRONG_MELON_JUICE("strong_melon_juice", () -> new RoseHipTeaItem(4, new Item.Properties().craftRemainder(Items.GLASS_BOTTLE).stacksTo(16))),
    STRONG_APPLE_CIDER("strong_apple_cider", () -> new DrinkableItem(new Item.Properties().food(FRFoods.STRONG_APPLE_CIDER).craftRemainder(Items.GLASS_BOTTLE).stacksTo(16), true, false)),

    GREEN_TEA_COOKIE("green_tea_cookie", () -> new Item(new Item.Properties().food(FRFoods.GREEN_TEA_COOKIE))),
    NETHER_WART_SOURDOUGH("nether_wart_sourdough", () -> new Item(new Item.Properties().food(FRFoods.NETHER_WART_SOURDOUGH))),

    BLACK_COD("black_cod", () -> new ConsumableItem(new Item.Properties().food(FRFoods.BLACK_COD).craftRemainder(Items.BOWL).stacksTo(16), true)),
    TEA_CURRY("tea_curry", () -> new ConsumableItem(new Item.Properties().food(FRFoods.TEA_CURRY).craftRemainder(Items.BOWL).stacksTo(16), true)),
    BLAZING_CHILI("blazing_chili", () -> new ConsumableItem(new Item.Properties().food(FRFoods.BLAZING_CHILLI).craftRemainder(Items.BOWL).stacksTo(16), true)),

    COFFEE_CAKE("coffee_cake", () -> new BlockItem(FRBlocks.COFFEE_CAKE.get(), new Item.Properties().stacksTo(1))),
    COFFEE_CAKE_SLICE("coffee_cake_slice", () -> new Item(new Item.Properties().food(FRFoods.COFFEE_CAKE_SLICE))),
    ROSE_HIP_PIE("rose_hip_pie", () -> new BlockItem(FRBlocks.ROSE_HIP_PIE.get(), new Item.Properties())),
    ROSE_HIP_PIE_SLICE("rose_hip_pie_slice", () -> new Item(new Item.Properties().food(FRFoods.ROSE_HIP_PIE_SLICE)));

    private final String pathName;
    public final Supplier<Item> itemSupplier;
    private final Integer burnTime;
    private Item item;

    FRItems(String pathName, Supplier<Item> itemSupplier) {
        this(pathName, itemSupplier, (Integer)null);
    }

    FRItems(String pathName, Supplier<Item> itemSupplier, Integer burnTime) {
        this.pathName = pathName;
        this.itemSupplier = itemSupplier;
        this.burnTime = burnTime;
    }

    public static void registerAll() {
        for (FRItems value : values()) {
            Registry.register(BuiltInRegistries.ITEM, FarmersRespite.id(value.pathName), value.get());
            if (value.burnTime != null && value.burnTime > 0) {
                FuelRegistry.INSTANCE.add(value.get(), value.burnTime);
            }
        }

        ItemGroupEvents.modifyEntriesEvent(FarmersRespite.CREATIVE_TAB).register((entries) -> {

            entries.acceptAll(Arrays.stream(values()).filter(frItems -> !frItems.pathName.contains("strong") && !frItems.pathName.contains("long")).map((item) -> {
                return item.get().getDefaultInstance();
            }).toList());
        });
    }

    public Item get() {
        if (this.item == null) {
            this.item = this.itemSupplier.get();
        }

        return this.item;
    }

    public String getId() {
        return BuiltInRegistries.ITEM.getKey(this.get()).toString();
    }
}
