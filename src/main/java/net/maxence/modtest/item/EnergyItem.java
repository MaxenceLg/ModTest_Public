package net.maxence.modtest.item;

import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.awt.*;
import java.util.List;

public abstract class EnergyItem extends Item {
    public EnergyItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    @Nullable
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt){
        return new EnergyContainer(20000);
    }

    public int receiveEnergy(ItemStack stack, int maxReceive, boolean simulate){
            return this.getCapability(stack).receiveEnergy(maxReceive, simulate);
    }
    public int extractEnergy(ItemStack stack, int maxExtract, boolean simulate){
        return this.getCapability(stack).extractEnergy(maxExtract, simulate);
    }
    public int getEnergyStored(ItemStack stack){
        return this.getCapability(stack).getEnergyStored();
    }
    public int getMaxEnergyStored(ItemStack stack){
        return this.getCapability(stack).getMaxEnergyStored();
    }
    public long receiveEnergyL(ItemStack stack, long maxReceive, boolean simulate) {
        return receiveEnergy(stack, (int) maxReceive, simulate);
    }
    public boolean canConsumeEnergy(ItemStack stack,int energyNeeded) {
        return extractEnergy(stack, energyNeeded, true) == energyNeeded;
    }

    public long extractEnergyL(ItemStack stack, long maxExtract, boolean simulate) {
        return extractEnergy(stack, (int) maxExtract, simulate);
    }

    public long getEnergyStoredL(ItemStack stack) {
        return getEnergyStored(stack);
    }

    public long getMaxEnergyStoredL(ItemStack stack) {
        return getMaxEnergyStored(stack);
    }
    public IEnergyStorage getCapability(ItemStack stack) {
        // Vérifie si la capacité est présente, sinon retourne null
        return stack.getCapability(CapabilityEnergy.ENERGY).orElse(null);
    }
    public boolean isBarVisible(@NotNull ItemStack stack) {
        return true;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, Level world, @NotNull List<Component> tooltip, @NotNull TooltipFlag flag) {
        tooltip.add(new TextComponent("Stored Energy: " + getEnergyStored(stack)/1000f + "kFe" + " / " + getMaxEnergyStored(stack)/1000f + "kFe").withStyle(ChatFormatting.RED));
        super.appendHoverText(stack, world, tooltip, flag);
    }

    public int getBarWidth(@NotNull ItemStack stack) {
        IEnergyStorage energyStorage = getCapability(stack);
        return (int) (energyStorage.getEnergyStored() / (float) energyStorage.getMaxEnergyStored() * 13);
    }

    public int getBarColor(@NotNull ItemStack stack) {
        return getBarWidth(stack) > 10 ? Color.green.getRGB() : (getBarWidth(stack) > 4 ? Color.yellow.getRGB() : Color.red.getRGB());
    }
}