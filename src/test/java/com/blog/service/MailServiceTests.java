package com.blog.service;

import com.blog.util.LogUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.util.ReflectionTestUtils;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MailServiceTests {

    @Mock
    private JavaMailSender mockJavaMailSender;

    @InjectMocks
    private MailService mailServiceUnderTest;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(mailServiceUnderTest, "sendMailer", "2436056388@qq.com");
    }

    @Test
    void testSendTextMailMessage_Success() throws MessagingException {
        // Given
        final MimeMessage mimeMessage = new MimeMessage(Session.getInstance(new Properties()));
        when(mockJavaMailSender.createMimeMessage()).thenReturn(mimeMessage);

        // When
        mailServiceUnderTest.sendTextMailMessage("212270053@hdu.edu.cn,2436056388@qq.com", "subject", "text content");

        // Then
        verify(mockJavaMailSender).createMimeMessage();
        verify(mockJavaMailSender).send(any(MimeMessage.class));
    }

    @Test
    void testSendTextMailMessage_ThrowsMessagingException() throws MessagingException {
        // Given
        final MimeMessage mimeMessage = new MimeMessage(Session.getInstance(new Properties()));
        when(mockJavaMailSender.createMimeMessage()).thenReturn(mimeMessage);
        doThrow(MessagingException.class).when(mockJavaMailSender).send(any(MimeMessage.class));

        // When
        mailServiceUnderTest.sendTextMailMessage("212270053@hdu.edu.cn", "subject", "text content");

        // Then
        verify(mockJavaMailSender).createMimeMessage();
        verify(mockJavaMailSender).send(any(MimeMessage.class));
    }
}
