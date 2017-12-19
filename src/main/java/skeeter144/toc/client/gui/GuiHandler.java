package skeeter144.toc.client.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler {
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		if(ID == Guis.SPELL_BOOK_GUI) 
			return new SpellBookGUI();
		else if(ID == Guis.LEVELS_GUI) 
			return new LevelsGui();
		else if(ID == Guis.SMELTING_GUI)
			return new SmeltingGui();
		else if(ID == Guis.SMITHING_GUI)
			return new SmithingGui(new BlockPos(x,y,z));
		return null;
	}
}
