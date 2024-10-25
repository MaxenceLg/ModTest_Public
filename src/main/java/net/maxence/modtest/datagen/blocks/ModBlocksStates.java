package net.maxence.modtest.datagen.blocks;

import net.maxence.modtest.ModTest;
import net.maxence.modtest.block.ModBlocks;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.*;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModBlocksStates extends BlockStateProvider {

    private final BlockModelProvider models;

    public ModBlocksStates(DataGenerator gen, ExistingFileHelper helper){
        super(gen, ModTest.MOD_ID, helper);
        this.models = new BlockModelProvider(gen, ModTest.MOD_ID, helper) {
            @Override
            protected void registerModels() {

            }
        };
    }

    @Override
    protected void registerStatesAndModels() {
        simpleBlock(ModBlocks.COUBEH_BLOCK.get());
        simpleBlock(ModBlocks.COUBEH_ORE.get());
        simpleBlock(ModBlocks.FEUR_BLOCK.get());
        simpleBlock(ModBlocks.FEUR_ORE.get());
        //simpleBlock(ModBlocks.SPEED_ENERGY_BLOCK.get());
        simpleBlock(ModBlocks.POWERED_ICE_BLOCK.get());
        horizontalBlock(ModBlocks.SPEED_ENERGY_BLOCK.get(), models().cubeTop("speed_energy_block", modLoc("block/speed_energy_block_side"),modLoc("block/speed_energy_block_top")));
    }

    @Override
    public String getName(){
        return "ModTest BlocksStates";
    }
}
