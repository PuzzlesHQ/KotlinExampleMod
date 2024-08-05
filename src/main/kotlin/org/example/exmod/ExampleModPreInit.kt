package org.example.exmod

import com.github.puzzle.loader.entrypoint.interfaces.PreModInitializer

class ExampleModPreInit : PreModInitializer {

    override fun onPreInit() {
        Constants.LOGGER.info("Hello From PRE-INIT")
    }
}
