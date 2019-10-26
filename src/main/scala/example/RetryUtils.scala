package example

import retry.{RetryDetails, RetryPolicies, RetryPolicy, Sleep}

import scala.concurrent.{ExecutionContext, Future}
import scala.concurrent.duration._
import cats.implicits._


object RetryUtils {

  implicit class FutureEnriched[A](futureToBeRetried: Future[A])(implicit sleep: Sleep[Future], ec: ExecutionContext) {

    val constantDelayWithGiveUp: RetryPolicy[Future] = RetryPolicies.constantDelay[Future](2.seconds) |+| RetryPolicies.limitRetries[Future](5)
    val exponentialBackoffDelayWithGiveUp: RetryPolicy[Future] = RetryPolicies.exponentialBackoff[Future](1.seconds) |+| RetryPolicies.limitRetries[Future](5)

    def onError[E] (e: E, retryDetails: RetryDetails): Future[Unit] =  {
      Future.successful(println(s"There was an error executing future, error: $e"))
    }

    def withRetriesOnAllErrors: Future[A] =
      retry.retryingOnAllErrors[A].apply[Future, Throwable](constantDelayWithGiveUp, onError)(futureToBeRetried)

    def withRetriesOnSomeErrors(isWorthRetrying: Throwable => Boolean): Future[A] =
      retry.retryingOnSomeErrors[A].apply[Future, Throwable](constantDelayWithGiveUp, isWorthRetrying, onError)(futureToBeRetried)
  }
}

