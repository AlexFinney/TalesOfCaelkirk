package skeeter144.toc.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import skeeter144.toc.TOCMain;
import skeeter144.toc.client.gui.HUD;
import skeeter144.toc.tasks.TickableTask;

public class AdjustPlayersScreenDim implements IMessage{

	float pct;
	public AdjustPlayersScreenDim() {}
	public AdjustPlayersScreenDim(float pct) {this.pct = pct;}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		pct = buf.readFloat();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeFloat(pct);
	}

	public static class AdjustPlayersScreenDimHandler implements IMessageHandler<AdjustPlayersScreenDim, IMessage>{
		public IMessage onMessage(AdjustPlayersScreenDim message, MessageContext ctx) {
			float startingLight = HUD.lightBlockedPct;
			float endingLight = message.pct;
			Minecraft.getInstance().addScheduledTask(new Runnable() {
				public void run() {
					TOCMain.clientTaskManager.addTask(new TickableTask(40) {
						public void tick(int worldTick) {
							float delta = worldTick - start;
							float fadeTime = 40;
							
							float light = startingLight + (endingLight - startingLight) * delta / fadeTime;
							HUD.lightBlockedPct = light;
						}
					});
				}
			});
			return null;
		}
		
	}
	
}
