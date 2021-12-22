package me.hellsingdarge.compactstatuseffects

import com.mojang.blaze3d.matrix.MatrixStack
import me.hellsingdarge.compactstatuseffects.config.ModConfig
import me.hellsingdarge.compactstatuseffects.modules.DrawModule
import me.hellsingdarge.compactstatuseffects.modules.NoName
import me.hellsingdarge.compactstatuseffects.modules.NoSprite
import me.hellsingdarge.compactstatuseffects.modules.OnlyName
import net.minecraft.potion.EffectInstance

class CustomEffectsDisplay(
    private val matrixStack: MatrixStack,
    private val uiX: Int,
    private val uiY: Int,
    private val statusEffects: Iterable<EffectInstance>
)
{
    fun draw()
    {
        val mode: DrawModule = when (Config.getConfig().module)
        {
            ModConfig.Module.NO_NAME -> NoName(matrixStack, uiX, uiY, statusEffects)
            ModConfig.Module.NO_SPRITE -> NoSprite(matrixStack, uiX, uiY, statusEffects)
            ModConfig.Module.ONLY_NAME -> OnlyName(matrixStack, uiX, uiY, statusEffects)
        }

        mode.draw()
    }
}