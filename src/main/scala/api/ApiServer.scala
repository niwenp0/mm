package api

import akka.actor.{Props, ActorSystem}
import akka.io.IO
import spray.can.Http

/**
 * Created by niwenp0
 * 20150203 22:51
 */
object ApiServer extends App {
  implicit val system = ActorSystem()

  val service = system.actorOf(Props[FileStatsServices], "fileStats-service")

  IO(Http) ! Http.Bind(service, interface = "0.0.0.0", port = 8080)

  System.in.read()
  system.shutdown()
}
