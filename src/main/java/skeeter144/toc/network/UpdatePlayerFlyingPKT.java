package skeeter144.toc.network;

import java.util.function.Supplier;

import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

public class UpdatePlayerFlyingPKT {

	public static void encode(UpdatePlayerFlyingPKT pkt, PacketBuffer buf) {}
	public static UpdatePlayerFlyingPKT decode(PacketBuffer buf) {return null;}
	public static class Handler
	{
		public static void handle(final UpdatePlayerFlyingPKT message, Supplier<NetworkEvent.Context> ctx){}
	}
	
	boolean isFlyingUp = false;
	boolean isFlyingDown = false;
	public UpdatePlayerFlyingPKT() {}
	public UpdatePlayerFlyingPKT(boolean flyingUp, boolean flyingDown) {
		this.isFlyingUp = flyingUp;
		this.isFlyingDown = flyingDown;
	}
//	
//	public void fromBytes(ByteBuf buf) {
//		this.isFlyingUp = buf.readBoolean();
//		this.isFlyingDown = buf.readBoolean();
//	}
//
//	public void toBytes(ByteBuf buf) {
//		buf.writeBoolean(isFlyingUp);
//		buf.writeBoolean(isFlyingDown);
//	}
//	
//	
//	public static class UpdatePlayerFlyingMessageHandlerHandler<UpdatePlayerFlyingMessage, IMessage>
//	{
//		public IMessage onMessage(UpdatePlayerFlyingMessage message, MessageContext ctx) 
//		{
//			
//			ctx.getServerHandler().player.getServerWorld().addScheduledTask(new Runnable() 
//			{
//				public void run() 
//				{
//					EntityPlayer player = ctx.getServerHandler().player;
//					if(player.getRidingEntity() == null || !(player.getRidingEntity()instanceof EntityAbstractFlyingMount))
//						return;
//					
//					EntityAbstractFlyingMount ep = ((EntityAbstractFlyingMount)ctx.getServerHandler().player.getRidingEntity());
//					
//					double speed = 0;
//					if(message.isFlyingUp && message.isFlyingDown) {
//						return;
//					}else if(message.isFlyingUp && !message.isFlyingDown) {
//						ep.setIsFlying(true);
//						ep.setNoGravity(true);
//						ep.motionY = .5;
//						speed = ep.getAttribute(SharedMonsterAttributes.FLYING_SPEED).getBaseValue();
//					}else if(!message.isFlyingUp && message.isFlyingDown) {
//						ep.motionY = -.5;
//					}else if(!message.isFlyingUp && !message.isFlyingDown) {
//						ep.motionY = 0;
//					}
//					
//					if(!ep.isAirBorne) {
//						speed = ep.getAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getBaseValue();
//					}
//					
//					ep.setAIMoveSpeed((float) speed);
//				}
//			});
//			return null;
//		}
//		
//	}

}
