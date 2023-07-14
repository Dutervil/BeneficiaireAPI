package com.wd.api.service.impl;


import com.wd.api.domain.Beneficiaire;
import com.wd.api.repository.BeneficiaireRepository;
import com.wd.api.service.BeneficiaireService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;

import static com.wd.api.constant.FileConstant.*;
import static com.wd.api.constant.FileConstant.JPG_EXTENTION;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import static org.hibernate.bytecode.BytecodeLogger.LOGGER;

@Service @Transactional
public class BeneficiaireImpl implements BeneficiaireService {

    private final BeneficiaireRepository beneficiaireRepository;

    public BeneficiaireImpl(BeneficiaireRepository beneficiaireRepository) {
        this.beneficiaireRepository = beneficiaireRepository;
    }


    @Override
    public Beneficiaire addBeneficiare(String code, String nom, String prenom, String sexe, String dateNaissance, String adresse, String niveauEtude, String telephone, String nomPersonneReponse, String phonePersonneResponsable, String type,String status, String dateIntegration, String commentaire, MultipartFile profileImage) throws IOException {
        Beneficiaire beneficiaire=new Beneficiaire();
        beneficiaire.setNom(nom);
        beneficiaire.setPrenom(prenom);
        beneficiaire.setCode(code);
        beneficiaire.setSexe(sexe);
        beneficiaire.setStatus(status);
        beneficiaire.setAdresse(adresse);
        beneficiaire.setCommentaire(commentaire);
        beneficiaire.setDateIntegration(dateIntegration);
        beneficiaire.setDateNaissance(dateNaissance);
        beneficiaire.setNiveauEtude(niveauEtude);
        beneficiaire.setNomPersonneReponse(nomPersonneReponse);
        beneficiaire.setPhonePersonneResponsable(phonePersonneResponsable);
        beneficiaire.setTelephone(telephone);
        beneficiaire.setType(type);
        beneficiaire.setImageUrl(getTemporaryProfileImageUrl(beneficiaire));
        beneficiaireRepository.save(beneficiaire);
        saveProfileImage(beneficiaire,profileImage);
        LOGGER.info("New Beneficiary created: ");
        return beneficiaire;
    }

    @Override
    public Beneficiaire updateBeneficiare(Long id, String code, String nom, String prenom, String sexe, String dateNaissance, String adresse, String niveauEtude, String telephone, String nomPersonneReponse, String phonePersonneResponsable, String type,String status, String dateIntegration, String commentaire, MultipartFile profileImage) throws IOException {
        Beneficiaire beneficiaire=new Beneficiaire();
        beneficiaire.setId(id);
        beneficiaire.setNom(nom);
        beneficiaire.setPrenom(prenom);
        beneficiaire.setCode(code);
        beneficiaire.setSexe(sexe);
        beneficiaire.setStatus(status);
        beneficiaire.setAdresse(adresse);
        beneficiaire.setCommentaire(commentaire);
        beneficiaire.setDateIntegration(dateIntegration);
        beneficiaire.setDateNaissance(dateNaissance);
        beneficiaire.setNiveauEtude(niveauEtude);
        beneficiaire.setNomPersonneReponse(nomPersonneReponse);
        beneficiaire.setPhonePersonneResponsable(phonePersonneResponsable);
        beneficiaire.setTelephone(telephone);
        beneficiaire.setType(type);
        beneficiaire.setImageUrl(getTemporaryProfileImageUrl(beneficiaire));
        beneficiaireRepository.save(beneficiaire);
        saveProfileImage(beneficiaire,profileImage);
        LOGGER.info("New Beneficiary created: ");
        return beneficiaire;
    }

    @Override
    public Beneficiaire getById(Long id) {
        return beneficiaireRepository.findById(id).get();
    }

    @Override
    public List<Beneficiaire> getAll() {
        return beneficiaireRepository.findAll();
    }

    @Override
    public void deleteById(Long id) {
      beneficiaireRepository.deleteById(id);
    }

    @Override
    public Beneficiaire updateProfileImage(Long id, MultipartFile profileImage) throws IOException {
        Beneficiaire beneficiaire=beneficiaireRepository.getOne(id);
        saveProfileImage(beneficiaire,profileImage);
        return beneficiaire;
    }







    private void saveProfileImage(Beneficiaire beneficiaire, MultipartFile profileImage) throws IOException {

        if (profileImage !=null){
            Path beneficiaireFolder= Paths.get(BMS_FOLDER + beneficiaire.getNom()).toAbsolutePath().normalize();
            if (!Files.exists(beneficiaireFolder)){
                Files.createDirectories(beneficiaireFolder);
                LOGGER.info(DIRECTORY_CREATED);
            }
            Files.deleteIfExists(Paths.get(beneficiaireFolder+ beneficiaire.getNom()+DOT+JPG_EXTENTION));
            Files.copy(profileImage.getInputStream(),beneficiaireFolder.resolve(beneficiaire.getNom()+DOT+JPG_EXTENTION),REPLACE_EXISTING);
            beneficiaire.setImageUrl(setProfileUrl(beneficiaire.getNom()));
            beneficiaireRepository.save(beneficiaire);
            LOGGER.info(FILE_SAVED_IN_FILE_SYSTEM+profileImage.getOriginalFilename());
            LOGGER.info(DIRECTORY_CREATED + beneficiaireFolder);
        }
    }

    private String setProfileUrl(String username) {
        return ServletUriComponentsBuilder.fromCurrentContextPath().path(BMS_IMAGE_PATH +  username+FORWARD_SLASH
                +username + DOT + JPG_EXTENTION).toUriString();

    }

    private String getTemporaryProfileImageUrl(Beneficiaire beneficiaire) {
        return ServletUriComponentsBuilder.fromCurrentContextPath().path(DEFAULT_BENEFICIAIRE_IMAGE_PATH +  beneficiaire.getNom()).toUriString();
    }

}
