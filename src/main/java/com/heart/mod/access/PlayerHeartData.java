package com.heart.mod.access;

import net.minecraft.entity.player.PlayerEntity;

public interface PlayerHeartData {
    int getBonusHearts();
    void setBonusHearts(int value);
    void applyBonusHearts(PlayerEntity player);
}
