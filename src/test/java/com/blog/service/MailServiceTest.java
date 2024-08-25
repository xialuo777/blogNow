package com.blog.service;

import com.blog.util.CodeUties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.util.ReflectionTestUtils;

import javax.mail.Session;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpSession;
import java.util.Properties;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MailServiceTest {

    @Mock
    private JavaMailSender mockJavaMailSender;

    @InjectMocks
    private MailService mailServiceUnderTest;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(mailServiceUnderTest, "sendMailer", "2436056388@qq.com");
    }

    @Test
    void testSendTextMailMessage() {
        // Setup
        // Configure JavaMailSender.createMimeMessage(...).
        final MimeMessage mimeMessage = new MimeMessage(Session.getInstance(new Properties()));
        when(mockJavaMailSender.createMimeMessage()).thenReturn(mimeMessage);

        // Run the test
        mailServiceUnderTest.sendTextMailMessage("to", "subject", "text");

        // Verify the results
        verify(mockJavaMailSender).send(any(MimeMessage.class));
    }

    @Test
    void testSendTextMailMessage_JavaMailSenderSendThrowsMailException() {
        // Setup
        // Configure JavaMailSender.createMimeMessage(...).
        final MimeMessage mimeMessage = new MimeMessage(Session.getInstance(new Properties()));
        when(mockJavaMailSender.createMimeMessage()).thenReturn(mimeMessage);

        doThrow(MailException.class).when(mockJavaMailSender).send(any(MimeMessage.class));

        // Run the test
        assertThatThrownBy(() -> mailServiceUnderTest.sendTextMailMessage("to", "subject", "text"))
                .isInstanceOf(MailException.class);
    }

    @Test
    void testSendCodeMailMessage() {
        // Setup
        String toEmail = "212270053@hdu.edu.cn";
        String code = CodeUties.getCode();
        //创建模拟的HttpSession对象
        final HttpSession session = Mockito.mock(HttpSession.class);
        when(session.getAttribute("code")).thenReturn(code);
        session.setAttribute("email", toEmail);

        // Configure JavaMailSender.createMimeMessage(...).
        final MimeMessage mimeMessage = new MimeMessage(Session.getInstance(new Properties()));
        when(mockJavaMailSender.createMimeMessage()).thenReturn(mimeMessage);

        // Run the test
        final boolean result = mailServiceUnderTest.sendCodeMailMessage("toEmail", session);

        // Verify the results
        assertThat(result).isTrue();
        verify(mockJavaMailSender).send(any(MimeMessage.class));
    }


    @Test
    void testSendCodeMailMessage_JavaMailSenderSendThrowsMailException() {
        // Setup
        final HttpSession session = null;

        // Configure JavaMailSender.createMimeMessage(...).
        final MimeMessage mimeMessage = new MimeMessage(Session.getInstance(new Properties()));
        when(mockJavaMailSender.createMimeMessage()).thenReturn(mimeMessage);

        doThrow(MailException.class).when(mockJavaMailSender).send(any(MimeMessage.class));

        // Run the test
        final boolean result = mailServiceUnderTest.sendCodeMailMessage("toEmail", session);

        // Verify the results
        assertThat(result).isFalse();
    }
}
