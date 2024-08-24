package org.example.exmod.worldgen

import finalforeach.cosmicreach.blocks.BlockState
import finalforeach.cosmicreach.savelib.blockdata.LayeredBlockData
import finalforeach.cosmicreach.world.Chunk
import finalforeach.cosmicreach.world.Zone
import finalforeach.cosmicreach.worldgen.ChunkColumn
import finalforeach.cosmicreach.worldgen.ZoneGenerator
import finalforeach.cosmicreach.worldgen.noise.SimplexNoise

class ExampleZoneGenerator : ZoneGenerator() {
    private val baseLevel = 64f
    private val seaLevel = 64f

    private val noiseAmplitude = 32f
    private val noiseScale = 0.01f

    private val softMaxY = 255
    private val softMinY = 0

    // Fetches on class instantiation, so will not be null
    var airBlock: BlockState = this.getBlockStateInstance("base:air[default]")
    var stoneBlock: BlockState = this.getBlockStateInstance("base:stone_basalt[default]")
    var waterBlock: BlockState = this.getBlockStateInstance("base:water[default]")

    private var noise: SimplexNoise? = null

    override fun getSaveKey(): String {
        return "exmod:example_terrain"
    }

    override fun getName(): String {
        // Not fetched from the lang file
        return "Example Terrain"
    }

    // Called on world load/create, after this.seed is set
    override fun create() {
        // Create noise generators
        noise = SimplexNoise(this.seed)
    }

    // Generate a chunk-column of the world at once (easier for the lighting engine this way)
    override fun generateForChunkColumn(zone: Zone, col: ChunkColumn) {
        val maxChunkY = Math.floorDiv(this.softMaxY, 16)
        val minChunkY = Math.floorDiv(this.softMinY, 16)

        // Only generate the regions containing minY < y < maxY
        if (col.chunkY < minChunkY || col.chunkY > maxChunkY) return


        for (chunkY in minChunkY..maxChunkY) {
            var chunk = zone.getChunkAtChunkCoords(col.chunkX, chunkY, col.chunkZ)

            if (chunk == null) {
                // Create a new chunk if it doesn't exist
                chunk = Chunk(col.chunkX, chunkY, col.chunkZ)

                // Create the chunk data
                chunk.initChunkData { LayeredBlockData(airBlock) }

                // Register the chunks in the world and column
                zone.addChunk(chunk)
                col.addChunk(chunk)
            }


            // === Block placing logic ===


            // Loop through all blocks in the chunk
            for (localX in 0 until Chunk.CHUNK_WIDTH) {
                val globalX = chunk.blockX + localX

                for (localZ in 0 until Chunk.CHUNK_WIDTH) {
                    val globalZ = chunk.blockZ + localZ

                    // Only have to sample height once for each Y column (not going to change on the Y axis ;p)
                    val columnHeight = (noise!!.noise2(
                        globalX * noiseScale,
                        globalZ * noiseScale
                    ) * noiseAmplitude + baseLevel).toDouble()

                    for (localY in 0 until Chunk.CHUNK_WIDTH) {
                        val globalY = chunk.blockY + localY

                        // Below the ground
                        if (globalY <= columnHeight) {
                            // Don't want to set existing solid blocks to air in unloaded chunks (what about structures?)
                            chunk.setBlockState(stoneBlock, localX, localY, localZ)
                        } else {
                            // Below the sea level
                            if (globalY <= seaLevel) {
                                chunk.setBlockState(waterBlock, localX, localY, localZ)
                            }
                        }
                    }
                }
            }
        }
    }

    override fun getDefaultRespawnYLevel(): Int {
        return 0
    }
}