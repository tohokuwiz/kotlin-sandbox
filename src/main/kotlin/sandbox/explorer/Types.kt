// ktlint-disable filename
package sandbox.explorer

import arrow.fx.ForIO
import arrow.mtl.EitherT

typealias EitherIO<A> = EitherT<ForIO, AppError, A>

sealed class AppError {
    object CsvImportError : AppError()
    object PersonInsertError : AppError()
    object JSONDeserializaitonError : AppError()
}
