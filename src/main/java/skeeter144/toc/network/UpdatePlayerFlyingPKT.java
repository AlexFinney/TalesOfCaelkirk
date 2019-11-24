package skeeter144.toc.network;

import java.util.function.Supplier;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;
import skeeter144.toc.entity.mob.mount.flying.EntityAbstractFlyingMount;

public class UpdatePlayerFlyingPKT {

	boolean isFlyingUp = false;
	boolean isFlyingDown = false;
	public UpdatePlayerFlyingPKT() {}
	public UpdatePlayerFlyingPKT(boolean flyingUp, boolean flyingDown) {
		this.isFlyingUp = flyingUp;
		this.isFlyingDown = flyingDown;
	}
	
	public static void encode(UpdatePlayerFlyingPKT pkt, PacketBuffer buf) {
		buf.writeBoolean(pkt.isFlyingUp);
		buf.writeBoolean(pkt.isFlyingDown);
	}
	
	public static UpdatePlayerFlyingPKT decode(PacketBuffer buf) {
		UpdatePlayerFlyingPKT pkt = new UpdatePlayerFlyingPKT();
		pkt.isFlyingUp = buf.readBoolean();
		pkt.isFlyingDown = buf.readBoolean();
		return pkt;
	}
	public static class Handler
	{
		public static void handle(final UpdatePlayerFlyingPKT message, Supplier<NetworkEvent.Context> ctx){
			ctx.get().enqueueWork(new Runnable() 
					{
						public void run() 
						{
							PlayerEntity player = ctx.get().getSender();
							if(player.getRidingEntity() == null || !(player.getRidingEntity()instanceof EntityAbstractFlyingMount))
								return;
							
							EntityAbstractFlyingMount ep = ((EntityAbstractFlyingMount)player.getRidingEntity());
							
							double speed = 0;
							if(message.isFlyingUp && message.isFlyingDown) {
								return;
							}else if(message.isFlyingUp && !message.isFlyingDown) {
								ep.setIsFlying(true);
								ep.setNoGravity(true);
								ep.setMotion(ep.getMotion().x, -.5, ep.getMotion().z);
								speed = ep.getAttribute(SharedMonsterAttributes.FLYING_SPEED).getBaseValue();
							}else if(!message.isFlyingUp && message.isFlyingDown) {
								ep.setMotion(ep.getMotion().x, -.5, ep.getMotion().z);
							}else if(!message.isFlyingUp && !message.isFlyingDown) {
								ep.setMotion(ep.getMotion().x, 0, ep.getMotion().z);
							}
							
							if(!ep.isAirBorne) {
								speed = ep.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getBaseValue();
							}
							
							ep.setAIMoveSpeed((float) speed);
						}
					});
		}
	}
	
	
//	
//	public void fromBytes(ByteBuf buf) {
//		
//	}
//
//	public void toBytes(ByteBuf buf) {
//	
//	}
//	
//	
//	public static class UpdatePlayerFlyingMessageHandlerHandler<UpdatePlayerFlyingMessage, IMessage>
//	{
//		public IMessage onMessage(UpdatePlayerFlyingMessage message, MessageContext ctx) 
//		{
//			
//		
//			return null;
//		}
//		
//	}

}
