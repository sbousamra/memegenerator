package memes

import java.net.URL
import java.util.UUID
import argonaut.Argonaut._
import argonaut._

object Domain {
  def main(args: Array[String]) {
    val createdMeme = CreatedMeme(new URL("https:fgsdgsdfg"), "dom is", "a fag", MemeId(UUID.randomUUID()), new URL("https:fgsdgsdfg"))
    val newMeme = NewMemeDto(new URL("https:fgsdgsdfg"), "bass is", "a fag")
    val json = newMeme.asJson.spaces2
    println(json)
    println(Parse.decodeEither[NewMemeDto](json))
  }

  case class NewMemeDto(source: URL, topText: String, bottomText: String)
  case class CreatedMeme(source: URL, topText: String, bottomText: String, id: MemeId, url: URL)
  case class MemeId(value: UUID)

  implicit def MemeIdEncodeJson: EncodeJson[MemeId] =
    EncodeJson((p: MemeId) => jString(p.value.toString))

  implicit def CreatedMemeEncodeJson: EncodeJson[CreatedMeme] =
    EncodeJson((p: CreatedMeme) => Json(
      "source" -> jString(p.source.toString),
      "top_text" -> jString(p.topText),
      "bottom_text" -> jString(p.bottomText),
      "id" -> MemeIdEncodeJson.apply(p.id),
      "url" -> jString(p.url.toString)
    ))

  implicit def NewMemeDtoEncodeJson: EncodeJson[NewMemeDto] =
    EncodeJson((p: NewMemeDto) => Json(
      "source" -> jString(p.source.toString),
      "top_text" -> jString(p.topText),
      "bottom_text" -> jString(p.bottomText)
    ))

  implicit def CreatedMemeDecodeJson: DecodeJson[CreatedMeme] =
    DecodeJson(p => for {
      source <- (p --\ "source").as[URL]
      topText <- (p --\ "top_text").as[String]
      bottomText <- (p --\ "bottom_text").as[String]
      id <- (p --\ "id").as[MemeId]
      url <- (p --\ "url").as[URL]
    } yield CreatedMeme(source, topText, bottomText, id, url))

  implicit def NewMemeDtoDecodeJson: DecodeJson[NewMemeDto] =
    DecodeJson(p => for {
      source <- (p --\ "source").as[URL]
      topText <- (p --\ "top_text").as[String]
      bottomText <- (p --\ "bottom_text").as[String]
    } yield NewMemeDto(source, topText, bottomText))

  implicit def URLDecodeJson: DecodeJson[URL] =
    DecodeJson(p => p.as[String].flatMap { x =>
      fromStringSafely[URL, String](x, z => new URL(z)) match {
        case Some(url) => DecodeResult.ok(url)
        case None => DecodeResult.fail("Invalid Url", p.history)
      }
    })

  implicit def MemeIdDecodeJson: DecodeJson[MemeId] =
    DecodeJson(p => p.as[String].flatMap { x =>
      fromStringSafely[MemeId, String](x, c => MemeId(UUID.fromString(c))) match {
        case Some(memeId) => DecodeResult.ok(memeId)
        case None => DecodeResult.fail("Invalid Meme Id", p.history)
      }
    })

  def fromStringSafely[A, B](input: B, f: B => A): Option[A] = {
    try {
      Some(f(input))
    } catch {
      case e: Exception => None
    }
  }
}