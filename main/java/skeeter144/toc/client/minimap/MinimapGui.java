package skeeter144.toc.client.minimap;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class MinimapGui extends Gui{

	Chunk lastKnownChunk = null;
	
	@SubscribeEvent
	public void onRenderExperienceBar(RenderGameOverlayEvent.Post event)
	{
		ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
		drawRect(10, 10, 160, 160, 0xFFFFFFFF);
		
		EntityPlayer player = Minecraft.getMinecraft().player;
		BlockPos pos = player.getPosition();
		Chunk c = player.world.getChunkFromBlockCoords(pos);
		
		int[][] colors = null;
		if(c != lastKnownChunk || colors == null) {
			colors = getChunkColors(c);
			lastKnownChunk = c;
		}
		
		if(colors != null)
			drawMap(colors);
		
		long end = System.nanoTime();
	
		//if(TOCMain.rand.nextInt(1000) == 0)
			//System.out.println((end - start) / 1000000000f);
	}
	
	private void drawMap(int[][] colors) {
		long start = System.nanoTime();
		for(int x = 0; x < 16; ++x) {
			for(int z = 0; z < 16; ++z) {
				drawRect(10 + x * 10, 10 + z * 10, 20 + x * 10, 20 + z * 10, colors[x][z] | 0xFF000000);
			}
		}
		
	}
	
	private int[][] getChunkColors(Chunk c) {
		if(c == null)
			return new int[16][16];
		
		EntityPlayer player = Minecraft.getMinecraft().player;
		
		int minHeight = Integer.MAX_VALUE;
		int maxHeight = Integer.MIN_VALUE;
		
		int[][] colors = new int[16][16];
		for(int x = 0; x < 16; ++x) {
			for(int z = 0; z < 16; ++z) {
				int height =  c.getHeightValue(x, z) - 1;
				
				if(height < minHeight)
					minHeight = height;
				
				if(height > maxHeight)
					maxHeight = height;
				
				IBlockState state = c.getBlockState(x, height, z);
				colors[x][z] = state.getMapColor(player.world, new BlockPos(x, height, z)).colorValue;
				
			}
		}
		
		
		for(int x = 0; x < 16; ++x) {
			for(int z = 0; z < 16; ++z) {
				int color = colors[x][z];
				int height =  c.getHeightValue(x, z) - 1;
				
				float lightReduction = ((float)height - minHeight) / (maxHeight - minHeight);
				lightReduction = 1;// - lightReduction;
				
				int r = (color >> 4) & 0xFF;
				int g = (color >> 2) & 0xFF;
				int b = (color) & 0xFF;
				
				r = (int) (((float)r) * lightReduction);
				g = (int) (((float)g) * lightReduction);
				b = (int) (((float)b) * lightReduction);
				
				
				color = (r << 4) & (g << 2) & b; 
				//colors[x][z] = color;
			}
		}

			
	
		
		return colors;
	}
	
}
