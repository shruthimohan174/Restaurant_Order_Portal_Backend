package com.users.service.impl;

import com.users.constants.UserConstants;
import com.users.exception.InvalidRequestException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.util.ReflectionTestUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Unit tests for the {@link EmailServiceImpl} class.
 * <p>
 * This class contains test cases for the methods in the {@link EmailServiceImpl} class,
 * including sending emails and handling exceptions.
 * </p>
 */
@ExtendWith(MockitoExtension.class)
public class EmailServiceImplTests {

  /**
   * Mock of {@link JavaMailSender} used for sending emails.
   * <p>
   * This mock is injected into the {@link EmailServiceImpl} instance to simulate
   * email sending functionality.
   * </p>
   */
  @Mock
  private JavaMailSender mailSender;

  /**
   * Mock of {@link MimeMessage} used for creating email messages.
   * <p>
   * This mock is used to simulate the behavior of the {@link MimeMessage} class
   * during email sending.
   * </p>
   */
  @Mock
  private MimeMessage mimeMessage;

  /**
   * The {@link EmailServiceImpl} instance to be tested.
   * <p>
   * This instance is injected with the mocked {@link JavaMailSender} and {@link MimeMessage}
   * to perform unit tests.
   * </p>
   */
  @InjectMocks
  private EmailServiceImpl emailService;

  /**
   * Sets up the test environment by setting dummy data.
   */
  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    ReflectionTestUtils.setField(emailService, "senderEmail", "email");
    ReflectionTestUtils.setField(emailService, "supportName", "customer Support Team");
    ReflectionTestUtils.setField(emailService, "supportContactInfo", "supportemail");
  }

  @Test
  void testSendContactUsEmailSuccess() throws MessagingException {
    when(mailSender.createMimeMessage()).thenReturn(mimeMessage);

    List<String> recipientEmails = Arrays.asList("recipientEmail", "recipient2Email");
    emailService.sendContactUsEmail(recipientEmails, "Test Subject", "customer", "Test message");

    verify(mailSender, times(1)).createMimeMessage();
    verify(mailSender, times(recipientEmails.size())).send(any(MimeMessage.class));
  }

  @Test
  void testSendContactUsEmailFailureThrowsException() {
    when(mailSender.createMimeMessage()).thenReturn(mimeMessage);
    doThrow(new MailSendException("Test exception")).when(mailSender).send(any(MimeMessage.class));

    List<String> recipientEmails = Arrays.asList("recipientEmail", "recipient2Email");
    assertThrows(InvalidRequestException.class, () ->
      emailService.sendContactUsEmail(recipientEmails, "Test Subject", "customer", "Test message")
    );

    verify(mailSender, times(1)).createMimeMessage();
    verify(mailSender, times(1)).send(any(MimeMessage.class));
  }

  @Test
  void testSendContactUsEmailMessagingExceptionThrowsEmailSendingException() throws MessagingException {
    when(mailSender.createMimeMessage()).thenReturn(mimeMessage);
    doThrow(new MessagingException("Test exception")).when(mimeMessage).setSubject(anyString());

    List<String> recipientEmails = Arrays.asList("recipientEmail", "recipient2Email");
    InvalidRequestException exception = assertThrows(InvalidRequestException.class, () ->
      emailService.sendContactUsEmail(recipientEmails, "Test Subject", "customer", "Test message")
    );

    verify(mailSender, times(1)).createMimeMessage();
    verify(mailSender, never()).send(any(MimeMessage.class));
    assert exception.getMessage().equals(UserConstants.EMAIL_SENT_FAILURE);
  }
}
