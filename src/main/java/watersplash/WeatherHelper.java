package watersplash;

import java.util.List;
import java.util.Random;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent.ClientTickEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import watersplash.particles.ParticleEffects;

public class WeatherHelper {

    private static Random random = new Random();
   
	@SubscribeEvent
    @SideOnly(Side.CLIENT)
	public void updateSplashParticlesForItems(ClientTickEvent event)
   {       
		Minecraft mc = Minecraft.getMinecraft();
        if (mc.isGamePaused())
            return;

		if(mc != null && mc.thePlayer != null && mc.thePlayer.worldObj != null) {
			EntityClientPlayerMP e = mc.thePlayer;
			World world = mc.thePlayer.worldObj;
			int r = 20;
			List items = world.getEntitiesWithinAABB(EntityItem.class,AxisAlignedBB.getBoundingBox(e.posX-r, e.posY-r, e.posZ-r, e.posX+r, e.posY+r, e.posZ+r));
			items.removeIf(i -> items.indexOf(i) >= ConfigurationMoD.SPLASH_MAXTRACKEDITEMS);
			if(items != null && !items.isEmpty() && items.size()>0) {
				for(int i = 0; i<items.size(); ++i) {
					if(EntityRainProperties.get((Entity) items.get(i)) != null  && ((Entity)items.get(i)).getDistanceToEntity(Minecraft.getMinecraft().thePlayer) < ConfigurationMoD.SPLASH_RENDER_DISTANCE) {
						EntityRainProperties.get((Entity) items.get(i)).updateWaterLogic();

					}
				}
			}
		}
   }

	@SubscribeEvent
   @SideOnly(Side.CLIENT)
	public void updateSplashParticlesForLiving(LivingUpdateEvent event) {
		if( event.entity.worldObj.isRemote || event.entity instanceof EntityPlayer) {			
			if(EntityRainProperties.get((Entity) event.entity) != null && event.entity.getDistanceToEntity(Minecraft.getMinecraft().thePlayer) < ConfigurationMoD.SPLASH_RENDER_DISTANCE) {
				EntityRainProperties.get((Entity) event.entity).updateWaterLogic();
				}
		}
	}

	@SubscribeEvent
   @SideOnly(Side.CLIENT)
	public void updateSplashParticlesForLiving(EntityJoinWorldEvent event) {
		if( event.entity.worldObj.isRemote) {	
			if(event.entity instanceof EntityPlayer) {
				if(EntityRainProperties.get((Entity) event.entity) == null) {
					if(ConfigurationMoD.Enable_Splash_for_ENTITYS)
						EntityRainProperties.register((EntityPlayer)event.entity);
					
				}		
			}		
		}
	}
	
	@SubscribeEvent
    @SideOnly(Side.CLIENT)
	public void addRainParticles(EntityConstructing event)
   {
		if((event.entity instanceof EntityLiving) || event.entity instanceof EntityPlayer) {
			if(EntityRainProperties.get((Entity) event.entity) == null) {
				if(ConfigurationMoD.Enable_Splash_for_ENTITYS && event.entity instanceof EntityLiving)
					EntityRainProperties.register((Entity)event.entity);
				}
		}
	
		if((event.entity instanceof EntityItem)) {
			if(EntityRainProperties.get((EntityItem) event.entity) == null) {
				if(ConfigurationMoD.Enable_Splash_for_ITEMS && event.entity instanceof EntityItem) {
					
				EntityRainProperties.register((Entity)event.entity);
				}
			}
		}
   } 

	@SubscribeEvent
    @SideOnly(Side.CLIENT)
	public void addRainParticles(ClientTickEvent event)
    {
		Minecraft mc = Minecraft.getMinecraft();
		EntityPlayer player = Minecraft.getMinecraft().thePlayer;
		World world = Minecraft.getMinecraft().theWorld;
		
        if (mc.isGamePaused() || !ConfigurationMoD.Enable_Ripple)
            return;
        
		if (player != null && world != null) {
        float f = world.getRainStrength(1.0F);
        
        if (!mc.gameSettings.fancyGraphics)
        {
            f /= 2.0F;
        }

        if (f != 0.0F)
        {


            if (player != null && world != null) {
            int x = MathHelper.floor_double(player.posX);
            int y = MathHelper.floor_double(player.posY);
            int z = MathHelper.floor_double(player.posZ);
            //range of particles
            byte b0 = (byte) ConfigurationMoD.RIPPLE_AREA_MODIFIER;
            
            double d0 = 0.0D;
            double d1 = 0.0D;
            double d2 = 0.0D;
            int l = 0;
            //rarity of particles
            int i1 = (int)((15.0F*ConfigurationMoD.RIPPLE_RARITY_MODIFIER) * f * f);

            if (mc.gameSettings.particleSetting == 1)
            {
                i1 >>= 1;
            }
            else if (mc.gameSettings.particleSetting == 2)
            {
                i1 = 0;
            }

            for (int j1 = 0; j1 < i1; ++j1)
            {
                int k1 = x + this.random.nextInt(b0) - this.random.nextInt(b0);
                int l1 = z + this.random.nextInt(b0) - this.random.nextInt(b0);
                int i2 = world.getPrecipitationHeight(k1, l1);
                Block block = world.getBlock(k1, i2 - 1, l1);
                BiomeGenBase biomegenbase = world.getBiomeGenForCoords(k1, l1);

                if (i2 <= y + b0 && i2 >= y - b0 && biomegenbase.canSpawnLightningBolt() && biomegenbase.getFloatTemperature(k1, i2, l1) >= 0.15F)
                {
                    float f1 = this.random.nextFloat();
                    float f2 = this.random.nextFloat();

                    if (block.getMaterial() == Material.water)
                    {

                            d0 = (double)((float)k1 + f1);
                            d1 = (double)((float)i2 + 0.1F) - block.getBlockBoundsMinY();
                            d2 = (double)((float)l1 + f2);
                            ParticleEffects.spawnParticle("ParticleWaterRipple",  (double)((float)k1 + f1), (double)((float)i2 + -0.1F) - block.getBlockBoundsMinY(), (double)((float)l1 + f2), 0.0D, 0.0D, 0.0D);
      
                    		}
                		}
            		}
            	}
        	}
		}
    }
}