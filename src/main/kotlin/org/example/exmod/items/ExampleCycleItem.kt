package org.example.exmod.items

import com.github.puzzle.core.Puzzle
import com.github.puzzle.game.items.IModItem
import com.github.puzzle.game.items.ITickingItem
import com.github.puzzle.game.items.data.DataTagManifest
import com.github.puzzle.game.items.data.attributes.ListDataAttribute
import finalforeach.cosmicreach.entities.ItemEntity
import finalforeach.cosmicreach.entities.player.Player
import finalforeach.cosmicreach.items.ItemSlot
import finalforeach.cosmicreach.items.ItemStack
import finalforeach.cosmicreach.util.Identifier
import finalforeach.cosmicreach.world.Zone
import org.example.exmod.Constants
import java.awt.Desktop
import java.io.IOException
import java.net.URI
import java.net.URISyntaxException

class ExampleCycleItem : IModItem, ITickingItem {

    private var tagManifest: DataTagManifest = DataTagManifest()
    private var id: Identifier = Identifier.of(Constants.MOD_ID, "example_cycling_item")

    override fun toString(): String {
        return id.toString()
    }

    var texture_count: Int = 0

    init {
        addTexture(
            IModItem.MODEL_2_5D_ITEM,
            Identifier.of(Puzzle.MOD_ID, "textures/items/null_stick.png"),
            Identifier.of("base", "textures/items/axe_stone.png"),
            Identifier.of("base", "textures/items/pickaxe_stone.png"),
            Identifier.of("base", "textures/items/shovel_stone.png"),
            Identifier.of("base", "textures/items/medkit.png"),
            Identifier.of(Puzzle.MOD_ID, "textures/items/block_wrench.png"),
            Identifier.of(Puzzle.MOD_ID, "textures/items/checker_board.png"),
            Identifier.of(Puzzle.MOD_ID, "textures/items/checker_board1.png"),
            Identifier.of(Puzzle.MOD_ID, "textures/items/checker_board2.png")
        )

        addTexture(
            IModItem.MODEL_2D_ITEM,
            Identifier.of(Puzzle.MOD_ID, "textures/items/null_stick.png"),
            Identifier.of("base", "textures/items/axe_stone.png"),
            Identifier.of("base", "textures/items/pickaxe_stone.png"),
            Identifier.of("base", "textures/items/shovel_stone.png"),
            Identifier.of("base", "textures/items/medkit.png"),
            Identifier.of(Puzzle.MOD_ID, "textures/items/block_wrench.png"),
            Identifier.of(Puzzle.MOD_ID, "textures/items/checker_board.png"),
            Identifier.of(Puzzle.MOD_ID, "textures/items/checker_board1.png"),
            Identifier.of(Puzzle.MOD_ID, "textures/items/checker_board2.png")
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

    override fun getName(): String {
        return "Example Cycle Item"
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