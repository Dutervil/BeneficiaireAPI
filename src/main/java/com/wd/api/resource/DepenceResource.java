package com.wd.api.resource;

import com.wd.api.domain.Depence;
import com.wd.api.service.DepenceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static com.wd.api.constant.AppConstant.MESSAGE;
import static com.wd.api.constant.AppConstant.SOMETHING_WENT_WRONG;

@RestController
@RequestMapping("/depence")
@CrossOrigin("*")
public class DepenceResource {

    private  final DepenceService depenceService;

    public DepenceResource(DepenceService depenceService) {
        this.depenceService = depenceService;
    }

    @PostMapping()
    public ResponseEntity<?> save(@RequestBody Depence depence){

        try{
            return new ResponseEntity<>(this.depenceService.create(depence), HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
        }
        return ResponseEntity.ok(Map.of(MESSAGE,SOMETHING_WENT_WRONG));
    }

    @PutMapping()
    public ResponseEntity<?> update(@RequestBody Depence depence){

        try{
            return new ResponseEntity<>(this.depenceService.update(depence), HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
        }
        return ResponseEntity.ok(Map.of(MESSAGE,SOMETHING_WENT_WRONG));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id){

        try{
            this.depenceService.delete(id);
            return new ResponseEntity<>(null, HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
        }
        return ResponseEntity.ok(Map.of(MESSAGE,SOMETHING_WENT_WRONG));
    }
    @GetMapping()
    public ResponseEntity<?> list(){

        try{
            return new ResponseEntity<>(this.depenceService.list(), HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
        }
        return ResponseEntity.ok(Map.of(MESSAGE,SOMETHING_WENT_WRONG));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> find(@PathVariable("id") Long id){

        try{
            return new ResponseEntity<>(this.depenceService.find(id), HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
        }
        return ResponseEntity.ok(Map.of(MESSAGE,SOMETHING_WENT_WRONG));
    }

    @GetMapping("/beneficiaire/{id}")
    public ResponseEntity<?> findByBeneficiary(@PathVariable("id") String id){

        try{
            return new ResponseEntity<>(this.depenceService.findByBeneficiareId(Long.parseLong(id)), HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
        }
        return ResponseEntity.ok(Map.of(MESSAGE,SOMETHING_WENT_WRONG));
    }


}
