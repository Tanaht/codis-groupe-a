package fr.istic.sit.codisgroupea.controller;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

/**
 * Controller for symbol routes
 * interventions/{id}/symbols/...
 */
@Controller
public class SymbolSocketController {
    private SimpMessagingTemplate simpMessagingTemplate;
}
