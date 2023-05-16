package com.ms.notificationservice.configuration;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import com.ms.notificationservice.model.HistoireTicket;
import com.ms.notificationservice.model.Membre;
import com.ms.notificationservice.model.Sprint;
import com.ms.notificationservice.model.TicketTache;
import com.ms.notificationservice.service.MembreFeignClient;
import com.ms.notificationservice.service.SprintFeignClient;
import com.ms.notificationservice.service.TicketHistoireFeignClient;
import com.ms.notificationservice.service.TicketTacheFeignClient;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

public class CheckSprintEnd implements Job {
    @Autowired
    MembreFeignClient membrefeignClient;
    @Autowired
    TicketTacheFeignClient ticketTacheFeignClient;
    @Autowired
    TicketHistoireFeignClient ticketHistoireFeignClient;
    @Autowired
    SprintFeignClient sprintFeignClient;

    @Autowired
    private JavaMailSender mailSender;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        Set<Membre> membresANotifier = new HashSet<>();
        List<Sprint> sprints = sprintFeignClient.getAllSprint();
        if (!sprints.isEmpty()) {
            for (Sprint sprint : sprints) {
                if (sprint.comparerFinAujourdhui()) {
                    List<HistoireTicket> histoires = ticketHistoireFeignClient.ticketHistoireBySprintId(sprint.getId());
                    if (!histoires.isEmpty()) {
                        membresANotifier.add(histoires.get(0).getMembre());
                        for (HistoireTicket histoire : histoires) {
                            List<TicketTache> taches = ticketTacheFeignClient
                                    .getTicketTacheByHistoireTicketId(histoire.getId());
                            for (TicketTache tache : taches) {
                                if (tache.getMembreId() != null)
                                    membresANotifier.add(tache.getMembre());
                            }
                        }
                        try {
                            generateMail(membresANotifier, sprint);
                        } catch (MessagingException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }

                }
                membresANotifier.clear();
            }

        }

    }

    public void generateMail(Set<Membre> membres, Sprint sprint) throws MessagingException { 
        String[] emails = membres.stream()
                             .map(Membre::getEmail)
                             .filter(Objects::nonNull)
                             .toArray(String[]::new);
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo(emails);
        helper.setSubject("Sprint expiration");
        /** creation de context le mien */
        String context = "Bonjourd ! on vous informe que aujourd'hui et la fint de \nsprint qui pour goal "
                + sprint.getObjectif() + " de ce projet veuillez verifier votre compte";
        String content = "<html><body style='background-color: #F5F5F5;'>";
        content += "<div style='background-color: #ffffff; padding: 20px; border-radius: 10px;'>";
        content += "<h1 style='color: #007bff; text-align:center;'>Projet: "
                + sprint.getProductBacklog().getProjet().getNom() + "</h1>";
        content += "<p style='text-align:center;'>Contexte: " + context + "</p>";
        content += "<div style='text-align:center;'><a href='http://localhost:4200/' style='display:inline-block; padding: 10px 20px; background-color: #007bff; color: #ffffff; border-radius: 5px; text-decoration: none;'>Accéder à l'application</a></div>";
        content += "</div>";
        content += "</body></html>";
        helper.setText(content, true);
        mailSender.send(message);

        /** ebd */

    }

}
