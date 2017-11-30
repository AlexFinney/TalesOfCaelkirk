package skeeter144.toc.network;

import java.util.ArrayList;
import java.util.List;

import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import skeeter144.toc.TOCMain;
import skeeter144.toc.magic.LightningBoltSpell;

public class LightningBoltCastMessage implements IMessage{

	public LightningBoltCastMessage() {}
	
	boolean didHitEntity; double x, y,  z;
	int hitId;
	public LightningBoltCastMessage(boolean hitEntity, double x, double y, double z, Entity hit) {
		this.didHitEntity = hitEntity;
		this.x = x;
		this.y = y;
		this.z = z;
		if(hitEntity)
			this.hitId = hit.getEntityId();
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		this.didHitEntity = buf.readBoolean();
		this.x = buf.readDouble();
		this.y = buf.readDouble();
		this.z = buf.readDouble();
		this.hitId = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeBoolean(didHitEntity);
		buf.writeDouble(x);
		buf.writeDouble(y);
		buf.writeDouble(z);
		buf.writeInt(hitId);
	}
	
	public static class LightningBoltCastHandler implements IMessageHandler<LightningBoltCastMessage, IMessage>{
		public IMessage onMessage(LightningBoltCastMessage message, MessageContext ctx) {
			ctx.getServerHandler().player.getServerWorld().addScheduledTask(new Runnable() {
				public void run() {
					Entity player = ctx.getServerHandler().player;
				}
			});
			
			
			return null;
		}
	
		
	}
	
}
