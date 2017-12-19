package skeeter144.toc.entity.mob.passive.shopkeeper;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import skeeter144.toc.Reference;
import skeeter144.toc.entity.mob.passive.EntityShopKeeper;
import skeeter144.toc.network.Network;
import skeeter144.toc.network.OpenShopGuiMessage;

public class EntityHumanShopKeeper extends EntityShopKeeper {

	public EntityHumanShopKeeper(World world) {
		super(world, "general_store");
		if(texture == null)
			texture = new ResourceLocation("toc:textures/entity/sam_derric.png");
	}
	
	public EntityHumanShopKeeper(World worldIn, String shopFileName, String textureName) {
		super(worldIn, shopFileName);
		if(texture == null)
			texture = new ResourceLocation(Reference.MODID, "textures/entity/" + textureName + ".png");
	}

	@Override
	protected boolean processInteract(EntityPlayer player, EnumHand hand) {
		if(player.world.isRemote) {
			return true;
		}
		
		
		Network.INSTANCE.sendTo(new OpenShopGuiMessage(this.shopData), (EntityPlayerMP)player);
		
		
		return super.processInteract(player, hand);
	}
	
}
