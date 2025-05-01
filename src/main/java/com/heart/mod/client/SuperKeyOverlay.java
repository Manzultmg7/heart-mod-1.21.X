package com.heart.mod.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.Identifier;

public class SuperKeyOverlay implements ClientModInitializer {
    private static int ticksRemaining = 0;
    private static final Identifier SUPER_KEY_TEXTURE = Identifier.of("heart-mod", "textures/item/super_key.png");

    @Override
    public void onInitializeClient() {
        // Listen for tick to reduce timer
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (ticksRemaining > 0) {
                ticksRemaining--;
            }
        });

        // Draw overlay when active
        HudRenderCallback.EVENT.register((drawContext, tickDelta) -> {
            if (ticksRemaining > 0) {
                MinecraftClient client = MinecraftClient.getInstance();
                int width = client.getWindow().getScaledWidth();
                int height = client.getWindow().getScaledHeight();

                // Center the image (64x64 size assumed)
                drawContext.drawTexture(SUPER_KEY_TEXTURE, width / 2 - 32, height / 2 - 32, 0, 0, 64, 64, 64, 64);
            }
        });
    }

    // Call this from SuperKeyItem via network packet or directly on client
    public static void triggerOverlay() {
        ticksRemaining = 40; // Show for 2 seconds (40 ticks)
    }
}
