package net.maxence.modtest;

import com.mojang.logging.LogUtils;
import net.maxence.modtest.block.ModBlocks;
import net.maxence.modtest.setup.ClientSetup;
import net.maxence.modtest.setup.ModRegistration;
import net.maxence.modtest.setup.ModSetup;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(ModTest.MOD_ID)
public class ModTest
{
    public static final String MOD_ID = "modtest";
    // Directly reference a slf4j logger
    public static final Logger LOGGER = LogUtils.getLogger();

    public ModTest() {
        // Register the setup method for modloading
        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
        //register items
        //modBus.addListener(ModRegistration::registerCapabilities);
        ModRegistration.register(modBus);
        modBus.addListener(ModSetup::setup);
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> modBus.addListener(ClientSetup::init));
        // Register ourselves for server and other game events we are interested in
    }
}
