package memes

import java.net.URL
import java.util.UUID
import memes.Domain._
import org.http4s._
import org.http4s.dsl._
import org.http4s.server.ServerApp
import org.http4s.server.blaze._

object Memegenerator extends ServerApp {

  val getRoute = HttpService {
    case GET -> Root / memesite / memeID => {
      val memeId = MemeId(UUID.randomUUID())
      val url = new URL("https:fgsdgsdfg")
      val newMeme = NewMemeDto(url, "bass is", "a fag")
      val constructedMeme = CreatedMeme(url, "dom is", "a fag", memeId, url)
      Ok(NewMemeDtoEncodeJson(newMeme).spaces2)
    }
  }

//  val postRoute = HttpService {
//    case req @ POST -> Root / hello / name => req.decode[CreatedMeme] { meme =>
//      Ok()
//    }
//  }

  def server(args: List[String]) = BlazeBuilder.mountService(getRoute, "/").start
}