package net.maxence.modtest.setup;

import com.mojang.logging.LogUtils;
import net.maxence.modtest.ModTest;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
public class ModSetup {
    public static void setup(FMLCommonSetupEvent event){
// some preinit code
        ModTest.LOGGER.info("HELLO FROM PREINIT");
        ModTest.LOGGER.info("DIRT BLOCK >> {}", Blocks.DIRT.getRegistryName());
    }
}
