package api

import akka.actor.{Actor, ActorLogging, ActorRefFactory}
import spray.http.MediaTypes
import spray.httpx.encoding.{Gzip, NoEncoding}
import spray.routing.HttpService
import storage.FileUploadHandler
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
  private val handler = new FileUploadHandler()

  val fileServiceRoute = respondWithMediaType(MediaTypes.`text/plain`) {
    (decodeRequest(Gzip) | decodeRequest(NoEncoding)) {
      (get & path("stats" / Segment)) { fname =>
        complete {
          handler.fromStore(fname).map(_.prettyResult)
        }
      } ~
      (post & path("filteredStats")) {
        formFields('filename, 'keyword) { (fname, keyword) =>
          complete {
            handler.fromStore(fname, x => !x.contains(keyword)).map(_.prettyResult)
          }
        }
      } ~
      (post & path("upload")) {
        formFields('filename, 'filecontent.as[Array[Byte]]) { (fname, fdata) =>
          complete {
            handler.process(fname, fdata).map(_.prettyResult)
          }
        }
      }
    }
  }
}
