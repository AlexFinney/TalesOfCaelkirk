package skeeter144.toc.entity.mob.passive.banker;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import skeeter144.toc.banking.BankManager;
import skeeter144.toc.entity.mob.passive.EntityNpc;

public class EntityBanker extends EntityNpc {

	public EntityBanker(World worldIn) {
		super(worldIn);
		
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(20);
		this.setHealth(50f);
	}
	
	@Override
	protected boolean processInteract(EntityPlayer player, EnumHand hand) {
		if(player.world.isRemote) {
			return true;
		}
		
		player.displayGUIChest(BankManager.getPlayerInventory(player, this.getClass()));
		return true;
	}

}
