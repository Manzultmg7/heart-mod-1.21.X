package com.heart.mod.mixin;

import com.heart.mod.access.PlayerHeartData;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin implements PlayerHeartData {

    @Unique
    private static final String BONUS_HEARTS_KEY = "BonusHearts";

    @Unique
    private int bonusHearts = 0;

    @Inject(method = "writeCustomDataToNbt", at = @At("TAIL"))
    private void writeBonusHeartsToNbt(NbtCompound nbt, CallbackInfo ci) {
        nbt.putInt(BONUS_HEARTS_KEY, bonusHearts);
    }

    @Inject(method = "readCustomDataFromNbt", at = @At("TAIL"))
    private void readBonusHeartsFromNbt(NbtCompound nbt, CallbackInfo ci) {
        if (nbt.contains(BONUS_HEARTS_KEY)) {
            this.bonusHearts = nbt.getInt(BONUS_HEARTS_KEY);
        } else {
            // This means player has never had hearts saved — new world
            this.bonusHearts = -1;
        }
    }

    @Override
    public int getBonusHearts() {
        return bonusHearts;
    }

    @Override
    public void setBonusHearts(int value) {
        this.bonusHearts = value;
    }
    @Override
    public void applyBonusHearts(PlayerEntity player) {
        var attr = player.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH);
        if (attr != null) {
            attr.clearModifiers();

            // Dynamically calculate base health
            double calculatedMaxHealth = 6.0 + (bonusHearts * 2.0);
            double clampedHealth = Math.max(2.0, Math.min(calculatedMaxHealth, 20.0));

            attr.setBaseValue(clampedHealth);
            player.setHealth((float) clampedHealth); // optional: restore full health

        }
    }




}
