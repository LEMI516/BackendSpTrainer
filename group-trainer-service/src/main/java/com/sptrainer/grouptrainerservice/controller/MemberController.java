package com.sptrainer.grouptrainerservice.controller;

import com.sptrainer.domain.model.GroupTrainer;
import com.sptrainer.domain.model.Member;
import com.sptrainer.domain.model.RegistrationRequest;
import com.sptrainer.grouptrainerservice.service.MemberService;
import com.sptrainer.grouptrainerservice.service.RegistrationRequestService;
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
    private RegistrationRequestService registrationRequestService;

    @Autowired
    private MemberService memberService;

    @PostMapping("/registration_request")
    public @ResponseBody
    ResponseEntity registration_request(@RequestBody RegistrationRequest registrationRequest) {
        RegistrationRequest r = registrationRequestService.save(registrationRequest);
        return new ResponseEntity<>(r, HttpStatus.OK);
    }

    @PostMapping("/response/registration_request")
    public @ResponseBody
    ResponseEntity response_registration_request(@RequestBody RegistrationRequest registrationRequest) {
        int result = registrationRequestService.response(registrationRequest);
        if(result==-5){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("No tiene cupos disponibles");
        }else{
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
    }

    @GetMapping("/findregistrationrequestbygroupidandstate/{groupid}/{state}")
    public @ResponseBody
    ResponseEntity findregistrationrequestbygroupidandstate(@PathVariable("groupid") Long groupid, @PathVariable("state") String state) {
        List<RegistrationRequest> list = registrationRequestService.findByGroupIdsAndUserIdsWhitUser(Arrays.asList(new Long[]{groupid}), new ArrayList<>(), Arrays.asList(new String[]{state}));
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/findmemberbygroupidandstate/{groupid}/{state}")
    public @ResponseBody
    ResponseEntity findmemberbygroupidandstate(@PathVariable("groupid") Long groupid, @PathVariable("state") String state) {
        List<Member> list = memberService.findByGroupIdsWhitUser(Arrays.asList(new Long[]{groupid}), state);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @PutMapping("/update_member/{groupid}/{userid}/{state}")
    public @ResponseBody
    ResponseEntity update_member(@PathVariable("groupid") Long groupid, @PathVariable("userid") Long userid, @PathVariable("state") String state) {
        int result = memberService.update(groupid, userid, state);
        if(result==-5){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("No tiene cupos disponibles");
        }else{
            return new ResponseEntity<>(result, HttpStatus.OK);
        }
    }

    @GetMapping("/findRegistrationByUserIdAndGroupId/{groupId}/{userId}")
    public @ResponseBody
    ResponseEntity findRegistrationByUserIdAndGroupId(@PathVariable("groupId") Long groupId, @PathVariable("userId") Long userId) {
        List<RegistrationRequest> list = registrationRequestService.findByGroupIdsAndUserIds(Arrays.asList(new Long[]{groupId}),Arrays.asList(new Long[]{userId}),new ArrayList<>());
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

}
