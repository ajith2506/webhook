package com.demo.webhook.controller;

import com.demo.webhook.model.PushEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.util.Formatter;

@RestController
@RequestMapping("/webhook")
public class WebhookController {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Value("${webhook.secret.key}")
    private String secretKey;

    @PostMapping("/receiver")
    public ResponseEntity<String> handleWebhook(
            @RequestBody String payload,
            @RequestHeader("X-GitHub-Event") String event,
            @RequestHeader("X-Hub-Signature-256") String signature) {

        try {
            if (!verifySignature(payload, signature)) {
                return ResponseEntity.status(403).body("Invalid signature");
            }

            if (!"push".equalsIgnoreCase(event)) {
                return ResponseEntity.badRequest().body("Unsupported event type: " + event);
            }

            PushEvent pushEvent = objectMapper.readValue(payload, PushEvent.class);
            System.out.println("Push Event Processed: " + pushEvent);

            System.out.println("Repository: " + pushEvent.getRepository().getName());
            pushEvent.getCommits().forEach(commit -> {
                System.out.println("Commit: " + commit.getId() + " - " + commit.getMessage());
                System.out.println("Author: " + commit.getAuthor().getName() +
                        " <" + commit.getAuthor().getEmail() + ">");
            });

            return ResponseEntity.ok("Push event processed successfully");

        } catch (Exception e) {
            System.err.println("Error processing push event:" + e.getMessage());
            return ResponseEntity.status(500).body("Error processing push event");
        }
    }

    private boolean verifySignature(String payload, String signature) {
        try {
            // Create HMAC SHA-256 instance with the secret key
            Mac hmac = Mac.getInstance("HmacSHA256");
            SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(), "HmacSHA256");
            hmac.init(secretKeySpec);

            byte[] hash = hmac.doFinal(payload.getBytes());

            String computedSignature = "sha256=" + toHexString(hash);
            return computedSignature.equals(signature);

        } catch (Exception e) {
            System.err.println("Error verifying signature: " + e.getMessage());
            return false;
        }
    }

    private String toHexString(byte[] bytes) {
        try (Formatter formatter = new Formatter()) {
            for (byte b : bytes) {
                formatter.format("%02x", b);
            }
            return formatter.toString();
        }
    }
}
