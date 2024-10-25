package net.maxence.modtest.datagen.blocks;

import net.maxence.modtest.ModTest;
import net.maxence.modtest.block.ModBlocks;
import net.maxence.modtest.tags.ModTags;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModBlocksTags extends BlockTagsProvider {
    public ModBlocksTags(DataGenerator gen, ExistingFileHelper helper){
        super(gen, ModTest.MOD_ID,helper);
    }

    @Override
    protected void addTags(){
        tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(ModBlocks.COUBEH_BLOCK.get())
                .add(ModBlocks.COUBEH_ORE.get())
                .add(ModBlocks.FEUR_BLOCK.get())
                .add(ModBlocks.FEUR_ORE.get())
                .add(ModBlocks.SPEED_ENERGY_BLOCK.get());
        tag(BlockTags.NEEDS_IRON_TOOL)
                .add(ModBlocks.SPEED_ENERGY_BLOCK.get());
        tag(BlockTags.NEEDS_DIAMOND_TOOL)
                .add(ModBlocks.COUBEH_BLOCK.get())
                .add(ModBlocks.COUBEH_ORE.get())
                .add(ModBlocks.FEUR_BLOCK.get())
                .add(ModBlocks.FEUR_ORE.get());
        tag(Tags.Blocks.ORES)
                .add(ModBlocks.COUBEH_ORE.get())
                .add(ModBlocks.FEUR_ORE.get());
        tag(ModTags.MODTEST_BLOCK)
                .add(ModBlocks.COUBEH_BLOCK.get())
                .add(ModBlocks.COUBEH_ORE.get())
                .add(ModBlocks.FEUR_BLOCK.get())
                .add(ModBlocks.FEUR_ORE.get())
                .add(ModBlocks.SPEED_ENERGY_BLOCK.get())
                .add(ModBlocks.POWERED_ICE_BLOCK.get());
    }

    @Override
    public String getName(){
        return "ModTest BlocksTags";
    }
}
