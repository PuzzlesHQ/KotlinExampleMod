package org.example.exmod.blocks

import com.github.puzzle.game.block.IModBlock
import com.github.puzzle.game.block.generators.BlockEventGenerator
import com.github.puzzle.game.block.generators.BlockGenerator
import com.github.puzzle.game.block.generators.model.BlockModelGenerator
import finalforeach.cosmicreach.blockevents.BlockEventArgs
import finalforeach.cosmicreach.util.Identifier
import org.example.exmod.Constants
import org.example.exmod.api.PlayerExtension
import java.util.*

class Bedrock : IModBlock {

    val BLOCK_ID: Identifier = Identifier.of(Constants.MOD_ID, "bedrock")
    val ALL_TEXTURE: Identifier = Identifier.of("base", "textures/blocks/lunar_soil.png")

    override fun getIdentifier(): Identifier {
        return BLOCK_ID
    }

    override fun onBreak(args: BlockEventArgs?) {
        val slot = (args!!.srcIdentity.player as PlayerExtension).heldItem
        if (slot.itemStack != null) {
            val selected = slot.itemStack.item
            val itemId = selected.id
            if (itemId.startsWith(BLOCK_ID.toString())) {
                // make the block breakable when the player holds bedrock
                super.onBreak(args)
            }
        }
        // make the block unbreakable, by omitting the super call here
    }

    override fun getBlockGenerator(): BlockGenerator {
        val generator = BlockGenerator(BLOCK_ID)
        val s = generator.createBlockState("default", "model", true, "events", true)
        generator.addBlockEntity(Constants.MOD_ID + ":example_entity", HashMap<String, Any>())
        s.langKey = "Bedrock" /* Adds Name to block state */
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