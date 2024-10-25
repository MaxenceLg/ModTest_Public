package net.maxence.modtest.item.vitalamplifier;

import net.maxence.modtest.effects.ModEffects;
import net.maxence.modtest.item.EnergyContainer;
import net.maxence.modtest.item.EnergyItem;
import net.minecraft.client.renderer.EffectInstance;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class VitalAmplifier extends EnergyItem {
    private final int tier;
    public VitalAmplifier(Properties pProperties, int tier) {
        super(pProperties.stacksTo(1));
        this.tier = tier;
    }
    @Override
    @Nullable
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt){
        return new EnergyContainer(16000000*this.tier);
    }

    @Override
    public void inventoryTick(@NotNull ItemStack pStack, @NotNull Level pLevel, @NotNull Entity pEntity, int pSlotId, boolean pIsSelected) {
        if(canConsumeEnergy(pStack, 1000) && pEntity instanceof Player player && hasRequirementWork(pSlotId, player.getInventory())) {
            extractEnergy(pStack, 100, false);
            player.addEffect(new MobEffectInstance(ModEffects.ABSORPTION_ADDITION.get(), 10, this.tier*5));
            //player.addEffect(new MobEffectInstance(ModEffects.HEALTH_ADDITION.get(), 11, this.tier*5));
            player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 20, this.tier*9));
        }
        super.inventoryTick(pStack, pLevel, pEntity, pSlotId, pIsSelected);
    }
    private boolean hasRequirementWork(int pSlot, Inventory pInventory) {
        for (int i = 0; i < pInventory.getContainerSize(); i++) {
            ItemStack stack = pInventory.getItem(i);
            if (stack.getItem() instanceof VitalAmplifier vital && canConsumeEnergy(stack,100) && (pSlot > i && vital.tier == this.tier ||  vital.tier > this.tier)){
                return false;
            }
        }
        return true;
    }
}
