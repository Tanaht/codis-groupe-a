package fr.istic.sit.codisgroupea.controller;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

/**
 * Controller for intervention basic routes
 * interventions/...
 */
@Controller
public class InterventionSocketController {
    private SimpMessagingTemplate simpMessagingTemplate;
}
