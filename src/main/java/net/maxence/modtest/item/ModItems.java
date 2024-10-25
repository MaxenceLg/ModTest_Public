package net.maxence.modtest.item;

import net.maxence.modtest.ModTest;
import net.maxence.modtest.block.ModBlocks;
import net.maxence.modtest.item.OreGenItem.oreGenitem;
import net.maxence.modtest.item.OreScanItem.oreScanItem;
import net.maxence.modtest.item.modstaff.EnergizedStaff;
import net.maxence.modtest.item.vitalamplifier.VitalAmplifier;
import net.maxence.modtest.setup.ModRegistration;
import net.maxence.modtest.tab.ModTabs;
import net.minecraft.world.item.*;
import net.minecraftforge.registries.RegistryObject;

public class ModItems{

    public static void init(){
        ModBlocks.init();
        ModTest.LOGGER.info("Items Initialization");
    }
    public static final RegistryObject<Item> FEUR_INGOT = ModRegistration.registerItem("feur_ingot", () -> new Item(new Item.Properties().rarity(Rarity.UNCOMMON).stacksTo(64).tab(ModTabs.TABFEUR)));
    public static final RegistryObject<Item> COUBEH_INGOT = ModRegistration.registerItem("coubeh_ingot", () -> new Item(new Item.Properties().rarity(Rarity.UNCOMMON).stacksTo(64).tab(ModTabs.TABCOUBEH)));
    public static final RegistryObject<Item> COUBEH_CHUNK = ModRegistration.registerItem("coubeh_chunk", () -> new Item(new Item.Properties().rarity(Rarity.UNCOMMON).stacksTo(64).tab(ModTabs.TABCOUBEH)));
    public static final RegistryObject<Item> FEUR_CHUNK = ModRegistration.registerItem("feur_chunk", () -> new Item(new Item.Properties().rarity(Rarity.UNCOMMON).stacksTo(64).tab(ModTabs.TABFEUR)));
    public static final RegistryObject<Item> FEUR_PICKAXE = ModRegistration.registerItem("feur_pickaxe", () -> new PickaxeItem(Tiers.NETHERITE, 4, 4f,new PickaxeItem.Properties().tab(ModTabs.TABFEUR)));
    public static final RegistryObject<Item> ORE_SCAN_ITEM = ModRegistration.registerItem("orescanitem", oreScanItem::new);
    public static final RegistryObject<Item> ORE_GEN_ITEM = ModRegistration.registerItem("oregenitem",oreGenitem::new);
    public static final RegistryObject<Item> ENERGIZED_STAFF = ModRegistration.registerItem("energizedstaff", () -> new EnergizedStaff(new Item.Properties().tab(ModTabs.TABFEUR)));
    public static final RegistryObject<Item> VITAL_AMPLIFIER_TIER1 = ModRegistration.registerItem("vital_amplifier_t1", () -> new VitalAmplifier(new Item.Properties().tab(ModTabs.TABFEUR), 1));
    public static final RegistryObject<Item> VITAL_AMPLIFIER_TIER2 = ModRegistration.registerItem("vital_amplifier_t2", () -> new VitalAmplifier(new Item.Properties().tab(ModTabs.TABFEUR), 2));
    public static final RegistryObject<Item> VITAL_AMPLIFIER_TIER3 = ModRegistration.registerItem("vital_amplifier_t3", () -> new VitalAmplifier(new Item.Properties().tab(ModTabs.TABFEUR), 3));
    public static final RegistryObject<Item> FEUR_BLOCK_ITEM = ModRegistration.registerBlockItem(ModBlocks.FEUR_BLOCK,ModTabs.TABFEUR);
    public static final RegistryObject<Item> FEUR_ORE_ITEM = ModRegistration.registerBlockItem(ModBlocks.FEUR_ORE,ModTabs.TABFEUR);
    public static final RegistryObject<Item> COUBEH_BLOCK_ITEM = ModRegistration.registerBlockItem(ModBlocks.COUBEH_BLOCK,ModTabs.TABCOUBEH);
    public static final RegistryObject<Item> COUBEH_ORE_ITEM = ModRegistration.registerBlockItem(ModBlocks.COUBEH_ORE,ModTabs.TABCOUBEH);
    public static final RegistryObject<Item> SPEED_ENERGY_BLOCK_ITEM = ModRegistration.registerBlockItem(ModBlocks.SPEED_ENERGY_BLOCK,ModTabs.TABFEUR);
    public static final RegistryObject<Item> POWERED_ICE_ITEM = ModRegistration.registerBlockItem(ModBlocks.POWERED_ICE_BLOCK,ModTabs.TABFEUR);
    public static final RegistryObject<Item> ENERGY_GENERATOR_ITEM = ModRegistration.registerBlockItem(ModBlocks.ENERGY_GENERATOR,ModTabs.TABFEUR);
    public static final RegistryObject<Item> ORE_GENERATOR_ITEM = ModRegistration.registerBlockItem(ModBlocks.ORE_GENERATOR,ModTabs.TABFEUR);
    public static final RegistryObject<Item> CHEST_BLOCK_ITEM = ModRegistration.registerBlockItem(ModBlocks.CHEST_BLOCK,ModTabs.TABFEUR);
}