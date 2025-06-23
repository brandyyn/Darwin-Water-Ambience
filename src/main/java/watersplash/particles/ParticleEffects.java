package watersplash.particles;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

public class ParticleEffects
{
	 private static Minecraft mc = Minecraft.getMinecraft();
	 private static World theWorld = mc.theWorld;


	 public static void spawnEntityParticle(String particleName, Entity entity, BiomeGenBase biomeGenBase,String Type, byte meta) {
		 if (mc != null)
		 {
		 EntityFX var21 = null;

		 if (particleName.equals("ParticleSplash"))
		 {
			 boolean i = true;
			 int o = 0;
			 double x =(int)entity.posX;
			 int y =(int)entity.posY;
			 double z =(int)entity.posZ;
			 while(i) {
				 ++o;
				 if(entity.worldObj.isAirBlock((int)x,y+o,(int)z) || o > 5) {

					 if (Type.equals("Waste")) {
						 var21 = new ParticleWaterSplash(theWorld,x,y+o,z, entity,biomeGenBase, meta, true);
						 mc.effectRenderer.addEffect(var21);
					 }
			   		 if (Type.equals("Water")) {
						var21 = new ParticleWaterSplash(theWorld, x, y+o,z, entity,biomeGenBase, meta, false);
					 	mc.effectRenderer.addEffect(var21);
					 	 }
			   		 if (Type.equals("Lava")){
						var21 = new ParticleLavaSplash(theWorld, x, y+o,z,entity,biomeGenBase); 
			   			mc.effectRenderer.addEffect(var21);
						 }
					 	i=false;
				 		
				 			}	
				 

			 			}
			 		}				 
		 		}
		}
	 
		 
	 public static EntityFX spawnParticle(String particleName, double par2, double par4, double par6, double par8, double par10, double par12){
	 if (mc != null)
	 {
		 
	 EntityFX var21 = null;
	 
	 	if (particleName.equals("ParticleWaterRipple"))
		 	{
			 var21 = new ParticleWaterRipple(theWorld, par2, par4, par6, (float)par8, (float)par10, (float)par12);
			 mc.effectRenderer.addEffect(var21);
		 	}  
	 	}
	 return null;
	 }


	public static void spawnTail(Entity entityL, double poY, boolean b) {
		 if (mc != null)
		 {
		 EntityFX var21 = null;
		 if(b) {
			 var21 = new ParticleWaterTailMain(theWorld, entityL);
		 }else {
			 var21 = new ParticleWaterTail(theWorld,poY-0.1, entityL);
			 
		 }
		 
		 if (var21 != null)
		 	mc.effectRenderer.addEffect(var21);
		 }
	}
}
