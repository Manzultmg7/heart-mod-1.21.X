package com.heart.mod.player;

import com.heart.mod.access.PlayerHeartData;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.server.network.ServerPlayerEntity;

public class PlayerHealthHandler {
    public static void registerPlayerHealthModifier() {

        // On full login (better than AFTER_RESPAWN for clean world)
        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
            ServerPlayerEntity player = handler.getPlayer();
            PlayerHeartData data = (PlayerHeartData) player;

            if (data.getBonusHearts() == -1 || data.getBonusHearts() == 0) {
                // New world or fresh player — force reset to 3 hearts
                var attr = player.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH);
                if (attr != null && attr.getValue() > 6.0) {
                    attr.clearModifiers();
                    attr.setBaseValue(6.0);
                    player.setHealth(6.0F);
                    data.setBonusHearts(0); // explicitly set
                }
            } else {
                // Existing player — apply hearts
                data.applyBonusHearts(player);
            }
        });

        // Preserve hearts on respawn
        ServerPlayerEvents.COPY_FROM.register((oldPlayer, newPlayer, alive) -> {
            PlayerHeartData oldData = (PlayerHeartData) oldPlayer;
            PlayerHeartData newData = (PlayerHeartData) newPlayer;
            newData.setBonusHearts(oldData.getBonusHearts());
        });
    }
}
