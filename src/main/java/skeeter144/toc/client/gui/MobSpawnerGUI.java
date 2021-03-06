package skeeter144.toc.client.gui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import skeeter144.toc.entity.tile.TileEntityMobSpawner;
import skeeter144.toc.network.Network;
import skeeter144.toc.network.SetMobSpawnerSettingsMessage;

public class MobSpawnerGUI extends GuiScreen{
	
	TileEntityMobSpawner spawner;
	
	int mobIndex;
	int spawnRadius;
	int spawnsPerMin;
	int mobSpawnLimit;
	int spawnSearchRadius;
	int minMobsPerSpawn;
	int maxMobsPersSpawn;
	
	List<EntityEntry> mobList = new ArrayList<EntityEntry>();
	
	public MobSpawnerGUI(TileEntityMobSpawner spawner) {
		this.spawner = spawner;
		
		this.mobIndex = 10;
		this.spawnRadius = spawner.spawn_radius;
		this.spawnsPerMin = spawner.avg_spawns_per_min;
		this.mobSpawnLimit = spawner.mob_spawn_limit;
		this.spawnSearchRadius = spawner.mob_spawn_search_radius;
		this.minMobsPerSpawn = spawner.mobs_per_spawn_min;
		this.maxMobsPersSpawn = spawner.mobs_per_spawn_max;
		
		int i = 0;
		for(EntityEntry entry :ForgeRegistries.ENTITIES) {
			if(entry.getEgg() != null) {
				mobList.add(entry);
				if(entry.getEntityClass().getName().equals(spawner.mob_name)) {
					mobIndex = i;
				}
				++i;
			}
		}
		
	} 
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		this.drawDefaultBackground();
	    super.drawScreen(mouseX, mouseY, partialTicks);
	   
	    drawBackground();
	}
	
	boolean wasMouseClicked = false;
	static int bookWidth, bookHeight, bookX, bookY;
	private void drawBackground() {
		
		TextureManager tm = Minecraft.getMinecraft().getTextureManager();
		ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
		
		this.buttonList.clear();
		
		this.drawString(this.fontRenderer, "Mob Type: " + mobList.get(mobIndex).getName(), 65, 5, 0XFFFFFF);
		this.drawString(this.fontRenderer, "Spawn Radius: " + spawnRadius, 65, 25, 0XFFFFFF);
		this.drawString(this.fontRenderer, "Average Spawns/Min: " + spawnsPerMin, 65, 45, 0XFFFFFF);
		this.drawString(this.fontRenderer, "Mob Spawn Limit: " + mobSpawnLimit, 65, 65, 0XFFFFFF);
		this.drawString(this.fontRenderer, "Mob Spawn Search Radius: " + spawnSearchRadius, 65, 85, 0XFFFFFF);
		this.drawString(this.fontRenderer, "Mobs Per Spawn Min: " + minMobsPerSpawn, 65, 105, 0XFFFFFF);
		this.drawString(this.fontRenderer, "Mobs Per Spawn Max: " + maxMobsPersSpawn, 65, 125, 0XFFFFFF);
		
		int buttonId = 0;
		for(int i = 0; i < 7; ++i) {
			GuiButton b = new GuiButton(buttonId, 0, i * 20, "<-");
			b.setWidth(30);
			
			GuiButton b2 = new GuiButton(buttonId + 1, 30, i * 20, "->");
			b2.setWidth(30);
			
			this.addButton(b);
			this.addButton(b2);
			
			buttonId += 2;
		}
		
		GuiButton b = new GuiButton(buttonId, 30, 160, "Save Spawner Settings");
		this.addButton(b);
	}
	
	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		super.mouseClicked(mouseX, mouseY, mouseButton);

		if(this.selectedButton == null)
			return;
		
		switch(this.selectedButton.id) {
		case 0:
			--mobIndex;
			break;
		case 1:
			++mobIndex;
			break;
		case 2:
			--spawnRadius;
			break;
		case 3:
			++spawnRadius;
			break;
		case 4:
			--spawnsPerMin;
			break;
		case 5:
			++spawnsPerMin;
			break;
		case 6:
			--mobSpawnLimit;
			break;
		case 7:
			++mobSpawnLimit;
			break;
		case 8:
			--spawnSearchRadius;
			break;
		case 9:
			++spawnSearchRadius;
			break;
		case 10:
			--minMobsPerSpawn;
			break;
		case 11:
			if(minMobsPerSpawn < maxMobsPersSpawn) {
				++minMobsPerSpawn;
			}
			break;
		case 12:
			--maxMobsPersSpawn;
			break;
		case 13:
			++maxMobsPersSpawn;
			break;
		case 14:
			Network.INSTANCE.sendToServer(new SetMobSpawnerSettingsMessage(
								    mobList.get(mobIndex).getEntityClass().getName(),
									spawnRadius,
									spawnsPerMin,
									mobSpawnLimit,
									spawnSearchRadius,
									minMobsPerSpawn,
									maxMobsPersSpawn, 
									spawner.getPos().getX(), 
									spawner.getPos().getY(), 
									spawner.getPos().getZ()));
			break;
		}
		
		if(mobIndex < 0)
			mobIndex = mobList.size() - 1;
		if(mobIndex == mobList.size())
			mobIndex = 0;
		if(spawnRadius < 3)
			spawnRadius = 3;
		if(spawnsPerMin < 1)
			spawnsPerMin = 1;
		if(spawnsPerMin > 10)
			spawnsPerMin = 10;
		if(minMobsPerSpawn < 1)
			minMobsPerSpawn = 1;
		if(maxMobsPersSpawn > 10)
			maxMobsPersSpawn = 10;
		if(spawnSearchRadius < 3)
			spawnSearchRadius = 3;
		if(spawnSearchRadius > 30)
			spawnSearchRadius = 30;
		if(mobSpawnLimit < 1)
			mobSpawnLimit = 1;
		if(mobSpawnLimit > 50)
			mobSpawnLimit = 50;
	}
	
	@Override
	public boolean doesGuiPauseGame() {
	    return false;
	}
}
