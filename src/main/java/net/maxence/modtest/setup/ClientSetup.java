package net.maxence.modtest.setup;

import net.maxence.modtest.block.ModBlocks;
import net.maxence.modtest.container.ModContainers;
import net.maxence.modtest.screen.chestscreens.ChestScreen;
import net.maxence.modtest.screen.egbescreen.EnergyGBEScreen;
import net.maxence.modtest.screen.oregenscreen.OreGenScreen;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import java.awt.*;

public class ClientSetup {
    public static void init(final FMLClientSetupEvent event){
            MenuScreens.register(ModContainers.ENERGY_GENERATOR_CONTAINER.get(), EnergyGBEScreen::new);           // Attach our container to the screen
            ItemBlockRenderTypes.setRenderLayer(ModBlocks.ENERGY_GENERATOR.get(), RenderType.translucent());
            MenuScreens.register(ModContainers.ORE_GENERATOR_CONTAINER.get(), OreGenScreen::new);           // Attach our container to the screen
            ItemBlockRenderTypes.setRenderLayer(ModBlocks.ORE_GENERATOR.get(), RenderType.translucent());
            MenuScreens.register(ModContainers.CHEST_CONTAINER.get(), ChestScreen::new);
            ItemBlockRenderTypes.setRenderLayer(ModBlocks.CHEST_BLOCK.get(),RenderType.cutout());
        // Attach our container to the screen
            // Set the render type for our power generator to transluce// Attach our container to the screen
    }
}