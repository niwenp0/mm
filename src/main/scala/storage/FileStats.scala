package storage

import storage.FileStats.{FileDetails, WCount, Word}
import scala.collection.immutable
import scala.collection.mutable

/**
 * Created by niwenp0
 * 20150203 20:19
 */


object FileStats {

  object Word {
    def apply(s: String): Word = new Word(s)
  }
  class Word(val value: String) extends AnyVal

  object WCount {
    def apply(c: Long): WCount = new WCount(c)
  }
  class WCount(val value: Long) extends AnyVal

  case class FileDetails(wordCount: Long, words: immutable.Map[Word, WCount]) {
    def prettyResult: String = s"Total words count = $wordCount.  Summary: \n" +
      s"${words.map(kv => s"[${kv._1.value} -> ${kv._2.value}]").mkString("   ")} \n"
  }
}

class FileStats(name: String) {
  private val tmp = mutable.HashMap[String, Long]().withDefault(x => 0)

  def updateWithLine(line: String): Unit = {
    line.split("\\W+").withFilter(_.nonEmpty) foreach (w => tmp(w) += 1)
  }

  def details: FileDetails = {
    FileDetails(wordCount = tmp.values.sum, words = tmp.map(kv => Word(kv._1) -> WCount(kv._2)).toMap)
  }
}
