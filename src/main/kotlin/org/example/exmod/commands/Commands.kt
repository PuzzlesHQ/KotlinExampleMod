package org.example.exmod.commands

import com.github.puzzle.game.commands.CommandManager
import com.github.puzzle.game.commands.ServerCommandSource
import com.github.puzzle.game.util.BlockUtil
import com.mojang.brigadier.arguments.IntegerArgumentType
import com.mojang.brigadier.arguments.StringArgumentType
import com.mojang.brigadier.context.CommandContext
import finalforeach.cosmicreach.blocks.BlockState
import finalforeach.cosmicreach.blocks.MissingBlockStateResult

object Commands {

    fun register() {
        val cmd = CommandManager.literal<ServerCommandSource>("setBlock")
        cmd.then(CommandManager.argument(ServerCommandSource::class.java, "x", IntegerArgumentType.integer())
            .then(CommandManager.argument(ServerCommandSource::class.java, "y", IntegerArgumentType.integer())
                .then(CommandManager.argument(ServerCommandSource::class.java, "z", IntegerArgumentType.integer())
                    .then(CommandManager.argument(
                        ServerCommandSource::class.java,
                        "blockstate",
                        StringArgumentType.greedyString()
                    )
                        .executes { context: CommandContext<ServerCommandSource>? ->
                            val x = IntegerArgumentType.getInteger(context, "x")
                            val y = IntegerArgumentType.getInteger(context, "y")
                            val z = IntegerArgumentType.getInteger(context, "z")
                            val blockState = StringArgumentType.getString(context, "blockstate")
                            BlockUtil.setBlockAt(
                                null,
                                BlockState.getInstance(
                                    blockState,
                                    MissingBlockStateResult.MISSING_OBJECT
                                ),
                                x,
                                y,
                                z
                            )
                            0
                        }
                    )
                )
            )
        )
        CommandManager.DISPATCHER.register(cmd)
    }

}