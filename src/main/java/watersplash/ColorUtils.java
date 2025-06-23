package watersplash;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;

public class ColorUtils {
    private static final int hexDefault = ConfigurationMoD.SPLASH_COLOR_HEX;
    private static final int redDefault = (hexDefault & 0xFF0000) >> 16, greenDefault = (hexDefault & 0xFF00) >> 8, blueDefault = hexDefault & 0xFF;
    public static int getFluidColor(World world, int x, int y, int z, BiomeGenBase biome) {
        Block block = world.getBlock(x, y, z);
        if (block == null) return 0xFFFFFF;

        int color = block.colorMultiplier(world, x, y, z);
        if (color != 0xFFFFFF) {
            float rPart = (float) ((color & 0xFF0000) >> 16);
            float gPart = (float) ((color & 0xFF00) >> 8);
            float bPart = (float) (color & 0xFF);
            rPart = Math.min(255, Math.round(redDefault*(rPart/255.00F)));
            gPart = Math.min(255, Math.round(greenDefault*(gPart/255.00F)));
            bPart = Math.min(255, Math.round(blueDefault*(bPart/255.00F)));
            return ((int) rPart << 16) | ((int) gPart << 8) | (int) bPart;
        }

        // Try biome color if colorMultiplier is plain white
        int biomeColor = biome.getWaterColorMultiplier();
        if (biomeColor != 0xFFFFFF) {
            float rPart = (float) ((biomeColor & 0xFF0000) >> 16);
            float gPart = (float) ((biomeColor & 0xFF00) >> 8);
            float bPart = (float) (biomeColor & 0xFF);
            rPart = Math.min(255, Math.round(redDefault*(rPart/255.00F)));
            gPart = Math.min(255, Math.round(greenDefault*(gPart/255.00F)));
            bPart = Math.min(255, Math.round(blueDefault*(bPart/255.00F)));
            return ((int) rPart << 16) | ((int) gPart << 8) | (int) bPart;
        }

        // If all else fails, fallback to a default water-like blue
        return hexDefault;
    }
}
