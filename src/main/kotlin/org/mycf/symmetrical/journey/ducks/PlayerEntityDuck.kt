package org.mycf.symmetrical.journey.ducks

interface PlayerEntityDuck {
    var hasKilledHolgin: Boolean
    val hoglinTickTime: Int
    var lastAttackedHoglinTickTime: Int
}