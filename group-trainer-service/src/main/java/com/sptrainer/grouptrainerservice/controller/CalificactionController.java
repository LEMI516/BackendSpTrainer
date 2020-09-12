package com.sptrainer.grouptrainerservice.controller;

import com.sptrainer.domain.model.Calification;
import com.sptrainer.grouptrainerservice.service.CalificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT})
@RequestMapping("calificate")
public class CalificactionController {

    @Autowired
    private CalificateService calificateService;

    @PostMapping("/update")
    public @ResponseBody
    ResponseEntity update(@RequestBody Calification calification) {
       return new ResponseEntity<>(calificateService.saveOrUpdate(calification), HttpStatus.OK);
    }

}
