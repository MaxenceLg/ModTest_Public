package net.maxence.modtest.chest.chests;

import it.unimi.dsi.fastutil.doubles.DoubleList;
import it.unimi.dsi.fastutil.floats.Float2FloatFunction;
import net.maxence.modtest.blockentity.ModBlocksEntities;
import net.maxence.modtest.chest.chestentity.ChestBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.*;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Cat;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.LidBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.BiPredicate;
import java.util.function.Supplier;

public class IronChestBlock extends BaseEntityBlock {
    public static final String SCREEN_TUTORIAL_MODCHEST = "screen.tutorial.modchest";
    public static final DirectionProperty FACING;
    public static final BooleanProperty WATERLOGGED;
    protected static final VoxelShape AABB;
    private static final DoubleBlockCombiner.Combiner<ChestBlockEntity, Optional<Container>> CHEST_COMBINER;
    private static final DoubleBlockCombiner.Combiner<ChestBlockEntity, Optional<MenuProvider>> MENU_PROVIDER_COMBINER;
    protected final Supplier<BlockEntityType<? extends ChestBlockEntity>> blockEntityType;

    public IronChestBlock(Properties pProperties) {
        super(pProperties.requiresCorrectToolForDrops());
        blockEntityType = ModBlocksEntities.CHEST_BE::get;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new ChestBlockEntity(pPos, pState);
    }

    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.ENTITYBLOCK_ANIMATED;
    }

    /** @deprecated */
    @Deprecated
    public BlockState updateShape(BlockState blockState, Direction direction, BlockState facingState, LevelAccessor levelAccessor, BlockPos currentPos, BlockPos facingPos) {
        if ((Boolean)blockState.getValue(WATERLOGGED)) {
            levelAccessor.scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(levelAccessor));
        }

        return super.updateShape(blockState, direction, facingState, levelAccessor, currentPos, facingPos);
    }

    /** @deprecated */
    @Deprecated
    public VoxelShape getShape(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext context) {
        return AABB;
    }

    public static Direction getConnectedDirection(BlockState blockState) {
        return (blockState.getValue(FACING)).getCounterClockWise();
    }

    public BlockState getStateForPlacement(BlockPlaceContext context) {
        FluidState fluidState = context.getLevel().getFluidState(context.getClickedPos());
        return (this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite())).setValue(WATERLOGGED, fluidState.getType() == Fluids.WATER);
    }

    /** @deprecated */
    @Deprecated
    public FluidState getFluidState(BlockState blockState) {
        return blockState.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(blockState);
    }

    public void setPlacedBy(Level level, BlockPos blockPos, BlockState blockState, @javax.annotation.Nullable LivingEntity livingEntity, ItemStack itemStack) {
        BlockEntity blockEntity = level.getBlockEntity(blockPos);
        if (blockEntity instanceof ChestBlockEntity) {
            ((ChestBlockEntity)blockEntity).wasPlaced(livingEntity, itemStack);
            if (itemStack.hasCustomHoverName()) {
                ((ChestBlockEntity)blockEntity).setCustomName(itemStack.getHoverName());
            }
        }

    }

    /** @deprecated */
    @Deprecated
    public void onRemove(BlockState blockState, Level level, BlockPos blockPos, BlockState newState, boolean isMoving) {
        if (!blockState.is(newState.getBlock())) {
            BlockEntity blockentity = level.getBlockEntity(blockPos);
            if (blockentity instanceof Container) {
                Containers.dropContents(level, blockPos, (Container)blockentity);
                level.updateNeighbourForOutputSignal(blockPos, this);
            }
            super.onRemove(blockState, level, blockPos, newState, isMoving);
        }

    }

    /** @deprecated */
    @Deprecated
    public InteractionResult use(BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand interactionHand, BlockHitResult blockHitResult) {
        if (level.isClientSide) {
            return InteractionResult.SUCCESS;
        } else {
            MenuProvider menuProvider = this.getMenuProvider(blockState, level, blockPos);
            if (menuProvider != null) {
                player.openMenu(menuProvider);
                //player.awardStat(this.getOpenChestStat());
            }

            return InteractionResult.CONSUME;
        }
    }

    public BlockEntityType<? extends ChestBlockEntity> blockEntityType() {
        return this.blockEntityType.get();
    }

    @javax.annotation.Nullable
    public static Container getContainer(IronChestBlock ironChestBlock, BlockState blockState, Level level, BlockPos blockPos, boolean ignoreBlockedChest) {
        return (Container)((Optional) ironChestBlock.combine(blockState, level, blockPos, ignoreBlockedChest).apply(CHEST_COMBINER)).orElse(null);
    }

    public DoubleBlockCombiner.NeighborCombineResult<? extends ChestBlockEntity> combine(BlockState blockState, Level level, BlockPos blockPos, boolean ignoreBlockedChest) {
        BiPredicate<LevelAccessor,BlockPos> biPredicate;
        if (ignoreBlockedChest) {
            biPredicate = (levelAccessor, blockPos1) -> false;
        } else {
            biPredicate = IronChestBlock::isChestBlockedAt;
        }

        return DoubleBlockCombiner.combineWithNeigbour((BlockEntityType)this.blockEntityType.get(), IronChestBlock::getBlockType, IronChestBlock::getConnectedDirection, FACING, blockState, level, blockPos, biPredicate);
    }

    @javax.annotation.Nullable
    public MenuProvider getMenuProvider(BlockState blockState, Level level, BlockPos blockPos) {
        return (MenuProvider)((Optional)this.combine(blockState, level, blockPos, false).apply(MENU_PROVIDER_COMBINER)).orElse((MenuProvider)null);
    }

    public static DoubleBlockCombiner.Combiner<ChestBlockEntity, Float2FloatFunction> opennessCombiner(final LidBlockEntity lidBlockEntity) {
        return new DoubleBlockCombiner.Combiner<>() {
            public Float2FloatFunction acceptDouble(ChestBlockEntity blockEntityOne, ChestBlockEntity blockEntityTwo) {
                return (lidBlockEntityx) -> {
                    return Math.max(blockEntityOne.getOpenNess(lidBlockEntityx), blockEntityTwo.getOpenNess(lidBlockEntityx));
                };
            }

            public Float2FloatFunction acceptSingle(ChestBlockEntity blockEntity) {
                Objects.requireNonNull(blockEntity);
                return blockEntity::getOpenNess;
            }

            public Float2FloatFunction acceptNone() {
                LidBlockEntity var10000 = lidBlockEntity;
                Objects.requireNonNull(var10000);
                return var10000::getOpenNess;
            }
        };
    }

    @javax.annotation.Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState blockState, BlockEntityType<T> blockEntityType) {
        return level.isClientSide ? createTickerHelper(blockEntityType, this.blockEntityType(), ChestBlockEntity::lidAnimateTick) : null;
    }

    public static boolean isChestBlockedAt(LevelAccessor levelAccessor, BlockPos blockPos) {
        return isBlockedChestByBlock(levelAccessor, blockPos) || isCatSittingOnChest(levelAccessor, blockPos);
    }

    public static DoubleBlockCombiner.BlockType getBlockType(BlockState blockState) {
        return DoubleBlockCombiner.BlockType.SINGLE;
    }

    private static boolean isCatSittingOnChest(LevelAccessor levelAccessor, BlockPos blockPos) {
        List<Cat> list = levelAccessor.getEntitiesOfClass(Cat.class, new AABB(blockPos.getX(), blockPos.getY() + 1, blockPos.getZ(), blockPos.getX() + 1, blockPos.getY() + 2, (double)(blockPos.getZ() + 1)));
        if (!list.isEmpty()) {
            Iterator var3 = list.iterator();

            while(var3.hasNext()) {
                Cat cat = (Cat)var3.next();
                if (cat.isInSittingPose()) {
                    return true;
                }
            }
        }

        return false;
    }

    private static boolean isBlockedChestByBlock(BlockGetter blockGetter, BlockPos blockPos) {
        BlockPos above = blockPos.above();
        return blockGetter.getBlockState(above).isRedstoneConductor(blockGetter, above);
    }
    public BlockState rotate(BlockState blockState, Rotation rotation) {
        return blockState.setValue(FACING, rotation.rotate((Direction)blockState.getValue(FACING)));
    }

    public BlockState mirror(BlockState blockState, Mirror mirror) {
        return blockState.rotate(mirror.getRotation((Direction)blockState.getValue(FACING)));
    }

    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> blockBlockStateBuilder) {
        blockBlockStateBuilder.add(FACING, WATERLOGGED);
    }

    public void tick(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, Random random) {
        BlockEntity blockEntity = serverLevel.getBlockEntity(blockPos);
        if (blockEntity instanceof ChestBlockEntity) {
            ((ChestBlockEntity)blockEntity).recheckOpen();
        }

    }


    static {
        FACING = HorizontalDirectionalBlock.FACING;
        WATERLOGGED = BlockStateProperties.WATERLOGGED;
        AABB = Block.box(1.0, 0.0, 1.0, 15.0, 14.0, 15.0);
        CHEST_COMBINER = new DoubleBlockCombiner.Combiner<>() {
            public Optional<Container> acceptDouble(ChestBlockEntity blockEntityOne, ChestBlockEntity blockEntityTwo) {
                return Optional.of(new CompoundContainer(blockEntityOne, blockEntityTwo));
            }

            public Optional<Container> acceptSingle(ChestBlockEntity blockEntity) {
                return Optional.of(blockEntity);
            }

            public Optional<Container> acceptNone() {
                return Optional.empty();
            }
        };
        MENU_PROVIDER_COMBINER = new DoubleBlockCombiner.Combiner<>() {
            public Optional<MenuProvider> acceptDouble(ChestBlockEntity blockEntityOne, ChestBlockEntity blockEntityTwo) {
                return Optional.empty();
            }

            public Optional<MenuProvider> acceptSingle(ChestBlockEntity blockEntity) {
                return Optional.of(blockEntity);
            }

            public Optional<MenuProvider> acceptNone() {
                return Optional.empty();
            }
        };
    }
}