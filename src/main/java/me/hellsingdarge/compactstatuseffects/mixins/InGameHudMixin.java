package me.hellsingdarge.compactstatuseffects.mixins;


import com.google.common.collect.Ordering;
import com.mojang.blaze3d.matrix.MatrixStack;
import me.hellsingdarge.compactstatuseffects.modules.HudTimer;
import net.minecraft.client.gui.IngameGui;
import net.minecraft.potion.EffectInstance;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Collection;

@Mixin(IngameGui.class)
public class InGameHudMixin
{
    @Inject(
            method = "renderEffects",
            at = @At(
                    value = "INVOKE",
                    target = "Ljava/util/List;forEach(Ljava/util/function/Consumer;)V",
                    shift = At.Shift.AFTER
            ),
            locals = LocalCapture.CAPTURE_FAILHARD
    )
    private void afterRun(MatrixStack matrices, CallbackInfo ci, Collection<EffectInstance> collection)
    {
        HudTimer.draw(matrices, Ordering.natural().reverse().sortedCopy(collection));
    }
}
