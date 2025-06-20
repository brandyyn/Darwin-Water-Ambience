package watersplash.particles;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.world.World;
import watersplash.ConfigurationMoD;

public class ParticleWaterTailMain extends EntityFX
{
	private double random = Math.random();
	private int timer = 0;
	private Entity entityL;

public ParticleWaterTailMain(World theWorld, Entity e){
	this(theWorld, e.posX, e.posY, e.posZ, 1.0F, e.motionX, e.motionY, e.motionZ, e);
}

public ParticleWaterTailMain(World par1World, double par2, double par4, double par6, float par8, double motionX, double motionY, double motionZ, Entity entity)
{
super(par1World, par2, par4, par6, 0.0D, 0.0D, 0.0D);

this.particleTextureIndexX = 0; 
this.particleTextureIndexY = 0;

this.particleRed = 1.0F;
this.particleGreen = 1.0F;
this.particleBlue = 1.0F;

entityL = entity;
this.posY-=1;

this.particleMaxAge = 15555;
this.noClip = false;
}

@Override
public void onUpdate()
{
	    if(entityL != null && !entityL.isDead) {
		this.posX = entityL.posX;
		this.posZ = entityL.posZ;
		this.prevPosX = this.posX;
		this.prevPosY = this.posY;
		this.prevPosZ = this.posZ;

		
		Material m = entityL.worldObj.getBlock((int)entityL.posX, (int)entityL.posY-1, (int)entityL.posZ).getMaterial();
		Material m2 = entityL.worldObj.getBlock((int)entityL.posX, (int)entityL.posY, (int)entityL.posZ).getMaterial();
		int meta = 0;
		if((m2 == Material.water || m == Material.water) && (entityL.motionX != 0 || entityL.motionZ != 0) && ++timer > ConfigurationMoD.TRAIL_RARITY_MODIFIER){
			double poY = 0;
			int o = 0 ;
			 while((o<5)) {
				 ++o;
				 if(entityL.worldObj.getBlock((int)entityL.posX, (int)entityL.posY-2+o, (int)entityL.posZ).getMaterial() == Material.air) {
					 poY = (int)entityL.posY-2+o;
					//TODO  Rotation, it's not necessary but it would look nicer, especially in case 1 where the block below has meta data 9
					/** float degrees = (float) Math.toDegrees((float) BlockLiquid.getFlowDirection(entityL.worldObj, (int) entityL.posX, (int) entityL.posY - 3 + o, (int) entityL.posZ, Material.water)) % 360;
					 if (!Float.isNaN(degrees)) {
						 System.out.println(this.posY + " " + (int) ((degrees + 22.5F) / 45F) % 8);
						 switch ((int) ((degrees + 22.5F) / 45F) % 8) {
							 case 2: // West (0°)
								 this.rotationPitch = 24;
								 this.rotationYaw = -40; // Roll left
								 break;
							 case -4: // Northwest (45°)
								 this.rotationPitch = 5;
								 this.rotationYaw = -5;
								 break;
							 case -3: // North (90°)
								 this.rotationPitch = 10; // Tilt forward
								 this.rotationYaw = 0;
								 break;
							 case -2: // Northeast (135°)
								 this.rotationPitch = 5;
								 this.rotationYaw = 5;
								 break;
							 case -1: // East (180°)
								 this.rotationPitch = 0;
								 this.rotationYaw = 10; // Roll right
								 break;
							 case 0: // Southeast (225°)
								 this.rotationPitch = -5;
								 this.rotationYaw = 5;
								 break;
							 case 1: // South (270°)
								 this.rotationPitch = -10; // Tilt backward
								 this.rotationYaw = 0;
								 break;
							 case 7: // Southwest (315°)
								 this.rotationPitch = -5;
								 this.rotationYaw = -5;
								 break;
						 }
					 }*/
					 meta = entityL.worldObj.getBlockMetadata((int)entityL.posX, (int)poY-1, (int)entityL.posZ);
					 switch (meta) {
						 case 1:
							 poY -= 0.1;
							 //Checks block below if it's flowing down.
							 if(entityL.worldObj.getBlockMetadata((int)entityL.posX, (int)poY-2, (int)entityL.posZ) == 9) {
								 poY -= 0.2;
							 }
							 break;
						 case 2:
							 poY -= 0.3;
							 break;
						 case 3:
							 poY -= 0.4;
							 break;
						 case 4:
							 poY -= 0.5;
							 break;
						 case 5:
							 poY -= 0.6;
							 break;
						 case 6:
							 poY -= 0.7;
							 break;
						 case 7:
							 poY -= 0.8;
							 break;
						 default:
							 break;
					 }
				 	if(entityL instanceof EntityItem)
				 		this.setDead();
					o = 10;
				 } 
			 }
			 if(timer > Math.max(ConfigurationMoD.TRAIL_RARITY_MODIFIER*(meta*1.1), ConfigurationMoD.TRAIL_RARITY_MODIFIER)) {
				 ParticleEffects.spawnTail(entityL, poY, false);
				 timer = 0;
			 }
		}else if(this.particleAge++ >= this.particleMaxAge) {
			this.setDead();
		} 
	}else {
		this.setDead();
	}
}

	@Override
	@SideOnly(Side.CLIENT)
	public int getBrightnessForRender(float p_70070_1_)
	{
		return 200;   
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void renderParticle(Tessellator par1Tessellator, float par2, float par3, float par4, float par5, float par6, float par7)
	{
	
	}
}