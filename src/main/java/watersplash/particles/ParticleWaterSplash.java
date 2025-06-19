package watersplash.particles;

import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.entity.item.EntityItem;
import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import watersplash.ConfigurationMoD;
import watersplash.WATERMAIN;

public class ParticleWaterSplash extends EntityFX
{
	
private int p;
private int outTime,inTime;
private Entity entityL;
private boolean spawnedTrail = false;
private double y;
private String resourceLocation = ":textures/gui/particles.png";

public ParticleWaterSplash(World theWorld, Entity entity, BiomeGenBase biomeGenBase, byte meta, boolean isWLMToxicWaste) {

super(theWorld,(int)entity.posX, (int)entity.posY, (int)entity.posZ);

this.motionX = 0;
this.motionY = 0;
this.motionZ = 0;

this.particleTextureIndexX = 10; //
this.particleTextureIndexY = 1;

	entityL =  entity;

int fogColour = biomeGenBase.getWaterColorMultiplier();
float rPart = (float) ((fogColour & 0xFF0000) >> 16);
float gPart = (float) ((fogColour & 0xFF00) >> 8);
float bPart = (float) (fogColour & 0xFF);

this.particleRed = rPart;
this.particleGreen = gPart;
this.particleBlue = bPart;
if(entity instanceof EntityOtherPlayerMP && ConfigurationMoD.Enable_Splash_for_ENTITYS_MP)
    this.particleScale *= 8.4F*0.9F*ConfigurationMoD.SPLASH_SCALE_MODIFIER_SIZE;
else if(entity instanceof EntityItem)
    this.particleScale *=16.8F*entity.getShadowSize()*ConfigurationMoD.SPLASH_SCALE_MODIFIER_SIZE;
else
    this.particleScale *= 8.4F*entity.getShadowSize()*ConfigurationMoD.SPLASH_SCALE_MODIFIER_SIZE;
this.outTime = 0;
this.inTime  = -1;
this.particleMaxAge = 12;
this.noClip = false;
if(theWorld.getBlock((int)entity.posX, (int)entity.posY, (int)entity.posZ).getMaterial() == Material.water)
	this.posY += 1;
if(isWLMToxicWaste)
    resourceLocation = ":textures/gui/particlesWLM.png";
switch (meta) {
    case 1:
        this.posY-= 0.2;
        if(theWorld.getBlockMetadata((int) entity.posX, (int) (entity.posY-2), (int) entity.posZ) == 9)
            this.posY-=0.5;
        break;
    case 2:
        this.posY-= 0.4;
        break;
    case 3:
        this.posY-= 0.5;
        break;
    case 4:
        this.posY-= 0.6;
        break;
    case 5:
        this.posY-= 0.8;
        break;
    case 6:
        this.posY-= 0.9;
        break;
    case 7:
        this.posY-= 1;
        break;

}
y = this.posY;
this.posX = entityL.posX;
this.posZ = entityL.posZ;
}

@Override
public void onUpdate()
{
		++p;
		if(p >= 1) {p = 0;
		if(this.particleAge <= 3 && particleAge < 6) {
			++outTime;
		}else {
			--outTime;
			}
		
		if(this.particleAge <= 5) {
			++inTime;
		}else {
			--inTime;
			}
		}
		
		if(entityL != null) {
			if( !spawnedTrail && ConfigurationMoD.Enable_Trail ) {
				spawnedTrail = true;
				ParticleEffects.spawnTail(entityL,y,true);
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
GL11.glDepthMask(true);
GL11.glEnable(GL11.GL_ALPHA_TEST);
Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation(WATERMAIN.MODID+ resourceLocation));
par1Tessellator.startDrawingQuads();
par1Tessellator.setBrightness(200);
par1Tessellator.setColorOpaque_F(this.particleRed, this.particleGreen, this.particleBlue);

render(par1Tessellator, par2, false);
if(inTime >= 0)
render(par1Tessellator, par2, true);

par1Tessellator.draw();
Minecraft.getMinecraft().getTextureManager().bindTexture( new ResourceLocation(resourceLocation));
par1Tessellator.startDrawingQuads();

}


public void render(Tessellator p_70539_1_, float p_70539_2_, boolean type)
{	
    float f6 = 2;
    float u = 1F;
    float i = 1F;
    if(type) {
    	f6 = ((float)particleTextureIndexX+inTime)/ 16.0F;
    	u = 0.9F;
    	i = (float) (2.1F*ConfigurationMoD.SPLASH_SCALE_MODIFIER_HIGHT);
    }else {
    	f6 = ((float)particleTextureIndexX+outTime)/ 16.0F;
    }
    
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
    p_70539_1_.addVertexWithUV((double)(x - 0.5* f10), (double)(y + (1*i) * f10), (double)(z - 0.5 * f10), (double)f7, (double)f8);
    p_70539_1_.addVertexWithUV((double)(x + 0.5* f10), (double)(y + (1*i) * f10), (double)(z - 0.5 * f10), (double)f6, (double)f8);
    p_70539_1_.addVertexWithUV((double)(x + 0.5* f10), (double)(y - 0 * f10), (double)(z - 0.5 * f10), (double)f6, (double)f9);
    
    p_70539_1_.addVertexWithUV((double)(x + 0.5* f10), (double)(y - 0 * f10), (double)(z - 0.5 * f10), (double)f7, (double)f9);
    p_70539_1_.addVertexWithUV((double)(x + 0.5* f10), (double)(y + (1*i) * f10), (double)(z - 0.5 * f10), (double)f7, (double)f8);
    p_70539_1_.addVertexWithUV((double)(x - 0.5* f10), (double)(y + (1*i) * f10), (double)(z - 0.5 * f10), (double)f6, (double)f8);
    p_70539_1_.addVertexWithUV((double)(x - 0.5* f10), (double)(y - 0 * f10), (double)(z - 0.5 * f10), (double)f6, (double)f9);
    
    p_70539_1_.addVertexWithUV((double)(x + 0.5* f10), (double)(y - 0 * f10), (double)(z + 0.5 * f10), (double)f7, (double)f9);
    p_70539_1_.addVertexWithUV((double)(x + 0.5* f10), (double)(y + (1*i) * f10), (double)(z + 0.5 * f10), (double)f7, (double)f8);
    p_70539_1_.addVertexWithUV((double)(x - 0.5* f10), (double)(y + (1*i) * f10), (double)(z + 0.5 * f10), (double)f6, (double)f8);
    p_70539_1_.addVertexWithUV((double)(x - 0.5* f10), (double)(y - 0 * f10), (double)(z + 0.5 * f10), (double)f6, (double)f9);
    
    p_70539_1_.addVertexWithUV((double)(x - 0.5* f10), (double)(y - 0 * f10), (double)(z + 0.5 * f10), (double)f7, (double)f9);
    p_70539_1_.addVertexWithUV((double)(x - 0.5* f10), (double)(y + (1*i) * f10), (double)(z + 0.5 * f10), (double)f7, (double)f8);
    p_70539_1_.addVertexWithUV((double)(x + 0.5* f10), (double)(y + (1*i) * f10), (double)(z + 0.5 * f10), (double)f6, (double)f8);
    p_70539_1_.addVertexWithUV((double)(x + 0.5* f10), (double)(y - 0 * f10), (double)(z + 0.5 * f10), (double)f6, (double)f9);
    
    
    
    
    p_70539_1_.addVertexWithUV((double)(x + 0.5* f10), (double)(y - 0 * f10), (double)(z + 0.5 * f10), (double)f7, (double)f9);
    p_70539_1_.addVertexWithUV((double)(x + 0.5* f10), (double)(y + (1*i) * f10), (double)(z + 0.5 * f10), (double)f7, (double)f8);
    p_70539_1_.addVertexWithUV((double)(x + 0.5* f10), (double)(y + (1*i) * f10), (double)(z - 0.5 * f10), (double)f6, (double)f8);
    p_70539_1_.addVertexWithUV((double)(x + 0.5* f10), (double)(y - 0 * f10), (double)(z - 0.5 * f10), (double)f6, (double)f9);
    
    p_70539_1_.addVertexWithUV((double)(x - 0.5* f10), (double)(y - 0 * f10), (double)(z + 0.5 * f10), (double)f7, (double)f9);
    p_70539_1_.addVertexWithUV((double)(x - 0.5* f10), (double)(y + (1*i) * f10), (double)(z + 0.5 * f10), (double)f7, (double)f8);
    p_70539_1_.addVertexWithUV((double)(x - 0.5* f10), (double)(y + (1*i) * f10), (double)(z - 0.5 * f10), (double)f6, (double)f8);
    p_70539_1_.addVertexWithUV((double)(x - 0.5* f10), (double)(y - 0 * f10), (double)(z - 0.5 * f10), (double)f6, (double)f9);
    
    p_70539_1_.addVertexWithUV((double)(x + 0.5* f10), (double)(y - 0 * f10), (double)(z - 0.5 * f10), (double)f7, (double)f9);
    p_70539_1_.addVertexWithUV((double)(x + 0.5* f10), (double)(y + (1*i) * f10), (double)(z - 0.5 * f10), (double)f7, (double)f8);
    p_70539_1_.addVertexWithUV((double)(x + 0.5* f10), (double)(y + (1*i) * f10), (double)(z + 0.5 * f10), (double)f6, (double)f8);
    p_70539_1_.addVertexWithUV((double)(x + 0.5* f10), (double)(y - 0 * f10), (double)(z + 0.5 * f10), (double)f6, (double)f9);
    
    p_70539_1_.addVertexWithUV((double)(x - 0.5* f10), (double)(y - 0 * f10), (double)(z - 0.5 * f10), (double)f7, (double)f9);
    p_70539_1_.addVertexWithUV((double)(x - 0.5* f10), (double)(y + (1*i) * f10), (double)(z - 0.5 * f10), (double)f7, (double)f8);
    p_70539_1_.addVertexWithUV((double)(x - 0.5* f10), (double)(y + (1*i) * f10), (double)(z + 0.5 * f10), (double)f6, (double)f8);
    p_70539_1_.addVertexWithUV((double)(x - 0.5* f10), (double)(y - 0 * f10), (double)(z + 0.5 * f10), (double)f6, (double)f9);
}
}