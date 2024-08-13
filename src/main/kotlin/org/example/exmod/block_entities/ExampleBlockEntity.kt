package org.example.exmod.block_entities

import com.badlogic.gdx.graphics.Camera
import com.github.puzzle.core.Identifier
import com.github.puzzle.game.blockentities.ExtendedBlockEntity
import com.github.puzzle.game.blockentities.IRenderable
import com.github.puzzle.game.blockentities.ITickable
import com.github.puzzle.game.util.BlockUtil
import finalforeach.cosmicreach.blockentities.BlockEntityCreator
import finalforeach.cosmicreach.blocks.Block
import finalforeach.cosmicreach.blocks.BlockState
import finalforeach.cosmicreach.world.Zone
import org.example.exmod.Constants

class ExampleBlockEntity(zone: Zone?, x: Int, y: Int, z: Int) : ExtendedBlockEntity(zone, x, y, z), ITickable, IRenderable {

    companion object {
        var id: Identifier = Identifier(Constants.MOD_ID, "example_entity")

        fun register() {
            BlockEntityCreator.registerBlockEntityCreator(id.toString()
            ) { _: BlockState?, zone: Zone?, x: Int, y: Int, z: Int ->
                ExampleBlockEntity(
                    zone,
                    x,
                    y,
                    z
                )
            }
        }
    }

    override fun getBlockEntityId(): String {
        return id.toString()
    }

    override fun onTick(tps: Float) {
        val above = BlockUtil.getBlockPosAtVec(zone, x, y, z).getOffsetBlockPos(zone, 0, 1, 0)
        val current = above.blockState
        if (current.block === Block.AIR) {
            above.blockState = Block.GRASS.defaultBlockState
            above.flagTouchingChunksForRemeshing(zone, false)
        }
    }

    override fun onRender(camera: Camera?) {
        // add custom rendering logic here
    }
}