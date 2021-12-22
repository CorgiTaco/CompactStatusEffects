package me.hellsingdarge.compactstatuseffects.modules

import com.mojang.blaze3d.matrix.MatrixStack
import me.hellsingdarge.compactstatuseffects.Utils
import net.minecraft.client.resources.I18n
import net.minecraft.potion.EffectInstance


class NoName(matrixStack: MatrixStack, uiX: Int, uiY: Int, effects: Iterable<EffectInstance>):
    DrawModule(matrixStack, uiX, uiY, effects)
{
    override val width: Int get() = 33
    override val height: Int get() = 41
    override val config = modConfig.noName
    override val yIncrement = if (!config.squash) height else height - 7
    override val maxNum = config.effectsPerColumn

    override fun draw()
    {
        drawBackground { x, y ->
            blit(matrixStack, x - xOffset, y, 0, 0, width, height)
        }

        drawSprite { x, y, sprite ->
            blit(matrixStack, x + 8 - xOffset, y + 7, blitOffset, 18, 18, sprite)
        }

        drawDescription { x, y, instance ->
            textRenderer.drawCentreAlign(matrixStack, Utils.effectDurationToStr(instance), x + 17f - xOffset, y + 36f, 0x7F7F7F, true)

            if (config.showLevel && instance.amplifier in 1..9)
            {
                val level: String = if (config.levelInArabic)
                {
                    (instance.amplifier + 1).toString()
                }
                else
                {
                    I18n.get("enchantment.level." + (instance.amplifier + 1))
                }

                textRenderer.drawRightAlign(matrixStack, level, x + 30f - xOffset, y + 27f, withShadow = true)
            }

            onHover(x, y) { mouseX, mouseY ->
                val effectName = instance.getName()

                textRenderer.drawLeftAlign(matrixStack, effectName, mouseX.toFloat(), mouseY.toFloat(), withShadow = true)
            }
        }
    }
}