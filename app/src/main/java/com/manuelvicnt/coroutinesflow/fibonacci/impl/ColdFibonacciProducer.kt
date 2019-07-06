package com.manuelvicnt.coroutinesflow.fibonacci.impl

import com.manuelvicnt.coroutinesflow.fibonacci.ColdFibonacci
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow

@ExperimentalCoroutinesApi
class ColdFibonacciProducer : ColdFibonacci {

    /**
     * `channelFlow` creates an instance of the cold [Flow] with elements
     * that are sent to a [SendChannel]. This gives you the behavior of a cold stream
     * (starts the block of code every time there's an observer) with the flexibility
     * of a Channel (being able to send elements between coroutines).
     *
     * Every time an observer starts listening to the Flow (using a terminator operator such as
     * collect), the block of code will start from the beginning: while the consumer is present and
     * it hasn't cancelled its execution yet, a new number is calculated and sent to the Channel
     * with the method `send`. Then we wait, and repeat the loop until the consumer is no longer
     * present.
     */
    override fun fibonacci() = channelFlow {
        var first = 1L
        var second = 1L
        while (true) {
            if (isClosedForSend) break // Stop if the consumer is no longer there

            val next = first + second
            send(next)
            first = second
            second = next
            delay(2000)
        }
    }
}

