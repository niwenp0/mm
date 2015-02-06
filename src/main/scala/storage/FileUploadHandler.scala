package storage

import storage.FileStats.FileDetails
import scala.concurrent.Future
import scala.io.Source
import scala.concurrent.ExecutionContext.Implicits.global

/**
 * Created by niwenp0
 * 20150204 23:54
 */
class FileUploadHandler() {

  private val store = new SimpleStore()

  def process(fname: String, fdata: Array[Byte]): Future[FileDetails] = Future {
    try {
      val fstats = new FileStats(fname)
      // Assumes correctly formated text file as input
      Source.fromBytes(fdata, "ASCII").getLines().foreach(fstats.updateWithLine)
      val res = fstats.details
      store.add(fname, res)
      res
    } catch {
      case e: Exception => throw new RuntimeException("File couldn't be processed", e)
    }
  }

  def fromStore(fname: String): Future[FileDetails] = Future(store.getStats(fname))

  def fromStore(fname: String, filter: String => Boolean): Future[FileDetails] = Future {
    store.getFilteredStats(fname)(filter)
  }
}
