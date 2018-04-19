package skeeter144.toc.entity.mob.passive.banker;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import skeeter144.toc.banking.BankInventory;
import skeeter144.toc.banking.BankManager;
import skeeter144.toc.entity.mob.passive.EntityNpc;
import skeeter144.toc.util.Reference;

public class EntityBanker extends EntityNpc {

	public EntityBanker(World worldIn) {
		super(worldIn);
		
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(20);
		this.setHealth(50f);
		
		this.texture = new ResourceLocation(Reference.MODID, "textures/entity/sam_derric.png");
	}
	
	@Override
	protected boolean processInteract(EntityPlayer player, EnumHand hand) {
		if(player.world.isRemote) {
			return true;
		}
		List<ItemStack> stacks = new ArrayList<ItemStack>();
		BankInventory inventory = BankManager.getPlayerInventory(player);
		for(int i = 0; i < inventory.getSizeInventory(); ++i) {
			stacks.add(inventory.getStackInSlot(i));
		}
		
		((EntityPlayerMP)player).displayGUIChest(inventory);
		
		return true;
	}

}
