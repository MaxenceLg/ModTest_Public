package net.maxence.modtest.tags;

import net.maxence.modtest.ModTest;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class ModTags {
    public static final TagKey<Block> MODTEST_BLOCK = TagKey.create(Registry.BLOCK_REGISTRY, new ResourceLocation(ModTest.MOD_ID,"modtest_blocks"));
    public static final TagKey<Item> MODTEST_ITEM = TagKey.create(Registry.ITEM_REGISTRY, new ResourceLocation(ModTest.MOD_ID,"modtest_items"));
    public static final TagKey<Item> MODTEST_CROPS = TagKey.create(Registry.ITEM_REGISTRY, new ResourceLocation(ModTest.MOD_ID,"modtest_items/crops"));
}
