package storage

import org.scalatest.FunSuite
import storage.FileStats.{FileDetails, WCount, Word}

/**
 * Created by niwenp0
 * 20150205 09:09
 */
class TestSimpleStore extends FunSuite {

  private def wct(w: String, ct: Long = 1): (Word, WCount) = Word(w) -> WCount(ct)

  val data = {
    val words = Map(wct("the", 4), wct("lorem"), wct("dummy"), wct("si"), wct("istae"), wct("iwacu_yo", 2), wct("iwacu"))
    FileDetails(11, words)
  }

  val store = new SimpleStore()
  store.add("dummy_file", data)

  test("retrieving details") {
    assertResult(store.getStats("dummy_file"))(data)
  }

  test("retrieving details with filter") {
    val filtered = FileDetails(8, data.words -- Seq(Word("iwacu_yo"), Word("iwacu")))
    assertResult(store.getFilteredStats("dummy_file")(x => !x.contains("iwacu")))(filtered)
  }
}
