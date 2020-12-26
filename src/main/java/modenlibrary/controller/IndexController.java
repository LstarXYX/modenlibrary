package modenlibrary.controller;

import lombok.extern.slf4j.Slf4j;
import modenlibrary.Common.code.ReturnCode;
import modenlibrary.Common.socket.WebSocketServer;
import modenlibrary.Common.utils.Result;
import modenlibrary.Common.vo.ResultVo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

/**
 * 跳转界面
 * @author L.star
 * @date 2020/12/23 15:52
 */
@Controller
@Slf4j
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

    /**
     * 管理员群发消息
     *
     * @param message
     * @return
     */
    @GetMapping("/im/sendall")
    @ResponseBody
    public ResultVo sendAllMsg(String message){
        try {
            WebSocketServer.BroadCastInfo(message);
        }catch (IOException e){
            log.error("群发消息失败");
            return Result.fail(ReturnCode.SYSTEM_ERROR);
        }
        return Result.success("ok");
    }



}
