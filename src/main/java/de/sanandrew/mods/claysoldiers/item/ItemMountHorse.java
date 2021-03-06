/* ******************************************************************************************************************
   * Authors:   SanAndreasP
   * Copyright: SanAndreasP
   * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
   *                http://creativecommons.org/licenses/by-nc-sa/4.0/
   *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.item;

import de.sanandrew.mods.claysoldiers.api.CsmConstants;
import de.sanandrew.mods.claysoldiers.api.NBTConstants;
import de.sanandrew.mods.claysoldiers.api.doll.ItemDoll;
import de.sanandrew.mods.claysoldiers.entity.mount.EntityClayHorse;
import de.sanandrew.mods.claysoldiers.registry.mount.EnumClayHorseType;
import de.sanandrew.mods.claysoldiers.util.CsmConfig;
import de.sanandrew.mods.claysoldiers.util.CsmCreativeTabs;
import de.sanandrew.mods.sanlib.lib.util.ItemStackUtils;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;

public class ItemMountHorse
        extends ItemDoll<EntityClayHorse, EnumClayHorseType>
{
    public ItemMountHorse() {
        super(CsmConstants.ID, "doll_horse", CsmCreativeTabs.DOLLS);
        this.maxStackSize = CsmConfig.BlocksAndItems.Dolls.horseDollStackSize;
    }

    @Override
    public EnumClayHorseType getType(ItemStack stack) {
        if( ItemStackUtils.isItem(stack, this) ) {
            NBTTagCompound nbt = stack.getSubCompound(NBTConstants.S_DOLL_HORSE);
            if( nbt != null && nbt.hasKey(NBTConstants.I_DOLL_TYPE, Constants.NBT.TAG_INT) ) {
                return EnumClayHorseType.VALUES[nbt.getInteger(NBTConstants.I_DOLL_TYPE)];
            }
        }

        return EnumClayHorseType.UNKNOWN;
    }

    @Override
    public EntityClayHorse createEntity(World world, EnumClayHorseType type, ItemStack newDollStack) {
        return new EntityClayHorse(world, type, newDollStack);
    }

    @Override
    public EnumClayHorseType[] getTypes() {
        return EnumClayHorseType.VALUES;
    }

    @Override
    public ItemStack getTypeStack(EnumClayHorseType type) {
        ItemStack stack = new ItemStack(this, 1);
        stack.getOrCreateSubCompound(NBTConstants.S_DOLL_HORSE).setInteger(NBTConstants.I_DOLL_TYPE, type.ordinal());
        return stack;
    }

    @Override
    public SoundEvent getPlacementSound() {
        return SoundEvents.BLOCK_GRAVEL_BREAK;
    }
}
