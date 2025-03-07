package com.github.jaguzmanb1.quasar;

import com.github.jaguzmanb1.quasar.entity.Satellite;
import com.github.jaguzmanb1.quasar.service.TranslationService;

import java.awt.*;

/**
 * Main class for the Quasar project.
 */
public class Main {
    public static void main(String[] args) {
        Satellite[] satellites = new Satellite[3];
        satellites[0] = new Satellite("Kenobi", new Point(-500, -200));
        satellites[1] = new Satellite("Skywalker", new Point(100, -100));
        satellites[2] = new Satellite("Sato", new Point(500, 100));

        TranslationService translationService = new TranslationService(satellites);

        try {
            Point location = translationService.getLocation(new double[]{100, 115.5, 142.7});
            System.out.println("Location: (" + location.getX() + ", " + location.getY() + ")");

            String message = translationService.getMessage(new String[][]{
                {"", "este", "", "", "mensaje"},
                {"", "este", "es", "", ""},
                {"", "", "", "un", ""}
            });
            System.out.println("Message: " + message);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}