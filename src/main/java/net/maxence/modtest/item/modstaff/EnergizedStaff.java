package net.maxence.modtest.item.modstaff;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.maxence.modtest.datagen.datatags.DataTags;
import net.maxence.modtest.item.EnergyContainer;
import net.maxence.modtest.item.EnergyItem;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.crafting.SmeltingRecipe;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ToolAction;
import net.minecraftforge.common.ToolActions;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static java.lang.Math.*;

public class EnergizedStaff extends EnergyItem {
    public static final Set<ToolAction> ALWAYS_SUPPORTED_ACTIONS = Set.of(ToolActions.AXE_DIG, ToolActions.HOE_DIG, ToolActions.SHOVEL_DIG, ToolActions.PICKAXE_DIG,
            ToolActions.SWORD_DIG);
    private final Multimap<Attribute, AttributeModifier> attributes;

    public EnergizedStaff(Properties pProperties) {
        super(pProperties.stacksTo(1).setNoRepair());
        ImmutableMultimap.Builder<Attribute, AttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(BASE_ATTACK_DAMAGE_UUID, "Tool modifier",  9, AttributeModifier.Operation.ADDITION));
        builder.put(Attributes.ATTACK_SPEED, new AttributeModifier(BASE_ATTACK_SPEED_UUID, "Tool modifier",  -2, AttributeModifier.Operation.ADDITION));
        this.attributes = builder.build();
    }
    @Override
    public void appendHoverText(@NotNull ItemStack stack, Level world, @NotNull List<Component> tooltip, @NotNull TooltipFlag flag) {
        super.appendHoverText(stack, world, tooltip, flag);
        if (stack.getItem() instanceof EnergizedStaff) {
            tooltip.add(new TextComponent("Mode : " + getMode(stack).getName() + ", DestroySpeed : " + getDestroySpeed(stack, null)).withStyle(ChatFormatting.BLUE));
        }
    }
    @Deprecated // Use ItemStack sensitive version.
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(@Nonnull EquipmentSlot slot, @Nonnull ItemStack stack) {
        return slot == EquipmentSlot.MAINHAND ? this.attributes : super.getAttributeModifiers(slot, stack);
    }
    @Override
    @javax.annotation.Nullable
    public ICapabilityProvider initCapabilities(ItemStack stack, @javax.annotation.Nullable CompoundTag nbt){
        return new EnergyContainer(1000000);
    }

    @Override
    public boolean mineBlock(@NotNull ItemStack stack, @NotNull Level pLevel, @NotNull BlockState pState, @NotNull BlockPos pPos, @NotNull LivingEntity pMiningEntity) {
        if(canConsumeEnergy(stack, energyNeeded(stack,pState))) {
            consumeEnergy(stack, pState);
            return true;
        }
        return false;
    }

    @Override
    public boolean canPerformAction(ItemStack stack, ToolAction toolAction) {
        return ALWAYS_SUPPORTED_ACTIONS.contains(toolAction);
    }

    @Override
    public boolean isCorrectToolForDrops(@NotNull BlockState pBlock) {
        return true;
    }
    @Override
    @NotNull
    public InteractionResultHolder<ItemStack> use(@NotNull Level pLevel, Player pPlayer, @NotNull InteractionHand pUsedHand) {
        ItemStack stack = pPlayer.getItemInHand(pUsedHand);
        Mode mode = getMode(stack).next();
        DataTags.setInt(stack, "destroySpeed", mode.getIndex());
        if(pLevel.isClientSide()) pPlayer.sendMessage(new TextComponent("Mode set to : " + mode.getName().toUpperCase() + ", Speed : " + (float) mode.getDestroySpeed()), pPlayer.getUUID());
        return super.use(pLevel, pPlayer, pUsedHand);
    }

    public Mode getMode(ItemStack stack) {
        return Mode.getModeByIndex(DataTags.getInt(stack, "destroySpeed"));
    }

    @Override
    public float getDestroySpeed(@NotNull ItemStack stack, @Nullable BlockState pState) {
        return (float) getMode(stack).getDestroySpeed();
    }

    private void consumeEnergy(ItemStack stack, BlockState state) {
        extractEnergy(stack, energyNeeded(stack,state), false);
    }

    private int energyNeeded(ItemStack stack, BlockState state) {
        int speed = Mode.getModeByIndex(DataTags.getInt(stack, "destroySpeed")).getDestroySpeed();
        float hardness = state.getDestroySpeed(null, null);
        return (int) (hardness * log(speed));
    }

    public static final class Mode {

        private final int destroySpeed;
        private final String name;
        public static final Mode OFF = new Mode(0,"off");
        public static final Mode SLOW = new Mode(4,"slow");
        public static final Mode FAST = new Mode(32,"fast");
        public static final Mode INSTANT = new Mode(1024,"instant");
        private Mode(int destroySpeed, String name) {
            this.destroySpeed = destroySpeed;
            this.name = name;
        }
        public int getDestroySpeed() {
            return this.destroySpeed;
        }
        public int getIndex() {
            return getModes().indexOf(this);
        }
        public Mode next() {
            return getModes().get((this.getIndex() + 1) % 4);
        }
        public static Mode getModeByIndex(int index) throws IndexOutOfBoundsException{
            if(index < 0 || index > 3) throw new IndexOutOfBoundsException("Index out of bounds");
            return getModes().get(index);
        }
        public String getName() {
            return this.name;
        }
        @Contract(" -> new")
        public static @NotNull List<Mode> getModes() {
            return new ArrayList<>(List.of(OFF,SLOW,FAST,INSTANT));
        }
    }
}
