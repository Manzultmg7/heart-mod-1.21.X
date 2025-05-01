package com.heart.mod.item;

import com.heart.mod.access.PlayerHeartData;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import net.minecraft.server.world.ServerWorld;

import java.util.Objects;

public class HeartKeyItem extends Item {

    public HeartKeyItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        ItemStack stack = player.getStackInHand(hand);

        if (!world.isClient) {
            PlayerHeartData data = (PlayerHeartData) player;
            int currentBonusHearts = data.getBonusHearts();

            // Max 7 bonus hearts (6.0 base + 7×2.0 = 20.0 max health)
            if (currentBonusHearts >= 7) {
                player.sendMessage(
                        Text.literal("§cYou already have full health!"),
                        true
                );
                return TypedActionResult.fail(stack);
            }

            // Update bonus heart count and apply modifiers
            data.setBonusHearts(currentBonusHearts + 1);
            data.applyBonusHearts(player);

            // Broadcast to all players in multiplayer
            if (player.getServer() != null) {
                player.getServer().getPlayerManager().broadcast(
                        Text.literal("§b" + player.getName().getString() + " unlocked a new heart!"),
                        true
                );
            }

            // Set current health to new max
            double newMaxHealth = Objects.requireNonNull(player.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH)).getValue();
            player.setHealth((float) newMaxHealth);


            // Play End Portal activation sound (when last Eye of Ender is placed)
            world.playSound(
                    null,
                    player.getBlockPos(),
                    SoundEvents.BLOCK_END_PORTAL_SPAWN,
                    player.getSoundCategory(),
                    1.0F,
                    1.0F
            );

            // Spawn heart particles
            if (world instanceof ServerWorld serverWorld) {
                serverWorld.spawnParticles(
                        net.minecraft.particle.ParticleTypes.HEART,
                        player.getX(), player.getY() + 1.0, player.getZ(),
                        6, 0.5, 0.5, 0.5, 0.0
                );
            }

            stack.decrement(1);
            return TypedActionResult.success(stack, false);
        }

        return TypedActionResult.pass(stack);
    }
}
