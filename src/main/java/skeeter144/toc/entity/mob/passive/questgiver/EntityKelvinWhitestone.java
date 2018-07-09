package skeeter144.toc.entity.mob.passive.questgiver;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import skeeter144.toc.items.TOCItems;
import skeeter144.toc.quest.QuestManager;

public class EntityKelvinWhitestone extends EntityNPCInteractable{
	
	public EntityKelvinWhitestone(World worldIn) {
		super(worldIn, QuestManager.aNewAdventure);
		this.setSize(.75f, 2f);
		if(texture == null)
			texture = new ResourceLocation("toc:textures/entity/kelvin_whitestone.png");
		
		this.setHeldItem(EnumHand.MAIN_HAND, new ItemStack(TOCItems.steel_pickaxe));
	}
	
	@Override
	protected boolean processInteract(EntityPlayer player, EnumHand hand) {
		if(player.world.isRemote) {
			return true;
		}
		return true;
	}
	
}
