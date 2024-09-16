package org.example.exmod.blocks

import com.github.puzzle.game.block.IModBlock
import com.github.puzzle.game.generators.BlockEventGenerator
import com.github.puzzle.game.generators.BlockGenerator
import com.github.puzzle.game.generators.BlockGenerator.State
import com.github.puzzle.game.generators.BlockModelGenerator
import finalforeach.cosmicreach.blocks.BlockPosition
import finalforeach.cosmicreach.blocks.BlockState
import finalforeach.cosmicreach.entities.player.Player
import finalforeach.cosmicreach.ui.UI
import finalforeach.cosmicreach.util.Identifier
import finalforeach.cosmicreach.world.Zone
import org.example.exmod.Constants
import java.util.*

class Bedrock : IModBlock {

    val BLOCK_ID: Identifier = Identifier.of(Constants.MOD_ID, "bedrock")
    val ALL_TEXTURE: Identifier = Identifier.of("base", "textures/blocks/lunar_soil.png")

    override fun getIdentifier(): Identifier {
        return BLOCK_ID
    }

    override fun onBreak(zone: Zone?, player: Player?, blockState: BlockState?, position: BlockPosition?) {
        val slot = UI.hotbar.selectedSlot ?: return
        if (slot.itemStack != null) {
            val selected = slot.itemStack.item
            val itemId = selected.id
            if (itemId.startsWith(BLOCK_ID.toString())) {
                // make the block breakable when the player holds bedrock
                super.onBreak(zone, player, blockState, position)
            }
        }
        // make the block unbreakable, by omitting the super call here
    }

    override fun getBlockGenerator(): BlockGenerator {
        val generator = BlockGenerator(BLOCK_ID)
        val s = generator.createBlockState("default", "model", true, "events", true)
        generator.addBlockEntity(Constants.MOD_ID + ":example_entity", HashMap<String, Any>())
        s.stateGenerators = arrayOf()
        s.tags = arrayOf()
        return generator
    }

    override fun getBlockModelGenerators(blockId: Identifier?): List<BlockModelGenerator> {
        val generator = BlockModelGenerator(blockId, "model")
        generator.createTexture("all", ALL_TEXTURE)
        generator.createCuboid(0f, 0f, 0f, 16f, 16f, 16f, "all")
        return listOf(generator)
    }

    override fun getBlockEventGenerators(blockId: Identifier?): List<BlockEventGenerator> {
        val generator = BlockEventGenerator(blockId, "events")
        return listOf(generator)
    }

}