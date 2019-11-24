package skeeter144.toc.entityeffect.effects;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.ServerPlayerEntity;
import skeeter144.toc.network.Network;
import skeeter144.toc.network.NotfyClientOfEffectPKT;

public abstract class EntityEffect {

	protected boolean purgeable;
	protected String iconName;
	protected String effectName;

	protected Entity effecter;
	protected Entity effected;
	
	protected int ticksRemaining;
	protected boolean active;
	protected int id;
	
	public EntityEffect(Entity effected, String name, int duration)
	{
		this.effected = effected;
		this.effectName = name;
		this.ticksRemaining = duration;
	}
	
	//TODO: make this take a name too
	public EntityEffect(Entity effected, Entity effecter, int duration)
	{
		this.effecter = effecter;
		this.effected = effected;
		this.effectName = "";
		this.ticksRemaining = duration;
	}
	
	
	public void start()
	{
		active  = true;
		onEffectStart();
		if(effected instanceof ServerPlayerEntity) {
			Network.INSTANCE.sendTo(new NotfyClientOfEffectPKT(effectName, true), (ServerPlayerEntity)effected);
		}
	}
	
	public void end(EffectEndType type) {
		if(effected instanceof ServerPlayerEntity) {
			Network.INSTANCE.sendTo(new NotfyClientOfEffectPKT(effectName, false), (ServerPlayerEntity)effected);
		}
		onEffectEnd(type);
	}
	
	public void tick()
	{
		if(active && --ticksRemaining <= 0) {
			active = false;
			end(EffectEndType.NORMAL);
			return;
		}
		
		onEffectTick();
	}
	
	protected abstract void onEffectStart();
	protected abstract void onEffectEnd(EffectEndType type);
	protected abstract void onEffectTick();
	
	public int getTicksRemaining()
	{
		return ticksRemaining;
	}
	
	public boolean isActive()
	{
		return active;
	}
	
	public void cancel()
	{
		ticksRemaining = 0;
		end(EffectEndType.CANCELLED);
	}
	
	public boolean isPurgeable()
	{
		return purgeable;
	}
	
	public void purge()
	{
		if(purgeable)
		{
			ticksRemaining = 0;
			end(EffectEndType.PURGED);
		}
			
	}
	
	public String getEffectName()
	{
		return effectName;
	}
	
	public enum EffectEndType{
		CANCELLED,
		PURGED,
		NORMAL
	}
	
	public int getId() {
		return id;
	}
}


