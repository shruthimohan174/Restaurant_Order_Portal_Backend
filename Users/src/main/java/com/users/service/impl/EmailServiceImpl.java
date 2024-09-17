package com.users.service.impl;

import com.users.constants.UserConstants;
import com.users.exception.InvalidRequestException;
import com.users.service.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.List;

/**
 * Implementation of the {@link EmailService} interface.
 * <p>
 * Provides functionality to send emails, specifically for "Contact Us" inquiries.
 * Uses Spring's {@link JavaMailSender} to handle the actual sending of emails.
 * </p>
 */
@Service
@Slf4j
public class EmailServiceImpl implements EmailService {

  /**
   * {@link JavaMailSender} used to send emails.
   * <p>
   * This is auto-wired by Spring and used to create and send {@link MimeMessage} instances.
   * </p>
   */
  @Autowired
  private JavaMailSender mailSender;

  /**
   * Email address used as the sender in the emails.
   * <p>
   * This value is injected from application properties using the key {@code spring.mail.username}.
   * </p>
   */
  @Value("${spring.mail.username}")
  private String senderEmail;

  /**
   * Name of the support team or individual handling the email inquiries.
   * <p>
   * This value is injected from application properties using the key {@code support.contact.name}.
   * </p>
   */
  @Value("${support.contact.name}")
  private String supportName;

  /**
   * Contact information for the support team or individual.
   * <p>
   * This value is injected from application properties using the key {@code support.contact.info}.
   * </p>
   */
  @Value("${support.contact.info}")
  private String supportContactInfo;

  /**
   * Sends an email in response to a "Contact Us" form submission.
   * <p>
   * The email is sent to the specified recipients with the provided subject and message.
   * The sender and contact information are configured through application properties.
   * </p>
   *
   * @param recipientEmails a list of email addresses of the recipients
   * @param subject         the subject of the email
   * @param customerName    the name of the customer sending the message
   * @param customMessage   the message content to be included in the email
   */
  @Override
  public void sendContactUsEmail(final List<String> recipientEmails, final String subject,
                                 final String customerName, final String customMessage) {
    try {
      MimeMessage mimeMessage = mailSender.createMimeMessage();
      MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

      String emailBody = "Hello " + customerName + "!\n\n"
        + customMessage + "\n\n"
        + "Best regards,\n"
        + supportName + "\n"
        + supportContactInfo;

      helper.setFrom(senderEmail);
      helper.setSubject(subject);
      helper.setText(emailBody);

      for (String recipientEmail : recipientEmails) {
        helper.setTo(recipientEmail);
        mailSender.send(mimeMessage);
        log.info("Contact Us email sent to {} with subject '{}'", recipientEmail, subject);
      }
    } catch (MessagingException | MailSendException e) {
      log.error("Failed to send Contact Us email with subject '{}'", subject, e);
      throw new InvalidRequestException(UserConstants.EMAIL_SENT_FAILURE);
    }
  }
}
