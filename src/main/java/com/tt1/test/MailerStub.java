package com.tt1.test;

/**
 * Simula el servicio de envío de correos electrónicos.
 * En lugar de enviar un email real, imprime el mensaje por la consola.
 */

public class MailerStub implements IMailer{
	
	/**
     * Simula el envío de un correo electrónico a una dirección específica.
     * * @param direccion La dirección de email del destinatario.
     * @param mensaje El contenido del correo a enviar.
     * @return true si la simulación del envío se ha realizado correctamente.
     */
	
	public boolean enviarCorreo(String direccion, String mensaje) {
        System.out.println("-> [EMAIL ENVIADO A: " + direccion + "] Mensaje: " + mensaje);
        return true;
    }
}
