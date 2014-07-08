package de.sanandrew.mods.claysoldiers.util.upgrades.behavior;

import de.sanandrew.core.manpack.util.SAPUtils;
import de.sanandrew.core.manpack.util.javatuples.Pair;
import de.sanandrew.core.manpack.util.javatuples.Quintet;
import de.sanandrew.mods.claysoldiers.entity.EntityClayMan;
import de.sanandrew.mods.claysoldiers.network.PacketProcessor;
import de.sanandrew.mods.claysoldiers.network.packet.PacketParticleFX;
import de.sanandrew.mods.claysoldiers.util.upgrades.SoldierUpgradeInst;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;

/**
 * @author SanAndreas
 * @version 1.0
 */
public class UpgradeFermSpiderEye
    extends AUpgradeBehavior
{
    @Override
    public boolean onUpdate(EntityClayMan clayMan, SoldierUpgradeInst upgradeInst) {
        if( !clayMan.worldObj.isRemote && clayMan.ticksExisted % 20 == 0 && SAPUtils.RANDOM.nextInt(10) == 0 ) {
            PacketProcessor.sendToAllAround(PacketProcessor.PKG_PARTICLES, clayMan.dimension, clayMan.posX, clayMan.posY, clayMan.posZ, 64.0D,
                                            Quintet.with(PacketParticleFX.FX_BREAK, clayMan.posX, clayMan.posY, clayMan.posZ,
                                                         Item.itemRegistry.getNameForObject(Items.fermented_spider_eye)
                                            )
            );
        }
        return super.onUpdate(clayMan, upgradeInst);
    }

    @Override
    public AttackState onTargeting(EntityClayMan clayMan, SoldierUpgradeInst upgradeInst, EntityClayMan target) {
        return AttackState.DENY;
    }

    @Override
    public Pair<Float, Boolean> onSoldierHurt(EntityClayMan clayMan, SoldierUpgradeInst upgradeInst, DamageSource source, float damage) {
        if( source.getEntity() instanceof EntityClayMan ) {
            clayMan.targetSoldier((EntityClayMan)source.getEntity(), false);
        }
        return super.onSoldierHurt(clayMan, upgradeInst, source, damage);
    }

    @Override
    public void onPickup(EntityClayMan clayMan, ItemStack stack) {
        stack.stackSize--;
        clayMan.playSound("random.pop", 1.0F, 1.0F);
    }
}