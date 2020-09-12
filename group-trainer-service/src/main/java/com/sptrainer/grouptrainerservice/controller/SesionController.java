package com.sptrainer.grouptrainerservice.controller;

import com.sptrainer.domain.model.GroupTrainer;
import com.sptrainer.domain.model.SesionTrainer;
import com.sptrainer.grouptrainerservice.service.SesionTrainerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT})
@RequestMapping("sesion")
public class SesionController {

    @Autowired
    private SesionTrainerService sesionService;

    @PostMapping("/save")
    public @ResponseBody
    ResponseEntity save(@RequestBody SesionTrainer sesion) {
        SesionTrainer s = sesionService.save(sesion);
        return new ResponseEntity<>(s, HttpStatus.OK);
    }

    @PutMapping("/update")
    public @ResponseBody
    ResponseEntity update(@RequestBody SesionTrainer sesion) {
        int result = sesionService.update(sesion);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{idsesion}/{idgroup}/{iduser}")
    public @ResponseBody
    ResponseEntity update(@PathVariable("idsesion") Long idsesion, @PathVariable("idgroup") Long idgroup, @PathVariable("iduser") Long iduser) {
        int result = sesionService.delete(idsesion, idgroup, iduser);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/findbygroupid/{idgroup}/{iduser}")
    public @ResponseBody
    List<SesionTrainer> findByGroupId(@PathVariable("idgroup") Long idgroup, @PathVariable("iduser") Long iduser) {
        return sesionService.findByGroupId(idgroup);
    }

}
