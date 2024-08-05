package org.example.exmod

import com.github.puzzle.core.Identifier
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

object Constants {
    val MOD_ID: String = "example-mod"
    val MOD_NAME: Identifier = Identifier(MOD_ID, "Example Mod")
    val LOGGER: Logger = LogManager.getLogger(MOD_ID)
}