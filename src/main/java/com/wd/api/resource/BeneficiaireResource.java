package com.wd.api.resource;

import com.wd.api.constant.AppConstant;
import com.wd.api.domain.Beneficiaire;
import com.wd.api.service.BeneficiaireService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.wd.api.constant.AppConstant.*;

@RestController
@RequestMapping("/beneficiaires")
public class BeneficiaireResource {

    private final BeneficiaireService beneficiaireService;



    public BeneficiaireResource(BeneficiaireService beneficiaireService) {
        this.beneficiaireService = beneficiaireService;
    }
    @PostMapping
    public ResponseEntity<?> save(@RequestBody Beneficiaire beneficiaire){
        try{
            Beneficiaire bdData=this.beneficiaireService.findOne(beneficiaire.getId());
            if(validateField(beneficiaire))
                return ResponseEntity.ok(Map.of(MESSAGE,ALL_FIELD_REQUIRED));
            if (Objects.isNull(bdData))
                return ResponseEntity.ok(Map.of(MESSAGE,BENEFICIARY_EXIST));
            this.beneficiaireService.save(beneficiaire);
            return ResponseEntity.ok(Map.of(MESSAGE,BENEFICIARY_ADDED));
        }catch (Exception e){
            e.printStackTrace();
        }
        return ResponseEntity.ok(Map.of(MESSAGE,SOMETHING_WENT_WRONG));
    }

    @PutMapping
    public ResponseEntity<?> update(@RequestBody Beneficiaire beneficiaire){
        try{
            Beneficiaire bdData=this.beneficiaireService.findOne(beneficiaire.getId());
            if(validateField(beneficiaire))
                return ResponseEntity.ok(Map.of(MESSAGE,ALL_FIELD_REQUIRED));
            if (Objects.isNull(bdData))
                return ResponseEntity.ok(Map.of(MESSAGE,BENEFICIARY_EXIST));
            return ResponseEntity.ok(Map.of(MESSAGE,BENEFICIARY_UPDATED));
        }catch (Exception e){
            e.printStackTrace();
        }
        return ResponseEntity.ok(Map.of(MESSAGE,SOMETHING_WENT_WRONG));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findOne(@PathVariable("id") Long id){
        try{
            Beneficiaire bdData=this.beneficiaireService.findOne(id);
            if (Objects.isNull(bdData))
                return ResponseEntity.ok(Map.of(MESSAGE,BENEFICIARY_NOT_FOUND));
            return ResponseEntity.ok(bdData);
        }catch (Exception e){
            e.printStackTrace();
        }
        return ResponseEntity.ok(Map.of(MESSAGE,SOMETHING_WENT_WRONG));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id){
        try{
            Beneficiaire bdData=this.beneficiaireService.findOne(id);
            if (Objects.isNull(bdData))
                return ResponseEntity.ok(Map.of(MESSAGE,BENEFICIARY_NOT_FOUND));
            this.beneficiaireService.delete(id);
            return ResponseEntity.ok(Map.of(MESSAGE,BENEFICIARY_DELETED));
        }catch (Exception e){
            e.printStackTrace();
        }
        return ResponseEntity.ok(Map.of(MESSAGE,SOMETHING_WENT_WRONG));
    }

    @DeleteMapping()
    public ResponseEntity<?> list(){
        try{
            List<Beneficiaire> bdData=this.beneficiaireService.findAll();
            if (Objects.isNull(bdData))
                return ResponseEntity.ok(Map.of(MESSAGE,EMPTY_DATA));
            return ResponseEntity.ok(bdData);
        }catch (Exception e){
            e.printStackTrace();
        }
        return ResponseEntity.ok(Map.of(MESSAGE,SOMETHING_WENT_WRONG));
    }

    private boolean validateField(Beneficiaire beneficiaire) {
        return
                beneficiaire.getNom().trim().isEmpty() ||
                beneficiaire.getPrenom().trim().isEmpty();
    }
}
