package modenlibrary.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 跳转界面
 * @author L.star
 * @date 2020/12/23 15:52
 */
@Controller
public class IndexController {

    @GetMapping({"/","/index"})
    public String index(){
        return "/index.html";
    }

    @GetMapping("/admin")
    public String admin(){
        return "/admin.html";
    }

    @GetMapping("/upload")
    public String upload(){
        return "fileupload";
    }
}
