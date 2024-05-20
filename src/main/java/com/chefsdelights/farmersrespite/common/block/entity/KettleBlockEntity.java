package com.chefsdelights.farmersrespite.common.block.entity;

import com.chefsdelights.farmersrespite.common.block.KettleBlock;
import com.chefsdelights.farmersrespite.common.block.entity.container.KettleContainer;
import com.chefsdelights.farmersrespite.common.crafting.KettleRecipe;
import com.chefsdelights.farmersrespite.core.FarmersRespite;
import com.chefsdelights.farmersrespite.core.registry.FRBlockEntityTypes;
import com.chefsdelights.farmersrespite.core.registry.FRRecipeSerializers;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import io.github.fabricators_of_create.porting_lib.transfer.item.ItemStackHandler;
import io.github.fabricators_of_create.porting_lib.transfer.item.ItemStackHandlerContainer;
import io.github.fabricators_of_create.porting_lib.transfer.item.RecipeWrapper;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectIterator;
import me.shedaniel.rei.api.common.display.basic.BasicDisplay;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Container;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.Nameable;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import vectorwing.farmersdelight.common.block.entity.HeatableBlockEntity;
import vectorwing.farmersdelight.common.block.entity.SyncedBlockEntity;
import vectorwing.farmersdelight.common.mixin.accessor.RecipeManagerAccessor;
import vectorwing.farmersdelight.common.registry.ModParticleTypes;

import java.util.Iterator;
import java.util.Optional;

public class KettleBlockEntity extends SyncedBlockEntity implements MenuProvider, ExtendedScreenHandlerFactory, HeatableBlockEntity, Nameable {

    public static final String TAG_KEY_COOK_RECIPES_USED = "RecipesUsed";
    public static final int MEAL_DISPLAY_SLOT = 2;
    public static final int CONTAINER_SLOT = 3;
    public static final int OUTPUT_SLOT = 4;
    public static final int INVENTORY_SIZE = OUTPUT_SLOT + 1;
    private final ItemStackHandlerContainer inventory;
    private Component customName;
    private int cookTime;
    private int cookTimeTotal;
    private ItemStack mealContainer;
    protected final ContainerData cookingPotData;
    private final Object2IntOpenHashMap<ResourceLocation> experienceTracker;
    private ResourceLocation lastRecipeID;
    private boolean checkNewRecipe;
    private boolean needWater;

    public KettleBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(FRBlockEntityTypes.KETTLE, blockPos, blockState);
        this.inventory = createHandler();
        this.mealContainer = ItemStack.EMPTY;
        this.cookingPotData = new KettleBlockEntity.CookingPotSyncedData();
        this.experienceTracker = new Object2IntOpenHashMap();
        this.checkNewRecipe = true;
    }

    private ItemStackHandlerContainer createHandler() {
        return new ItemStackHandlerContainer(INVENTORY_SIZE)
        {
            @Override
            protected void onContentsChanged(int slot) {
                if (slot >= 0 && slot < MEAL_DISPLAY_SLOT) {
                    checkNewRecipe = true;
                }
                inventoryChanged();
            }
        };
    }

    @Override
    public AbstractContainerMenu createMenu(int id, Inventory player, Player entity) {
        return new KettleContainer(id, player, this, cookingPotData);
    }

    public CompoundTag getUpdateTag() {
        CompoundTag nbt = new CompoundTag();
        this.saveAdditional(nbt);
        return nbt;
    }

    public void load(CompoundTag tag) {
        super.load(tag);
        inventory.deserializeNBT(tag.getCompound("Inventory"));
        this.cookTime = tag.getInt("CookTime");
        this.cookTimeTotal = tag.getInt("CookTimeTotal");
        this.mealContainer = ItemStack.of(tag.getCompound("Container"));
        needWater = tag.getBoolean("NeedWater");
        if (tag.contains("CustomName", 8)) {
            this.customName = Component.Serializer.fromJson(tag.getString("CustomName"));
        }

        CompoundTag compoundRecipes = tag.getCompound("RecipesUsed");

        for (String key : compoundRecipes.getAllKeys()) {
            this.experienceTracker.put(new ResourceLocation(key), compoundRecipes.getInt(key));
        }
    }

    public void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putInt("CookTime", this.cookTime);
        tag.putInt("CookTimeTotal", this.cookTimeTotal);
        if (this.customName != null) {
            tag.putString("CustomName", Component.Serializer.toJson(this.customName));
        }
        tag.putBoolean("NeedWater", needWater);
        tag.put("Container", this.mealContainer.save(new CompoundTag()));
        tag.put("Inventory", inventory.serializeNBT());
        CompoundTag compoundRecipes = new CompoundTag();
        this.experienceTracker.forEach((identifier, craftedAmount) -> {
            compoundRecipes.putInt(identifier.toString(), craftedAmount);
        });
        tag.put("RecipesUsed", compoundRecipes);
    }

    public CompoundTag writeMeal(CompoundTag tag) {
        if (this.getMeal().isEmpty()) {
            return tag;
        } else {
            if (this.customName != null) {
                tag.putString("CustomName", Component.Serializer.toJson(this.customName));
            }

            tag.put("Container", this.mealContainer.save(new CompoundTag()));
            ItemStackHandler drops = new ItemStackHandler(INVENTORY_SIZE);

            for (int i = 0; i < INVENTORY_SIZE; ++i) {
                drops.setStackInSlot(i, i == MEAL_DISPLAY_SLOT ? this.inventory.getItem(i) : ItemStack.EMPTY);
            }

            tag.put("Inventory", drops.serializeNBT());
            return tag;
        }
    }

    public Component getName() {
        return this.customName != null ? this.customName : FarmersRespite.i18n("container.kettle");
    }

    public Component getDisplayName() {
        return this.getName();
    }

    public void setCustomName(Component customName) {
        this.customName = customName;
    }


    public static void cookingTick(Level world, BlockPos pos, BlockState state, KettleBlockEntity cookingPot) {
        boolean isHeated = cookingPot.isHeated(world, pos);
        boolean dirty = false;
        if (isHeated && cookingPot.hasInput()) {
            Optional<KettleRecipe> recipe = cookingPot.getMatchingRecipe(cookingPot.inventory);
            if (recipe.isPresent() && cookingPot.canCook(recipe.get())) {
                dirty = cookingPot.processCooking(recipe.get());
            } else {
                cookingPot.cookTime = 0;
            }
        } else if (cookingPot.cookTime > 0) {
            cookingPot.cookTime = Mth.clamp(cookingPot.cookTime - 2, 0, cookingPot.cookTimeTotal);
        }

        ItemStack meal = cookingPot.getMeal();
        if (!meal.isEmpty()) {
            if (!cookingPot.doesMealHaveContainer(meal)) {
                cookingPot.moveMealToOutput();
                dirty = true;
            } else if (!cookingPot.getInventory().getItem(CONTAINER_SLOT).isEmpty()) {
                cookingPot.useStoredContainersOnMeal();
                dirty = true;
            }
        }

        if (dirty) {
            cookingPot.inventoryChanged();
        }

    }

    public @Nullable Component getCustomName() {
        return this.customName;
    }

    private Optional<KettleRecipe> getMatchingRecipe(ItemStackHandlerContainer inventory) {
        if (this.level == null) {
            return Optional.empty();
        } else {
            if (this.lastRecipeID != null) {
                Recipe<Container> recipe = ((RecipeManagerAccessor) level.getRecipeManager()).getRecipeMap(FRRecipeSerializers.BREWING)
                        .get(lastRecipeID);
                if (recipe instanceof KettleRecipe kettleRecipe) {
                    if (recipe.matches(inventory, this.level)) {
                        return Optional.of(kettleRecipe);
                    }

                    if (ItemStack.isSameItem(recipe.getResultItem(level.registryAccess()), this.getMeal())) {
                        return Optional.empty();
                    }
                }
            }

            if (this.checkNewRecipe) {
                Optional<KettleRecipe> recipe = level.getRecipeManager().getRecipeFor(FRRecipeSerializers.BREWING, inventory, level);

                if (recipe.isPresent()) {
                    this.lastRecipeID = recipe.get().getId();
                    return recipe;
                }
            }

            this.checkNewRecipe = false;
            return Optional.empty();
        }

    }

    public ItemStack getMealContainer() {
        return !this.mealContainer.isEmpty() ? this.mealContainer : new ItemStack(this.getMeal().getItem().getCraftingRemainingItem());
    }

    private boolean hasInput() {
        for (int i = 0; i < MEAL_DISPLAY_SLOT; ++i) {
            if (!this.inventory.getItem(i).isEmpty()) {
                return true;
            }
        }

        return false;
    }

    protected boolean canCook(KettleRecipe recipeIn) {
        needWater = recipeIn.getNeedWater();
        if (this.hasInput() && recipeIn != null) {
            ItemStack recipeOutput = recipeIn.getResultItem(level.registryAccess());
            if (recipeOutput.isEmpty()) {
                return false;
            } else {
                ItemStack currentOutput = this.inventory.getItem(MEAL_DISPLAY_SLOT);
                if ((isWater() || !needWater)) {
                    if (currentOutput.isEmpty()) {
                        return true;
                    } else if (!ItemStack.isSameItem(currentOutput, recipeOutput)) {
                        return false;
                    } else if (currentOutput.getCount() + recipeOutput.getCount() <= this.inventory.getMaxStackSize()) {
                        return true;
                    } else {
                        return currentOutput.getCount() + recipeOutput.getCount() <= recipeOutput.getMaxStackSize();
                    }
                }
            }
        }
        return false;
    }

    private boolean processCooking(KettleRecipe recipe) {
        if (this.level != null && recipe != null) {
            int j = this.getBlockState().getValue(KettleBlock.WATER_LEVEL);
            ++this.cookTime;
            this.cookTimeTotal = recipe.brewTime;
            if (this.cookTime < this.cookTimeTotal) {
                return false;
            } else {
                this.cookTime = 0;
                this.mealContainer = recipe.container;
                ItemStack recipeOutput = recipe.getResultItem(level.registryAccess());
                ItemStack currentOutput = this.inventory.getItem(MEAL_DISPLAY_SLOT);
                if (currentOutput.isEmpty()) {
                    this.inventory.setItem(MEAL_DISPLAY_SLOT, recipeOutput.copy());
                } else if (currentOutput.getItem() == recipeOutput.getItem()) {
                    currentOutput.grow(recipeOutput.getCount());
                }

                this.trackRecipeExperience(recipe);
                if (needWater) {
                    level.setBlockAndUpdate(worldPosition, this.getBlockState().setValue(KettleBlock.WATER_LEVEL, j - 1));
                }

                for (int i = 0; i < MEAL_DISPLAY_SLOT; ++i) {
                    ItemStack itemStack = this.inventory.getItem(i);
                    if (itemStack.getItem().hasCraftingRemainingItem() && this.level != null) {
                        Direction direction = this.getBlockState().getValue(KettleBlock.FACING).getCounterClockWise();
                        double dropX = (double) this.worldPosition.getX() + 0.5 + (double) direction.getStepX() * 0.25;
                        double dropY = (double) this.worldPosition.getY() + 0.7;
                        double dropZ = (double) this.worldPosition.getZ() + 0.5 + (double) direction.getStepZ() * 0.25;
                        ItemEntity entity = new ItemEntity(this.level, dropX, dropY, dropZ, new ItemStack(this.inventory.getItem(i).getItem().getCraftingRemainingItem()));
                        entity.setDeltaMovement((float) direction.getStepX() * 0.08F, 0.25, (float) direction.getStepZ() * 0.08F);
                        this.level.addFreshEntity(entity);
                    }

                    if (!this.inventory.getItem(i).isEmpty()) {
                        this.inventory.getItem(i).shrink(1);
                    }
                }

                return true;
            }
        } else {
            return false;
        }
    }

    public void trackRecipeExperience(@Nullable Recipe<?> recipe) {
        if (recipe != null) {
            ResourceLocation recipeID = recipe.getId();
            this.experienceTracker.addTo(recipeID, 1);
        }

    }

    public void clearUsedRecipes(Player player) {
        this.grantStoredRecipeExperience(player.level(), player.position());
        this.experienceTracker.clear();
    }

    public void grantStoredRecipeExperience(Level world, Vec3 pos) {
        ObjectIterator var3 = this.experienceTracker.object2IntEntrySet().iterator();

        while (var3.hasNext()) {
            Object2IntMap.Entry<ResourceLocation> entry = (Object2IntMap.Entry) var3.next();
            world.getRecipeManager().byKey(entry.getKey()).ifPresent((recipe) -> {
                splitAndSpawnExperience(world, pos, entry.getIntValue(), ((KettleRecipe) recipe).getExperience());
            });
        }

    }

    private static void splitAndSpawnExperience(Level world, Vec3 pos, int craftedAmount, float experience) {
        int expTotal = Mth.floor((float) craftedAmount * experience);
        float expFraction = Mth.frac((float) craftedAmount * experience);
        if (expFraction != 0.0F && Math.random() < (double) expFraction) {
            ++expTotal;
        }

        while (expTotal > 0) {
            int expValue = ExperienceOrb.getExperienceValue(expTotal);
            expTotal -= expValue;
            world.addFreshEntity(new ExperienceOrb(world, pos.x, pos.y, pos.z, expValue));
        }

    }

    public static void animationTick(Level world, BlockPos pos, BlockState state, KettleBlockEntity cookingPot) {
        if (world != null && cookingPot.isHeated(world, pos)) {
            RandomSource random = world.random;
            double baseX;
            double baseY;
            double baseZ;
            if (random.nextFloat() < 0.2F) {
                baseX = (double) pos.getX() + 0.5 + (random.nextDouble() * 0.6 - 0.3);
                baseY = (double) pos.getY() + 0.7;
                baseZ = (double) pos.getZ() + 0.5 + (random.nextDouble() * 0.6 - 0.3);
                world.addParticle(ParticleTypes.BUBBLE_POP, baseX, baseY, baseZ, 0.0, 0.0, 0.0);
            }

            if (random.nextFloat() < 0.05F) {
                baseX = (double) pos.getX() + 0.5 + (random.nextDouble() * 0.4 - 0.2);
                baseY = (double) pos.getY() + 0.5;
                baseZ = (double) pos.getZ() + 0.5 + (random.nextDouble() * 0.4 - 0.2);
                double motionY = random.nextBoolean() ? 0.015 : 0.005;
                world.addParticle(ModParticleTypes.STEAM.get(), baseX, baseY, baseZ, 0.0, motionY, 0.0);
            }
        }

    }

    public ItemStack getMeal() {
        return this.inventory.getItem(MEAL_DISPLAY_SLOT);
    }

    public boolean isHeated() {
        return this.level != null && this.isHeated(this.level, this.worldPosition);
    }

    public NonNullList<ItemStack> getDroppableInventory() {
        NonNullList<ItemStack> drops = NonNullList.create();

        for (int i = 0; i < INVENTORY_SIZE; ++i) {
            drops.add(i == MEAL_DISPLAY_SLOT ? ItemStack.EMPTY : this.inventory.getItem(i));
        }

        return drops;
    }

    private void moveMealToOutput() {
        ItemStack mealDisplay = this.inventory.getItem(MEAL_DISPLAY_SLOT);
        ItemStack finalOutput = this.inventory.getItem(OUTPUT_SLOT);
        int mealCount = Math.min(mealDisplay.getCount(), mealDisplay.getMaxStackSize() - finalOutput.getCount());
        if (finalOutput.isEmpty()) {
            this.inventory.setItem(OUTPUT_SLOT, mealDisplay.split(mealCount));
        } else if (finalOutput.getItem() == mealDisplay.getItem()) {
            mealDisplay.shrink(mealCount);
            finalOutput.grow(mealCount);
        }

    }

    private void useStoredContainersOnMeal() {
        ItemStack mealDisplay = this.inventory.getItem(MEAL_DISPLAY_SLOT);
        ItemStack containerInput = this.inventory.getItem(CONTAINER_SLOT);
        ItemStack finalOutput = this.inventory.getItem(OUTPUT_SLOT);
        if (this.isContainerValid(containerInput) && finalOutput.getCount() < finalOutput.getMaxStackSize()) {
            int smallerStack = Math.min(mealDisplay.getCount(), containerInput.getCount());
            int mealCount = Math.min(smallerStack, mealDisplay.getMaxStackSize() - finalOutput.getCount());
            if (finalOutput.isEmpty()) {
                containerInput.shrink(mealCount);
                this.inventory.setItem(OUTPUT_SLOT, mealDisplay.split(mealCount));
            } else if (finalOutput.getItem() == mealDisplay.getItem()) {
                mealDisplay.shrink(mealCount);
                containerInput.shrink(mealCount);
                finalOutput.grow(mealCount);
            }
        }

    }

    public ItemStack useHeldItemOnMeal(ItemStack container) {
        if (this.isContainerValid(container) && !this.getMeal().isEmpty()) {
            container.shrink(1);
            return this.getMeal().split(1);
        } else {
            return ItemStack.EMPTY;
        }
    }

    private boolean doesMealHaveContainer(ItemStack meal) {
        return !this.mealContainer.isEmpty() || meal.getItem().hasCraftingRemainingItem();
    }

    public boolean isContainerValid(ItemStack containerItem) {
        if (containerItem.isEmpty() || this.getMeal().isEmpty()) {
            return false;
        } else {
            return !this.mealContainer.isEmpty() ? ItemStack.isSameItem(this.mealContainer, containerItem) : ItemStack.isSameItem(new ItemStack(this.getMeal().getItem().getCraftingRemainingItem()), containerItem);
        }
    }

    public ItemStackHandlerContainer getInventory() {
        return this.inventory;
    }

    public void writeScreenOpeningData(ServerPlayer player, FriendlyByteBuf buf) {
        buf.writeBlockPos(this.worldPosition);
    }

    public void setCheckNewRecipe(boolean checkNewRecipe) {
        this.checkNewRecipe = checkNewRecipe;

        inventoryChanged();
    }

    private class CookingPotSyncedData implements ContainerData {
        private CookingPotSyncedData() {
        }

        public int get(int index) {
            int var10000;
            switch (index) {
                case 0:
                    var10000 = KettleBlockEntity.this.cookTime;
                    break;
                case 1:
                    var10000 = KettleBlockEntity.this.cookTimeTotal;
                    break;
                default:
                    var10000 = 0;
            }

            return var10000;
        }

        public void set(int index, int value) {
            if (index == 0) {
                KettleBlockEntity.this.cookTime = value;
            } else if (index == 1) {
                KettleBlockEntity.this.cookTimeTotal = value;
            }

        }

        public int getCount() {
            return 2;
        }
    }

    public boolean isWater() {
        if (this.level == null) {
            return false;
        } else {
            BlockState state = this.level.getBlockState(this.getBlockPos());
            int i = state.getValue(KettleBlock.WATER_LEVEL);
            boolean flag = i > 0;
            return flag;
        }
    }
}