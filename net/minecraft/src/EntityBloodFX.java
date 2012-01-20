package net.minecraft.src;

import java.util.Random;

public class EntityBloodFX extends EntityFX
{
    float bloodParticleScale;
    float ticksWaterEvent;
    boolean caughtInWater;

    public EntityBloodFX(World world, double d, double d1, double d2,
            float f, float f1, float f2)
    {
        this(world, d, d1, d2, mod_Epixcrafted.particleScale, f, f1, f2);
    }

    public EntityBloodFX(World world, double d, double d1, double d2,
            float f, float f1, float f2, float f3)
    {
        super(world, d, d1, d2, 0.0D, 0.0D, 0.0D);
        motionX *= 0.10000000149011612D;
        motionY = mod_Epixcrafted.startYSpeedParticles;
        motionZ *= 0.10000000149011612D;
        float f4 = (float)Math.random() * 0.4F + 0.6F;
        particleRed = ((float)(Math.random() * 0.20000000298023224D) + 0.8F) * f1 * f4;
        particleGreen = ((float)(Math.random() * 0.20000000298023224D) + 0.8F) * f2 * f4;
        particleBlue = ((float)(Math.random() * 0.20000000298023224D) + 0.8F) * f3 * f4;
        particleScale *= 0.75F;
        particleScale *= f;
        bloodParticleScale = particleScale;
        particleMaxAge = rand.nextInt() % 5 + 1;
        particleMaxAge += mod_Epixcrafted.particlesLifeTime;
        noClip = false;
        caughtInWater = false;
        ticksWaterEvent = mod_Epixcrafted.updatesNextWaterCheck;
        if (mod_Epixcrafted.useOwnTextures)
        {
            setParticleTextureIndex(167);
        }
    }

    public void renderParticle(Tessellator tessellator, float f, float f1, float f2, float f3, float f4, float f5)
    {
        super.renderParticle(tessellator, f, f1, f2, f3, f4, f5);
    }

    public void onUpdate()
    {
        prevPosX = posX;
        prevPosY = posY;
        prevPosZ = posZ;
        if (particleAge++ >= particleMaxAge || posY < 0.20000000000000001D)
        {
            setEntityDead();
        }
        if (mod_Epixcrafted.useOwnTextures)
        {
            setParticleTextureIndex(167 - (particleAge * 8) / particleMaxAge);
        }
        else
        {
            setParticleTextureIndex(7 - (particleAge * 8) / particleMaxAge);
        }
        moveEntity(motionX, motionY, motionZ);
        motionX *= 0.95999997854232788D;
        if (!caughtInWater)
        {
            motionY *= mod_Epixcrafted.speedUpRate;
        }
        else
        {
            motionY *= 0.82100099999999998D;
        }
        motionZ *= 0.95999997854232788D;
        if (ticksWaterEvent <= 0.0F)
        {
            if (worldObj.getBlockMaterial(MathHelper.floor_double(posX), MathHelper.floor_double(posY), MathHelper.floor_double(posZ)) == Material.water)
            {
                if (!caughtInWater)
                {
                    caughtInWater = true;
                }
                motionY = -(mod_Epixcrafted.startYSpeedParticles / 2.0F);
            }
            else if (motionY > 0.0D)
            {
                motionY = mod_Epixcrafted.startYSpeedParticles / 2.0F;
            }
            ticksWaterEvent = mod_Epixcrafted.updatesNextWaterCheck;
        }
        else
        {
            ticksWaterEvent -= mod_Epixcrafted.updateCountPerTickWater;
        }
        if (onGround)
        {
            motionX = 0.0D;
            motionY = mod_Epixcrafted.startYSpeedParticles;
            motionZ = 0.0D;
        }
    }
}
