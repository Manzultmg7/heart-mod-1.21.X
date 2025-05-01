package com.heart.mod.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;

@Mixin(InGameHud.class)
public class InGameHudMixin {

    @Unique
    private static final Identifier LOCKED_HEART_TEXTURE = Identifier.of("heart-mod", "textures/item/locked_heart.png");

    @Inject(method = "renderHealthBar", at = @At("TAIL"))
    private void renderLockedHearts(DrawContext context, PlayerEntity player, int x, int y, int lines, int regeneratingHeartIndex, float maxHealth, int lastHealth, int health, int absorption, boolean blinking, CallbackInfo ci) {
        MinecraftClient client = MinecraftClient.getInstance();
        int totalHearts = 10; // 10 hearts = 20 health
        int activeHearts = (int) (Objects.requireNonNull(player.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH)).getValue() / 2);

        RenderSystem.setShaderTexture(0, LOCKED_HEART_TEXTURE);
        RenderSystem.enableBlend();

        for (int i = activeHearts; i < totalHearts; i++) {
            context.drawTexture(LOCKED_HEART_TEXTURE, x + i * 8, y, 0, 0, 9, 9, 9, 9);
        }

        RenderSystem.disableBlend();
    }
}
