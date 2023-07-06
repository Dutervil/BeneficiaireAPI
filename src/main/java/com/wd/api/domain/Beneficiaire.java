package com.wd.api.domain;


import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity

public class Beneficiaire {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false,updatable = false)
    private Long id;
    private String code;
    private String nom;
    private String prenom;
    private Date dateNaissance;
    private String adresse;
    private String niveauEtude;
    private String telephone;
    private String imageUrl;
    private String nomPersonneReponse;
    private String phonePersonneResponsable;
    private Date dateIntegration;
    private String commentaire;
    private String type;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Dossier> dossierList;

    public Beneficiaire(){}
    public Beneficiaire(Long id, String code, String nom, String prenom, Date dateNaissance, String adresse, String niveauEtude, String telephone, String imageUrl, String nomPersonneReponse, String phonePersonneResponsable, Date dateIntegration, String commentaire, String type, List<Dossier> dossierList) {
        this.id = id;
        this.code = code;
        this.nom = nom;
        this.prenom = prenom;
        this.dateNaissance = dateNaissance;
        this.adresse = adresse;
        this.niveauEtude = niveauEtude;
        this.telephone = telephone;
        this.imageUrl = imageUrl;
        this.nomPersonneReponse = nomPersonneReponse;
        this.phonePersonneResponsable = phonePersonneResponsable;
        this.dateIntegration = dateIntegration;
        this.commentaire = commentaire;
        this.type = type;
        this.dossierList = dossierList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public Date getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(Date dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getNiveauEtude() {
        return niveauEtude;
    }

    public void setNiveauEtude(String niveauEtude) {
        this.niveauEtude = niveauEtude;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getNomPersonneReponse() {
        return nomPersonneReponse;
    }

    public void setNomPersonneReponse(String nomPersonneReponse) {
        this.nomPersonneReponse = nomPersonneReponse;
    }

    public String getPhonePersonneResponsable() {
        return phonePersonneResponsable;
    }

    public void setPhonePersonneResponsable(String phonePersonneResponsable) {
        this.phonePersonneResponsable = phonePersonneResponsable;
    }

    public Date getDateIntegration() {
        return dateIntegration;
    }

    public void setDateIntegration(Date dateIntegration) {
        this.dateIntegration = dateIntegration;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<Dossier> getDossierList() {
        return dossierList;
    }

    public void setDossierList(List<Dossier> dossierList) {
        this.dossierList = dossierList;
    }
}
