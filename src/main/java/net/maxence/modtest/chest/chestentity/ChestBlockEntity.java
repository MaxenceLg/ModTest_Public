package net.maxence.modtest.chest.chestentity;

import net.maxence.modtest.block.ModBlocks;
import net.maxence.modtest.blockentity.ModBlocksEntities;
import net.maxence.modtest.chest.chestcontainer.ChestContainer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.CompoundContainer;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.*;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;
import java.util.function.Supplier;

public class ChestBlockEntity extends RandomizableContainerBlockEntity implements LidBlockEntity{
    private static final int EVENT_SET_OPEN_COUNT = 1;
    public static final int SIZE = 50;
    private NonNullList<ItemStack> items;
    private boolean inventoryTouched;
    private boolean hadStuff;
    private final ContainerOpenersCounter openersCounter = new ContainerOpenersCounter() {
        protected void onOpen(Level level, BlockPos pos, BlockState blockState) {
            ChestBlockEntity.playSound(level, pos, blockState, SoundEvents.CHEST_OPEN);
        }

        protected void onClose(Level level, BlockPos pos, BlockState blockState) {
            ChestBlockEntity.playSound(level, pos, blockState, SoundEvents.CHEST_CLOSE);
        }

        protected void openerCountChanged(Level level, BlockPos pos, BlockState blockState, int previousCount, int newCount) {
            ChestBlockEntity.this.signalOpenCount(level, pos, blockState, previousCount, newCount);
        }

        protected boolean isOwnContainer(Player player) {
            if (!(player.containerMenu instanceof ChestContainer)) {
                return false;
            } else {
                Container container = ((ChestContainer)player.containerMenu).getContainer();
                return container instanceof ChestBlockEntity || container instanceof CompoundContainer && ((CompoundContainer)container).contains(ChestBlockEntity.this);
            }
        }
    };
    private final ChestLidController chestLidController = new ChestLidController();
    private final Supplier<Block> blockToUse;

    public ChestBlockEntity(BlockPos blockPos, BlockState blockState) {
        this(ModBlocksEntities.CHEST_BE.get(), blockPos, blockState, ModBlocks.CHEST_BLOCK);
    }

    public ChestBlockEntity(BlockEntityType<?> blockEntityType, BlockPos blockPos, BlockState blockState, Supplier<Block> blockToUseIn) {
        super(blockEntityType, blockPos, blockState);
        this.items = NonNullList.withSize(SIZE, ItemStack.EMPTY);
        this.blockToUse = blockToUseIn;
    }

    protected AbstractContainerMenu createMenu(int containerId, Inventory playerInventory) {
        return ChestContainer.createChestContainer(containerId, playerInventory, this);
    }

    public int getContainerSize() {
        return this.getItems().size();
    }

    protected Component getDefaultName() {
        return new TranslatableComponent("ironchest.container." + "_chest");
    }

    public void load(CompoundTag compoundTag) {
        super.load(compoundTag);
        this.items = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
        if (!this.tryLoadLootTable(compoundTag)) {
            ContainerHelper.loadAllItems(compoundTag, this.items);
        }

    }

    public void saveAdditional(CompoundTag compoundTag) {
        super.saveAdditional(compoundTag);
        if (!this.trySaveLootTable(compoundTag)) {
            ContainerHelper.saveAllItems(compoundTag, this.items);
        }

    }

    public static void lidAnimateTick(Level level, BlockPos blockPos, BlockState blockState, ChestBlockEntity chestBlockEntity) {
        chestBlockEntity.chestLidController.tickLid();
    }

    static void playSound(Level level, BlockPos blockPos, BlockState blockState, SoundEvent soundEvent) {
        double d0 = (double)blockPos.getX() + 0.5;
        double d1 = (double)blockPos.getY() + 0.5;
        double d2 = (double)blockPos.getZ() + 0.5;
        level.playSound((Player)null, d0, d1, d2, soundEvent, SoundSource.BLOCKS, 0.5F, level.random.nextFloat() * 0.1F + 0.9F);
    }

    public boolean triggerEvent(int id, int type) {
        if (id == 1) {
            this.chestLidController.shouldBeOpen(type > 0);
            return true;
        } else {
            return super.triggerEvent(id, type);
        }
    }

    public void startOpen(Player player) {
        if (!this.remove && !player.isSpectator()) {
            this.openersCounter.incrementOpeners(player, this.getLevel(), this.getBlockPos(), this.getBlockState());
        }

    }

    public void stopOpen(Player player) {
        if (!this.remove && !player.isSpectator()) {
            this.openersCounter.decrementOpeners(player, this.getLevel(), this.getBlockPos(), this.getBlockState());
        }

    }

    public NonNullList<ItemStack> getItems() {
        return this.items;
    }

    public float getOpenNess(float partialTicks) {
        return this.chestLidController.getOpenness(partialTicks);
    }

    public static int getOpenCount(BlockGetter blockGetter, BlockPos blockPos) {
        BlockState blockstate = blockGetter.getBlockState(blockPos);
        if (blockstate.hasBlockEntity()) {
            BlockEntity blockentity = blockGetter.getBlockEntity(blockPos);
            if (blockentity instanceof ChestBlockEntity) {
                return ((ChestBlockEntity)blockentity).openersCounter.getOpenerCount();
            }
        }

        return 0;
    }

    public void recheckOpen() {
        if (!this.remove) {
            this.openersCounter.recheckOpeners(this.getLevel(), this.getBlockPos(), this.getBlockState());
        }

    }

    protected void signalOpenCount(Level level, BlockPos blockPos, BlockState blockState, int previousCount, int newCount) {
        Block block = blockState.getBlock();
        level.blockEvent(blockPos, block, 1, newCount);
    }

    public void wasPlaced(@Nullable LivingEntity livingEntity, ItemStack stack) {
    }
    public static void tick(Level level, BlockPos blockPos, BlockState blockState, ChestBlockEntity chestBlockEntity) {
        if (!level.isClientSide && chestBlockEntity.inventoryTouched) {
            chestBlockEntity.inventoryTouched = false;
        }
    }
    public void setItems(NonNullList<ItemStack> itemsIn) {
        this.items = NonNullList.withSize(SIZE, ItemStack.EMPTY);

        for(int i = 0; i < itemsIn.size(); ++i) {
            if (i < this.items.size()) {
                this.getItems().set(i, (ItemStack)itemsIn.get(i));
            }
        }
        this.inventoryTouched = true;
    }

    public ItemStack getItem(int index) {
        this.inventoryTouched = true;
        return super.getItem(index);
    }

    public Block getBlockToUse() {
        return (Block)this.blockToUse.get();
    }
}
