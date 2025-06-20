package watersplash;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;
import watersplash.particles.ParticleEffects;

public class EntityRainProperties implements IExtendedEntityProperties {
	  public static final String RAIN_PROP = "KorinRain";
	  
	  public final Entity entity;

	private boolean trigger = true;

	private int time;

	  
	  
	  public EntityRainProperties(Entity entityp) {
	    this.entity = entityp;
		if(entity instanceof EntityItem) {
			trigger = false;
		}
	  }
	  
	  public static final void register(Entity entity) {
	    entity.registerExtendedProperties(RAIN_PROP, new EntityRainProperties(entity));
	  }
	  
	  public static final EntityRainProperties get(Entity entity) {
	    return (EntityRainProperties)entity.getExtendedProperties(RAIN_PROP);
	  }

	@Override
	public void saveNBTData(NBTTagCompound compound) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void loadNBTData(NBTTagCompound compound) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void init(Entity entity, World world) {
		// TODO Auto-generated method stub
		
	}

	public void updateWaterLogic() {

		
        if(entity != null && entity.worldObj != null) {
			int y = (int) entity.posY;

			boolean r = entity.isRiding();


			if (entity instanceof EntityPlayer && !(entity instanceof EntityOtherPlayerMP)) {

				y -= 1;
				if (r)
					y = (int) (entity.ridingEntity.posY - 1);
			}


			Block b = this.entity.worldObj.getBlock((int) entity.posX, y, (int) entity.posZ);

			if (b != null) {
				if (!trigger) {
					if (b.getMaterial() == Material.water) {
						trigger = true;
						time = 0;
						if(b.getUnlocalizedName().equals("tile.toxicWasteBlock"))
							ParticleEffects.spawnEntityParticle("ParticleSplash", entity, entity.worldObj.getBiomeGenForCoords((int) entity.posX, (int) entity.posZ), "Waste", (byte)entity.worldObj.getBlockMetadata((int) entity.posX, y, (int) entity.posZ));
						else
							ParticleEffects.spawnEntityParticle("ParticleSplash", entity, entity.worldObj.getBiomeGenForCoords((int) entity.posX, (int) entity.posZ), "Water", (byte)entity.worldObj.getBlockMetadata((int) entity.posX, y, (int) entity.posZ));
					}
					if (ConfigurationMoD.Enable_SPLASH_LAVA && b.getMaterial() == Material.lava) {
						trigger = true;
						time = 0;
						ParticleEffects.spawnEntityParticle("ParticleSplash", entity, entity.worldObj.getBiomeGenForCoords((int) entity.posX, (int) entity.posZ), "Lava", (byte)entity.worldObj.getBlockMetadata((int) entity.posX, y, (int) entity.posZ));
					}

				} else {
					Block bn = this.entity.worldObj.getBlock((int) entity.posX, (int) y, (int) entity.posZ);
					Block bu = this.entity.worldObj.getBlock((int) entity.posX, (int) y - 1, (int) entity.posZ);

					if (bu != null) {
						if (!(bu.getMaterial() == Material.water || bu.getMaterial() == Material.lava)) {

							if (!(bn.getMaterial() == Material.water || bn.getMaterial() == Material.lava)) {
								if (ConfigurationMoD.SPLASH_LOWPERFORMANCEMOD || updateTime()) {
									trigger = false;
								}
							}
						}
					}
				}
			}
		}
    }

	private boolean updateTime() {
		if(++time >= 10) {
			time = 0;
			return true;
			
		}else {
			return false;
				
			}
		}
	}
