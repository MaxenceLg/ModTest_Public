package net.maxence.modtest.datagen.language;

import net.maxence.modtest.ModTest;
import net.maxence.modtest.block.ModBlocks;
import net.maxence.modtest.block.customblock.energygenerator.EnergyGenerator;
import net.maxence.modtest.block.customblock.oregenerator.OreGenerator;
import net.maxence.modtest.container.EGenBEContainer.EnergyGBEContainer;
import net.maxence.modtest.item.ModItems;
import net.maxence.modtest.tab.ModTabs;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.LanguageProvider;

public class ModLanguageProvider extends LanguageProvider {
    public ModLanguageProvider(DataGenerator gen, String enUs) {
        super(gen, ModTest.MOD_ID,enUs);
    }

    @Override
    protected void addTranslations() {
        add("itemGroup." + ModTabs.TABCOUBEH.getRecipeFolderName(), "TabCoubeh");
        add("itemGroup." + ModTabs.TABFEUR.getRecipeFolderName(), "TabFeur");
        //add("itemGroup." + ModTabs.TABDEFAULT.getRecipeFolderName(), "TabDefault");
        add(ModItems.COUBEH_INGOT.get(),"Coubeh Ingot");
        add(ModItems.COUBEH_ORE_ITEM.get(),"Coubeh Ore");
        add(ModItems.COUBEH_BLOCK_ITEM.get(),"Coubeh Block");
        add(ModItems.FEUR_INGOT.get(),"Feur Ingot");
        add(ModItems.FEUR_BLOCK_ITEM.get(),"Feur Block");
        add(ModItems.FEUR_ORE_ITEM.get(),"Feur Ore");
        add(ModItems.ORE_SCAN_ITEM.get(),"oreScanItem");
        add(ModItems.FEUR_PICKAXE.get(),"Feur Pickaxe");
        add(ModItems.ORE_GEN_ITEM.get(),"oreGenItem");
        add(ModItems.COUBEH_CHUNK.get(),"Coubeh Chunk");
        add(ModItems.FEUR_CHUNK.get(),"Feur Chunk");
        add(ModItems.ENERGIZED_STAFF.get(),"Energized Staff");
        add(ModItems.SPEED_ENERGY_BLOCK_ITEM.get(),"Speed Energy Block");
        add(ModItems.POWERED_ICE_ITEM.get(),"Powered Ice");
        add(ModItems.VITAL_AMPLIFIER_TIER1.get(),"Vital Amplifier Tier 1");
        add(ModItems.VITAL_AMPLIFIER_TIER2.get(),"Vital Amplifier Tier 2");
        add(ModItems.VITAL_AMPLIFIER_TIER3.get(),"Vital Amplifier Tier 3");
        add(ModItems.ENERGY_GENERATOR_ITEM.get(),"Energy Generator");
        add(EnergyGenerator.SCREEN_TUTORIAL_POWERGEN,"Energy Generator");
        add(EnergyGenerator.MESSAGE_POWERGEN,"Energy Generator");
        add(OreGenerator.SCREEN_TUTORIAL_OREGEN,"Ore Generator");
        add(ModItems.ORE_GENERATOR_ITEM.get(),"Ore Generator");
        add(ModItems.CHEST_BLOCK_ITEM.get(),"Iron Chest");
    }
}