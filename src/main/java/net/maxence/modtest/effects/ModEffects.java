package net.maxence.modtest.effects;

import net.maxence.modtest.effects.addition.Absorption;
import net.maxence.modtest.effects.addition.HealthAddition;
import net.maxence.modtest.setup.ModRegistration;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraftforge.registries.RegistryObject;

public class ModEffects {

    public static final RegistryObject<MobEffect> HEALTH_ADDITION = ModRegistration.registerMobEffect("health_addition" , () -> new HealthAddition(MobEffectCategory.BENEFICIAL, 2445989));
    public static final RegistryObject<MobEffect> ABSORPTION_ADDITION = ModRegistration.registerMobEffect("absorption_addition", () -> new Absorption(MobEffectCategory.BENEFICIAL, 2445989));
    public static void init() {
    }
}
