package com.wd.api.service;

import com.wd.api.domain.Beneficiaire;
import com.wd.api.domain.User;
import com.wd.api.exception.domain.EmailExistException;
import com.wd.api.exception.domain.EmailNotFoundException;
import com.wd.api.exception.domain.UserNotFoundException;
import com.wd.api.exception.domain.UsernameExistException;
import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.stylesheets.LinkStyle;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.Date;
import java.util.List;

public interface BeneficiaireService {

    Beneficiaire addBeneficiare(String code,
                        String nom,
                        String prenom,
                        String sexe,
                        String dateNaissance,
                        String adresse,
                        String niveauEtude,
                        String telephone,
                        String nomPersonneReponse,
                        String phonePersonneResponsable,
                        String type,
                        String status,
                        String dateIntegration,
                        String commentaire,
                        MultipartFile profileImage) throws IOException;

    Beneficiaire updateBeneficiare(
                        Long id,
                        String code,
                        String nom,
                        String prenom,
                        String sexe,
                        String dateNaissance,
                        String adresse,
                        String niveauEtude,
                        String telephone,
                        String nomPersonneReponse,
                        String phonePersonneResponsable,
                        String type,
                        String status,
                        String dateIntegration,
                        String commentaire,
                        MultipartFile profileImage) throws IOException;

    Beneficiaire getById(Long id);
    List<Beneficiaire> getAll();
    void deleteById(Long id);
    Beneficiaire updateProfileImage(Long id,MultipartFile profileImage) throws  IOException;


}


