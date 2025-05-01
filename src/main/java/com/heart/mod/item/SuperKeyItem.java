package com.heart.mod.item;

import com.heart.mod.access.PlayerHeartData;
import com.heart.mod.client.SuperKeyOverlay;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

import java.util.Objects;

public class SuperKeyItem extends Item {

    public SuperKeyItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        ItemStack stack = player.getStackInHand(hand);

        if (!world.isClient) {
            PlayerHeartData data = (PlayerHeartData) player;
            int bonusHearts = data.getBonusHearts();

            // Max 7 bonus hearts (6 base + 7×2 = 20)
            if (bonusHearts >= 7) {
                player.sendMessage(Text.literal("§cYou already have full health!"), true);
                return TypedActionResult.fail(stack);
            }

            int heartsToAdd = Math.min(3, 7 - bonusHearts); // Only add up to cap
            data.setBonusHearts(bonusHearts + heartsToAdd);
            data.applyBonusHearts(player);

            // Update current health to max
            double newMax = Objects.requireNonNull(player.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH)).getValue();
            player.setHealth((float) newMax);

            //  Multiplayer message
            if (player.getServer() != null) {
                player.getServer().getPlayerManager().broadcast(
                        Text.literal("§a" + player.getName().getString() + " unlocked " + heartsToAdd + " hearts using a Super Key!"),
                        true
                );
            }

            // Totem animation (visual)
            player.swingHand(hand);

            //  Sound
            world.playSound(null, player.getBlockPos(), SoundEvents.UI_TOAST_CHALLENGE_COMPLETE, player.getSoundCategory(), 1.0F, 1.0F);

            //  Particles
            if (world instanceof ServerWorld serverWorld) {
                serverWorld.spawnParticles(
                        net.minecraft.particle.ParticleTypes.TOTEM_OF_UNDYING,
                        player.getX(), player.getY() + 1.0, player.getZ(),
                        50, 0.6, 0.6, 0.6, 0.05
                );
            }
        } else {
            SuperKeyOverlay.triggerOverlay();
        }

        //  Consume item
        stack.decrement(1);
        return TypedActionResult.success(stack, world.isClient);
    }
}
