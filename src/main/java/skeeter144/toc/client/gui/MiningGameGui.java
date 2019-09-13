package skeeter144.toc.client.gui;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.texture.TextureManager;
import scala.tools.nsc.GlobalSymbolLoaders;
import skeeter144.toc.minigames.MiningMinigame_Old;
import skeeter144.toc.minigames.MiningMinigame_Old.GameTile;
import skeeter144.toc.util.Util;

public class MiningGameGui extends GuiScreen{
	MiningMinigame_Old game = null;
	boolean wasMouseClicked = false;
	public MiningGameGui() {
		game = new MiningMinigame_Old(10, 10);
	}
	
	
	boolean mouseClicked = false;
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		this.drawDefaultBackground();
		drawBlockIcons();
		handleMouseClicks();
	}
	
	
	void handleMouseClicks() {
		ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
		int adjustedMouseX = Mouse.getX() * sr.getScaledWidth() / Minecraft.getMinecraft().displayWidth;
		int adjustedMouseY = sr.getScaledHeight() - Mouse.getY() * sr.getScaledHeight() / Minecraft.getMinecraft().displayHeight;
		
		//row/column in the puzzle
		int x = adjustedMouseX / 17;
		int y = adjustedMouseY / 17;
		
		if(!wasMouseClicked && Mouse.isButtonDown(0)) {//Mouse Down
			mouseDown(x, y);
		}else if(wasMouseClicked && !Mouse.isButtonDown(0)) { //Mouse Up
			mouseUp(x,y);
		}
		
		wasMouseClicked = Mouse.isButtonDown(0);
	}
	
	//x and y in puzzle coords
	void mouseDown(int x, int y) {
		if(Util.inRange(x, 0, game.board.tiles.length - 1) && 
		   Util.inRange(y, 0, game.board.tiles[0].length - 1)) {
				game.mouseDown(x, y);
		}
	}

	//x and y in puzzle coords
	void mouseUp(int x, int y) {
		game.mouseUp(x, y);
	}
	
	
	void drawBlockIcons() {
		ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
		int adjustedMouseX = Mouse.getX() * sr.getScaledWidth() / Minecraft.getMinecraft().displayWidth;
		int adjustedMouseY = sr.getScaledHeight() - Mouse.getY() * sr.getScaledHeight() / Minecraft.getMinecraft().displayHeight;
		
		TextureManager tm = Minecraft.getMinecraft().getTextureManager();
		
		int mouseTileX = adjustedMouseX / 17;
		int mouseTileY = adjustedMouseY / 17;
		
		//main blocks
		for(int x = 0; x < game.board.tiles[0].length; ++x) {
			for(int y = 0; y < game.board.tiles.length; ++y) {
				boolean skewed = false;
				GameTile tile = game.board.tiles[x][y];
				
				if(game.draggedTile != null) {
					if(game.draggedTile == tile)
						continue;
					
					if(mouseTileY == y && mouseTileX == x) {
						continue;
					}
				}
				
				tm.bindTexture(tile.block.icon);
				drawModalRectWithCustomSizedTexture(x * 17, y * 17, 0, 0, 16, 16, 16, 16);
				
				if(tile.tileItem != null) {
					tm.bindTexture(tile.tileItem.icon);
				}
				drawModalRectWithCustomSizedTexture(x * 17, y * 17, 0, 0, 16, 16, 16, 16);
			}
		}
		
		if(game.draggedTile != null) {
			tm.bindTexture(game.draggedTile.block.icon);
			drawModalRectWithCustomSizedTexture(-game.dragOffsetX + adjustedMouseX, -game.dragOffsetY + adjustedMouseY, 0, 0, 16, 16, 16, 16);
		
			if(!Util.inRange(mouseTileX, 0, game.board.tiles.length - 1) || 
					   !Util.inRange(mouseTileY, 0, game.board.tiles[0].length - 1)) {
						return;
			}
			
			if(game.board.tileAt(mouseTileY, mouseTileY).tileItem == null &&( mouseTileX != game.draggedTile.x || mouseTileY != game.draggedTile.y)) {
				tm.bindTexture(game.board.tileAt(mouseTileX, mouseTileY).block.icon);
				drawModalRectWithCustomSizedTexture(game.draggedTile.x * 17, game.draggedTile.y  * 17, 0, 0, 16, 16, 16, 16);
			}
		}
		
		//highlight main selected tile
		if(game.selectedTile != null) {
			drawRect(game.selectedTile.x*17, game.selectedTile.y*17, game.selectedTile.x*17 + 16, game.selectedTile.y*17 + 16, 0xA0FFFF00);
			drawRect(0,0,0,0, 0xFFFFFFFF);
		}
		
		//highlight moveable tiles
		if(game.breakableTiles != null) {
			for(GameTile gt : game.breakableTiles) {
				drawRect(gt.x*17, gt.y*17, gt.x*17 + 16, gt.y*17 + 16, 0x80ff9900);
				drawRect(0,0,0,0, 0xFFFFFFFF);
			}
		}
		
	}
	public boolean doesGuiPauseGame() {
		return false;
	}
	
	 
}
