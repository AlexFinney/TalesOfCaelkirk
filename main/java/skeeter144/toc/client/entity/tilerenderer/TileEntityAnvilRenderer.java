package skeeter144.toc.client.entity.tilerenderer;

import net.minecraft.block.BlockAnvil;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.Vec3i;
import net.minecraftforge.common.util.EnumHelper;
import skeeter144.toc.entity.tile.TileEntityAnvil;

public class TileEntityAnvilRenderer extends TileEntitySpecialRenderer{
	
	@Override
	public void render(TileEntity te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
		super.render(te, x, y, z, partialTicks, destroyStage, alpha);
		TileEntityAnvil anvil = (TileEntityAnvil)te;
		
		if(anvil.ingot == null && anvil.producedItem == null)
			return;
		
		ItemStack stack = new ItemStack(anvil.ingot);
		IBakedModel model = Minecraft.getMinecraft().getRenderItem().getItemModelMesher().getItemModel(stack);
		
		IBlockState bs = te.getWorld().getBlockState(te.getPos());
		EnumFacing facing = bs.getValue(BlockAnvil.FACING);
		facing = facing.rotateYCCW();
		
		Vec3i forward = facing.getDirectionVec();
		Vec3i right = forward.crossProduct(new Vec3i(0, 1, 0));
		
		float rot = 0;
		if(facing.equals(EnumFacing.NORTH))
			rot = 180;
		else if(facing.equals(EnumFacing.SOUTH))
			rot = 0;
		else if(facing.equals(EnumFacing.EAST))
			rot = 90;
		else if(facing.equals(EnumFacing.WEST))
			rot = -90;
		
		GlStateManager.pushMatrix();
		GlStateManager.enableLighting();
		GlStateManager.translate(x, y, z);
		GlStateManager.translate(.5, 1.05, .5);
		
		switch(anvil.addedIngots) {
			case 6:
				GlStateManager.pushMatrix();
				GlStateManager.translate(right.getX() * .3f, 0, right.getZ() * .3f);
				GlStateManager.translate(-forward.getX() * .15f, 0, -forward.getZ() * .15f);
				GlStateManager.rotate(rot, 0, 1, 0);
				GlStateManager.rotate(90, 1, 0, 0);
				GlStateManager.scale(.25, .25, 1);
				Minecraft.getMinecraft().getRenderItem().renderItem(stack, ItemCameraTransforms.TransformType.NONE);
				GlStateManager.popMatrix();
			case 5:
				GlStateManager.pushMatrix();
				GlStateManager.translate(-forward.getX() * .15f, 0, -forward.getZ() * .15f);
				GlStateManager.rotate(rot, 0, 1, 0);
				GlStateManager.rotate(90, 1, 0, 0);
				GlStateManager.scale(.25, .25, 1);
				Minecraft.getMinecraft().getRenderItem().renderItem(stack, ItemCameraTransforms.TransformType.NONE);
				GlStateManager.popMatrix();
			case 4:
				GlStateManager.pushMatrix();
				GlStateManager.translate(-right.getX() * .3f, 0, -right.getZ() * .3f);
				GlStateManager.translate(-forward.getX() * .15f, 0, -forward.getZ() * .15f);
				GlStateManager.rotate(rot, 0, 1, 0);
				GlStateManager.rotate(90, 1, 0, 0);
				GlStateManager.scale(.25, .25, 1);
				Minecraft.getMinecraft().getRenderItem().renderItem(stack, ItemCameraTransforms.TransformType.NONE);
				GlStateManager.popMatrix();
			case 3:
				GlStateManager.pushMatrix();
				GlStateManager.translate(right.getX() * .3f, 0, right.getZ() * .3f);
				GlStateManager.translate(forward.getX() * .15f, 0, forward.getZ() * .15f);
				GlStateManager.rotate(rot, 0, 1, 0);
				GlStateManager.rotate(90, 1, 0, 0);
				GlStateManager.scale(.25, .25, 1);
				Minecraft.getMinecraft().getRenderItem().renderItem(stack, ItemCameraTransforms.TransformType.NONE);
				GlStateManager.popMatrix();
			case 2:
				GlStateManager.pushMatrix();
				GlStateManager.translate(forward.getX() * .15f, 0, forward.getZ() * .15f);
				GlStateManager.rotate(rot, 0, 1, 0);
				GlStateManager.rotate(90, 1, 0, 0);
				GlStateManager.scale(.25, .25, 1);
				Minecraft.getMinecraft().getRenderItem().renderItem(stack, ItemCameraTransforms.TransformType.NONE);
				GlStateManager.popMatrix();
			case 1:
				GlStateManager.pushMatrix();
				GlStateManager.translate(-right.getX() * .3f, 0, -right.getZ() * .3f);
				GlStateManager.translate(forward.getX() * .15f, 0, forward.getZ() * .15f);
				GlStateManager.rotate(rot, 0, 1, 0);
				GlStateManager.rotate(90, 1, 0, 0);
				GlStateManager.scale(.25, .25, 1);
				Minecraft.getMinecraft().getRenderItem().renderItem(stack, ItemCameraTransforms.TransformType.NONE);
				GlStateManager.popMatrix();
		}
		
		
		if(anvil.producedItem != null) {
			GlStateManager.rotate(90, forward.getX(), forward.getY(), forward.getZ());
			Minecraft.getMinecraft().getRenderItem().renderItem(anvil.producedItem, ItemCameraTransforms.TransformType.HEAD);
		}
		
		
		GlStateManager.popMatrix();
	}
}
