package org.example.exmod

import com.github.puzzle.core.loader.provider.mod.entrypoint.impls.PreModInitializer

class ExampleModPreInit : PreModInitializer {

    override fun onPreInit() {
        Constants.LOGGER.info("Hello From PRE-INIT")
    }
}
