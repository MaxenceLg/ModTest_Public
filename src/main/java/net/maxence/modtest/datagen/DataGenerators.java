package net.maxence.modtest.datagen;

import net.maxence.modtest.ModTest;
import net.maxence.modtest.datagen.blocks.ModBlocksStates;
import net.maxence.modtest.datagen.blocks.ModBlocksTags;
import net.maxence.modtest.datagen.items.ModItemsModels;
import net.maxence.modtest.datagen.items.ModItemsTags;
import net.maxence.modtest.datagen.language.ModLanguageProvider;
import net.maxence.modtest.datagen.loots.ModLootTables;
import net.maxence.modtest.datagen.recipes.ModRecipes;
import net.maxence.modtest.setup.ModRegistration;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;

@Mod.EventBusSubscriber(modid = ModTest.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators {
    @SubscribeEvent
    public static void constructData(GatherDataEvent event){
        DataGenerator gen = event.getGenerator();
        ModRegistration.init();
        if(event.includeServer()){
            gen.addProvider(new ModRecipes(gen));
            gen.addProvider(new ModLootTables(gen));
            ModBlocksTags blocksTags = new ModBlocksTags(gen,event.getExistingFileHelper());
            gen.addProvider(blocksTags);
            gen.addProvider(new ModItemsTags(gen,blocksTags,event.getExistingFileHelper()));
        }
        if(event.includeClient()){
            gen.addProvider(new ModBlocksStates(gen,event.getExistingFileHelper()));
            gen.addProvider(new ModItemsModels(gen,event.getExistingFileHelper()));
            gen.addProvider(new ModLanguageProvider(gen,"en_us"));
        }
    }
}