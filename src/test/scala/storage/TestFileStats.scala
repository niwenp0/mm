package storage

import org.scalatest.FunSuite
import storage.FileStats.{WCount, Word, FileDetails}

/**
 * Created by niwenp0
 * 20150205 08:54
 */
class TestFileStats extends FunSuite {

  private def wct(w: String, ct: Long = 1): (Word, WCount) = Word(w) -> WCount(ct)

  private def check(lines: Seq[String], details: FileDetails): Unit = {
    val fstats = new FileStats("somename.txt")
    lines.foreach(fstats.updateWithLine)
    assertResult(details)(fstats.details)
  }

  test("empty lines") {
    check(Seq.empty, FileDetails(0L, Map.empty))
  }

  test("does not include white lines") {
    val lines = Seq("This is:   urundi rurimi", "\t ")
    val res = FileDetails(4, Map(wct("This"), wct("is"), wct("urundi"), wct("rurimi")))
    check(lines, res)
  }
}
