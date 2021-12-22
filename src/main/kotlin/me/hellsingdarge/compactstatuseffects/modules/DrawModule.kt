package me.hellsingdarge.compactstatuseffects.modules

import com.mojang.blaze3d.matrix.MatrixStack
import com.mojang.blaze3d.systems.RenderSystem
import me.hellsingdarge.compactstatuseffects.AnchoredTextRenderer
import me.hellsingdarge.compactstatuseffects.Config
import me.hellsingdarge.compactstatuseffects.config.IConfigCommon
import me.hellsingdarge.compactstatuseffects.config.ModConfig
import net.minecraft.client.Minecraft
import net.minecraft.client.gui.AbstractGui
import net.minecraft.client.renderer.texture.PotionSpriteUploader
import net.minecraft.client.renderer.texture.TextureAtlasSprite
import net.minecraft.client.renderer.texture.TextureManager
import net.minecraft.client.resources.I18n
import net.minecraft.potion.EffectInstance
import net.minecraft.util.ResourceLocation

abstract class DrawModule(
    protected val matrixStack: MatrixStack,
    protected val uiX: Int,
    protected val uiY: Int,
    protected val effects: Iterable<EffectInstance>
): AbstractGui()
{
    protected val xOffset: Int get() = width + config.margin - 1
    protected val xDecrement: Int get() = width - 1

    protected val modConfig: ModConfig = Config.getConfig();
    protected val backgroundTexture = ResourceLocation("compactstatuseffects:textures/atlas.png")
    protected val spriteManager: PotionSpriteUploader = mc.mobEffectTextures
    protected val textureManager: TextureManager = mc.textureManager
    protected val textRenderer = AnchoredTextRenderer(mc.font)

    protected abstract val width: Int
    protected abstract val height: Int
    protected abstract val config: IConfigCommon
    protected abstract val yIncrement: Int
    protected abstract val maxNum: Int

    open fun draw()
    {
    }

    protected inline fun drawBackground(body: (x: Int, y: Int) -> Unit)
    {
        textureManager.bind(backgroundTexture)
        var i = uiX
        var j = uiY

        repeat(effects.count()) { index ->
            RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F)

            body(i, j)

            i = uiX - ((index + 1) / maxNum) * xDecrement
            j += yIncrement
            if ((index + 1) % maxNum == 0) j = uiY
        }
    }

    protected inline fun drawSprite(body: (x: Int, y: Int, sprite: TextureAtlasSprite) -> Unit)
    {
        var i = uiX
        var j = uiY

        effects.forEachIndexed { index, instance ->
            val effect = instance.effect
            val sprite = spriteManager.get(effect)
            textureManager.bind(sprite.atlas().location())

            body(i, j, sprite)

            i = uiX - ((index + 1) / maxNum) * xDecrement
            j += yIncrement
            if ((index + 1) % maxNum == 0) j = uiY
        }
    }

    protected inline fun drawDescription(body: (x: Int, y: Int, instance: EffectInstance) -> Unit)
    {
        var i = uiX
        var j = uiY

        effects.forEachIndexed { index, instance ->
            body(i, j, instance)

            i = uiX - ((index + 1) / maxNum) * xDecrement
            j += yIncrement
            if ((index + 1) % maxNum == 0) j = uiY
        }
    }

    protected fun EffectInstance.getName(): String
    {
        var effectName = I18n.get(this.effect.descriptionId, *arrayOfNulls(0))

        if (this.amplifier in 1..9)
        {
            effectName += ' ' + I18n.get("enchantment.level." + (this.amplifier + 1), *arrayOfNulls(0))
        }

        return effectName
    }


    protected inline fun onHover(x: Int, y: Int, block: (mouseX: Int, mouseY: Int) -> Unit)
    {
        val mouseX = (mc.mouseHandler.xpos() * mc.window.guiScaledWidth / mc.window.width).toInt()
        val mouseY = (mc.mouseHandler.ypos() * mc.window.guiScaledHeight / mc.window.height).toInt()

        if (mouseX in (x - xDecrement - config.margin + 1)..(x - config.margin - 1) && mouseY in (y + 1)..(y + yIncrement - 1))
        {
            block(mouseX, mouseY)
        }
    }

    companion object
    {
        val mc: Minecraft by lazy { Minecraft.getInstance() }
    }
}