package skeeter144.toc.client.entity.tilerenderer;

import com.mojang.blaze3d.platform.GlStateManager;

import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.math.Vec3i;
import skeeter144.toc.blocks.BlockAnvil;
import skeeter144.toc.entity.tile.TileEntityAnvil;

public class TileEntityAnvilRenderer extends TileEntityRenderer<TileEntityAnvil>{
	
	@Override
	public void render(TileEntityAnvil te, double x, double y, double z, float partialTicks, int destroyStage) {
		super.render(te, x, y, z, partialTicks, destroyStage);
		TileEntityAnvil anvil = (TileEntityAnvil)te;
		
		if(anvil.ingot == null && anvil.producedItem == null)
			return;
		
		ItemStack stack = new ItemStack(anvil.ingot);
		IBakedModel model = Minecraft.getInstance().getItemRenderer().getItemModelMesher().getItemModel(stack);
		
		BlockState bs = te.getWorld().getBlockState(te.getPos());
		Direction facing = bs.get(BlockAnvil.FACING);
		facing = facing.rotateYCCW();
		
		Vec3i forward = facing.getDirectionVec();
		Vec3i right = forward.crossProduct(new Vec3i(0, 1, 0));
		
		float rot = 0;
		if(facing.equals(Direction.NORTH))
			rot = 180;
		else if(facing.equals(Direction.SOUTH))
			rot = 0;
		else if(facing.equals(Direction.EAST))
			rot = 90;
		else if(facing.equals(Direction.WEST))
			rot = -90;
		
		GlStateManager.pushMatrix();
		GlStateManager.enableLighting();
		GlStateManager.translated(x, y, z);
		GlStateManager.translated(.5, 1.05, .5);
		
		switch(anvil.addedIngots) {
			case 6:
				GlStateManager.pushMatrix();
				GlStateManager.translated(right.getX() * .3f, 0, right.getZ() * .3f);
				GlStateManager.translated(-forward.getX() * .15f, 0, -forward.getZ() * .15f);
				GlStateManager.rotatef(rot, 0, 1, 0);
				GlStateManager.rotatef(90, 1, 0, 0);
				GlStateManager.scalef(.25f, .25f, 1);
				Minecraft.getInstance().getItemRenderer().renderItem(stack, ItemCameraTransforms.TransformType.NONE);
				GlStateManager.popMatrix();
			case 5:
				GlStateManager.pushMatrix();
				GlStateManager.translatef(-forward.getX() * .15f, 0, -forward.getZ() * .15f);
				GlStateManager.rotatef(rot, 0, 1, 0);
				GlStateManager.rotatef(90, 1, 0, 0);
				GlStateManager.scaled(.25, .25, 1);
				Minecraft.getInstance().getItemRenderer().renderItem(stack, ItemCameraTransforms.TransformType.NONE);
				GlStateManager.popMatrix();
			case 4:
				GlStateManager.pushMatrix();
				GlStateManager.translated(-right.getX() * .3f, 0, -right.getZ() * .3f);
				GlStateManager.translated(-forward.getX() * .15f, 0, -forward.getZ() * .15f);
				GlStateManager.rotatef(rot, 0, 1, 0);
				GlStateManager.rotatef(90, 1, 0, 0);
				GlStateManager.scaled(.25, .25, 1);
				Minecraft.getInstance().getItemRenderer().renderItem(stack, ItemCameraTransforms.TransformType.NONE);
				GlStateManager.popMatrix();
			case 3:
				GlStateManager.pushMatrix();
				GlStateManager.translated(right.getX() * .3f, 0, right.getZ() * .3f);
				GlStateManager.translated(forward.getX() * .15f, 0, forward.getZ() * .15f);
				GlStateManager.rotatef(rot, 0, 1, 0);
				GlStateManager.rotatef(90, 1, 0, 0);
				GlStateManager.scaled(.25, .25, 1);
				Minecraft.getInstance().getItemRenderer().renderItem(stack, ItemCameraTransforms.TransformType.NONE);
				GlStateManager.popMatrix();
			case 2:
				GlStateManager.pushMatrix();
				GlStateManager.translated(forward.getX() * .15f, 0, forward.getZ() * .15f);
				GlStateManager.rotatef(rot, 0, 1, 0);
				GlStateManager.rotatef(90, 1, 0, 0);
				GlStateManager.scaled(.25, .25, 1);
				Minecraft.getInstance().getItemRenderer().renderItem(stack, ItemCameraTransforms.TransformType.NONE);
				GlStateManager.popMatrix();
			case 1:
				GlStateManager.pushMatrix();
				GlStateManager.translated(-right.getX() * .3f, 0, -right.getZ() * .3f);
				GlStateManager.translated(forward.getX() * .15f, 0, forward.getZ() * .15f);
				GlStateManager.rotatef(rot, 0, 1, 0);
				GlStateManager.rotatef(90, 1, 0, 0);
				GlStateManager.scaled(.25, .25, 1);
				Minecraft.getInstance().getItemRenderer().renderItem(stack, ItemCameraTransforms.TransformType.NONE);
				GlStateManager.popMatrix();
		}
		
		
		if(anvil.producedItem != null) {
			GlStateManager.rotatef(90, forward.getX(), forward.getY(), forward.getZ());
			Minecraft.getInstance().getItemRenderer().renderItem(anvil.producedItem, ItemCameraTransforms.TransformType.NONE);
		}
		
		
		GlStateManager.popMatrix();
	}
	
}
