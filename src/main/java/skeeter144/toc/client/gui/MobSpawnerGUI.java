package skeeter144.toc.client.gui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.client.MainWindow;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.entity.EntityType;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.registries.ForgeRegistries;
import skeeter144.toc.entity.tile.TileEntityMobSpawner;
import skeeter144.toc.network.Network;
import skeeter144.toc.network.SetMobSpawnerSettingsPKT;

public class MobSpawnerGUI extends Screen{
	
	TileEntityMobSpawner spawner;
	
	int mobIndex;
	int spawnRadius;
	int spawnsPerMin;
	int mobSpawnLimit;
	int spawnSearchRadius;
	int minMobsPerSpawn;
	int maxMobsPersSpawn;
	
	Button mobIndexP, mobIndexM, spawnRadiusP, spawnRadiusM,
	spawnsPerMinP, spawnsPerMinM, mobSpawnLimitP, mobSpawnLimitM,
	spawnSearchRadiusP, spawnSearchRadiusM, minMobsPerSpawnP, 
	minMobsPerSpawnM, maxMobsPersSpawnP, maxMobsPersSpawnM;
	
	List<EntityType<?> > mobList = new ArrayList<EntityType<?> >();
	final Map<Button, Integer> buttons = new HashMap<Button, Integer>();
	
	public MobSpawnerGUI(TileEntityMobSpawner spawner) {
		super(new StringTextComponent("Mob Spawner"));
		this.spawner = spawner;
		
		this.mobIndex = 10;
		this.spawnRadius = spawner.spawn_radius;
		this.spawnsPerMin = spawner.avg_spawns_per_min;
		this.mobSpawnLimit = spawner.mob_spawn_limit;
		this.spawnSearchRadius = spawner.mob_spawn_search_radius;
		this.minMobsPerSpawn = spawner.mobs_per_spawn_min;
		this.maxMobsPersSpawn = spawner.mobs_per_spawn_max;
		
		int i = 0;
		for(EntityType<?> entry : ForgeRegistries.ENTITIES) {
			if(entry.isSummonable()) {
				mobList.add(entry);
				if(entry.getClass().getName().equals(spawner.mob_name)) {
					mobIndex = i;
				}
				++i;
			}
		}
		
	} 
	
	@Override
	protected void init() {
		super.init();
		super.buttons.clear();
		
		mobIndexP = new Button(30, 20, 30, 20, ">", new Button.IPressable() {
			public void onPress(Button btn) {
				++mobIndex; enforceSettingsBounds();
			}
		});
		mobIndexM = new Button(0, 20, 30, 20, "<", new Button.IPressable() {
			public void onPress(Button btn) {
				--mobIndex; enforceSettingsBounds();
			}
		});
		
		spawnRadiusP = new Button(30, 40, 30, 20, ">", new Button.IPressable() {
			public void onPress(Button btn) {
				++spawnRadius; enforceSettingsBounds();
			}
		});
		spawnRadiusM = new Button(0, 40, 30, 20, "<", new Button.IPressable() {
			public void onPress(Button btn) {
				--spawnRadius; enforceSettingsBounds();
			}
		});
		
		spawnsPerMinP = new Button(30, 60, 30, 20, ">", new Button.IPressable() {
			public void onPress(Button btn) {
				++spawnsPerMin; enforceSettingsBounds();
			}
		});
		spawnsPerMinM = new Button(0, 60, 30, 20, "<", new Button.IPressable() {
			public void onPress(Button btn) {
				--spawnsPerMin; enforceSettingsBounds();
			}
		});
		
		mobSpawnLimitP = new Button(30, 80, 30, 20, ">", new Button.IPressable() {
			public void onPress(Button btn) {
				++mobSpawnLimit; enforceSettingsBounds();
			}
		});
		mobSpawnLimitM = new Button(0, 80, 30, 20, "<", new Button.IPressable() {
			public void onPress(Button btn) {
				--mobSpawnLimit; enforceSettingsBounds();
			}
		});
		
		spawnSearchRadiusP = new Button(30, 100, 30, 20, ">", new Button.IPressable() {
			public void onPress(Button btn) {
				++spawnSearchRadius; enforceSettingsBounds();
			}
		});
		spawnSearchRadiusM = new Button(0, 100, 30, 20, "<", new Button.IPressable() {
			public void onPress(Button btn) {
				--spawnSearchRadius; enforceSettingsBounds();
			}
		});
		
		minMobsPerSpawnP = new Button(30, 120, 30, 20, ">", new Button.IPressable() {
			public void onPress(Button btn) {
				++minMobsPerSpawn; enforceSettingsBounds();
			}
		});
		minMobsPerSpawnM = new Button(0, 120, 30, 20, "<", new Button.IPressable() {
			public void onPress(Button btn) {
				--minMobsPerSpawn; enforceSettingsBounds();
			}
		});
		
		maxMobsPersSpawnP = new Button(30, 140, 30, 20, ">", new Button.IPressable() {
			public void onPress(Button btn) {
				++maxMobsPersSpawn; enforceSettingsBounds();
			}
		});
		maxMobsPersSpawnM = new Button(0, 140, 30, 20, "<", new Button.IPressable() {
			public void onPress(Button btn) {
				--maxMobsPersSpawn; enforceSettingsBounds();
			}
		});
		
		Button saveSettings = addButton(new Button(0, 160, 30, 20, "Save", new Button.IPressable() {
			@Override
			public void onPress(Button btn) {
				Network.INSTANCE.sendToServer(new SetMobSpawnerSettingsPKT(
					    mobList.get(mobIndex).getRegistryName().toString(),
						spawnRadius,
						spawnsPerMin,
						mobSpawnLimit,
						spawnSearchRadius,
						minMobsPerSpawn,
						maxMobsPersSpawn, 
						spawner.getPos().getX(), 
						spawner.getPos().getY(), 
						spawner.getPos().getZ()));
			}
		}));
		
		addButtons(mobIndexP, mobIndexM, spawnRadiusP, spawnRadiusM,
		spawnsPerMinP, spawnsPerMinM, mobSpawnLimitP, mobSpawnLimitM,
		spawnSearchRadiusP, spawnSearchRadiusM, minMobsPerSpawnP, 
		minMobsPerSpawnM, maxMobsPersSpawnP, maxMobsPersSpawnM);
	}
	
	void addButtons(Button... buttons) {
		for(Button button : buttons){
			addButton(button);
		}
	}
	
	@Override
	public void render(int mouseX, int mouseY, float partialTicks) {
		this.renderBackground();
		super.render(mouseX, mouseY, partialTicks);
		
		this.drawString(this.font, "Mob Type: " + mobList.get(mobIndex).getRegistryName(), 65, 20+6, 0XFFFFFF);
		this.drawString(this.font, "Spawn Radius: " + spawnRadius, 65, 40+6, 0XFFFFFF);
		this.drawString(this.font, "Average Spawns/Min: " + spawnsPerMin, 65, 60+6, 0XFFFFFF);
		this.drawString(this.font, "Mob Spawn Limit: " + mobSpawnLimit, 65, 80+6, 0XFFFFFF);
		this.drawString(this.font, "Mob Spawn Search Radius: " + spawnSearchRadius, 65, 100+6, 0XFFFFFF);
		this.drawString(this.font, "Mobs Per Spawn Min: " + minMobsPerSpawn, 65, 120+6, 0XFFFFFF);
		this.drawString(this.font, "Mobs Per Spawn Max: " + maxMobsPersSpawn, 65, 140+6, 0XFFFFFF);
	}
	
	void enforceSettingsBounds() {
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
	public boolean isPauseScreen() {
		return false;
	}
}
