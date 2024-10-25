package net.maxence.modtest.chest.chestcontainer;

import net.maxence.modtest.container.ModContainers;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import org.jetbrains.annotations.NotNull;

public class ChestContainer extends AbstractContainerMenu {
    private final Container container;
    private final Inventory playerInventory;
    protected ChestContainer(int containerId, Inventory playerInventory, Container inventory) {
        super(ModContainers.CHEST_CONTAINER.get(), containerId);
        this.playerInventory = playerInventory;
        this.container = inventory;
        inventory.startOpen(playerInventory.player);
        buildSlots(inventory);
        layoutPlayerInventorySlots(21, 126);
    }

    public void buildSlots(Container c){
        addSlotBox(c, 0, 12 /*12*/, 18/*18*/, 10, 18, 5, 18);
    }
    public static ChestContainer createChestContainer(int containerId, Inventory playerInventory, Container inventory) {
        return new ChestContainer(containerId,playerInventory,inventory);
    }
    public boolean stillValid(@NotNull Player pPlayer) {
        return this.container.stillValid(pPlayer);
    }
    private int addSlotRange(Container c, int index, int x, int y, int amount, int dx) {
        for (int i = 0; i < amount; i++) {
            addSlot(new Slot(c, index, x, y));
            x += dx;
            index++;
        }
        return index;
    }
    private int addSlotBox(Container c, int index, int x, int y, int horAmount, int dx, int verAmount, int dy) {
        for (int j = 0; j < verAmount; j++) {
            index = addSlotRange(c, index, x, y, horAmount, dx);
            y += dy;
        }
        return index;
    }
    private void layoutPlayerInventorySlots(int leftCol, int topRow) {
        // Player inventory
        addSlotBox(this.playerInventory, 9, leftCol, topRow, 9, 18, 3, 18);

        // Hotbar
        topRow += 58;
        addSlotRange(this.playerInventory, 0, leftCol, topRow, 9, 18);
    }

    public static ChestContainer createChestContainer(int i, Inventory inventory) {
        return createChestContainer(i, inventory, new SimpleContainer(50));
    }
    public Container getContainer() {
        return this.container;
    }
}