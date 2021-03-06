/* ******************************************************************************************************************
   * Authors:   SanAndreasP
   * Copyright: SanAndreasP
   * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
   *                http://creativecommons.org/licenses/by-nc-sa/4.0/
   *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.api.doll;

import net.minecraft.item.ItemStack;

public interface IDollType
{
    boolean isVisible();
    boolean isValid();
    String getName();
    ItemStack getTypeStack();
    int getItemColor();
}
