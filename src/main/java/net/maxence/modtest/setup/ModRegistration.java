package net.maxence.modtest.setup;

import ca.weblite.objc.Client;
import net.maxence.modtest.ModTest;
import net.maxence.modtest.block.ModBlocks;
import net.maxence.modtest.blockentity.ModBlocksEntities;
import net.maxence.modtest.container.ModContainers;
import net.maxence.modtest.effects.ModEffects;
import net.maxence.modtest.item.ModItems;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class ModRegistration {
    private static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, ModTest.MOD_ID);
    private static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, ModTest.MOD_ID);
    private static final DeferredRegister<MobEffect> MOB_EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, ModTest.MOD_ID);
    private static final DeferredRegister<BlockEntityType<?>> BLOCKS_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, ModTest.MOD_ID);
    private static final DeferredRegister<MenuType<?>> CONTAINERS = DeferredRegister.create(ForgeRegistries.CONTAINERS, ModTest.MOD_ID);


    public static void init(){
        ModBlocks.init();
        ModItems.init();
        ModEffects.init();
        ModBlocksEntities.init();
        ModContainers.init();
    }
    public static <B extends Block> RegistryObject<B> registerBlock(String name, Supplier<B> block){
        return BLOCKS.register(name, block);
    }
    public static <B extends Block> RegistryObject<Item> registerBlockItem(RegistryObject<B> block, CreativeModeTab tab){
        return registerItem(block.getId().getPath(), () -> new BlockItem(block.get(),new Item.Properties().tab(tab)));
        //block.get()
    }
    public static void register(IEventBus eventBus){
        init();
        BLOCKS.register(eventBus);
        ITEMS.register(eventBus);
        MOB_EFFECTS.register(eventBus);
        BLOCKS_ENTITIES.register(eventBus);
        CONTAINERS.register(eventBus);
    }

    public static <I extends Item> RegistryObject<Item> registerItem(String name, Supplier<I> item) {
        return ITEMS.register(name, item);
    }

    public static <I extends MobEffect> RegistryObject<MobEffect> registerMobEffect(String name, Supplier<I> effect) {
        return MOB_EFFECTS.register(name, effect);
    }
    public static <I extends BlockEntityType<?>> RegistryObject<I> registerBlockEntity(String name, Supplier<I> blockEntity) {
        return BLOCKS_ENTITIES.register(name, blockEntity);
    }
    public static <I extends MenuType<?>> RegistryObject<I> registerContainer(String name, Supplier<I> container) {
        return CONTAINERS.register(name, container);
    }

    public static void registerCapabilities(@NotNull RegisterCapabilitiesEvent event) {
    }
}
