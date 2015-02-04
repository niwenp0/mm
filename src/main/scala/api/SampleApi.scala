package api

import akka.actor.Actor.Receive
import akka.actor.{ActorLogging, ActorRefFactory, Actor}
import spray.http.{StatusCodes, MediaTypes}
import spray.httpx.encoding.{NoEncoding, Gzip}
import spray.routing.HttpService
import scala.concurrent.ExecutionContextExecutor

/**
 * Created by niwenp0
 * 20150203 21:24
 */


class FileStatsServices extends Actor with SampleApi with ActorLogging {
  implicit val system = context.system

  override implicit def actorRefFactory: ActorRefFactory = context
  override def receive: Receive = runRoute(fileServiceRoute)
}

trait SampleApi extends HttpService {
  implicit def ec: ExecutionContextExecutor = actorRefFactory.dispatcher
  import SerSprayMarshalling._

  val fileServiceRoute = respondWithMediaType(MediaTypes.`text/plain`) {
    (decodeRequest(Gzip) | decodeRequest(NoEncoding)) {
      (get & path("stats" / Segment)) { fname =>
        complete {
          "HEY THERE"
        }
      }
      //      ~
      //        ((post | put) & path("upload")){
      //          try{
      //            entity(as)
      //
      //          }catch {
      //            case e: Exception =>
      //          }
      //        }

    }
  }


}
