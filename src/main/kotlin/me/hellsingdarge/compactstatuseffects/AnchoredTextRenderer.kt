package me.hellsingdarge.compactstatuseffects

import com.mojang.blaze3d.matrix.MatrixStack
import net.minecraft.client.gui.AbstractGui
import net.minecraft.client.gui.FontRenderer
import net.minecraft.client.renderer.IRenderTypeBuffer
import net.minecraft.client.renderer.Tessellator
import org.lwjgl.opengl.GL11

class AnchoredTextRenderer(private val textRenderer: FontRenderer)
{
    // same as TextRenderer.draw, but here for completeness
    fun drawLeftAlign(matrixStack: MatrixStack, text: String, xPivot: Float, yPivot: Float, colour: Int = 0xFFFFFF, withShadow: Boolean = false, fontSize: Int = textRenderer.lineHeight)
    {
        drawAnchor(matrixStack, xPivot, yPivot, 0xFF0000)

        draw(matrixStack, text, xPivot, yPivot - fontSize, colour, withShadow, fontSize)
    }

    fun drawCentreAlign(matrixStack: MatrixStack, text: String, xPivot: Float, yPivot: Float, colour: Int = 0xFFFFFF, withShadow: Boolean = false, fontSize: Int = textRenderer.lineHeight)
    {
        drawAnchor(matrixStack, xPivot, yPivot, 0x00FF00)

        val xPos = xPivot - textRenderer.width(text) / 2 * fontSize / textRenderer.lineHeight
        draw(matrixStack, text, xPos, yPivot - fontSize, colour, withShadow, fontSize)
    }

    fun drawRightAlign(matrixStack: MatrixStack, text: String, xPivot: Float, yPivot: Float, colour: Int = 0xFFFFFF, withShadow: Boolean = false, fontSize: Int = textRenderer.lineHeight)
    {
        drawAnchor(matrixStack, xPivot, yPivot, 0x0000FF)

        val xPos = xPivot - textRenderer.width(text) * fontSize / textRenderer.lineHeight
        draw(matrixStack, text, xPos, yPivot - fontSize, colour, withShadow, fontSize)
    }

    private fun drawAnchor(matrixStack: MatrixStack, x: Float, y: Float, colour: Int)
    {
        if (false)
        {
            val size = 1
            val length = 5
            val debugColour = (0.6f * 255).toInt().shl(24) + colour
            // Horizontal
            AbstractGui.fill(matrixStack, (x - length).toInt(), (y - size).toInt(), (x + length).toInt(), (y + size).toInt(), debugColour)
            // Vertical
            AbstractGui.fill(matrixStack, (x - size).toInt(), (y - length).toInt(), (x + size).toInt(), (y + length).toInt(), debugColour)
        }
    }

    private fun draw(matrixStack: MatrixStack, text: String, xPivot: Float, yPivot: Float, colour: Int = 0xFFFFFF, withShadow: Boolean = false, fontSize: Int = textRenderer.lineHeight)
    {
        drawAnchor(matrixStack, xPivot, yPivot, 0xFFFFFF)

        val fontScale = fontSize / textRenderer.lineHeight.toFloat()
        GL11.glPushMatrix()
        GL11.glScalef(fontScale, fontScale, fontScale)

        val vertexConsumer = IRenderTypeBuffer.immediate(Tessellator.getInstance().builder)

        textRenderer.drawInBatch(
            text,
            xPivot / fontScale,
            yPivot / fontScale,
            colour,
            withShadow,
            matrixStack.last().pose(),
            vertexConsumer,
            false,
            0,
            0xF00F0,
            textRenderer.isBidirectional
        )
        vertexConsumer.endBatch()
        GL11.glPopMatrix()
    }
}