package com.sptrainer.user.controller;

import com.sptrainer.domain.model.LogLogin;
import com.sptrainer.user.service.LogLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT})
@RequestMapping("log")
public class LogLoginController {

    @Autowired
    private LogLoginService logLoginService;

    @PostMapping("/login/save")
    private @ResponseBody int save(@RequestBody LogLogin logLogin) {
        return logLoginService.save(logLogin);
    }

}
