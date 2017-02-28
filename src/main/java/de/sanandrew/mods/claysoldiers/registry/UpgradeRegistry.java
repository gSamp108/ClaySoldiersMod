/* ******************************************************************************************************************
   * Authors:   SanAndreasP
   * Copyright: SanAndreasP
   * License:   Creative Commons Attribution-NonCommercial-ShareAlike 4.0 International
   *                http://creativecommons.org/licenses/by-nc-sa/4.0/
   *******************************************************************************************************************/
package de.sanandrew.mods.claysoldiers.registry;

import com.google.common.collect.ImmutableList;
import de.sanandrew.mods.claysoldiers.api.CsmConstants;
import de.sanandrew.mods.claysoldiers.api.soldier.ISoldierUpgrade;
import de.sanandrew.mods.claysoldiers.api.soldier.IUpgradeRegistry;
import de.sanandrew.mods.claysoldiers.registry.upgrade.UpgradeFlint;
import de.sanandrew.mods.claysoldiers.registry.upgrade.UpgradeStick;
import de.sanandrew.mods.claysoldiers.util.HashItemStack;
import de.sanandrew.mods.sanlib.lib.util.ItemStackUtils;
import de.sanandrew.mods.sanlib.lib.util.MiscUtils;
import net.minecraft.item.ItemStack;
import org.apache.logging.log4j.Level;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public final class UpgradeRegistry
        implements IUpgradeRegistry
{
    public static final UpgradeRegistry INSTANCE = new UpgradeRegistry();

    private final List<ISoldierUpgrade> upgrades;
    private final Map<UUID, ISoldierUpgrade> uuidUpgradeMap;
    private final Map<ISoldierUpgrade, UUID> upgradeUuidMap;
    private final Map<HashItemStack, ISoldierUpgrade> stackUpgradeMap;

    private UpgradeRegistry() {
        this.uuidUpgradeMap = new HashMap<>();
        this.upgradeUuidMap = new HashMap<>();
        this.stackUpgradeMap = new HashMap<>();
        this.upgrades = new ArrayList<>();
    }

    @Override
    public boolean registerUpgrade(UUID id, ISoldierUpgrade upgradeInst) {
        if( id == null || upgradeInst == null ) {
            CsmConstants.LOG.log(Level.WARN, String.format("Upgrade ID and instance cannot be null nor empty for ID %s!", id));
            return false;

        }

        ItemStack upgItem = upgradeInst.getStack();
        if( !ItemStackUtils.isValid(upgItem) ) {
            CsmConstants.LOG.log(Level.WARN, String.format("Upgrade item is invalid for ID %s!", id));
            return false;
        }

        if( this.uuidUpgradeMap.containsKey(id) ) {
            CsmConstants.LOG.log(Level.WARN, String.format("Duplicate Upgrade ID %s!", id));
            return false;
        }

        if( this.upgradeUuidMap.containsKey(upgradeInst) ) {
            CsmConstants.LOG.log(Level.WARN, String.format("Duplicate Upgrade instances for %s!", id));
            return false;
        }

        HashItemStack hStack = new HashItemStack(upgItem);
        if( this.stackUpgradeMap.containsKey(hStack) ) {
            CsmConstants.LOG.log(Level.WARN, String.format("Duplicate Upgrade Item %s for ID %s!", upgItem, id));
            return false;
        }

        this.uuidUpgradeMap.put(id, upgradeInst);
        this.upgradeUuidMap.put(upgradeInst, id);
        this.stackUpgradeMap.put(hStack, upgradeInst);
        this.upgrades.add(upgradeInst);

        return true;
    }

    @Nullable
    @Override
    public ISoldierUpgrade getUpgrade(UUID id) {
        return this.uuidUpgradeMap.get(id);
    }

    @Nullable
    @Override
    public UUID getId(ISoldierUpgrade upgrade) {
        return this.upgradeUuidMap.get(upgrade);
    }

    @Nullable
    @Override
    public ISoldierUpgrade getUpgrade(ItemStack stack) {
        return MiscUtils.defIfNull(this.stackUpgradeMap.get(new HashItemStack(stack)), this.stackUpgradeMap.get(new HashItemStack(stack, true)));
    }

    @Override
    public List<ISoldierUpgrade> getUpgrades() {
        return ImmutableList.copyOf(this.upgrades);
    }

    public static void initialize(IUpgradeRegistry registry) {
        registry.registerUpgrade(MH_STICK, new UpgradeStick());
        registry.registerUpgrade(MC_FLINT, new UpgradeFlint());
    }

    public static final UUID MH_STICK = UUID.fromString("31F0A3DB-F1A7-4418-9EA6-A9D0C900EB41");
    public static final UUID MC_FLINT = UUID.fromString("63342EEB-932B-4330-9B60-C5E21434A0B8");
}