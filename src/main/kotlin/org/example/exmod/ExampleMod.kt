package org.example.exmod

import com.github.puzzle.core.loader.provider.mod.entrypoint.impls.ModInitializer
import com.github.puzzle.core.localization.ILanguageFile
import com.github.puzzle.core.localization.LanguageManager
import com.github.puzzle.core.localization.files.LanguageFileVersion1
import com.github.puzzle.game.PuzzleRegistries
import com.github.puzzle.game.block.DataModBlock
import com.github.puzzle.game.events.OnPreLoadAssetsEvent
import com.github.puzzle.game.events.OnRegisterBlockEvent
import com.github.puzzle.game.events.OnRegisterZoneGenerators
import com.github.puzzle.game.items.IModItem
import com.github.puzzle.game.items.impl.BasicItem
import com.github.puzzle.game.items.impl.BasicTool
import com.github.puzzle.game.resources.PuzzleGameAssetLoader
import finalforeach.cosmicreach.util.Identifier
import meteordevelopment.orbit.EventHandler
import org.example.exmod.block_entities.ExampleBlockEntity
import org.example.exmod.blocks.Bedrock
import org.example.exmod.commands.Commands
import org.example.exmod.items.ExampleCycleItem
import org.example.exmod.items.ExamplePickaxe
import org.example.exmod.worldgen.ExampleZoneGenerator
import java.io.IOException

class ExampleMod : ModInitializer {

    override fun onInit() {
        PuzzleRegistries.EVENT_BUS.post(this)

        Constants.LOGGER.info("Hello From INIT")
        ExampleBlockEntity.register()

        Commands.register()

        IModItem.registerItem(ExamplePickaxe())
        IModItem.registerItem(ExampleCycleItem())
        IModItem.registerItem(BasicItem(Identifier.of(Constants.MOD_ID, "example_item")))
        IModItem.registerItem(BasicTool(Identifier.of(Constants.MOD_ID, "stone_sword")))
    }

    @EventHandler
    fun onEvent(event: OnRegisterBlockEvent) {
        event.registerBlock {
            DataModBlock(
                Identifier.of(Constants.MOD_ID, "diamond_block.json")
            )
        }
        event.registerBlock { Bedrock() }
    }

    @EventHandler
    fun onEvent(event: OnRegisterZoneGenerators) {
        event.registerGenerator { ExampleZoneGenerator() }
    }

    @EventHandler
    fun onEvent(event: OnPreLoadAssetsEvent?) {
        var lang: ILanguageFile? = null
        try {
            lang = LanguageFileVersion1.loadLanguageFile(
                PuzzleGameAssetLoader.locateAsset(Identifier.of(
                    Constants.MOD_ID,
                    "languages/en-US.json"
                ))
            )
        } catch (e: IOException) {
            throw RuntimeException(e)
        }
        LanguageManager.registerLanguageFile(lang)
    }
}