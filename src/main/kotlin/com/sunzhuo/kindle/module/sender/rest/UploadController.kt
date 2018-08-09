package com.sunzhuo.kindle.module.sender.rest

import com.sunzhuo.kindle.common.httpstatus.FromEmailInvalidException
import com.sunzhuo.kindle.common.httpstatus.ToEmailInvalidException
import com.sunzhuo.kindle.module.sender.service.ContentService
import org.apache.commons.validator.routines.EmailValidator
import org.springframework.boot.system.ApplicationTemp
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Paths

@RestController
class UploadController {

    @PostMapping("/upload")
    @ResponseStatus(HttpStatus.CREATED)
    fun upload(@RequestParam(value = "file") file: MultipartFile, @RequestParam(value = "from_email") from_email: String, @RequestParam(value = "to_email") to_email: String) {
        if (!EmailValidator.getInstance().isValid(from_email)) {
            throw FromEmailInvalidException(from_email)
        }
        if (!EmailValidator.getInstance().isValid(to_email)) {
            throw ToEmailInvalidException(to_email)
        }

        if (to_email.endsWith("@kindle.cn")
                || to_email.endsWith("@kindle.com")
                || to_email.endsWith("@free.kindle.com")
                || to_email.endsWith("@iduokan.com")) {
            ContentService.upload(saveUploadedFile(file), from_email, to_email)
        } else {
            throw ToEmailInvalidException(to_email)
        }
    }

    @Throws(IOException::class)
    private fun saveUploadedFile(file: MultipartFile): String {
        val bytes = file.bytes
        val path = Paths.get(ApplicationTemp().dir.path, file.originalFilename)
        Files.write(path, bytes)
        return path.toString()
    }
}