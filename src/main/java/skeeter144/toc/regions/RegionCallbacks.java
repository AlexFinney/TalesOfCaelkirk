package skeeter144.toc.regions;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.WorldServer;
import skeeter144.toc.TOCMain;
import skeeter144.toc.entity.mob.monster.EntityGiantSpider;
import skeeter144.toc.entityeffect.ServerEffectHandler;
import skeeter144.toc.entityeffect.effects.ReducedVision;
import skeeter144.toc.network.Network;
import skeeter144.toc.network.PlayMusicTrackPKT;
import skeeter144.toc.sounds.Sounds;
import skeeter144.toc.sounds.music.MusicManager.SoundCategory;

public class RegionCallbacks {

	public static void spiderForrestEntered(EntityLivingBase e) {
		ServerEffectHandler.attemptAddNewEffect(e.getUniqueID(), new ReducedVision(e, "reduced_vision", .4f));
		if(e instanceof EntityPlayerMP) {
			Network.INSTANCE.sendTo(new PlayMusicTrackPKT(SoundCategory.SUPER_CREEPY), (EntityPlayerMP)e);
		}
	}
	
	public static void spiderForrestExited(EntityLivingBase e) {
		ServerEffectHandler.removeEffectFromEntity(e.getUniqueID(), ReducedVision.class);
	}
	
	public static void spiderForrestTick(EntityLivingBase e) {
		int val = TOCMain.rand.nextInt(20);
		if(val == 0) {
			e.world.playSound(null, e.getPosition(), Sounds.spider_forest_ambient, net.minecraft.util.SoundCategory.MASTER, 1, 1);
		}
		
		if(TOCMain.rand.nextInt(8) == 0) {
			WorldServer ws = (WorldServer)e.world;
			EntityGiantSpider spider = new EntityGiantSpider(null, e.world);
			
			Vec3d playerPos = e.getPositionVector();
			Vec3d playerLook = e.getForward();
			
			Vec3d spiderPos = playerPos.add(playerLook.scale(7)).add(new Vec3d(0,5,0));
			
			if(ws.getBlockState(new BlockPos(spiderPos.x, spiderPos.y, spiderPos.z)).getBlock().equals(Blocks.AIR)) {
				
			}else {
				spiderPos = playerPos.add(new Vec3d(0,5,0));
			}
			ws.spawnEntity(spider);
			spider.setPosition(spiderPos.x, spiderPos.y, spiderPos.z);
			
		}
		
	}
}
