package com.ms.gestionProductBacklog.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class HistoireTicket {
    private Long id;
    private String priorite;
    private int effort;
    private int position;
    private Date dateFin;
}
