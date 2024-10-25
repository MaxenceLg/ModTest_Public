package net.maxence.modtest.datagen.recipes;

import net.maxence.modtest.ModTest;
import net.maxence.modtest.item.ModItems;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.SimpleCookingRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SmeltingRecipe;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;

import java.util.function.Consumer;

public class ModRecipes extends RecipeProvider {
    public ModRecipes(DataGenerator gen) {
        super(gen);
    }
    @Override
    protected void buildCraftingRecipes(Consumer<FinishedRecipe> consumer){
        SimpleCookingRecipeBuilder.cooking(Ingredient.of(ModItems.COUBEH_CHUNK.get()),ModItems.COUBEH_INGOT.get(),1.0f,200, RecipeSerializer.SMELTING_RECIPE)
                .unlockedBy("has_coubeh_chunk",has(ModItems.COUBEH_CHUNK.get()))
                .save(consumer, "coubeh_ingot_smelting");
        SimpleCookingRecipeBuilder.cooking(Ingredient.of(ModItems.FEUR_CHUNK.get()),ModItems.FEUR_INGOT.get(),1.0f,200, RecipeSerializer.SMELTING_RECIPE)
                .unlockedBy("has_feur_chunk",has(ModItems.FEUR_CHUNK.get()))
                .save(consumer, "feur_ingot_smelting");
        ShapedRecipeBuilder.shaped(ModItems.FEUR_PICKAXE.get())
                .define('#', Items.STICK).define('!', ModItems.FEUR_INGOT.get())
                .pattern("!!!").pattern(" # ").pattern(" # ")
                .unlockedBy("has_feur_ingot",has(ModItems.FEUR_INGOT.get()))
                .save(consumer,"feur_pickaxe_crafting");
        nineBlockStorageRecipes(consumer,ModItems.FEUR_INGOT.get(),ModItems.FEUR_BLOCK_ITEM.get());
        nineBlockStorageRecipes(consumer,ModItems.COUBEH_INGOT.get(),ModItems.COUBEH_BLOCK_ITEM.get());
    }

}
