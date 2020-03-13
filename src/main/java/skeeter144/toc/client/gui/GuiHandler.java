package skeeter144.toc.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import skeeter144.toc.entity.tile.TileEntityMobSpawner;

public class GuiHandler /*implements IGuiHandler*/ {
	
	static GuiHandler instance;
	public static GuiHandler Instance() {
		if(instance == null) instance = new GuiHandler();

		return instance;
	}
	
	public Object getServerGuiElement(int ID, PlayerEntity player, World world, int x, int y, int z) {
		return null;
	}

	public Object getClientGuiElement(int ID, PlayerEntity player, World world, int x, int y, int z) {
		if(ID == Guis.SPELL_BOOK_GUI) 
			return new SpellBookGUI(new StringTextComponent("Spellbook"));
		else if(ID == Guis.LEVELS_GUI) 
			return new LevelsGui(new StringTextComponent("Levels"));
		else if(ID == Guis.SMELTING_GUI)
			return new SmeltingGui();
		else if(ID == Guis.SMITHING_GUI)
			return new SmithingGui(new BlockPos(x,y,z));
		else if(ID == Guis.MOB_SPAWNER_GUI)
			return new MobSpawnerGUI((TileEntityMobSpawner) world.getTileEntity(new BlockPos(x, y, z)));
		
		return null;
	}
}
