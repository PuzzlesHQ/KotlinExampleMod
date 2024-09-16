package org.example.exmod.block_entities

import com.badlogic.gdx.graphics.Camera
import com.github.puzzle.game.util.BlockUtil
import finalforeach.cosmicreach.blockentities.BlockEntity
import finalforeach.cosmicreach.blockentities.BlockEntityCreator
import finalforeach.cosmicreach.blocks.Block
import finalforeach.cosmicreach.blocks.BlockState
import finalforeach.cosmicreach.util.Identifier
import finalforeach.cosmicreach.world.Zone
import org.example.exmod.Constants

class ExampleBlockEntity(zone: Zone?, x: Int, y: Int, z: Int) : BlockEntity(zone, x, y, z) {

    val zone: Zone? = zone;

    companion object {
        var id: Identifier = Identifier.of(Constants.MOD_ID, "example_entity")

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

    override fun isTicking(): Boolean {
        return true
    }

    override fun onTick() {
        val above = BlockUtil.getBlockPosAtVec(zone, globalX, globalY, globalZ).getOffsetBlockPos(zone, 0, 1, 0)
        val current = above.blockState
        if (current.block === Block.AIR) {
            above.blockState = Block.GRASS.defaultBlockState
            above.flagTouchingChunksForRemeshing(zone, false)
        }
    }

}