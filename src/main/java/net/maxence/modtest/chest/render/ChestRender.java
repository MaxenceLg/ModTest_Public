package net.maxence.modtest.chest.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import it.unimi.dsi.fastutil.floats.Float2FloatFunction;
import it.unimi.dsi.fastutil.ints.Int2IntFunction;
import net.maxence.modtest.ModTest;
import net.maxence.modtest.chest.chestentity.ChestBlockEntity;
import net.maxence.modtest.chest.chests.IronChestBlock;
import net.maxence.modtest.model.ModelItem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.BrightnessCombiner;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.Material;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DoubleBlockCombiner;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.LidBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;

import java.util.Arrays;
import java.util.List;

//@Mod.EventBusSubscriber(modid = ModTest.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ChestRender<T extends BlockEntity & LidBlockEntity> implements BlockEntityRenderer<T> {
        private final ModelPart lid;
        private final ModelPart bottom;
        private final ModelPart lock;
        private final BlockEntityRenderDispatcher renderer;
        private static final List<ModelItem> MODEL_ITEMS = Arrays.asList(new ModelItem(new Vector3f(0.3F, 0.45F, 0.3F), 3.0F), new ModelItem(new Vector3f(0.7F, 0.45F, 0.3F), 3.0F), new ModelItem(new Vector3f(0.3F, 0.45F, 0.7F), 3.0F), new ModelItem(new Vector3f(0.7F, 0.45F, 0.7F), 3.0F), new ModelItem(new Vector3f(0.3F, 0.1F, 0.3F), 3.0F), new ModelItem(new Vector3f(0.7F, 0.1F, 0.3F), 3.0F), new ModelItem(new Vector3f(0.3F, 0.1F, 0.7F), 3.0F), new ModelItem(new Vector3f(0.7F, 0.1F, 0.7F), 3.0F), new ModelItem(new Vector3f(0.5F, 0.32F, 0.5F), 3.0F));

        public ChestRender(BlockEntityRendererProvider.Context context, GatherDataEvent event){
            ModelPart modelPart = context.bakeLayer(new ModelLayerLocation(new ResourceLocation("ironchest", "iron_chest"), "main"));
            this.renderer = context.getBlockEntityRenderDispatcher();
            this.bottom = modelPart.getChild("iron_bottom");
            this.lid = modelPart.getChild("iron_lid");
            this.lock = modelPart.getChild("iron_lock");
        }

        public static LayerDefinition createBodyLayer() {
            MeshDefinition meshDefinition = new MeshDefinition();
            PartDefinition partDefinition = meshDefinition.getRoot();
            partDefinition.addOrReplaceChild("iron_bottom", CubeListBuilder.create().texOffs(0, 19).addBox(1.0F, 0.0F, 1.0F, 14.0F, 10.0F, 14.0F), PartPose.ZERO);
            partDefinition.addOrReplaceChild("iron_lid", CubeListBuilder.create().texOffs(0, 0).addBox(1.0F, 0.0F, 0.0F, 14.0F, 5.0F, 14.0F), PartPose.offset(0.0F, 9.0F, 1.0F));
            partDefinition.addOrReplaceChild("iron_lock", CubeListBuilder.create().texOffs(0, 0).addBox(7.0F, -1.0F, 15.0F, 2.0F, 4.0F, 1.0F), PartPose.offset(0.0F, 8.0F, 0.0F));
            return LayerDefinition.create(meshDefinition, 64, 64);
        }

        public void render(T tileEntityIn, float partialTicks, PoseStack poseStack, MultiBufferSource bufferSource, int combinedLightIn, int combinedOverlayIn) {
            ChestBlockEntity tileEntity = (ChestBlockEntity)tileEntityIn;
            Level level = tileEntity.getLevel();
            boolean useTileEntityBlockState = level != null;
            BlockState blockState = useTileEntityBlockState ? tileEntity.getBlockState() : (BlockState)tileEntity.getBlockToUse().defaultBlockState().setValue(IronChestBlock.FACING, Direction.SOUTH);
            Block block = blockState.getBlock();
            if (block instanceof IronChestBlock abstractChestBlock) {
                poseStack.pushPose();
                float f = ((Direction)blockState.getValue(IronChestBlock.FACING)).toYRot();
                poseStack.translate(0.5, 0.5, 0.5);
                poseStack.mulPose(Vector3f.YP.rotationDegrees(-f));
                poseStack.translate(-0.5, -0.5, -0.5);
                DoubleBlockCombiner.NeighborCombineResult neighborCombineResult = null;
                if (useTileEntityBlockState) {
                    neighborCombineResult = abstractChestBlock.combine(blockState, level, tileEntityIn.getBlockPos(), true);
                }
                float openness = ((Float2FloatFunction)neighborCombineResult.apply(IronChestBlock.opennessCombiner(tileEntity))).get(partialTicks);
                openness = 1.0F - openness;
                openness = 1.0F - openness * openness * openness;
                int brightness = ((Int2IntFunction)neighborCombineResult.apply(new BrightnessCombiner())).applyAsInt(combinedLightIn);
                Material material = new Material(Sheets.CHEST_SHEET, new ResourceLocation("block/chest"));
                VertexConsumer vertexConsumer = material.buffer(bufferSource, RenderType::entityCutout);
                this.render(poseStack, vertexConsumer, this.lid, this.lock, this.bottom, openness, brightness, combinedOverlayIn);
                poseStack.popPose();
            }

        }

        private void render(PoseStack poseStack, VertexConsumer vertexConsumer, ModelPart lid, ModelPart lock, ModelPart bottom, float openness, int brightness, int combinedOverlayIn) {
            lid.xRot = -(openness * 1.5707964F);
            lock.xRot = lid.xRot;
            lid.render(poseStack, vertexConsumer, brightness, combinedOverlayIn);
            lock.render(poseStack, vertexConsumer, brightness, combinedOverlayIn);
            bottom.render(poseStack, vertexConsumer, brightness, combinedOverlayIn);
        }

        public static void renderItem(PoseStack matrices, MultiBufferSource buffer, ItemStack item, ModelItem modelItem, float rotation, int light) {
            if (!item.isEmpty()) {
                matrices.pushPose();
                Vector3f center = modelItem.getCenter();
                matrices.translate((double)center.x(), (double)center.y(), (double)center.z());
                matrices.mulPose(Vector3f.YP.rotationDegrees(rotation));
                float scale = modelItem.getSizeScaled();
                matrices.scale(scale, scale, scale);
                Minecraft.getInstance().getItemRenderer().renderStatic(item, ItemTransforms.TransformType.NONE, light, OverlayTexture.NO_OVERLAY, matrices, buffer, 0);
                matrices.popPose();
            }
        }
}
