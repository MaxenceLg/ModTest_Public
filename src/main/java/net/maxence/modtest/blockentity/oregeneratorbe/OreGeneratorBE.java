package net.maxence.modtest.blockentity.oregeneratorbe;

import net.maxence.modtest.blockentity.ModBlocksEntities;
import net.maxence.modtest.container.oregencontainer.OreGeneratorContainer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.EnergyStorage;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.tags.ITag;
import net.minecraftforge.registries.tags.ITagManager;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class OreGeneratorBE extends BlockEntity {
    public static final int MAX_ENERGY = 1000000000;
    public static final int MAX_ITEMS = 50;
    private final ItemStackHandler itemStackHandler;
    private final LazyOptional<ItemStackHandler> handler;
    private final EnergyStorage energyStorage;
    private final LazyOptional<IEnergyStorage> energyCapability;
    public OreGeneratorBE(BlockPos pPos, BlockState pBlockState) {
        super(ModBlocksEntities.ORE_GENERATOR_BE.get(),pPos, pBlockState);
        this.itemStackHandler = new ItemStackHandler(MAX_ITEMS);
        this.handler = LazyOptional.of(() -> this.itemStackHandler);
        this.energyStorage = new EnergyStorage(MAX_ENERGY);
        this.energyCapability = LazyOptional.of(() -> this.energyStorage);
    }
    public void tick() {
        if (this.level != null && !this.level.isClientSide()) {
            Item item = getOreItem();
            int slot = this.canInsert(item);
            if (this.energyStorage.getEnergyStored() >= 100000 && slot != -1) {
                this.energyStorage.extractEnergy(100000, false);
                if(Math.random() > 0.95) {
                    this.itemStackHandler.insertItem(slot, new ItemStack(item), false);
                }
            }
        }
    }
    private Item getOreItem() {
        ITagManager<Item> tagManager = ForgeRegistries.ITEMS.tags();
        ITag<Item> tag = tagManager.getTag(Tags.Items.ORES);
        List<Item> list = tag.stream().toList();
        return list.get((int) (Math.random() * list.size()));
    }
    private int canInsert(Item item) {
        int emptySlot = -1;
        for (int i = 0; i < this.itemStackHandler.getSlots(); i++) {
            ItemStack stack = this.itemStackHandler.getStackInSlot(i);
            if (stack.isEmpty()) {
                emptySlot = i;
            }
            if (stack.getItem() == item && stack.getCount() < stack.getMaxStackSize()) {
                return i;
            }
        }
        return emptySlot;
    }
    @NotNull
    @Override
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, Direction side) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return handler.cast();
        }
        if (cap == CapabilityEnergy.ENERGY) {
            return energyCapability.cast();
        }
        return super.getCapability(cap, side);
    }

    @Override
    public void load(CompoundTag tag) {
        if (tag.contains("Inventory")) {
            itemStackHandler.deserializeNBT(tag.getCompound("Inventory"));
        }
        if (tag.contains("Energy")) {
            energyStorage.deserializeNBT(tag.get("Energy"));
        }
        super.load(tag);
    }

    @Override
    public void saveAdditional(CompoundTag tag) {
        tag.put("Inventory", itemStackHandler.serializeNBT());
        tag.put("Energy", energyStorage.serializeNBT());
    }
}