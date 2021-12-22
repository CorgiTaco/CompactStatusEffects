package me.hellsingdarge.compactstatuseffects.modules

import com.mojang.blaze3d.matrix.MatrixStack
import me.hellsingdarge.compactstatuseffects.Utils
import net.minecraft.potion.EffectInstance

class NoSprite(matrixStack: MatrixStack, uiX: Int, uiY: Int, effects: Iterable<EffectInstance>):
    DrawModule(matrixStack, uiX, uiY, effects)
{
    override val width: Int get() = 100
    override val height: Int get() = 31
    override val config = modConfig.noSprite
    override val yIncrement = if (!config.squash) height else height - 7
    override val maxNum = if (!config.squash) config.effectsPerColumn else config.effectsPerColumn + 1

    override fun draw()
    {
        drawBackground { x, y ->
            blit(matrixStack, x - xOffset, y, 0, 60, width, height)
        }

        drawDescription { x, y, instance ->
            val effectName = instance.getName()

            textRenderer.drawLeftAlign(matrixStack, effectName, x + 5f - xOffset, y + 15f, 0xFFFFFF, true)
            textRenderer.drawLeftAlign(matrixStack, Utils.effectDurationToStr(instance), x + 5f - xOffset, y + 25f, 0x7F7F7F, true)

        }
    }
}