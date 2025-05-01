package com.heart.mod.player;

import net.minecraft.nbt.NbtCompound;

public class PlayerHealthComponent {
    private int bonusHearts = 0;

    public void addBonusHeart() {
        bonusHearts++;
    }

    public int getBonusHearts() {
        return bonusHearts;
    }

    public void readFromNbt(NbtCompound nbt) {
        this.bonusHearts = nbt.getInt("BonusHearts");
    }

    public void writeToNbt(NbtCompound nbt) {
        nbt.putInt("BonusHearts", bonusHearts);
    }
}
