package me.hellsingdarge.compactstatuseffects.mixins;


import com.mojang.blaze3d.matrix.MatrixStack;
import me.hellsingdarge.compactstatuseffects.Config;
import me.hellsingdarge.compactstatuseffects.CustomEffectsDisplay;
import me.hellsingdarge.compactstatuseffects.config.ModConfig;
import net.minecraft.client.gui.DisplayEffectsScreen;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.text.ITextComponent;
import org.checkerframework.checker.units.qual.A;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DisplayEffectsScreen.class)
public abstract class AbstractInventoryScreenMixin<T extends Container> extends ContainerScreen<T> {
    public AbstractInventoryScreenMixin(T handler, PlayerInventory inventory, ITextComponent title) {
        super(handler, inventory, title);
    }

    @Redirect(method = "renderEffects", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DisplayEffectsScreen;renderBackgrounds(Lcom/mojang/blaze3d/matrix/MatrixStack;IILjava/lang/Iterable;)V"))
    void redirectDrawBackground(DisplayEffectsScreen<?> ais, MatrixStack matrixStack, int i, int j, Iterable<EffectInstance> effects) {
        CustomEffectsDisplay customEffectsDisplay = new CustomEffectsDisplay(matrixStack, leftPos, topPos, effects);
        customEffectsDisplay.draw();
    }

    @Redirect(method = "checkEffectRendering", at = @At(value = "FIELD", target = "Lnet/minecraft/client/gui/DisplayEffectsScreen;leftPos:I", opcode = Opcodes.PUTFIELD, ordinal = 1))
    void redirectOffset(DisplayEffectsScreen<?> abstractInventoryScreen, int value) {
        ModConfig config = Config.getConfig();
        switch (config.getModule()) {
            case NO_NAME:
                this.leftPos = (this.width - this.imageWidth) / 2 + config.getNoName().getUiOffset() * 20;
                break;
            case NO_SPRITE:
                this.leftPos = (this.width - this.imageWidth) / 2 + config.getNoSprite().getUiOffset() * 20;
                break;
            case ONLY_NAME:
                this.leftPos = (this.width - this.imageWidth) / 2 + config.getOnlyName().getUiOffset() * 20;
                break;
        }
    }

    @Inject(method = "renderBackgrounds", at = @At("HEAD"), cancellable = true)
    void onDrawSprite(MatrixStack matrixStack, int i, int j, Iterable<EffectInstance> iterable, CallbackInfo ci) {
        ci.cancel();
    }

    @Inject(method = "renderLabels", at = @At("HEAD"), cancellable = true)
    void onDrawDescriptions(MatrixStack matrixStack, int i, int j, Iterable<EffectInstance> iterable, CallbackInfo ci) {
        ci.cancel();
    }

    @Inject(method = "renderIcons", at = @At("HEAD"), cancellable = true)
    private void onDrawIcons(MatrixStack pPoseStack, int pRenderX, int pYOffset, Iterable<EffectInstance> pEffects, CallbackInfo ci) {
        ci.cancel();
    }
}
