package com.sptrainer.integrationservice.controller;

import com.sptrainer.domain.model.GroupTrainer;
import com.sptrainer.domain.model.SesionTrainer;
import com.sptrainer.domain.model.User;
import com.sptrainer.integrationservice.service.GroupTrainerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT})
@RequestMapping("grouptrainer")
public class GroupTrainerController {

    @Autowired
    GroupTrainerService groupTrainerService;

    @GetMapping("/findbyuserid/{userid}")
    public @ResponseBody
    ResponseEntity findByUserId(@PathVariable("userid") Long userid) {
        return groupTrainerService.findByUserId(userid);
    }

    @GetMapping("/findbyuseridforclient/{userid}")
    public @ResponseBody
    ResponseEntity findByUserIdForClient(@PathVariable("userid") Long userid) {
        return groupTrainerService.findByUserIdForClient(userid);
    }

    @GetMapping("/findByTrainer/{userId}")
    public @ResponseBody
    ResponseEntity findByTrainer(@PathVariable("userId") Long userId) {
        return groupTrainerService.findByTrainer(userId);
    }

    @GetMapping("/findbygroupidanduserid/{userid}/{groupid}")
    public @ResponseBody
    ResponseEntity findByGroupIdAndUserId(@PathVariable("userid") Long userid, @PathVariable("groupid") Long groupid) {
        return groupTrainerService.findByGroupIdAndUserId(userid, groupid);
    }

    @PostMapping("/save")
    public @ResponseBody
    ResponseEntity save(@RequestBody GroupTrainer groupTrainer) {
        return groupTrainerService.save(groupTrainer);
    }

    @PutMapping("/update")
    public @ResponseBody
    ResponseEntity update(@RequestBody GroupTrainer groupTrainer) {
        return groupTrainerService.update(groupTrainer);
    }

    @GetMapping("/findbymultiplefilter")
    public @ResponseBody
    ResponseEntity findByMultipleFilter(@RequestParam(value = "idusers", required = false) String idusers,
                                        @RequestParam(value = "categories", required = false) String categories,
                                        @RequestParam(value = "filter", required = false) String filter,
                                        @RequestParam(value = "iduser") Long iduser,
                                        @RequestParam(value = "coordinate") String coordinate) {
        return groupTrainerService.findByMultipleFilter(idusers, categories, filter, coordinate, iduser);
    }

    @PutMapping("/publish/{idgroup}/{iduser}")
    public @ResponseBody
    ResponseEntity publish(@PathVariable("idgroup") Long idgroup, @PathVariable("iduser") Long iduser) {
        return groupTrainerService.publish(iduser, idgroup);
    }

    @GetMapping("/findbymember/{userid}")
    public @ResponseBody
    ResponseEntity findByMember(@PathVariable("userid") Long userid) {
        return groupTrainerService.findByMember(userid);
    }

    @GetMapping("/findbygroupinits")
    public @ResponseBody
    ResponseEntity findbygroupinits(@RequestParam(value = "coordinate") String coordinate, @RequestParam(value = "iduser") Long iduser) {
        return groupTrainerService.findGroupsInit(coordinate, iduser);
    }

    @GetMapping("/findbyidcompleteinfo/{idgroup}/{iduser}")
    public @ResponseBody
    ResponseEntity findByUserId(@PathVariable("idgroup") Long idgroup,
                                @PathVariable("iduser") Long iduser,
                                @RequestParam(value = "coordinate") String coordinate) {
        return groupTrainerService.findByIdCompleteInfo(idgroup,iduser,coordinate);
    }

    /**
     * SesionTrainer
     **/
    @PostMapping("/session/save")
    public @ResponseBody
    ResponseEntity saveSession(@RequestBody SesionTrainer session) {
        return groupTrainerService.saveSession(session);
    }

    @PutMapping("/session/update")
    public @ResponseBody
    ResponseEntity updateSession(@RequestBody SesionTrainer session) {
        return groupTrainerService.updateSession(session);
    }

    @DeleteMapping("/session/delete/{idsession}/{idgroup}/{iduser}")
    public @ResponseBody
    ResponseEntity deleteSession(@PathVariable("idsession") Long idsession, @PathVariable("idgroup") Long idgroup, @PathVariable("iduser") Long iduser) {
        return groupTrainerService.deleteSession(idsession, idgroup, iduser);
    }

    @GetMapping("/session/findbydgroupid/{idgroup}/{iduser}")
    public @ResponseBody
    ResponseEntity findSessionByGroupId(@PathVariable("idgroup") Long idgroup, @PathVariable("iduser") Long iduser) {
        return groupTrainerService.findSessionByUserAndGroupId(idgroup, iduser);
    }


}
