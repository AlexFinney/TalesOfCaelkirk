package skeeter144.toc.client.entity.renderer;

import java.util.Map;

import com.google.common.collect.Maps;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.model.ModelHorseArmorBase;
import net.minecraft.entity.passive.AbstractHorse;
import net.minecraft.entity.passive.EntitySkeletonHorse;
import net.minecraft.entity.passive.EntityZombieHorse;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import skeeter144.toc.entity.mob.mount.basic_horse.EntityDonkeyMount;
import skeeter144.toc.entity.mob.mount.basic_horse.EntityMuleMount;
import skeeter144.toc.entity.mob.mount.basic_horse.EntityVariableHorseMount;

@OnlyIn(Dist.CLIENT)
public class RenderCustomAbstractHorse extends RenderLiving<AbstractHorse>
{
    private static final Map <Class<?>, ResourceLocation> DONKEY_MAP = Maps.newHashMap();
    private static final Map <Integer, ResourceLocation> HORSE_MAP = Maps.newHashMap();
    private final float scale;

    public RenderCustomAbstractHorse(RenderManager rm)
    {
        this(rm, 1.0F);
    }

    public RenderCustomAbstractHorse(RenderManager rm, float scale)
    {
        super(rm, new ModelHorseArmorBase(), 0.75F);
        this.scale = scale;
    }

    /**
     * Allows the render to do state modifications necessary before the model is rendered.
     */
    protected void preRenderCallback(AbstractHorse entitylivingbaseIn, float partialTickTime)
    {
        GlStateManager.scalef(this.scale, this.scale, this.scale);
        super.preRenderCallback(entitylivingbaseIn, partialTickTime);
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    protected ResourceLocation getEntityTexture(AbstractHorse entity)
    {
    	if(entity instanceof EntityVariableHorseMount) {
    		return HORSE_MAP.get( ((EntityVariableHorseMount)entity).getHorseVariant()  );
    	}
    	return DONKEY_MAP.get(entity.getClass());	
    }

    static
    {
    	DONKEY_MAP.put(EntityMuleMount.class, new ResourceLocation("textures/entity/horse/mule.png"));
        DONKEY_MAP.put(EntityDonkeyMount.class, new ResourceLocation("textures/entity/horse/donkey.png"));
        DONKEY_MAP.put(EntityZombieHorse.class, new ResourceLocation("textures/entity/horse/horse_zombie.png"));
        DONKEY_MAP.put(EntitySkeletonHorse.class, new ResourceLocation("textures/entity/horse/horse_skeleton.png"));
    	HORSE_MAP.put(0, new ResourceLocation("textures/entity/horse/horse_black.png"));
    	HORSE_MAP.put(1, new ResourceLocation("textures/entity/horse/horse_gray.png"));
    	HORSE_MAP.put(2, new ResourceLocation("textures/entity/horse/horse_chestnut.png"));
    	HORSE_MAP.put(3, new ResourceLocation("textures/entity/horse/horse_creamy.png"));
    	HORSE_MAP.put(4, new ResourceLocation("textures/entity/horse/horse_white.png"));
    	HORSE_MAP.put(5, new ResourceLocation("textures/entity/horse/horse_zombie.png"));
    	HORSE_MAP.put(6, new ResourceLocation("textures/entity/horse/horse_skeleton.png"));
    }
    
    /*
     * public static enum HORSE_TYPES{
		LAME_BLACK,
		LAME_DAPPLE_GRAY,
		CHESTNUT,
		BUCKSKIN,
		WHITE_STALLION,
		ZOMBIE,
		SKELETON
	}
     */
    
    
}