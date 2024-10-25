package net.maxence.modtest.event.catcher;

import net.maxence.modtest.effects.ModEffects;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class LivingHurtEventHandler {
    // Remplacez par votre propre instance de MobEffect

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onLivingHurt(LivingHurtEvent event) {
        LivingEntity entity = event.getEntityLiving();

        // Vérifiez s'il s'agit d'un joueur et que les dégâts sont causés par votre MobEffect
        if (entity instanceof Player player) {
            // Vérifiez si la source des dégâts possède votre MobEffect
            if (player.hasEffect(ModEffects.ABSORPTION_ADDITION.get())) {
                player.sendMessage(new TextComponent("Catch !"), player.getUUID());
                // Annulez l'animation de dégâts (hurt animation) pour ce joueur
                event.setCanceled(true);
                player.hurtTime = 0; // Met à zéro le compteur d'animation de dégâts
            }
        }
    }
}
