package com.wd.api.domain;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Entity @Data @AllArgsConstructor @NoArgsConstructor
public class Dossier {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false,updatable = false)
    private Long id;

    private String docFileUrl;
    private String titre;
    private String description;



}
