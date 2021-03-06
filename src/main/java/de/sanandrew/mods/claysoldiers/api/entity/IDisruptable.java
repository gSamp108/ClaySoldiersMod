/* ******************************************************************************************************************
   * Authors:   SanAndreasP
   * Copyright: SanAndreasP
   * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
   *                http://creativecommons.org/licenses/by-nc-sa/4.0/
   *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.api.entity;

import de.sanandrew.mods.claysoldiers.api.CsmConstants;
import net.minecraft.util.DamageSource;

public interface IDisruptable
{
    DamageSource DISRUPT_DAMAGE = new DamageSource(CsmConstants.ID + ".disrupt").setDamageIsAbsolute().setDamageBypassesArmor();

    void disrupt();

    DisruptState getDisruptableState();

    enum DisruptState
    {
        ALL,
        ALL_DOLLS,
        SOLDIERS,
        MOUNTS,
        COMPANIONS,
        CLAY;

        public static final DisruptState[] VALUES = values();
    }
}
