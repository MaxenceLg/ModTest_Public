package net.maxence.modtest.datagen.items;

import net.maxence.modtest.ModTest;
import net.maxence.modtest.datagen.blocks.ModBlocksTags;
import net.maxence.modtest.item.ModItems;
import net.maxence.modtest.tags.ModTags;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModItemsTags extends ItemTagsProvider {
    public ModItemsTags(DataGenerator gen, ModBlocksTags blocksTags, ExistingFileHelper existingFileHelper) {
        super(gen,blocksTags, ModTest.MOD_ID,existingFileHelper);
    }

    @Override
    protected void addTags(){
        tag(Tags.Items.ORES)
                .add(ModItems.COUBEH_ORE_ITEM.get())
                .add(ModItems.FEUR_ORE_ITEM.get());
        tag(ModTags.MODTEST_ITEM)
                .add(ModItems.COUBEH_ORE_ITEM.get())
                .add(ModItems.FEUR_ORE_ITEM.get())
                .add(ModItems.FEUR_INGOT.get())
                .add(ModItems.COUBEH_INGOT.get())
                .add(ModItems.ORE_SCAN_ITEM.get())
                .add(ModItems.FEUR_PICKAXE.get())
                .add(ModItems.COUBEH_BLOCK_ITEM.get())
                .add(ModItems.FEUR_BLOCK_ITEM.get())
                .add(ModItems.ORE_GEN_ITEM.get())
                .add(ModItems.COUBEH_CHUNK.get())
                .add(ModItems.FEUR_CHUNK.get())
                .add(ModItems.ENERGIZED_STAFF.get())
                .add(ModItems.SPEED_ENERGY_BLOCK_ITEM.get())
                .add(ModItems.POWERED_ICE_ITEM.get())
                .add(ModItems.VITAL_AMPLIFIER_TIER1.get())
                .add(ModItems.VITAL_AMPLIFIER_TIER2.get())
                .add(ModItems.VITAL_AMPLIFIER_TIER3.get());
        //add fuel item

    }
    @Override
    public String getName(){
        return "ModTest ItemsTags";
    }
}
