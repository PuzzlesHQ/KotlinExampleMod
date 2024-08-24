package org.example.exmod.items

import com.github.puzzle.core.Identifier
import com.github.puzzle.core.Puzzle
import com.github.puzzle.core.resources.ResourceLocation
import com.github.puzzle.game.items.IModItem
import com.github.puzzle.game.items.ITickingItem
import com.github.puzzle.game.items.data.DataTag
import com.github.puzzle.game.items.data.DataTagManifest
import com.github.puzzle.game.items.data.attributes.IntDataAttribute
import com.github.puzzle.game.items.data.attributes.ListDataAttribute
import com.github.puzzle.game.util.DataTagUtil
import finalforeach.cosmicreach.entities.ItemEntity
import finalforeach.cosmicreach.entities.player.Player
import finalforeach.cosmicreach.items.ItemSlot
import finalforeach.cosmicreach.items.ItemStack
import finalforeach.cosmicreach.world.Zone
import org.example.exmod.Constants
import java.awt.Desktop
import java.io.IOException
import java.net.URI
import java.net.URISyntaxException

class ExampleCycleItem : IModItem, ITickingItem {

    private var tagManifest: DataTagManifest = DataTagManifest()
    private var id: Identifier = Identifier(Constants.MOD_ID, "example_cycling_item")

    override fun toString(): String {
        return id.toString()
    }

    var texture_count: Int = 0

    init {
        addTexture(
            IModItem.MODEL_2_5D_ITEM,
            ResourceLocation(Puzzle.MOD_ID, "textures/items/null_stick.png"),
            ResourceLocation("base", "textures/items/axe_stone.png"),
            ResourceLocation("base", "textures/items/pickaxe_stone.png"),
            ResourceLocation("base", "textures/items/shovel_stone.png"),
            ResourceLocation("base", "textures/items/medkit.png"),
            ResourceLocation(Puzzle.MOD_ID, "textures/items/block_wrench.png"),
            ResourceLocation(Puzzle.MOD_ID, "textures/items/checker_board.png"),
            ResourceLocation(Puzzle.MOD_ID, "textures/items/checker_board1.png"),
            ResourceLocation(Puzzle.MOD_ID, "textures/items/checker_board2.png")
        )

        addTexture(
            IModItem.MODEL_2D_ITEM,
            ResourceLocation(Puzzle.MOD_ID, "textures/items/null_stick.png"),
            ResourceLocation("base", "textures/items/axe_stone.png"),
            ResourceLocation("base", "textures/items/pickaxe_stone.png"),
            ResourceLocation("base", "textures/items/shovel_stone.png"),
            ResourceLocation("base", "textures/items/medkit.png"),
            ResourceLocation(Puzzle.MOD_ID, "textures/items/block_wrench.png"),
            ResourceLocation(Puzzle.MOD_ID, "textures/items/checker_board.png"),
            ResourceLocation(Puzzle.MOD_ID, "textures/items/checker_board1.png"),
            ResourceLocation(Puzzle.MOD_ID, "textures/items/checker_board2.png")
        )

        texture_count = (getTagManifest().getTag<Any>("textures").attribute as ListDataAttribute<*>).value.size - 1
    }

    override fun isTool(): Boolean {
        return true
    }

    override fun use(slot: ItemSlot, player: Player) {
        if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
            try {
                Desktop.getDesktop().browse(URI("https://discord.gg/VeEnVHwRXN"))
            } catch (e: IOException) {
                throw RuntimeException(e)
            } catch (e: URISyntaxException) {
                throw RuntimeException(e)
            }
        }
    }

    override fun getIdentifier(): Identifier {
        return id
    }

    override fun getTagManifest(): DataTagManifest {
        return tagManifest
    }

    override fun isCatalogHidden(): Boolean {
        return false
    }

    private fun getCurrentEntry(stack: ItemStack): Int {
        val manifest = DataTagUtil.getManifestFromStack(stack)
        if (!manifest.hasTag("currentEntry")) manifest.addTag(DataTag("currentEntry", IntDataAttribute(0)))
        println(manifest.hasTag("currentEntry"))
        return manifest.getTag<Any>("currentEntry").getTagAsType(Integer::class.java).value.toInt()
    }

    private fun setCurrentEntry(stack: ItemStack, entry: Int) {
        val manifest = DataTagUtil.getManifestFromStack(stack)
        manifest.addTag(DataTag("currentEntry", IntDataAttribute(entry)))
        DataTagUtil.setManifestOnStack(manifest, stack)
    }

    override fun tickStack(fixedUpdateTimeStep: Float, stack: ItemStack, isBeingHeld: Boolean) {
        var textureEntry = getCurrentEntry(stack)
        textureEntry = if (textureEntry >= texture_count) 0 else textureEntry + 1
        setCurrentEntry(stack, textureEntry)
    }

    override fun tickEntity(zone: Zone, deltaTime: Double, entity: ItemEntity, stack: ItemStack) {
        var textureEntry = getCurrentEntry(stack)
        textureEntry = if (textureEntry >= texture_count) 0 else textureEntry + 1
        setCurrentEntry(stack, textureEntry)
    }
}