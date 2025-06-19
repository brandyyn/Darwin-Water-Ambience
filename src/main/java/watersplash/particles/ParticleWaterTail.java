package watersplash.particles;


import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import watersplash.ConfigurationMoD;
import watersplash.WATERMAIN;

public class ParticleWaterTail extends EntityFX
{
	private double random = Math.random();
	private int timer = 0;
	private Entity entityL;
	
public ParticleWaterTail(World theWorld, double poY, Entity e){
	
	this(theWorld, e.posX,poY, e.posZ, 1.0F, 0, 0, 0, e);
}

public ParticleWaterTail(World par1World, double par2, double par4, double par6, float par8, double motionX, double motionY, double motionZ, Entity entity)
{
	super(par1World, par2, par4, par6, 0.0D, 0.0D, 0.0D);
this.motionX = motionX;
this.motionY = motionY;
this.motionZ = motionZ;
this.particleTextureIndexX = 0; 
this.particleTextureIndexY = 2;

this.particleRed = 1.0F;
this.particleGreen = 1.0F;
this.particleBlue = 1.0F;
entityL = entity;
this.particleScale *= 6;
this.particleMaxAge = 40;
this.noClip = false;
this.rotationYaw = entity.rotationYaw ;

}

	@Override
	public void onUpdate()
	{
		if(++timer > 8) {
			timer = 0;
			if(++particleTextureIndexX >4) {
				particleTextureIndexX = 0;
				}
			}
			this.prevPosX = this.posX;
			this.prevPosY = this.posY;
			this.prevPosZ = this.posZ;
			if (this.particleAge++ >= this.particleMaxAge)
		{
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

	par1Tessellator.draw();
	GL11.glEnable(GL11.GL_BLEND);
	GL11.glDepthMask(false);
	GL11.glEnable(GL11.GL_ALPHA_TEST);
	Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation(WATERMAIN.MODID+":textures/gui/particles.png"));
	par1Tessellator.startDrawingQuads();
	par1Tessellator.setBrightness(200);//make sure you have this!!
	render(par1Tessellator, par2, -0.99F, 1.22F, 0.002F, -0.01F, -0.99F);
	par1Tessellator.draw();
	par1Tessellator.startDrawingQuads();
	Minecraft.getMinecraft().getTextureManager().bindTexture( new ResourceLocation("textures/particle/particles.png"));
	}


	public void render(Tessellator p_70539_1_, float p_70539_2_, float p_70539_3_, float p_70539_4_, float p_70539_5_, float p_70539_6_, float p_70539_7_)
	{
    float f6 = (float)this.particleTextureIndexX / 16.0F;
    float f7 = f6 + 0.0624375F;
    float f8 = (float)this.particleTextureIndexY / 16.0F;
    float f9 = f8 + 0.0624375F;
    float f10 = 0.1F * this.particleScale;

    if (this.particleIcon != null)
    {
        f6 = this.particleIcon.getMinU();
        f7 = this.particleIcon.getMaxU();
        f8 = this.particleIcon.getMinV();
        f9 = this.particleIcon.getMaxV();
    }
    float alpha = this.particleAlpha;
    if(ConfigurationMoD.Enable_Trail_Transparancy)
    	alpha= (float) (this.particleAlpha-((particleAlpha*particleAge/particleMaxAge)/1.1));
    			
    float f11 = (float)(this.prevPosX + (this.posX - this.prevPosX) * (double)p_70539_2_ - interpPosX);
    float f12 = (float)(this.prevPosY + (this.posY - this.prevPosY) * (double)p_70539_2_ - interpPosY);
    float f13 = (float)(this.prevPosZ + (this.posZ - this.prevPosZ) * (double)p_70539_2_ - interpPosZ);
    p_70539_1_.setColorRGBA_F(this.particleRed, this.particleGreen, this.particleBlue,alpha );
    p_70539_1_.addVertexWithUV((double)(f11 - p_70539_3_ * f10 - p_70539_6_ * f10), (double)(f12 - 0 * f10), (double)(f13 - p_70539_5_ * f10 - p_70539_7_ * f10), (double)f7, (double)f9);
    p_70539_1_.addVertexWithUV((double)(f11 - p_70539_3_ * f10 + p_70539_6_ * f10), (double)(f12 + 0 * f10), (double)(f13 - p_70539_5_ * f10 + p_70539_7_ * f10), (double)f7, (double)f8);
    p_70539_1_.addVertexWithUV((double)(f11 + p_70539_3_ * f10 + p_70539_6_ * f10), (double)(f12 + 0 * f10), (double)(f13 + p_70539_5_ * f10 + p_70539_7_ * f10), (double)f6, (double)f8);
    p_70539_1_.addVertexWithUV((double)(f11 + p_70539_3_ * f10 - p_70539_6_ * f10), (double)(f12 - 0 * f10), (double)(f13 + p_70539_5_ * f10 - p_70539_7_ * f10), (double)f6, (double)f9);

	}
}