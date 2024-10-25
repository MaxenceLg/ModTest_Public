package net.maxence.modtest.block;

import net.maxence.modtest.ModTest;
import net.maxence.modtest.chest.chests.IronChestBlock;
import net.maxence.modtest.block.customblock.PoweredIce;
import net.maxence.modtest.block.customblock.SpeedEnergyBlock;
import net.maxence.modtest.block.customblock.energygenerator.EnergyGenerator;
import net.maxence.modtest.block.customblock.oregenerator.OreGenerator;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.OreBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.registries.RegistryObject;


import static net.maxence.modtest.setup.ModRegistration.registerBlock;

public class ModBlocks {

    public static void init(){
        ModTest.LOGGER.info("Blocks Initialization");
    }
    public static final RegistryObject<Block> FEUR_BLOCK = registerBlock("feur_block", () -> new OreBlock(BlockBehaviour.Properties.of(Material.METAL).strength(2f).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> COUBEH_BLOCK = registerBlock("coubeh_block", () -> new OreBlock(BlockBehaviour.Properties.of(Material.METAL).strength(2f).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> COUBEH_ORE = registerBlock("coubeh_ore", () -> new OreBlock(BlockBehaviour.Properties.of(Material.STONE).strength(1f).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> FEUR_ORE = registerBlock("feur_ore", () -> new OreBlock(BlockBehaviour.Properties.of(Material.STONE).strength(1f).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> SPEED_ENERGY_BLOCK = registerBlock("speed_energy_block", () -> new SpeedEnergyBlock(BlockBehaviour.Properties.of(Material.STONE).strength(2f).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> POWERED_ICE_BLOCK = registerBlock("powered_ice", () -> new PoweredIce(BlockBehaviour.Properties.of(new Material.Builder(MaterialColor.ICE).build()).strength(0.5f)));
    public static final RegistryObject<Block> ENERGY_GENERATOR = registerBlock("energy_generator", () -> new EnergyGenerator(BlockBehaviour.Properties.of(Material.METAL).strength(2f).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> ORE_GENERATOR = registerBlock("ore_generator", () -> new OreGenerator(BlockBehaviour.Properties.of(Material.METAL).strength(2f).requiresCorrectToolForDrops()));
    public static final RegistryObject<Block> CHEST_BLOCK = registerBlock("iron_chest", () -> new IronChestBlock(BlockBehaviour.Properties.of(Material.METAL).strength(2f).requiresCorrectToolForDrops()));
}
