package com.bts.model.mail;

import lombok.Builder;
import lombok.Data;


@Data
public class EmailDto {
    private String subject;
    private String body;
    private String addressee;
    private String userName;
    private String fondoName;
    private boolean isSuscripcion;

    @Builder
    public EmailDto(String subject, String addressee, String userName, String fondoName, boolean isSuscripcion) {
        this.subject = subject;
        this.addressee = addressee;
        this.userName = userName;
        this.fondoName = fondoName;
        this.isSuscripcion = isSuscripcion;
        this.body = generarBody();
    }

    private String generarBody() {
        String mensaje = isSuscripcion ? generarMensajeSuscripcion() : generarMensajeCancelacion();
        return generarPlantillaHtml(mensaje);
    }

    private String generarMensajeSuscripcion() {
        return String.format(
                "<p>Hola <strong>%s</strong>,</p>" +
                        "<p>¡Te has suscrito exitosamente al fondo <strong>%s</strong>!</p>" +
                        "<p>A partir de ahora podrás ver el rendimiento de tu inversión en tu panel de usuario.</p>",
                userName, fondoName
        );
    }

    private String generarMensajeCancelacion() {
        return String.format(
                "<p>Hola <strong>%s</strong>,</p>" +
                        "<p>Has cancelado tu suscripción al fondo <strong>%s</strong>.</p>" +
                        "<p>Si tienes alguna duda, no dudes en contactarnos.</p>",
                userName, fondoName
        );
    }

    private String generarPlantillaHtml(String mensaje) {
        return """
                <!DOCTYPE html>
                <html lang="es">
                <head>
                    <meta charset="UTF-8">
                    <meta http-equiv="X-UA-Compatible" content="IE=edge">
                    <meta name="viewport" content="width=device-width, initial-scale=1.0">
                    <title>%s</title>
                    <style>
                        %s
                    </style>
                </head>
                <body>
                    <div class="container">
                        <div class="header">
                            <h2>BTG Pactual</h2>
                        </div>
                        <div class="content">
                            %s
                        </div>
                        <div class="footer">
                            <p>Gracias por confiar en nuestro servicio.</p>
                            <p>Atentamente,</p>
                            <p><strong>Equipo de BTG Pactual</strong></p>
                        </div>
                    </div>
                </body>
                </html>
                """.formatted(subject, obtenerEstilosCSS(), mensaje);
    }

    private String obtenerEstilosCSS() {
        return """
                body {
                    font-family: Arial, sans-serif;
                    color: #333;
                    background-color: #f9f9f9;
                    margin: 0;
                    padding: 20px;
                }
                .container {
                    background-color: #ffffff;
                    padding: 20px;
                    border-radius: 8px;
                    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
                }
                .header {
                    background-color: #D40808;
                    color: white;
                    padding: 10px 0;
                    text-align: center;
                    border-radius: 8px;
                }
                .content {
                    margin-top: 20px;
                }
                .content p {
                    font-size: 16px;
                    line-height: 1.5;
                }
                .footer {
                    font-size: 12px;
                    color: #777;
                    margin-top: 30px;
                    text-align: center;
                }
                """;
    }
}
