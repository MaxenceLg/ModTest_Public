package net.maxence.modtest.container;

import net.maxence.modtest.ModTest;
import net.maxence.modtest.container.EGenBEContainer.EnergyGBEContainer;
import net.maxence.modtest.chest.chestcontainer.ChestContainer;
import net.maxence.modtest.container.oregencontainer.OreGeneratorContainer;
import net.maxence.modtest.setup.ModRegistration;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.registries.RegistryObject;

public class ModContainers {
    public static void init(){
        ModTest.LOGGER.info("Containers Initialization");
    }
    public static final RegistryObject<MenuType<EnergyGBEContainer>> ENERGY_GENERATOR_CONTAINER = ModRegistration.registerContainer("energy_container", () -> IForgeMenuType.create((windowId, inv, data) -> new EnergyGBEContainer(windowId, data.readBlockPos(), inv, inv.player)));
    public static final RegistryObject<MenuType<OreGeneratorContainer>> ORE_GENERATOR_CONTAINER = ModRegistration.registerContainer("oregen_container", () -> IForgeMenuType.create((windowId, inv, data) -> new OreGeneratorContainer(windowId, data.readBlockPos(), inv, inv.player)));
    public static final RegistryObject<MenuType<ChestContainer>> CHEST_CONTAINER = ModRegistration.registerContainer("chest_container", () -> new MenuType<>(ChestContainer::createChestContainer));
}