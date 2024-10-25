package net.maxence.modtest.item;

import net.minecraft.core.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.EnergyStorage;
import net.minecraftforge.energy.IEnergyStorage;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import net.minecraft.nbt.IntTag;

public class EnergyContainer implements ICapabilitySerializable<IntTag>{
    private final EnergyStorage energyStorage; // Stockage d'énergie
    private final LazyOptional<IEnergyStorage> energyCapability; // Capacité d'énergie

    public EnergyContainer(int capacity) {
        this.energyStorage = new EnergyStorage(capacity);
        this.energyCapability = LazyOptional.of(() -> this.energyStorage);
    }

    @NonNull
    @Override
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        return CapabilityEnergy.ENERGY.orEmpty(cap, this.energyCapability);
    }

    @Override
    public IntTag serializeNBT() {
        return IntTag.valueOf(energyStorage.getEnergyStored());
    }

    @Override
    public void deserializeNBT(@NotNull IntTag nbt) {
        energyStorage.receiveEnergy(nbt.getAsInt(), false);
    }
}
