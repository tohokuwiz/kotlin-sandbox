package sandbox.explorer

import arrow.core.Left
import com.beust.klaxon.KlaxonException
import io.kotlintest.matchers.startWith
import io.kotlintest.should
import io.kotlintest.shouldBe
import io.kotlintest.shouldThrow
import io.kotlintest.specs.StringSpec
import java.io.File

class ModelsSpec : StringSpec({
    "can deserialize a UserInfo from json string" {
        val userInfoData: String = File("./resources/github-user-info.json").readText(Charsets.UTF_8)

        val userInfo = GitHubUserInfo.deserializeFromJson(userInfoData)

        userInfo.map { it.username shouldBe "adomokos" }
    }

    "won't work with invalid data" {
        val exception = shouldThrow<KlaxonException> {
            GitHubUserInfo.deserializeFromJson("something")
        }
        exception.message should startWith("Unexpected character at position 0: 's'")
    }

    "can deserialize a UserInfo from json string with Either returned type" {
        val userInfoData: String = File("./resources/github-user-info.json").readText(Charsets.UTF_8)

        val userInfo = GitHubUserInfo.deserializeFromJson2(userInfoData).unsafeRunSync()

        userInfo.map { it.username shouldBe "adomokos" }
    }

    "returns Left if any error occurs" {
        val userInfo =
            GitHubUserInfo.deserializeFromJson2("something").unsafeRunSync()

        userInfo shouldBe Left(AppError.JSONDeserializaitonError)
    }
})
