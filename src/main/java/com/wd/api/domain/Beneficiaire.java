package com.wd.api.domain;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Data  @NoArgsConstructor @AllArgsConstructor
public class Beneficiaire {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false,updatable = false)
    private Long id;
    private String code;
    private String nom;
    private String prenom;
    private String sexe;
    private String dateNaissance;
    private String adresse;
    private String niveauEtude;
    private String telephone;
    private String imageUrl;
    private String nomPersonneReponse;
    private String phonePersonneResponsable;
    private String dateIntegration;
    private String commentaire;
    private String type;
    private String status ;// Actif, Inactif
    private String numeroCompte;
    private String moncashNumber;
    private String groupe;


}
