package net.maxence.modtest.effects.addition;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;

public class Absorption extends MobEffect {

    public Absorption(MobEffectCategory pCategory, int pColor) {
        super(pCategory, pColor);
    }

    public void removeAttributeModifiers(@NotNull LivingEntity pLivingEntity, @NotNull AttributeMap pAttributeMap, int pAmplifier) {
        if(pLivingEntity instanceof Player player){
            player.setAbsorptionAmount(player.getAbsorptionAmount() - pAmplifier*2);
        }
        super.removeAttributeModifiers(pLivingEntity, pAttributeMap, pAmplifier);
    }

    public void addAttributeModifiers(@NotNull LivingEntity pLivingEntity, @NotNull AttributeMap pAttributeMap, int pAmplifier) {
        if(pLivingEntity instanceof Player player){
            player.setAbsorptionAmount(player.getAbsorptionAmount() + pAmplifier*2);
        }
        super.addAttributeModifiers(pLivingEntity, pAttributeMap, pAmplifier);
    }
}
