package storage

import cgta.serland.SerBuilder
import storage.FileStats.{FileDetails, WCount, Word}
import scala.collection.immutable
import scala.collection.mutable

/**
 * Created by niwenp0
 * 20150203 20:19
 */


object FileStats {

  object Word {
    implicit val ser = SerBuilder.reuseSerial[Word, String](_.value, apply)
    def apply(s: String): Word = new Word(s)
  }
  class Word(val value: String) extends AnyVal

  object WCount {
    implicit val ser = SerBuilder.reuseSerial[WCount, Long](_.value, apply)
    def apply(c: Long): WCount = new WCount(c)
  }
  class WCount(val value: Long) extends AnyVal

  object FileDetails { implicit val ser = SerBuilder.forCase(this.apply _)}
  case class FileDetails(wordCount: Long, words: immutable.Map[Word, WCount])
}


class FileStats(name: String) {

  private val tmp = mutable.HashMap.empty[String, Long]

  def updateWithLines(xs: Seq[String]): Unit = {
    xs.flatMap(_.split("\\W+")).foreach(w => tmp += w -> (tmp.getOrElse(w, 0L) + 1))
  }

  def details: FileDetails = {
    FileDetails(wordCount = tmp.values.sum, words = tmp.map(kv => Word(kv._1) -> WCount(kv._2)).toMap)
  }
}
