package net.maxence.modtest.effects.addition;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;

public class HealthAddition extends MobEffect{
    public HealthAddition(MobEffectCategory pCategory, int pColor) {
        super(pCategory, pColor);
    }
    public void removeAttributeModifiers(@NotNull LivingEntity pLivingEntity, @NotNull AttributeMap pAttributeMap, int pAmplifier) {
        if (pLivingEntity instanceof Player player) {
            double health = player.getMaxHealth();
            player.getAttributes().getInstance(Attributes.MAX_HEALTH).setBaseValue(health - 2 * pAmplifier);
            player.setHealth(Math.max(player.getHealth(), player.getMaxHealth() - 2 * pAmplifier));
            player.hurtDuration = 0;
            player.hurtTime = 0;
            player.animateHurt();
            player.hurtDir = 0;
        }
        super.removeAttributeModifiers(pLivingEntity, pAttributeMap, pAmplifier);
    }

    public void addAttributeModifiers(@NotNull LivingEntity pLivingEntity, @NotNull AttributeMap pAttributeMap, int pAmplifier) {
        if(pLivingEntity instanceof Player player){
            double health = player.getMaxHealth();
            player.getAttributes().getInstance(Attributes.MAX_HEALTH).setBaseValue(health + 2 * pAmplifier);
            player.setHealth(player.getHealth() + 2 * pAmplifier);
        }
        super.addAttributeModifiers(pLivingEntity, pAttributeMap, pAmplifier);
    }
}
