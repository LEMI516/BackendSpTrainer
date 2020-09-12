package com.sptrainer.grouptrainerservice.controller;

import com.sptrainer.domain.model.*;
import com.sptrainer.domain.util.GeneralUtil;
import com.sptrainer.grouptrainerservice.service.GroupTrainerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE, RequestMethod.PUT})
@RequestMapping("group")
public class GroupController {

    @Autowired
    GroupTrainerService groupTrainerService;

    @GetMapping("/findbyuserid/{userid}")
    public @ResponseBody
    ResponseEntity findByUserId(@PathVariable("userid") Long userid) {
        List<GroupTrainer> list = groupTrainerService.findByUserId(userid);
        if (list != null && !list.isEmpty()) {
            return new ResponseEntity<>(list, HttpStatus.OK);
        } else {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Usuario no posee grupos asociados");
        }
    }

    @GetMapping("/findByTrainer/{userId}")
    public @ResponseBody
    ResponseEntity findByTrainer(@PathVariable("userId") Long userId) {
        List<GroupTrainer> list = groupTrainerService.findByTrainer(userId);
        if (list != null && !list.isEmpty()) {
            return new ResponseEntity<>(list, HttpStatus.OK);
        } else {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Usuario no posee grupos asociados");
        }
    }

    @GetMapping("/findbyidcompleteinfo/{idgroup}/{iduser}")
    public @ResponseBody
    ResponseEntity findByUserId(@PathVariable("idgroup") Long idgroup,
                                @PathVariable("iduser") Long iduser,
                                @RequestParam(value = "coordinate") String coordinate) {
        MessageResult<double[]> validateResult = GeneralUtil.validateCoordinate(coordinate);
        if (!validateResult.isResponse()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Coordenadas no validas");
        }
        GroupTrainer g = groupTrainerService.findByIdCompleteInfo(idgroup,validateResult.getObjeto(),iduser);
        return new ResponseEntity<>(g, HttpStatus.OK);
    }

    @GetMapping("/findbyuseridforclient/{userid}")
    public @ResponseBody
    ResponseEntity findByUserIdForClient(@PathVariable("userid") Long userid) {
        List<GroupTrainer> list = groupTrainerService.findByUserIdforClient(userid);
        if (list != null && !list.isEmpty()) {
            return new ResponseEntity<>(list, HttpStatus.OK);
        } else {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Usuario no posee grupos asociados");
        }
    }

    @GetMapping("/findbygroupidanduserid/{userid}/{groupid}")
    public @ResponseBody
    ResponseEntity findByGroupIdAndUserId(@PathVariable("userid") Long userid, @PathVariable("groupid") Long groupid) {
        GroupTrainer g = groupTrainerService.findByGroupIdAndUserId(userid, groupid);
        if (g.getId() != null) {
            return new ResponseEntity<>(g, HttpStatus.OK);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Grupo no encontrado");
        }
    }

    @GetMapping("/findbymember/{userid}")
    public @ResponseBody
    ResponseEntity findByMember(@PathVariable("userid") Long userid) {
        List<GroupTrainer> list = groupTrainerService.findByMember(userid);
        if (list != null && !list.isEmpty()) {
            return new ResponseEntity<>(list, HttpStatus.OK);
        } else {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Usuario no posee grupos asociados");
        }
    }

    @PostMapping("/save")
    public @ResponseBody
    ResponseEntity save(@RequestBody GroupTrainer groupTrainer) {
        GroupTrainer g = groupTrainerService.save(groupTrainer);
        return new ResponseEntity<>(g, HttpStatus.OK);
    }

    @PutMapping("/update")
    public @ResponseBody
    ResponseEntity update(@RequestBody GroupTrainer groupTrainer) {
        int result = groupTrainerService.update(groupTrainer);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/findbymultiplefilter")
    public @ResponseBody
    ResponseEntity findByMultipleFilter(@RequestParam(value = "idusers", required = false) String idusers,
                                        @RequestParam(value = "categories", required = false) String categories,
                                        @RequestParam(value = "filter", required = false) String filter,
                                        @RequestParam(value = "coordinate") String coordinate,
                                        @RequestParam(value = "iduser") Long iduser) {
        MessageResult<double[]> validateResult = GeneralUtil.validateCoordinate(coordinate);
        if (!validateResult.isResponse()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Coordenadas no validas");
        }

        SearchGroupRequestDTO dto = new SearchGroupRequestDTO();
        if (idusers != null && !idusers.isEmpty())
            dto.setIduser(GeneralUtil.getListByString(idusers, ",").stream().map(x -> Long.parseLong(x)).collect(Collectors.toList()));
        if (categories != null && !categories.isEmpty()) dto.setCategory(GeneralUtil.getListByString(categories, ","));
        dto.setFilter(filter);
        List<GroupTrainer> list = groupTrainerService.findByMultipleFilter(dto, validateResult.getObjeto(), iduser);
        if (list != null && !list.isEmpty()) {
            return new ResponseEntity<>(list, HttpStatus.OK);
        } else {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("La consulta generada no arrojo resultados");
        }
    }

    @GetMapping("/findbygroupinits")
    public @ResponseBody
    ResponseEntity findbygroupinits(@RequestParam(value = "coordinate") String coordinate,
                                    @RequestParam(value = "iduser") Long iduser) {
        MessageResult<double[]> validateResult = GeneralUtil.validateCoordinate(coordinate);
        if (!validateResult.isResponse()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Coordenadas no validas");
        }

        MessageResult<ResponseGroupInitDTO> result = groupTrainerService.findByGroupsInit(validateResult.getObjeto(), iduser);
        ResponseEntity resp;
        switch (result.getStatus()) {
            case 200:
                resp = new ResponseEntity<>(result.getObjeto(), HttpStatus.OK);
                break;
            case 404:
                resp = ResponseEntity.status(HttpStatus.NO_CONTENT).body(result.getMsj());
                break;
            default:
                resp = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result.getMsj());
                break;
        }
        return resp;
    }

    @PutMapping("/publish/{idgroup}/{iduser}")
    public @ResponseBody
    ResponseEntity publish(@PathVariable("idgroup") Long idgroup, @PathVariable("iduser") Long iduser) {
        MessageResult<Integer> publishResult = groupTrainerService.publish(iduser, idgroup);
        ResponseEntity resp;
        switch (publishResult.getStatus()) {
            case 200:
                resp = new ResponseEntity<>(publishResult.getStatus(), HttpStatus.OK);
                break;
            case 409:
                resp = ResponseEntity.status(HttpStatus.CONFLICT).body(publishResult.getMsj());
                break;
            default:
                resp = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(publishResult.getMsj());
                break;
        }
        return resp;
    }


}
