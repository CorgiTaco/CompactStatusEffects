package me.hellsingdarge.compactstatuseffects.mixins;

import me.hellsingdarge.compactstatuseffects.Config;
import net.minecraft.client.network.play.ClientPlayNetHandler;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.RegistryKey;
import net.minecraft.world.DimensionType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Supplier;

@Mixin(ClientWorld.class)
public class MixinClientWorld {

    @Inject(method = "<init>", at = @At("RETURN"))
    private void cse_serializeConfig(ClientPlayNetHandler p_i242067_1_, ClientWorld.ClientWorldInfo p_i242067_2_, RegistryKey p_i242067_3_, DimensionType p_i242067_4_, int p_i242067_5_, Supplier p_i242067_6_, WorldRenderer p_i242067_7_, boolean p_i242067_8_, long p_i242067_9_, CallbackInfo ci) {
        Config.getConfig(true);
    }
}
