package sandbox.arrow

import arrow.core.Either
import arrow.core.extensions.either.applicative.applicative
import arrow.core.extensions.list.traverse.traverse
import arrow.core.fix
import io.kotest.assertions.arrow.either.shouldBeRight
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.types.shouldBeInstanceOf

fun parseIntEither(s: String): Either<Throwable, Int> = s.safeToInt()

class TraverseSpec : DescribeSpec({
    describe("Can traverse through a list and report error") {
        it("can safely convert list of Int candidates") {
            val correctItems = listOf("1", "2", "3")
            val incorrectItems = listOf("1", "2", "Jimmy")

            val result = correctItems.traverse(Either.applicative(), ::parseIntEither).fix()
            result.shouldBeRight(listOf(1, 2, 3))

            val result2 = incorrectItems.traverse(Either.applicative(), ::parseIntEither).fix()
            result2.mapLeft {
                it.shouldBeInstanceOf<NumberFormatException>()
            }
        }
    }
})
