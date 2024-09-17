package com.users.service;

import java.util.List;

/**
 * Service interface for email-related functionalities.
 * <p>
 * Provides methods to send emails, including handling "Contact Us" form submissions.
 * </p>
 */
public interface EmailService {

  /**
   * Sends an email in response to a "Contact Us" form submission.
   *
   * @param subject       the subject of the email
   * @param customerName  the name of the customer sending the message
   * @param customMessage the message content to be included in the email
   * @param recipientEmails  the list of recipient emails
   */
  void sendContactUsEmail(List<String> recipientEmails, String subject, String customerName, String customMessage);
}
