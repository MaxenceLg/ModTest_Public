package net.maxence.modtest.screen.oregenscreen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.maxence.modtest.ModTest;
import net.maxence.modtest.container.EGenBEContainer.EnergyGBEContainer;
import net.maxence.modtest.container.oregencontainer.OreGeneratorContainer;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import org.jetbrains.annotations.NotNull;

public class OreGenScreen extends AbstractContainerScreen<OreGeneratorContainer> {

    private final ResourceLocation GUI = new ResourceLocation(ModTest.MOD_ID, "textures/gui/oregen_gui.png");

    public OreGenScreen(OreGeneratorContainer container, Inventory inv, Component name) {
        super(container, inv, name);
        this.imageWidth = 202;
        this.imageHeight = 208;
    }

    @Override
    public void render(@NotNull PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        this.renderTooltip(matrixStack, mouseX, mouseY);
    }

    @Override
    protected void renderLabels(PoseStack pPoseStack, int pMouseX, int pMouseY) {
        this.font.draw(pPoseStack, this.title, 11, (float)7, 4210752);
        this.font.draw(pPoseStack, this.playerInventoryTitle, (float) 20, (float)116, 4210752);
    }

    @Override
    protected void renderBg(@NotNull PoseStack matrixStack, float partialTicks, int mouseX, int mouseY) {
        RenderSystem.setShaderTexture(0, GUI);
        int relX = (this.width - this.imageWidth) / 2;
        int relY = (this.height - this.imageHeight) / 2;
        this.blit(matrixStack, relX, relY, 0, 0, this.imageWidth, this.imageHeight);
    }
}