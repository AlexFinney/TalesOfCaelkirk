package skeeter144.toc.minigames;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.lwjgl.input.Mouse;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.ResourceLocation;
import skeeter144.toc.util.Reference;
import skeeter144.toc.util.Util;

public class MiningMinigame_Old {
	private final static OreBlock DIRT       = new OreBlock(new ResourceLocation(Reference.MODID, "textures/minigames/mining/dirt.png"));
	private final static OreBlock STONE      = new OreBlock(new ResourceLocation(Reference.MODID, "textures/minigames/mining/stone.png"));
	private final static OreBlock IRON       = new OreBlock(new ResourceLocation(Reference.MODID, "textures/minigames/mining/iron_ore.png"));
	private final static OreBlock GOLD       = new OreBlock(new ResourceLocation(Reference.MODID, "textures/minigames/mining/gold_ore.png"));
	private final static OreBlock DIAMOND    = new OreBlock(new ResourceLocation(Reference.MODID, "textures/minigames/mining/diamond_ore.png"));
	private final static OreBlock AIR        = new OreBlock(new ResourceLocation(Reference.MODID, "textures/minigames/mining/air.png"));
	private final static OreBlock TNT        = new OreBlock(new ResourceLocation(Reference.MODID, "textures/minigames/mining/tnt.png"));
	
	public GameTile selectedTile = null;
	public GameTile draggedTile = null;
	public int dragOffsetX, dragOffsetY;
	public List<GameTile> breakableTiles;
	public GameBoard board = null;
	int swapsLeft = 10;
	
	public MiningMinigame_Old(int sizeX, int sizeY) {
		board = new GameBoard(sizeX, sizeY);
		breakableTiles = new ArrayList<GameTile>();
	}
	
	
	public void mouseDown(int x, int y) {
		//board.tiles[5][5] = new GameTile(AIR, 5, 5);
		
		if(board.tileAt(x, y).block == AIR)
			return;
		
		if(board.tileAt(x, y).tileItem == null) {
			ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
			
			int adjustedMouseX = Mouse.getX() * sr.getScaledWidth() / Minecraft.getMinecraft().displayWidth;
			int adjustedMouseY = sr.getScaledHeight() - Mouse.getY() * sr.getScaledHeight() / Minecraft.getMinecraft().displayHeight;
			
			draggedTile = board.tileAt(x, y);
			dragOffsetX = adjustedMouseX - x * 17;
			dragOffsetY = adjustedMouseY - y * 17;
		}
	}
	
	public void mouseUp(int x, int y) {
		if(!Util.inRange(x, 0, board.tiles.length - 1) || 
		   !Util.inRange(y, 0, board.tiles[0].length - 1)) {
				draggedTile = null;
				return;
		}
		
		if(draggedTile != null) {
			board.swapTiles(board.tileAt(x, y), board.tileAt(draggedTile.x, draggedTile.y));
			draggedTile = null;
			return;
		}
		
		GameTile t = board.tileAt(x, y);
		if(selectedTile == t) {//if this is the selected tile, unselect it
			setSelectedTile(0, 0, null);
		}else if(breakableTiles.contains(t)) {
			GameTile temp = board.tiles[selectedTile.x][selectedTile.y];
			board.setTileAt(selectedTile.x, selectedTile.y, null);
			board.setTileAt(x, y, null);
			
			setSelectedTile(0, 0, null);
		}else {//select a tile if one is not chosen
			if(selectedTile != null)
				setSelectedTile(0, 0, null);
			else
				setSelectedTile(x, y, t);
		}
	}
	
	
	public void gameUpdate() {
	}
	
	public void setSelectedTile(int x, int y, GameTile t) {
		this.selectedTile = t;
		updateMoveableTiles();
	}
	
	void updateMoveableTiles() {
		breakableTiles.clear();
		
		if(selectedTile != null)
			breakableTiles = selectedTile.getMoveableTiles(this);
	}
	
	public static class GameBoard{
		public GameTile[][] tiles = null;
		public int score = 0;
		public GameBoard(int sizeX, int sizeY) {
			tiles = new GameTile[sizeX][sizeY];
			Random rand = Minecraft.getMinecraft().world.rand;
			
			//generate tiles
			for(int i = 0; i < sizeX; ++i) {
				for(int j = 0; j < sizeY; ++j) {
					generateTileAt(i, j);
				}
			}
		
			//place pickaxes
			for(int p = 0; p < 10; ++p) {
				int rX = rand.nextInt(sizeX);
				int rY = rand.nextInt(sizeY);
				
				if(tiles[rX][rY].tileItem != null) {
					--p;
					continue;
				}
				
				float pick_roll = rand.nextFloat();
				if(pick_roll < .1)
					tiles[rX][rY].tileItem = new BronzePick(new ResourceLocation(Reference.MODID, "textures/minigames/mining/wood_pickaxe.png"));
				else
					tiles[rX][rY].tileItem = new BronzePick(new ResourceLocation(Reference.MODID, "textures/minigames/mining/wood_pickaxe.png"));
			}
		}
	
		public GameTile tileAt(int x, int y) {
			return tiles[x][y];
		}
		
		public void setTileAt(int x, int y, GameTile t){
			if(t != null) {
				t.x = x;
				t.y = y;
				tiles[x][y] = t;
				return;
			}
			
			for (int j = y; j < tiles[0].length - 1; ++j) {
				tiles[x][y] = tiles[x][y + 1];
			}
			this.generateTileAt(x, tiles[0].length - 1);
		}
		
		void swapTiles(GameTile a, GameTile b) {
			GameTile temp = new GameTile(a.block, a.x, a.y);	
			a.x = b.x;
			a.y = b.y;
			
			b.x = temp.x;
			b.y = temp.y;
			
			tiles[a.x][a.y] = a;
			tiles[b.x][b.y] = b;
		}
		
		void generateTileAt(int i, int j) {
			Random rand = Minecraft.getMinecraft().world.rand;
			
			OreBlock block = DIRT;
			float roll = rand.nextFloat();
			if (Util.inRange(j, 0, 1)) {
			
				if (Util.inRange(roll, 0f, .6f))				block = DIRT;
				else if (Util.inRange(roll, .6f, 1f))			block = STONE;
		
			} else if (Util.inRange(j, 2, 4)) {
				
				if (Util.inRange(roll, 0f, .4f))				block = DIRT;
				else if (Util.inRange(roll, .4f, .95f))			block = STONE;
				else if (Util.inRange(roll, .95f, 1f))			block = IRON;
			
			} else if (Util.inRange(j, 5, 6)) {
				
				if (Util.inRange(roll, 0f, .2f))				block = DIRT;
				else if (Util.inRange(roll, .2f, .9f))			block = STONE;
				else if (Util.inRange(roll, .9f, .98f))			block = IRON;
				else if (Util.inRange(roll, .98f, 1f))			block = GOLD;
			
			} 
			else {
				
				if (Util.inRange(roll, 0f, .1f))				block = DIRT;
				else if (Util.inRange(roll, .1, .9f))			block = STONE;
				else if (Util.inRange(roll, .9f, .95f))			block = IRON;
				else if (Util.inRange(roll, .95f, .98f))		block = GOLD;
				else if (Util.inRange(roll, .98f, 1f))			block = DIAMOND;
			
			} 
			this.tiles[i][j] = new GameTile(block, i, j);
		}
	}
	
	public static class GameTile{
		public OreBlock block;
		public TileItem tileItem = null;
		public int x, y;
		public GameTile(OreBlock block, int x, int y) {
			this.x = x;
			this.y = y;
			this.block = block;
		}
		
		public GameTile(OreBlock block, int x, int y, TileItem item) {
			this(block, x, y);
			this.tileItem = item;
		}
		
		public void tileClicked(MiningMinigame_Old game) {
			
		}
		
		public List<GameTile> getMoveableTiles(MiningMinigame_Old game){
			List<GameTile> moveableTiles = new ArrayList<GameTile>();
			
			if(this.tileItem == null) {
				for(int i = -1; i <= 1; ++i) {
					for(int j = -1; j <= 1; ++j) {
						if(i == 0 && j == 0)
							continue;
		
						if(Util.inRange(x+i, 0, game.board.tiles.length - 1) && Util.inRange(y+j, 0, game.board.tiles[0].length - 1))
							moveableTiles.add(game.board.tiles[x+i][y+j]);
					}
				}
			}else {
				return tileItem.getMoveableTiles(game.board, this);
			}
			
			return moveableTiles;
		}
	}
	
	public static abstract class GameObject{
		public final ResourceLocation icon;
		public GameObject(ResourceLocation rl) {
			this.icon = rl;
		}
	}
	
	public static class OreBlock extends GameObject{
	
		public OreBlock(ResourceLocation icon) {
			super(icon);
		}
		
		public OreBlock(OreBlock rhs) {
			super(rhs.icon);
		}
	}
	
	public static abstract class TileItem extends GameObject{
		public TileItem(ResourceLocation rl) {
			super(rl);
		}
		
		public abstract void itemClicked(int itemX, int itemY, MiningMinigame_Old board);
		public abstract List<GameTile> getMoveableTiles(GameBoard board, GameTile owner);
	}
	
	public static class BronzePick extends TileItem{
		public BronzePick(ResourceLocation icon) {
			super(icon);
		}

		@Override
		public void itemClicked(int itemX, int itemY, MiningMinigame_Old board) {
			
		}

		@Override
		public List<GameTile> getMoveableTiles(GameBoard board, GameTile owner) {
			ArrayList<GameTile> list = new ArrayList<GameTile>();
			int range = 5;
			for(int i = -range; i <= range; ++i) {
				for (int j = -range; j <= range; ++j) {
					if (i == 0 && j == 0 || i != j)
						continue;
					if (Util.inRange(owner.x + i, 0, board.tiles.length - 1)
							&& Util.inRange(owner.y + j, 0, board.tiles[0].length - 1))
						list.add(board.tiles[owner.x + i][owner.y + j]);

				}

				for(int j = -range; j <= range; ++j) {
					if(i == 0 && j == 0 || i != -j) 
						continue;
					
					if(Util.inRange(owner.x + i, 0, board.tiles.length - 1) &&
							   Util.inRange(owner.y + j, 0, board.tiles[0].length - 1))
									list.add(board.tiles[owner.x+i][owner.y+j]);
				}
			}
			return list;
		}
	}
	
}
