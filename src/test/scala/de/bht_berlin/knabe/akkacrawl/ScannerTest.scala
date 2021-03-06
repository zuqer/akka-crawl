package de.bht_berlin.knabe.akkacrawl

import akka.http.scaladsl.model.Uri
import org.junit.{ Test }
import org.scalatest.Matchers
import org.scalatest.junit.JUnitSuite

/**
 * Created by knabe on 15.11.14.
 */
class ScannerTest extends JUnitSuite with Matchers {

  private val hostPath = "://spray.io/documentation/1.2.2/spray-can/"
  private val baseUri = Uri("http://www.berlin.de/");

  @Test def hrefHttpPattern() {
    val text = "<a href=\"http" + hostPath + "\">"
    val result = Scanner.linkPattern.findFirstMatchIn(text)
    result match {
      case Some(matched) => assertResult("http" + hostPath) { matched.group(1) }
      case None          => fail(s"Should find URL match in: $text")
    }
  }
  @Test def hrefHttpsPattern() {
    val text = "<a href=\"https" + hostPath + "\"def"
    val result = Scanner.linkPattern.findFirstMatchIn(text)
    result match {
      case Some(matched) => assertResult("https" + hostPath) { matched.group(1) }
      case None          => fail(s"Should find URL match in: $text")
    }
  }

  @Test def worthToFollowUri() {
    {
      val result = Scanner.worthToFollowUri("https://datenschutz-berlin.de//", baseUri)
      result shouldBe Some(Uri("https://datenschutz-berlin.de//"))
    }
    {
      val result = Scanner.worthToFollowUri("http://www.kicker.de", baseUri)
      result shouldBe Some(Uri("http://www.kicker.de"))
    }
    {
      val result = Scanner.worthToFollowUri("http://www.kicker.de/", baseUri)
      result shouldBe Some(Uri("http://www.kicker.de/"))
    }
    {
      val result = Scanner.worthToFollowUri("http://spray.io/documentation/1.2.2/spray-can", baseUri)
      result shouldBe Some(Uri("http://spray.io/documentation/1.2.2/spray-can"))
    }
    {
      val result = Scanner.worthToFollowUri("http://spray.io/documentation/1.2.2/spray-can/", baseUri)
      result shouldBe Some(Uri("http://spray.io/documentation/1.2.2/spray-can/"))
    }
    {
      val result = Scanner.worthToFollowUri("http://kicker.de/images/logo-kicker.html", baseUri)
      result shouldBe Some(Uri("http://kicker.de/images/logo-kicker.html"))
    }
    {
      val result = Scanner.worthToFollowUri("http://mediadb.kicker.de/special/facebook/images/logo-kicker.png", baseUri)
      result shouldBe None
    }
    {
      val result = Scanner.worthToFollowUri("http://voting.kicker.de/generic/js/general_7.68.5430.30309.js", baseUri)
      result shouldBe None
    }
    {
      val result = Scanner.worthToFollowUri("http://www.kicker.de/search.xml", baseUri)
      result shouldBe None
    }
    {
      val result = Scanner.worthToFollowUri("https://www.kicker.de", baseUri)
      result shouldBe Some(Uri("https://www.kicker.de"))
    }
    {
      //Delete #fragment from URI:
      val result = Scanner.worthToFollowUri("https://www.berlin.de/kino/_bin/filmdetail.php/244046#tab1_405", baseUri)
      result shouldBe Some(Uri("https://www.berlin.de/kino/_bin/filmdetail.php/244046"))
    }
    {
      //Delete #query from URI:
      val result = Scanner.worthToFollowUri("http://www.businesslocationcenter.de/ru?closed=1", baseUri)
      result shouldBe Some(Uri("http://www.businesslocationcenter.de/ru"))
    }
  }

} // class ScannerTest
