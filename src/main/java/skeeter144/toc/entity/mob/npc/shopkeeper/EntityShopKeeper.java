package skeeter144.toc.entity.mob.npc.shopkeeper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import skeeter144.toc.entity.mob.npc.EntityNpc;
import skeeter144.toc.entity.mob.npc.shopkeeper.ShopData.ItemPrice;
import skeeter144.toc.entity.mob.npc.shopkeeper.ShopData.ShopListing;
import skeeter144.toc.network.Network;
import skeeter144.toc.network.OpenShopGuiMessage;

public class EntityShopKeeper extends EntityNpc {

	public ShopData shopData;
	String shopFileData = "";

	public EntityShopKeeper(World worldIn, String shopFileName) {
		super(worldIn);
		if (!worldIn.isRemote) {
			shopFileData = parseShopDataFile("shops/" + shopFileName + ".csv");
			shopData = parseShopDataString(shopFileData);
			shopData.keepId = this.getPersistentID();
		}

		this.tasks.addTask(1, new EntityAISwimming(this));
		this.tasks.addTask(3, new EntityAIMoveTowardsRestriction(this, .5f));
		this.tasks.addTask(5, new EntityAIWander(this, .5f));
		this.tasks.addTask(6, new EntityAILookIdle(this));

		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(100);
		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0);

		this.setSize(1f, 1f);
		this.setHealth(100f);
	}

	@Override
	protected boolean processInteract(EntityPlayer player, EnumHand hand) {
		if(player.world.isRemote) {
			return true;
		}
		
		Network.INSTANCE.sendTo(new OpenShopGuiMessage(this.shopData), (EntityPlayerMP)player);
		return true;
	}
	
	public static ShopData parseShopDataString(String shopData) {
		ShopData data = new ShopData();
		String[] lines = shopData.split("\n");
		for (String l : lines) {
			String[] tokens = l.split(",");
			if (tokens.length == 8) {
				String itemName = tokens[0];
				int g1 = Integer.parseInt(tokens[1].split("g")[0]);
				int s1 = Integer.parseInt(tokens[2].split("s")[0]);
				int c1 = Integer.parseInt(tokens[3].split("c")[0]);

				int g2 = Integer.parseInt(tokens[5].split("g")[0]);
				int s2 = Integer.parseInt(tokens[6].split("s")[0]);
				int c2 = Integer.parseInt(tokens[7].split("c")[0]);

				data.listings.add(new ShopListing(itemName, new ItemPrice(g2, s2, c2),  new ItemPrice(g1, s1, c1)));
			} else {
				try {
					throw new Exception("Malformed Shop CSV.  Row [" + l + "] doesn't have 8 columns");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return data;
	}

	public static String parseShopDataFile(String fileName) {
		String data = "";
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(new File(fileName)));
			String line = null;
			while ((line = br.readLine()) != null) {
				data += line + "\n";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return data;
	}
}
