package net.maxence.modtest.datagen.loots;

import net.maxence.modtest.block.ModBlocks;
import net.maxence.modtest.blockentity.ModBlocksEntities;
import net.maxence.modtest.item.ModItems;
import net.minecraft.data.DataGenerator;

public class ModLootTables extends BaseLootTableProvider {
    public ModLootTables(DataGenerator gen) {
        super(gen);
    }

    @Override
    protected void addTables() {
        lootTables.put(ModBlocks.COUBEH_BLOCK.get(), createSimpleTable("coubeh_block",ModBlocks.COUBEH_BLOCK.get()));
        //add loot table for coubeh_ore dropping coubeh chunk
        lootTables.put(ModBlocks.COUBEH_ORE.get(), createSilkTouchTable("coubeh_ore",ModBlocks.COUBEH_ORE.get(),ModItems.COUBEH_CHUNK.get(),1,3));
        lootTables.put(ModBlocks.FEUR_BLOCK.get(), createSimpleTable("feur_block",ModBlocks.FEUR_BLOCK.get()));
        //add loot table for feur_ore dropping feur chunk
        lootTables.put(ModBlocks.FEUR_ORE.get(), createSilkTouchTable("feur_ore",ModBlocks.FEUR_ORE.get(),ModItems.FEUR_CHUNK.get(),1,3));
        lootTables.put(ModBlocks.ENERGY_GENERATOR.get(), createStandardTable("energy_generator",ModBlocks.ENERGY_GENERATOR.get(), ModBlocksEntities.ENERGY_GENERATOR_BE.get()));
    }
}
