package net.maxence.modtest.item.OreScanItem;

import net.maxence.modtest.tab.ModTabs;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.Tags;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class oreScanItem extends Item {

    private Map<String, Integer> OresFound;
    public oreScanItem() {
        super(new Item.Properties().durability(64).tab(ModTabs.TABFEUR));
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pHand) {
        if (!pLevel.isClientSide()) {
            BlockPos playerPos = pPlayer.getOnPos();
            ServerLevel world = (ServerLevel) pLevel;
            OresFound = new TreeMap<>();
            scanMap(playerPos, world, pPlayer);
            outputAll(OresFound, pPlayer);
        }
        pPlayer.getItemInHand(pHand).hurtAndBreak(1, pPlayer, (player) -> player.broadcastBreakEvent(player.getUsedItemHand()));
        return InteractionResultHolder.pass(pPlayer.getItemInHand(pHand));
    }

    private void outputAll(Map<String, Integer> oresFound, Player player) {
        for(String name: oresFound.keySet()) {
            player.sendMessage(new TextComponent("Block : " + name + "Found :" + oresFound.get(name)).withStyle(ChatFormatting.BLUE),player.getUUID());
        }
    }

    @Override
    public boolean isRepairable(ItemStack stack) {
        return canRepair;
    }


    private void output(Player player, Block block, BlockPos blockPos){
        player.sendMessage(new TextComponent( player.getName().getContents() + ", Block found : " + block.asItem().getRegistryName().toString() + "X : "+ blockPos.getX() + "Y : "+ blockPos.getY() + "Z : "+ blockPos.getZ()),player.getUUID());
    }

    private boolean isOre(BlockState blockState) {
        //return block in Tag : ModTags.Blocks.ORES
        return blockState.is(Tags.Blocks.ORES);
    }
    private void mineBlock(ServerLevel world, BlockPos position, Player player) {
        BlockState blockState = world.getBlockState(position);
        Block block = blockState.getBlock();
        BlockEntity blockEntity = world.getBlockEntity(position);
        world.destroyBlock(position,false,player,0);
        List<ItemStack> drops = Block.getDrops(blockState, world, position, blockEntity, player, ItemStack.EMPTY);
        for(ItemStack drop : drops){
            if(!player.getInventory().add(drop)){
                Block.popResource(world, player.getOnPos(), drop);
            }
        }
        world.sendBlockUpdated(position, Blocks.AIR.defaultBlockState(), blockState, 0);
    }

    private void scanMap(BlockPos playerPos, ServerLevel world, Player player) {
        for (int x = playerPos.getX() - 32; x <= playerPos.getX() + 32; x++) {
            for (int z = playerPos.getZ() - 32; z <= playerPos.getZ() + 32; z++) {
                for (int y = -64 ; y <= 255; y++) {
                    BlockPos position = new BlockPos(x, y, z);
                    BlockState blockState = world.getBlockState(position);
                    Block block = blockState.getBlock();
                    if (isOre(blockState)) {
                        addOresFound(block);//ajoute le bloc à la liste des blocs trouvés
                        //mine le bloc et le place dans l'inventaire du joueur
                        mineBlock(world, position, player);



                        //output(player, blockBelow, player.getOnPos().below(y));
                    }
                }
            }
        }
    }

    private void addOresFound(Block block) {
        String registryName = block.getRegistryName().toString();

        // Vérifiez si la clé existe dans OresFound
        if (OresFound.containsKey(registryName)) {
            // Clé existante : incrémentez la valeur
            int currentValue = OresFound.get(registryName);
            OresFound.put(registryName, currentValue + 1);
        } else {
            // Nouvelle clé : ajoutez-la avec une valeur de 1
            OresFound.put(registryName, 1);
        }
    }

}
