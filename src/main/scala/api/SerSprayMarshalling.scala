package api

import cgta.serland.SerClass
import spray.http.{ContentTypeRange, ContentTypes}
import spray.httpx.marshalling.Marshaller
import spray.httpx.unmarshalling.Unmarshaller

/**
 * Created by niwenp0
 * 20150203 23:41
 */
object SerSprayMarshalling {
  implicit def sprayMarshaller[T](implicit writer: SerClass[T]): Marshaller[T] =
    Marshaller.delegate[T, String](ContentTypes.`text/plain(UTF-8)`) { v =>
      v.toJsonCompact
    }

  implicit def sprayUnmarshaller[T](implicit writer: SerClass[T]): Unmarshaller[T] =
    Unmarshaller.delegate[String, T](ContentTypeRange.*) { str =>
      str.fromJson[T]
    }
}
