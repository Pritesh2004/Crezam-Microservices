package com.crezam.auth_service.service;

import com.github.benmanes.caffeine.cache.Cache;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Random;

@Service
public class OtpService {

    private final Cache<String, String> otpCache;
    private final EmailService emailService;

    public OtpService(Cache<String, String> otpCache, EmailService emailService) {
        this.otpCache = otpCache;
        this.emailService = emailService;
    }

    // Generate a 6-digit OTP
    public String generateOtp() {
        SecureRandom random = new SecureRandom();
        return String.valueOf(100000 + random.nextInt(900000));
    }

    // Send OTP to Email
    public void sendOtp(String email) {
        String otp = generateOtp();
        otpCache.put(email, otp);
        emailService.sendOtp(email, otp);
    }


    // Validate OTP
    public boolean validateOtp(String email, String otp) {
        String storedOtp = otpCache.getIfPresent(email);
        if (storedOtp != null && storedOtp.equals(otp)) {
            otpCache.invalidate(email); // Remove OTP after successful validation
            return true;
        }
        return false;
    }
}
