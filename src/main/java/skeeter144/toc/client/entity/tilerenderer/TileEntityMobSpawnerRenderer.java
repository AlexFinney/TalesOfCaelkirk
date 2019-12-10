package skeeter144.toc.client.entity.tilerenderer;

import java.util.Random;

import org.lwjgl.opengl.GL11;

import com.mojang.blaze3d.platform.GlStateManager;

import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelDataManager;
import net.minecraftforge.client.model.data.IModelData;
import skeeter144.toc.entity.tile.TileEntityMobSpawner;

public class TileEntityMobSpawnerRenderer extends TileEntityRenderer<TileEntityMobSpawner> {

	BlockRendererDispatcher blockRenderer;

	@Override
	public void render(TileEntityMobSpawner te, double x, double y, double z, float partialTicks, int destroyStage) {
		if(!Minecraft.getInstance().player.isCreative()) 
			return;
		ItemStack stack = new ItemStack(Items.ZOMBIE_HEAD);
		IBakedModel model = Minecraft.getInstance().getItemRenderer().getItemModelMesher().getItemModel(stack);
		
		GlStateManager.pushMatrix();
		GlStateManager.enableLighting();
		GlStateManager.translated(x, y, z);
		GlStateManager.translated(.5, 1, .5);
		GlStateManager.scaled(2, 2, 2);
		Minecraft.getInstance().getItemRenderer().renderItem(stack, ItemCameraTransforms.TransformType.NONE);
		GlStateManager.popMatrix();
	}

	private boolean renderStateModel(BlockPos pos, BlockState state, BufferBuilder buffer, World w, boolean checkSides) {
        IBakedModel model = blockRenderer.getBlockModelShapes().getModel(state);
        IModelData data = model.getModelData(w, pos, state, ModelDataManager.getModelData(w, pos));
		
		return blockRenderer.getBlockModelRenderer().renderModel(w, model, state, pos, buffer, false, new Random(), 69, data);
	}
}
