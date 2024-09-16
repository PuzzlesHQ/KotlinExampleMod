package org.example.exmod

import finalforeach.cosmicreach.util.Identifier
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger

object Constants {
    val MOD_ID: String = "example-mod"
    val MOD_NAME: Identifier = Identifier.of(MOD_ID, "Example Mod")
    val LOGGER: Logger = LogManager.getLogger(MOD_ID)
}