package storage

import java.util.concurrent.locks.ReentrantLock
import storage.FileStats.{WCount, Word, FileDetails}
import scala.collection.immutable

/**
 * Created by niwenp0
 * 20150203 20:20
 */


class SimpleStore {

  private val lock = new ReentrantLock()

  private var files = immutable.Map.empty[String, FileDetails]

  private def withLock[T](f: => T) = {
    lock.lock()
    try {
      f
    } finally {
      lock.unlock()
    }
  }

  def add(name: String, details: FileDetails): Unit = {
    withLock {
      files += name -> details
    }
  }

  def getFilteredStats(name: String)(p: String => Boolean): FileDetails = {
    withLock {
      val words = files.get(name).map(_.words.filter(kv => p(kv._1.value))).getOrElse(Map.empty[Word, WCount])
      FileDetails(words.values.map(_.value).sum, words)
    }
  }

  def getStats(name: String): FileDetails = getFilteredStats(name)(x => true)

}
