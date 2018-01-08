package skeeter144.toc.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import skeeter144.toc.entity.mob.mount.flying.EntityAbstractFlyingMount;

public class UpdatePlayerFlyingMessage implements IMessage {

	boolean isFlyingUp = false;
	boolean isFlyingDown = false;
	public UpdatePlayerFlyingMessage() {}
	public UpdatePlayerFlyingMessage(boolean flyingUp, boolean flyingDown) {
		this.isFlyingUp = flyingUp;
		this.isFlyingDown = flyingDown;
	}
	
	public void fromBytes(ByteBuf buf) {
		this.isFlyingUp = buf.readBoolean();
		this.isFlyingDown = buf.readBoolean();
	}

	public void toBytes(ByteBuf buf) {
		buf.writeBoolean(isFlyingUp);
		buf.writeBoolean(isFlyingDown);
	}
	
	
	public static class UpdatePlayerFlyingMessageHandler implements IMessageHandler<UpdatePlayerFlyingMessage, IMessage>
	{
		public IMessage onMessage(UpdatePlayerFlyingMessage message, MessageContext ctx) 
		{
			
			ctx.getServerHandler().player.getServerWorld().addScheduledTask(new Runnable() 
			{
				public void run() 
				{
					EntityPlayer player = ctx.getServerHandler().player;
					if(player.getRidingEntity() == null || !(player.getRidingEntity()instanceof EntityAbstractFlyingMount))
						return;
					
					EntityAbstractFlyingMount ep = ((EntityAbstractFlyingMount)ctx.getServerHandler().player.getRidingEntity());
					
					double speed = 0;
					if(message.isFlyingUp && message.isFlyingDown) {
						return;
					}else if(message.isFlyingUp && !message.isFlyingDown) {
						ep.setIsFlying(true);
						ep.setNoGravity(true);
						ep.motionY = .5;
						speed = ep.getEntityAttribute(SharedMonsterAttributes.FLYING_SPEED).getBaseValue();
					}else if(!message.isFlyingUp && message.isFlyingDown) {
						ep.motionY = -.5;
					}else if(!message.isFlyingUp && !message.isFlyingDown) {
						ep.motionY = 0;
					}
					
					if(!ep.isAirBorne) {
						speed = ep.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getBaseValue();
					}
					
					ep.setAIMoveSpeed((float) speed);
				}
			});
			return null;
		}
		
	}

}
