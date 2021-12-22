package me.hellsingdarge.compactstatuseffects.modules

import com.mojang.blaze3d.matrix.MatrixStack
import me.hellsingdarge.compactstatuseffects.AnchoredTextRenderer
import me.hellsingdarge.compactstatuseffects.Utils
import net.minecraft.client.Minecraft
import net.minecraft.potion.EffectInstance
import net.minecraft.util.math.MathHelper
import kotlin.math.max

object HudTimer
{
    private val mc: Minecraft by lazy { Minecraft.getInstance() }
    private val textRenderer by lazy { AnchoredTextRenderer(mc.font) }

    @JvmStatic
    fun draw(matrices: MatrixStack, effects: Collection<EffectInstance>)
    {
        var i = 0
        var j = 0

        effects.forEach { inst ->
            if (inst.showIcon())
            {
                val fontSize = 6
                var k = mc.window.guiScaledWidth
                var l = 24

                if (inst.effect.isBeneficial)
                {
                    k -= 25 * i
                    i++
                }
                else
                {
                    k -= 25 * j
                    l += 26
                    j++
                }

                val colour = 0xD3D3D3.let { base ->
                    if (inst.duration <= 200)
                    {
                        val m = 10 - inst.duration / 20
                        val f = MathHelper.clamp(inst.duration.toFloat() / 10.0f / 5.0f * 0.5f, 0.0f, 0.5f) +
                                MathHelper.cos(inst.duration.toFloat() * 3.1415927f / 5.0f) *
                                MathHelper.clamp(m.toFloat() / 10.0f * 0.25f, 0.0f, 0.25f)
                        // Must be 4, otherwise TextRenderer.tweakTransparency will convert it to full brightness
                        max((f * 255).toInt(), 4).shl(24) + base
                    }
                    else
                    {
                        0xFF.shl(24) + base
                    }
                }

                textRenderer.drawRightAlign(matrices, Utils.effectDurationToStr(inst), k - 3.25f, l - 0.25f, colour = colour, withShadow = true, fontSize = fontSize)
            }
        }
    }
}