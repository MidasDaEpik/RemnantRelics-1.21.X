package com.midasdaepik.remnantrelics.item;

import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class Dragonbone extends Item {
    public Dragonbone(Properties pProperties) {
        super(pProperties.fireResistant());
    }

    @Override
    public boolean onEntityItemUpdate(ItemStack pItemStack, ItemEntity pItemEntity) {
        pItemEntity.setDeltaMovement(pItemEntity.getDeltaMovement().multiply(0.98, 0.98, 0.98));
        if (!pItemEntity.isNoGravity()) {
            pItemEntity.setNoGravity(true);
        }
        return false;
    }
}
