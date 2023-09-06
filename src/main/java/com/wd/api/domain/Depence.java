package com.wd.api.domain;

import com.sun.jdi.event.StepEvent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Depence {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false,updatable = false)
    private Long id;
    private  String typeDepence;
    private double montant;
    private String dateDepence;
    private String description;

    private String accountNumber;
    private String moncashNumber;
    private String numeroCheque;
    private String nomCheque;
   private  String uniteMonetaire; //[USD,HTG]
    private String versement;
    private Long beneficiareId;

}
