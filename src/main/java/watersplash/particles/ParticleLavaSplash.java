package watersplash.particles;

import java.awt.Color;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import watersplash.ConfigurationMoD;
import watersplash.TextureRegister;
import watersplash.WATERMAIN;

public class ParticleLavaSplash extends EntityFX
{
	
private int p;
private int lightlevel = 200;


public ParticleLavaSplash(World par1World, double par2, double par4, double par6, Entity entity, BiomeGenBase biomeGenBase)
{
super(par1World,par2, par4,par6, 0.0D, 0.0D, 0.0D);
this.motionX = 0;
this.motionY = 0;
this.motionZ = 0;
float var12 = (float)Math.random() * 0.4F + 0.6F;
this.particleTextureIndexX = 10; //
this.particleTextureIndexY = 3;

Color c = null;

if(par1World.getBlock((int)par2,(int) par4, (int)par6) != null )
c = TextureRegister.getColorForBlock(par1World.getBlock((int)par2,(int) par4-1, (int)par6),2,par1World.getBlockMetadata((int)par2,(int) par4, (int)par6));

if(c != null) {
this.particleRed = c.getRed();
this.particleGreen = c.getGreen();
this.particleBlue = c.getBlue();
}else {

int fogColour = biomeGenBase.getWaterColorMultiplier();
float rPart = (float) ((fogColour & 0xFF0000) >> 16);
float gPart = (float) ((fogColour & 0xFF00) >> 8);
float bPart = (float) (fogColour & 0xFF);
this.particleRed = rPart;
this.particleGreen = gPart;
this.particleBlue = bPart;
}


this.particleScale *= 5.4F*entity.getShadowSize()*ConfigurationMoD.SPLASH_SCALE_MODIFIER_SIZE;
this.particleMaxAge = 5;
this.noClip = false;
this.lightlevel = (int) (200*Minecraft.getMinecraft().theWorld.getSunBrightnessFactor(Minecraft.getMinecraft().theWorld.rainingStrength));
}



@Override
public void onUpdate()
{
			++particleTextureIndexX;
		
		
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
public void renderParticle(Tessellator tess, float par2, float par3, float par4, float par5, float par6, float par7)

{Tessellator par1Tessellator = Tessellator.instance;
GL11.glEnable(GL11.GL_BLEND);
GL11.glDepthMask(true);
GL11.glEnable(GL11.GL_ALPHA_TEST);
Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation(WATERMAIN.MODID+":textures/gui/particles.png"));
par1Tessellator.setColorRGBA_F(1.0F, 1.0F, 1.0F, 1.0F);
par1Tessellator.setColorOpaque_F(this.particleRed/255, this.particleGreen/255, this.particleBlue/255);
par1Tessellator.setBrightness(200);

render(par1Tessellator, par2, par3, par4, par5, par6, par7);

par1Tessellator.draw();
Minecraft.getMinecraft().getTextureManager().bindTexture( new ResourceLocation("textures/particle/particles.png"));
par1Tessellator.startDrawingQuads();
}


public void render(Tessellator p_70539_1_, float p_70539_2_, float par3, float par4, float par5, float par6, float par7)
{	
    float f6 = 2;
    float u = 1F;
    float i = 1F;
    
    	f6 = ((float)particleTextureIndexX)/ 16.0F;
    	u = 0.9F;
    	i = (float) (1.1F*ConfigurationMoD.SPLASH_SCALE_MODIFIER_HEIGHT);
    
    
    float f7 = f6 + 0.0624375F;
    float f8 = (float)this.particleTextureIndexY / 16.0F;
    float f9 = f8 + 0.0624375F;
    float f10 = (float)(0.1F * this.particleScale*u);

    if (this.particleIcon != null)
    {
        f6 = this.particleIcon.getMinU();
        f7 = this.particleIcon.getMaxU();
        f8 = this.particleIcon.getMinV();
        f9 = this.particleIcon.getMaxV();
    }
    float x = (float)(this.prevPosX + (this.posX - this.prevPosX) * (double)p_70539_2_ - interpPosX);
    float y = (float)(this.prevPosY + (this.posY - this.prevPosY) * (double)p_70539_2_ - interpPosY);
    float z = (float)(this.prevPosZ + (this.posZ - this.prevPosZ) * (double)p_70539_2_ - interpPosZ);
    
    p_70539_1_.addVertexWithUV((double)(x - 0.5* f10), (double)(y - 0 * f10), (double)(z - 0.5 * f10), (double)f7, (double)f9);
    p_70539_1_.addVertexWithUV((double)(x - 0.5* f10), (double)(y + (0.7*i) * f10), (double)(z - 0.5 * f10), (double)f7, (double)f8);
    p_70539_1_.addVertexWithUV((double)(x + 0.5* f10), (double)(y + (0.7*i) * f10), (double)(z - 0.5 * f10), (double)f6, (double)f8);
    p_70539_1_.addVertexWithUV((double)(x + 0.5* f10), (double)(y - 0 * f10), (double)(z - 0.5 * f10), (double)f6, (double)f9);
    
    p_70539_1_.addVertexWithUV((double)(x + 0.5* f10), (double)(y - 0 * f10), (double)(z - 0.5 * f10), (double)f7, (double)f9);
    p_70539_1_.addVertexWithUV((double)(x + 0.5* f10), (double)(y + (0.7*i) * f10), (double)(z - 0.5 * f10), (double)f7, (double)f8);
    p_70539_1_.addVertexWithUV((double)(x - 0.5* f10), (double)(y + (0.7*i) * f10), (double)(z - 0.5 * f10), (double)f6, (double)f8);
    p_70539_1_.addVertexWithUV((double)(x - 0.5* f10), (double)(y - 0 * f10), (double)(z - 0.5 * f10), (double)f6, (double)f9);
    
    p_70539_1_.addVertexWithUV((double)(x + 0.5* f10), (double)(y - 0 * f10), (double)(z + 0.5 * f10), (double)f7, (double)f9);
    p_70539_1_.addVertexWithUV((double)(x + 0.5* f10), (double)(y + (0.7*i) * f10), (double)(z + 0.5 * f10), (double)f7, (double)f8);
    p_70539_1_.addVertexWithUV((double)(x - 0.5* f10), (double)(y + (0.7*i) * f10), (double)(z + 0.5 * f10), (double)f6, (double)f8);
    p_70539_1_.addVertexWithUV((double)(x - 0.5* f10), (double)(y - 0 * f10), (double)(z + 0.5 * f10), (double)f6, (double)f9);
    
    p_70539_1_.addVertexWithUV((double)(x - 0.5* f10), (double)(y - 0 * f10), (double)(z + 0.5 * f10), (double)f7, (double)f9);
    p_70539_1_.addVertexWithUV((double)(x - 0.5* f10), (double)(y + (0.7*i) * f10), (double)(z + 0.5 * f10), (double)f7, (double)f8);
    p_70539_1_.addVertexWithUV((double)(x + 0.5* f10), (double)(y + (0.7*i) * f10), (double)(z + 0.5 * f10), (double)f6, (double)f8);
    p_70539_1_.addVertexWithUV((double)(x + 0.5* f10), (double)(y - 0 * f10), (double)(z + 0.5 * f10), (double)f6, (double)f9);
    
    
    
    
    p_70539_1_.addVertexWithUV((double)(x + 0.5* f10), (double)(y - 0 * f10), (double)(z + 0.5 * f10), (double)f7, (double)f9);
    p_70539_1_.addVertexWithUV((double)(x + 0.5* f10), (double)(y + (0.7*i) * f10), (double)(z + 0.5 * f10), (double)f7, (double)f8);
    p_70539_1_.addVertexWithUV((double)(x + 0.5* f10), (double)(y + (0.7*i) * f10), (double)(z - 0.5 * f10), (double)f6, (double)f8);
    p_70539_1_.addVertexWithUV((double)(x + 0.5* f10), (double)(y - 0 * f10), (double)(z - 0.5 * f10), (double)f6, (double)f9);
    
    p_70539_1_.addVertexWithUV((double)(x - 0.5* f10), (double)(y - 0 * f10), (double)(z + 0.5 * f10), (double)f7, (double)f9);
    p_70539_1_.addVertexWithUV((double)(x - 0.5* f10), (double)(y + (0.7*i) * f10), (double)(z + 0.5 * f10), (double)f7, (double)f8);
    p_70539_1_.addVertexWithUV((double)(x - 0.5* f10), (double)(y + (0.7*i) * f10), (double)(z - 0.5 * f10), (double)f6, (double)f8);
    p_70539_1_.addVertexWithUV((double)(x - 0.5* f10), (double)(y - 0 * f10), (double)(z - 0.5 * f10), (double)f6, (double)f9);
    
    p_70539_1_.addVertexWithUV((double)(x + 0.5* f10), (double)(y - 0 * f10), (double)(z - 0.5 * f10), (double)f7, (double)f9);
    p_70539_1_.addVertexWithUV((double)(x + 0.5* f10), (double)(y + (0.7*i) * f10), (double)(z - 0.5 * f10), (double)f7, (double)f8);
    p_70539_1_.addVertexWithUV((double)(x + 0.5* f10), (double)(y + (0.7*i) * f10), (double)(z + 0.5 * f10), (double)f6, (double)f8);
    p_70539_1_.addVertexWithUV((double)(x + 0.5* f10), (double)(y - 0 * f10), (double)(z + 0.5 * f10), (double)f6, (double)f9);
    
    p_70539_1_.addVertexWithUV((double)(x - 0.5* f10), (double)(y - 0 * f10), (double)(z - 0.5 * f10), (double)f7, (double)f9);
    p_70539_1_.addVertexWithUV((double)(x - 0.5* f10), (double)(y + (0.7*i) * f10), (double)(z - 0.5 * f10), (double)f7, (double)f8);
    p_70539_1_.addVertexWithUV((double)(x - 0.5* f10), (double)(y + (0.7*i) * f10), (double)(z + 0.5 * f10), (double)f6, (double)f8);
    p_70539_1_.addVertexWithUV((double)(x - 0.5* f10), (double)(y - 0 * f10), (double)(z + 0.5 * f10), (double)f6, (double)f9);
}
}