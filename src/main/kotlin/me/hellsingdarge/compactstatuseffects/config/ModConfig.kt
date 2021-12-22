package me.hellsingdarge.compactstatuseffects.config

class ModConfig
{
    val module: Module = Module.NO_NAME

    val noName = NoNameConfig()

    val onlyName = OnlyNameConfig()

    val noSprite = NoSpriteConfig()

    enum class Module
    {
        NO_NAME,
        NO_SPRITE,
        ONLY_NAME;
    }

    class NoSpriteConfig: IConfigCommon
    {
        override val uiOffset: Int = 0

        override val margin: Int = 10

        override val squash: Boolean = false

        override val effectsPerColumn: Int = 5
    }

    class NoNameConfig: IConfigCommon
    {
        override val uiOffset: Int = 0

        override val margin: Int = 10

        override val squash: Boolean = false

        override val effectsPerColumn: Int = 4

        val showLevel: Boolean = true
        val levelInArabic: Boolean = false
    }

    class OnlyNameConfig: IConfigCommon
    {
        override val uiOffset: Int = 0

        override val margin: Int = 10

        override val effectsPerColumn: Int = 8

        override val squash: Boolean = false

        val permanentColour: Int = 0xFFFFFF

        val wontExpireSoonColour: Int = 0x00B009

        val soonToExpireColour: Int = 0xD4CD00

        val expiringColour: Int = 0xD40000

        val expiringBound: Int = 20

        val soonToExpireBound: Int = 60
    }
}