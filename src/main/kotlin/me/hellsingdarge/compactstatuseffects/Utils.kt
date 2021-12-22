package me.hellsingdarge.compactstatuseffects

import net.minecraft.potion.EffectInstance
import net.minecraft.util.StringUtils
import net.minecraft.util.math.MathHelper

object Utils
{
    fun effectDurationToStr(effect: EffectInstance): String
    {
        return if (effect.isNoCounter)
        {
            "∞"
        }
        else
        {
            val i = MathHelper.floor(effect.duration.toFloat())
            StringUtils.formatTickDuration(i)
        }
    }
}