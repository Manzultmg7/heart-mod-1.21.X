package com.heart.mod.item;

import com.heart.mod.access.PlayerHeartData;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.EntityEffectParticleEffect;
import net.minecraft.particle.ParticleType;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import net.minecraft.server.world.ServerWorld;

public class CursedKeyItem extends Item {

    public CursedKeyItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        if (!world.isClient) {
            PlayerHeartData data = (PlayerHeartData) player;
            int bonusHearts = data.getBonusHearts();
            int nextBonus = bonusHearts - 1;
            double simulatedHealth = 6.0 + (nextBonus * 2.0);

            if (simulatedHealth < 2.0) {
                player.sendMessage(Text.literal("§cYou can't go below 1 heart!"), true);
                return TypedActionResult.fail(player.getStackInHand(hand));
            }
            // Decrease bonus heart count and apply
            data.setBonusHearts(bonusHearts - 1);
            data.applyBonusHearts(player);

            // Effects
            world.playSound(null, player.getBlockPos(), SoundEvents.ENTITY_WITHER_HURT, player.getSoundCategory(), 1.0F, 1.0F);
            if (world instanceof ServerWorld serverWorld) {

                serverWorld.spawnParticles(ParticleTypes.WITCH, player.getX(), player.getY() + 1.0, player.getZ(), 6, 0.5, 0.5, 0.5, 0.01);
            }
          //only use in multiplayer && player.getServer().getPlayerManager().getPlayerList().size() > 1
            // Multiplayer message
            if (player.getServer() != null ) {
                player.getServer().getPlayerManager().broadcast(
                        Text.literal("§c" + player.getName().getString() + " lost a heart..."), true
                );
            }

            player.getStackInHand(hand).decrement(1);
            return TypedActionResult.success(player.getStackInHand(hand), false);
        }

        return TypedActionResult.pass(player.getStackInHand(hand));
    }

}
