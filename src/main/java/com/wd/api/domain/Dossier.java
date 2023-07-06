package com.wd.api.domain;



import javax.persistence.*;


@Entity
public class Dossier {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false,updatable = false)
    private Long id;
    @OneToOne(cascade = CascadeType.ALL)
    private Beneficiaire beneficiaire;
    private String docFileUrl;
    private String titre;
    private String description;

    public Dossier() {
    }

    public Dossier(Long id, Beneficiaire beneficiaire, String docFileUrl, String titre, String description) {
        this.id = id;
        this.beneficiaire = beneficiaire;
        this.docFileUrl = docFileUrl;
        this.titre = titre;
        this.description = description;
    }
}
