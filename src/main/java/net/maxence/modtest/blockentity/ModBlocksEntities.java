package net.maxence.modtest.blockentity;

import net.maxence.modtest.ModTest;
import net.maxence.modtest.block.ModBlocks;
import net.maxence.modtest.blockentity.EGenBe.EnergyGBE;
import net.maxence.modtest.chest.chestentity.ChestBlockEntity;
import net.maxence.modtest.blockentity.oregeneratorbe.OreGeneratorBE;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.RegistryObject;

import static net.maxence.modtest.setup.ModRegistration.registerBlockEntity;

public class ModBlocksEntities {
    public static void init(){
        ModTest.LOGGER.info("Blocks Entities Initialization");
    }
    public static final RegistryObject<BlockEntityType<EnergyGBE>> ENERGY_GENERATOR_BE = registerBlockEntity("energy_generator", () -> BlockEntityType.Builder.of(EnergyGBE::new, ModBlocks.ENERGY_GENERATOR.get()).build(null));
    public static final RegistryObject<BlockEntityType<OreGeneratorBE>> ORE_GENERATOR_BE = registerBlockEntity("ore_generator", () -> BlockEntityType.Builder.of(OreGeneratorBE::new, ModBlocks.ORE_GENERATOR.get()).build(null));
    public static final RegistryObject<BlockEntityType<ChestBlockEntity>> CHEST_BE = registerBlockEntity("chest_block", () -> BlockEntityType.Builder.of(ChestBlockEntity::new, ModBlocks.CHEST_BLOCK.get()).build(null));
}