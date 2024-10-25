package net.maxence.modtest.item.OreGenItem;

import net.maxence.modtest.datagen.datatags.DataTags;
import net.maxence.modtest.item.EnergyItem;
import net.maxence.modtest.tab.ModTabs;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.Tags;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.registries.*;
import net.minecraftforge.registries.tags.ITag;
import net.minecraftforge.registries.tags.ITagManager;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class oreGenitem extends EnergyItem {

    public oreGenitem() {
        super(new Properties().tab(ModTabs.TABFEUR).stacksTo(1));
    }

    @Override
    public void inventoryTick(@NotNull ItemStack stack, @NotNull Level world, @NotNull Entity entity, int slot, boolean isSelected) {
        if (
                entity instanceof Player player
                && stack.getItem() instanceof oreGenitem
                && !world.isClientSide()
        )
        {
            boolean Active = DataTags.getBoolean(stack, "Active");
            int times = DataTags.getInt(stack, "times");
            if (canConsumeEnergy(stack,20) && Active) {
                // Nombre d'utilisations nécessaires pour générer un minerai
                if (times == 20) {
                    extractEnergy(stack, 20, false);
                    player.getInventory().add(new ItemStack(getOreItem()));
                    DataTags.setInt(stack, "times", 1);
                } else {
                    DataTags.setInt(stack, "times", times + 1);
                }
            }
        }
        super.inventoryTick(stack, world, entity, slot, isSelected);
    }

    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
        return DataTags.getBoolean(oldStack, "Active") != DataTags.getBoolean(newStack, "Active");
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, Level world, @NotNull List<Component> tooltip, @NotNull TooltipFlag flag) {
        super.appendHoverText(stack, world, tooltip, flag);
        if (stack.getItem() instanceof oreGenitem) {
            tooltip.add(new TextComponent((DataTags.getBoolean(stack, "Active") ? "active" : "inactive")).withStyle(ChatFormatting.BLUE));
        }

    }
    @Override
    public boolean isRepairable(@NotNull ItemStack stack) {
        return false;
    }
    private Item getOreItem() {
        ITagManager<Item> tagManager = ForgeRegistries.ITEMS.tags();
        ITag<Item> tag = tagManager.getTag(Tags.Items.ORES);
        List<Item> list = tag.stream().toList();
        return list.get((int) (Math.random() * list.size()));
    }
    @Override
    @NotNull
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, @NotNull InteractionHand pUsedHand) {
        ItemStack stack = pPlayer.getItemInHand(pUsedHand);

        boolean Active = !DataTags.getBoolean(stack, "Active");
        DataTags.setBoolean(stack, "Active", Active);
        int load = DataTags.getInt(stack, "load");
        DataTags.setInt(stack, "load", load == 1 ? 2 : 1);
        stack.getUseAnimation();
        if (!pLevel.isClientSide()) {
            pPlayer.sendMessage(new TextComponent("Item is now " + (Active ? "active" : "inactive")), pPlayer.getUUID());
        }
        return super.use(pLevel, pPlayer, pUsedHand);
    }


}