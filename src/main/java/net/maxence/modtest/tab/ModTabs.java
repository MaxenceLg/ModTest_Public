package net.maxence.modtest.tab;

import net.maxence.modtest.item.ModItems;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModTabs {
    public static final CreativeModeTab TABFEUR = new CreativeModeTab("tabfeur") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ModItems.FEUR_INGOT.get());
        }
    };
    public static final CreativeModeTab TABCOUBEH = new CreativeModeTab("tabcoubeh") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ModItems.COUBEH_INGOT.get());
        }
    };
}