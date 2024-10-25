package net.maxence.modtest.datagen.items;

import net.maxence.modtest.ModTest;
import net.maxence.modtest.block.ModBlocks;
import net.maxence.modtest.item.ModItems;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModItemsModels extends ItemModelProvider{
    public ModItemsModels(DataGenerator gen,ExistingFileHelper helper){
        super(gen, ModTest.MOD_ID,helper);
    }

    @Override
    protected void registerModels() {
        withExistingParent(ModItems.COUBEH_ORE_ITEM.get().getRegistryName().getPath(),modLoc("block/coubeh_ore"));
        withExistingParent(ModItems.COUBEH_BLOCK_ITEM.get().getRegistryName().getPath(),modLoc("block/coubeh_block"));
        withExistingParent(ModItems.FEUR_ORE_ITEM.get().getRegistryName().getPath(),modLoc("block/feur_ore"));
        withExistingParent(ModItems.FEUR_BLOCK_ITEM.get().getRegistryName().getPath(),modLoc("block/feur_block"));
        withExistingParent(ModItems.SPEED_ENERGY_BLOCK_ITEM.get().getRegistryName().getPath(),modLoc("block/speed_energy_block"));
        withExistingParent(ModItems.POWERED_ICE_ITEM.get().getRegistryName().getPath(),modLoc("block/powered_ice"));
        //withExistingParent(ModBlocks.CHEST_BLOCK.get().getRegistryName().getPath(),modLoc("block/chest_block"));
        singleTexture(ModItems.COUBEH_INGOT.get().getRegistryName().getPath(),mcLoc("item/generated"),"layer0",modLoc("item/coubeh_ingot"));
        singleTexture(ModItems.FEUR_INGOT.get().getRegistryName().getPath(),mcLoc("item/generated"),"layer0",modLoc("item/feur_ingot"));
        singleTexture(ModItems.FEUR_PICKAXE.get().getRegistryName().getPath(),mcLoc("item/handheld"),"layer0",modLoc("item/feur_pickaxe"));
        singleTexture(ModItems.ORE_SCAN_ITEM.get().getRegistryName().getPath(),mcLoc("item/generated"),"layer0",modLoc("item/orescanitem"));
        singleTexture(ModItems.ORE_GEN_ITEM.get().getRegistryName().getPath(),mcLoc("item/generated"),"layer0",modLoc("item/oregenitem"));
        singleTexture(ModItems.COUBEH_CHUNK.get().getRegistryName().getPath(),mcLoc("item/generated"),"layer0",modLoc("item/coubeh_chunk"));
        singleTexture(ModItems.FEUR_CHUNK.get().getRegistryName().getPath(),mcLoc("item/generated"),"layer0",modLoc("item/feur_chunk"));
        singleTexture(ModItems.ENERGIZED_STAFF.get().getRegistryName().getPath(),mcLoc("item/handheld"),"layer0",modLoc("item/energizedstaff"));
    }

    @Override
    public String getName(){
        return "ModTest ItemsModels";
    }
}
