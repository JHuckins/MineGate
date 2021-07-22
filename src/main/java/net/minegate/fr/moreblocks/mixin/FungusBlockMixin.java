package net.minegate.fr.moreblocks.mixin;

import net.minecraft.block.*;
import net.minecraft.block.enums.SlabType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minegate.fr.moreblocks.block.Blocks;
import net.minegate.fr.moreblocks.block.enums.FernType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FungusBlock.class)
public class FungusBlockMixin extends PlantBlock
{
    private static final EnumProperty<SlabType> TYPE;
    private static final EnumProperty<FernType> FERN_TYPE;
    private static final VoxelShape             FUNGUS_SHAPE = Block.createCuboidShape(4.0D, -8.0D, 4.0D, 12.0D, 1.0D, 12.0D);

    protected FungusBlockMixin(Settings settings)
    {
        super(settings);
    }

    @Inject(at = @At("RETURN"), method = "getOutlineShape", cancellable = true)
    private void getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context, CallbackInfoReturnable<VoxelShape> cir)
    {
        Block block = world.getBlockState(pos.down()).getBlock();
        BlockState blockState = world.getBlockState(pos.down());

        if (block.equals(net.minegate.fr.moreblocks.block.Blocks.GRASS_BLOCK_SLAB) || block.equals(net.minegate.fr.moreblocks.block.Blocks.DIRT_SLAB) || block.equals(net.minegate.fr.moreblocks.block.Blocks.COARSE_DIRT_SLAB) || block.equals(net.minegate.fr.moreblocks.block.Blocks.PODZOL_SLAB))
        {
            if (blockState.equals(net.minegate.fr.moreblocks.block.Blocks.GRASS_BLOCK_SLAB.getDefaultState().with(TYPE, SlabType.BOTTOM)) ||
                    blockState.equals(net.minegate.fr.moreblocks.block.Blocks.DIRT_SLAB.getDefaultState().with(TYPE, SlabType.BOTTOM)) ||
                    blockState.equals(net.minegate.fr.moreblocks.block.Blocks.COARSE_DIRT_SLAB.getDefaultState().with(TYPE, SlabType.BOTTOM)) ||
                    blockState.equals(net.minegate.fr.moreblocks.block.Blocks.PODZOL_SLAB.getDefaultState().with(TYPE, SlabType.BOTTOM)))
            {
                cir.setReturnValue(FUNGUS_SHAPE);
            }
        }
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack itemStack)
    {
        Block block = world.getBlockState(pos.down()).getBlock();
        BlockState blockState = world.getBlockState(pos.down());

        if (block.equals(net.minegate.fr.moreblocks.block.Blocks.GRASS_BLOCK_SLAB) || block.equals(net.minegate.fr.moreblocks.block.Blocks.DIRT_SLAB) || block.equals(net.minegate.fr.moreblocks.block.Blocks.COARSE_DIRT_SLAB) || block.equals(net.minegate.fr.moreblocks.block.Blocks.PODZOL_SLAB))
        {
            if (blockState.equals(net.minegate.fr.moreblocks.block.Blocks.GRASS_BLOCK_SLAB.getDefaultState().with(TYPE, SlabType.BOTTOM)) ||
                    blockState.equals(net.minegate.fr.moreblocks.block.Blocks.DIRT_SLAB.getDefaultState().with(TYPE, SlabType.BOTTOM)) ||
                    blockState.equals(net.minegate.fr.moreblocks.block.Blocks.COARSE_DIRT_SLAB.getDefaultState().with(TYPE, SlabType.BOTTOM)) ||
                    blockState.equals(Blocks.PODZOL_SLAB.getDefaultState().with(TYPE, SlabType.BOTTOM)))
            {
                world.setBlockState(pos, state.with(FERN_TYPE, FernType.PLANT));
            }
            else
            {
                world.setBlockState(pos, state.with(FERN_TYPE, FernType.DEFAULT));
            }
        }
        else
        {
            world.setBlockState(pos, state.with(FERN_TYPE, FernType.DEFAULT));
        }
    }

    static
    {
        FERN_TYPE = net.minegate.fr.moreblocks.state.Properties.FERN_TYPE;
        TYPE = Properties.SLAB_TYPE;
    }
}
