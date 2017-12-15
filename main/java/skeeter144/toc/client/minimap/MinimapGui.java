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
	MapChunk[][] loadedChunks = new MapChunk[3][3];
	
	@SubscribeEvent
	public void onRenderExperienceBar(RenderGameOverlayEvent.Post event)
	{
		ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
		drawRect(10, 10, 160, 160, 0xFFFFFFFF);
		
		EntityPlayer player = Minecraft.getMinecraft().player;
		BlockPos pos = player.getPosition();
		Chunk playersChunk = player.world.getChunkFromBlockCoords(pos);
		
		int[][] colors = null;
		if(playersChunk != lastKnownChunk || colors == null) {
			colors = getChunkColors(playersChunk);
			lastKnownChunk = playersChunk;
			int xC = 0;
			int zC = 0;
			for(int x = playersChunk.x - 1; x <= playersChunk.x + 1; ++x) {
				for(int z = playersChunk.z - 1; z <= playersChunk.z + 1; ++z) {
					loadedChunks[xC][zC] = new MapChunk(getChunkColors(player.world.getChunkFromChunkCoords(x, z)));
					++zC;
				}
				zC = 0;
				++xC;
			}
		}
		
		if(colors != null)
			drawMap(colors);
		
		long end = System.nanoTime();
	
		//if(TOCMain.rand.nextInt(1000) == 0)
			//System.out.println((end - start) / 1000000000f);
	}
	
	private void drawMap(int[][] colors) {
		
		int center = 100;
		int chunkWidth = 16;
		int blockPixels = chunkWidth / 16;

		for(int x = 0; x <= 2; ++x) {
			for(int z = 0; z <= 2; ++z) {
				for(int chunkX = 0; chunkX < 16; ++chunkX) {
					for(int chunkZ = 0; chunkZ < 16; ++chunkZ) {
						
						drawRect(chunkX * blockPixels + chunkWidth * x, 
								chunkZ * blockPixels + chunkWidth * z, 
								chunkX * blockPixels + chunkX + chunkWidth * x, 
								chunkZ * blockPixels + chunkZ + chunkWidth * z,
								loadedChunks[x][z].colors[chunkX][chunkZ] | 0xFF000000
								);
					}
				}
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
