package skeeter144.toc.client.gui;

import net.minecraft.client.MainWindow;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.text.StringTextComponent;
import skeeter144.toc.minigames.MiningMinigame_Old;
import skeeter144.toc.minigames.MiningMinigame_Old.GameTile;
import skeeter144.toc.util.Mouse;
import skeeter144.toc.util.Util;

public class MiningGameGui extends Screen{
	MiningMinigame_Old game = null;
	boolean wasMouseClicked = false;
	public MiningGameGui() {
		super(new StringTextComponent(""));
		game = new MiningMinigame_Old(10, 10);
	}
	
	
	boolean mouseClicked = false;
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		this.renderBackground();
		drawBlockIcons();
		handleMouseClicks();
	}
	
	
	void handleMouseClicks() {
		MainWindow mw = Minecraft.getInstance().mainWindow;
		int adjustedMouseX = Mouse.getX() * mw.getScaledWidth() / mw.getWidth();
		int adjustedMouseY = (int) (mw.getScaledHeight() - Minecraft.getInstance().mouseHelper.getMouseY() * mw.getScaledHeight() / Minecraft.getInstance().mainWindow.getHeight());
		
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
		MainWindow sr = Minecraft.getInstance().mainWindow;
		int adjustedMouseX = Mouse.getX() * sr.getScaledWidth() / sr.getWidth();
		int adjustedMouseY = sr.getScaledHeight() - Mouse.getY() * sr.getScaledHeight() / sr.getHeight();
		
		TextureManager tm = Minecraft.getInstance().getTextureManager();
		
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
				blit(x * 17, y * 17, 0, 0, 16, 16, 16, 16);
				
				if(tile.tileItem != null) {
					tm.bindTexture(tile.tileItem.icon);
				}
				blit(x * 17, y * 17, 0, 0, 16, 16, 16, 16);
			}
		}
		
		if(game.draggedTile != null) {
			tm.bindTexture(game.draggedTile.block.icon);
			blit(-game.dragOffsetX + adjustedMouseX, -game.dragOffsetY + adjustedMouseY, 0, 0, 16, 16, 16, 16);
		
			if(!Util.inRange(mouseTileX, 0, game.board.tiles.length - 1) || 
					   !Util.inRange(mouseTileY, 0, game.board.tiles[0].length - 1)) {
						return;
			}
			
			if(game.board.tileAt(mouseTileY, mouseTileY).tileItem == null &&( mouseTileX != game.draggedTile.x || mouseTileY != game.draggedTile.y)) {
				tm.bindTexture(game.board.tileAt(mouseTileX, mouseTileY).block.icon);
				blit(game.draggedTile.x * 17, game.draggedTile.y  * 17, 0, 0, 16, 16, 16, 16);
			}
		}
		
		//highlight main selected tile
		if(game.selectedTile != null) {
			fill(game.selectedTile.x*17, game.selectedTile.y*17, game.selectedTile.x*17 + 16, game.selectedTile.y*17 + 16, 0xA0FFFF00);
			fill(0,0,0,0, 0xFFFFFFFF);
		}
		
		//highlight moveable tiles
		if(game.breakableTiles != null) {
			for(GameTile gt : game.breakableTiles) {
				fill(gt.x*17, gt.y*17, gt.x*17 + 16, gt.y*17 + 16, 0x80ff9900);
				fill(0,0,0,0, 0xFFFFFFFF);
			}
		}
		
	}
	public boolean doesGuiPauseGame() {
		return false;
	}
	
	 
}
