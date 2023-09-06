package com.wd.api.resource;

import com.wd.api.constant.AppConstant;
import com.wd.api.domain.Beneficiaire;
import com.wd.api.domain.HttpResponse;
import com.wd.api.domain.User;
import com.wd.api.exception.domain.EmailExistException;
import com.wd.api.exception.domain.UserNotFoundException;
import com.wd.api.exception.domain.UsernameExistException;
import com.wd.api.service.BeneficiaireService;
import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.wd.api.constant.AppConstant.*;
import static com.wd.api.constant.FileConstant.*;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.IMAGE_JPEG_VALUE;

@RestController
@RequestMapping("/beneficiaire")
@CrossOrigin("*")
public class BeneficiaireResource {

    private final BeneficiaireService beneficiaireService;


    public BeneficiaireResource(BeneficiaireService beneficiaireService) {
        this.beneficiaireService = beneficiaireService;
    }

    @PostMapping
    public ResponseEntity<?> save(
            @RequestParam("code") String code,
            @RequestParam("nom") String nom,
            @RequestParam("prenom") String prenom,
            @RequestParam("sexe") String sexe,
            @RequestParam("dateNaissance") String dateNaissance,
            @RequestParam("adresse") String adresse,
            @RequestParam("niveauEtude") String niveauEtude,
            @RequestParam("telephone") String telephone,
            @RequestParam("nomPersonneReponse") String nomPersonneReponse,
            @RequestParam("phonePersonneResponsable") String phonePersonneResponsable,
            @RequestParam("dateIntegration") String dateIntegration,
            @RequestParam("type") String type,
            @RequestParam("commentaire") String commentaire,
            @RequestParam("status") String status,
            @RequestParam("numeroCompte") String numeroCompte,
            @RequestParam("numeroMoncash") String numeroMoncash,
            @RequestParam("groupe") String groupe,
            @RequestParam(value = "profileImage",required = false) MultipartFile profileImage) throws IOException{
        try{
            Beneficiaire beneficiaire=this.beneficiaireService.addBeneficiare(
                    code,nom,prenom,sexe,dateNaissance,adresse,niveauEtude,
                    telephone,nomPersonneReponse,phonePersonneResponsable,type,
                    status,dateIntegration,commentaire,numeroCompte,numeroMoncash,groupe,profileImage
            );
        return  new ResponseEntity<>(beneficiaire, HttpStatus.OK);

        }catch (Exception e){
            e.printStackTrace();
        }
        return ResponseEntity.ok(Map.of(MESSAGE,SOMETHING_WENT_WRONG));
    }

    @PutMapping
    public ResponseEntity<?> update(
            @RequestParam("id") String id,
            @RequestParam("code") String code,
            @RequestParam("nom") String nom,
            @RequestParam("prenom") String prenom,
            @RequestParam("sexe") String sexe,
            @RequestParam("dateNaissance") String dateNaissance,
            @RequestParam("adresse") String adresse,
            @RequestParam("niveauEtude") String niveauEtude,
            @RequestParam("telephone") String telephone,
            @RequestParam("nomPersonneReponse") String nomPersonneReponse,
            @RequestParam("phonePersonneResponsable") String phonePersonneResponsable,
            @RequestParam("dateIntegration") String dateIntegration,
            @RequestParam("type") String type,
            @RequestParam("commentaire") String commentaire,
            @RequestParam("status") String status,

            @RequestParam("numeroCompte") String numeroCompte,
            @RequestParam("numeroMoncash") String numeroMoncash,
            @RequestParam("groupe") String groupe,
            @RequestParam(value = "profileImage",required = false) MultipartFile profileImage
    ){
        try{
            Beneficiaire beneficiaire=this.beneficiaireService.updateBeneficiare(
                    Long.parseLong(id),
                    code,nom,prenom,sexe,dateNaissance,adresse,niveauEtude,
                    telephone,nomPersonneReponse,phonePersonneResponsable,type,
                    status,dateIntegration,commentaire,numeroCompte,numeroMoncash,groupe,profileImage
            );
            return  new ResponseEntity<>(beneficiaire, HttpStatus.OK);

        }catch (Exception e){
            e.printStackTrace();
        }
        return ResponseEntity.ok(Map.of(MESSAGE,SOMETHING_WENT_WRONG));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findOne(@PathVariable("id") Long id){
        try{
          return new ResponseEntity<>(this.beneficiaireService.getById(id),HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
        }
        return ResponseEntity.ok(Map.of(MESSAGE,SOMETHING_WENT_WRONG));
    }

    @DeleteMapping("/{id}")
//    @PreAuthorize("hasAnyAuthority('beneficiare:delete,beneficiare:read')")
    public ResponseEntity<?> delete(@PathVariable("id") Long id){
        try{
        this.beneficiaireService.deleteById(id);
        return new ResponseEntity<>("Beneficiary deleted",HttpStatus.NO_CONTENT);
        }catch (Exception e){
            e.printStackTrace();
        }
        return ResponseEntity.ok(Map.of(MESSAGE,SOMETHING_WENT_WRONG));
    }


   @GetMapping
    public ResponseEntity<?> list(){
        try{
         return new ResponseEntity<>(this.beneficiaireService.getAll(),HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
        }
        return ResponseEntity.ok(Map.of(MESSAGE,SOMETHING_WENT_WRONG));
    }



    @PutMapping("/updateProfileImage")
    public ResponseEntity<Beneficiaire>updateProfileImage(
            @RequestParam("id") String id,
            @RequestParam("profileImage") MultipartFile profileImage) throws IOException {
        Beneficiaire beneficiaire=beneficiaireService.updateProfileImage(Long.parseLong(id), profileImage);

        return new ResponseEntity<>(beneficiaire,OK);
    }


    @GetMapping(path ="/image/{nom}/{fileName}",produces = {IMAGE_JPEG_VALUE})
    public byte[] getProfileImage( @PathVariable("nom") String nom,
                                   @PathVariable("fileName") String fileName) throws IOException {
        return Files.readAllBytes(Paths.get(BMS_FOLDER+nom+FORWARD_SLASH+fileName));
    }

    @GetMapping(path ="/image/profile/{nom}",produces = {IMAGE_JPEG_VALUE})
    public byte[] getTempProfileImage( @PathVariable("nom") String nom) throws IOException {
        URL url=new URL(TEMP_PROFILE_IAMGE_BASE_URL+nom);
        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
        try(InputStream inputStream=url.openStream()) {
            int bytesRead;
            byte[] chunk=new byte[1024];
            while ((bytesRead=inputStream.read(chunk))>0){
                byteArrayOutputStream.write(chunk,0,bytesRead);
            }
        }
        return byteArrayOutputStream.toByteArray();
    }
    private ResponseEntity<HttpResponse> response(HttpStatus httpStatus, String message) {
        return new ResponseEntity<>(new HttpResponse(httpStatus.value(),httpStatus,httpStatus.getReasonPhrase().toUpperCase(),message.toUpperCase()),httpStatus);
    }
}
