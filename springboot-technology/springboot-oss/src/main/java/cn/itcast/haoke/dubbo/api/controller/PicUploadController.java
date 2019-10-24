package cn.itcast.haoke.dubbo.api.controller;

import cn.itcast.haoke.dubbo.api.service.PicUploadService;
import cn.itcast.haoke.dubbo.api.vo.PicUploadResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RequestMapping("/pic/upload")
@RestController
public class PicUploadController {

  @Autowired
  private PicUploadService picUploadService;

  @PostMapping
  public PicUploadResult upload(@RequestParam("file") MultipartFile multipartFile) {
    return this.picUploadService.upload(multipartFile);
  }

}
