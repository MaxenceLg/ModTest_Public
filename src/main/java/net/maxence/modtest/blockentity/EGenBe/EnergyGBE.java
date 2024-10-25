package net.maxence.modtest.blockentity.EGenBe;

import com.sun.jna.platform.unix.X11;
import net.maxence.modtest.blockentity.ModBlocksEntities;
import net.maxence.modtest.item.EnergyContainer;
import net.maxence.modtest.item.EnergyItem;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.EnergyStorage;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.atomic.AtomicInteger;

import static net.maxence.modtest.blockentity.ModBlocksEntities.ENERGY_GENERATOR_BE;

public class EnergyGBE extends BlockEntity {

    public static final int MAX_ENERGY = 1000000000;
    private final ItemStackHandler itemStackHandler;
    private final LazyOptional<ItemStackHandler> handler;
    private final EnergyStorage energyStorage;
    private final LazyOptional<IEnergyStorage> energyCapability;

    public EnergyGBE(BlockPos pPos, BlockState pBlockState) {
        super(ModBlocksEntities.ENERGY_GENERATOR_BE.get(), pPos, pBlockState);
        this.itemStackHandler = new ItemStackHandler(1);
        this.handler = LazyOptional.of(() -> this.itemStackHandler);
        this.energyStorage = new EnergyStorage(MAX_ENERGY);
        this.energyCapability = LazyOptional.of(() -> this.energyStorage);
        this.energyStorage.receiveEnergy(MAX_ENERGY, false);
    }

    public void tickServer() {
        if (this.level != null && !this.level.isClientSide()) {
            sendOutPower();
            //if the item in the slot is an energy item charge it
            ItemStack stack = itemStackHandler.getStackInSlot(0);
            if (stack.getItem() instanceof EnergyItem eItem) {
                int energyStored = eItem.getEnergyStored(stack);
                int energyToStore = eItem.getMaxEnergyStored(stack) - energyStored;
                eItem.receiveEnergy(stack, energyToStore, false);
            }
        }
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

    private void sendOutPower() {
        for (Direction direction : Direction.values()) {
            BlockEntity be = this.level.getBlockEntity(this.worldPosition.relative(direction));
            if (be != null) {
                be.getCapability(CapabilityEnergy.ENERGY, direction.getOpposite()).map(handler -> {
                    if (handler.canReceive()) {
                        int received = handler.receiveEnergy(handler.getMaxEnergyStored() - handler.getEnergyStored(), false);
                        setChanged();
                    }
                    return false;
                });
            }
        }
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
