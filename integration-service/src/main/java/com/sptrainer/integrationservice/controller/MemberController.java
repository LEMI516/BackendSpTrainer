package com.sptrainer.integrationservice.controller;

import com.sptrainer.domain.model.Calification;
import com.sptrainer.domain.model.GroupTrainer;
import com.sptrainer.domain.model.RegistrationRequest;
import com.sptrainer.integrationservice.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT})
@RequestMapping("member")
public class MemberController {

    @Autowired
    private MemberService memberService;

    @PostMapping("/registration_request")
    public @ResponseBody
    ResponseEntity registration_request(@RequestBody RegistrationRequest registrationRequest) {
        return memberService.registration_request(registrationRequest);
    }

    @PostMapping("/response/registration_request")
    public @ResponseBody
    ResponseEntity response_registration_request(@RequestBody RegistrationRequest registrationRequest) {
        return memberService.response_registration_request(registrationRequest);
    }

    @GetMapping("/findregistrationrequestbygroupidandstate/{groupid}/{state}")
    public @ResponseBody
    ResponseEntity findregistrationrequestbygroupidandstate(@PathVariable("groupid") Long groupid, @PathVariable("state") String state) {
        return memberService.findRequestByGroupIdAndState(groupid, state);
    }

    @GetMapping("/findmemberbygroupidandstate/{groupid}/{state}")
    public @ResponseBody
    ResponseEntity findmemberbygroupidandstate(@PathVariable("groupid") Long groupid, @PathVariable("state") String state) {
        return memberService.findMemberByGroupIdAndState(groupid, state);
    }

    @PutMapping("/update_member/{groupid}/{userid}/{state}")
    public @ResponseBody
    ResponseEntity update_member(@PathVariable("groupid") Long groupid, @PathVariable("userid") Long userid, @PathVariable("state") String state) {
        return memberService.update_member(groupid, userid, state);
    }

    @PostMapping("/calificate")
    public @ResponseBody
    ResponseEntity calificate(@RequestBody Calification calification) {
        return memberService.calificateUpdate(calification);
    }

    @GetMapping("/findRegistrationByUserIdAndGroupId/{groupId}/{userId}")
    public @ResponseBody
    ResponseEntity findRegistrationByUserIdAndGroupId(@PathVariable("groupId") Long groupId, @PathVariable("userId") Long userId) {
        return memberService.findRegistrationByUserIdAndGroupId(groupId, userId);
    }


}
