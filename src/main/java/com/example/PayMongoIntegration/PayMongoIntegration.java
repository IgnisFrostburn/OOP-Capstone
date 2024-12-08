package com.example.PayMongoIntegration;

import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PayMongoIntegration {
    private static final String API_KEY = "sk_test_avbmowaB9oVWWEy81zAzKFAy";
    private static final String BASE_URL = "https://api.paymongo.com/v1/";

    private final OkHttpClient client;
    private final ObjectMapper mapper;

    public PayMongoIntegration() {
        this.client = new OkHttpClient();
        this.mapper = new ObjectMapper();
    }

    public String createPaymentIntent(int amount, String currency) throws IOException {
        Map<String, Object> attributes = new HashMap<>();
        attributes.put("amount", amount); // Amount in cents (1 PHP = 100 cents)
        attributes.put("currency", currency);
        attributes.put("payment_method_allowed", new String[]{"gcash"});
        attributes.put("capture_type", "automatic");
        attributes.put("statement_descriptor", "ExcelOne"); // Optional descriptor for transactions

        Map<String, Object> data = new HashMap<>();
        data.put("attributes", attributes);

        Map<String, Object> payload = new HashMap<>();
        payload.put("data", data);

        RequestBody body = RequestBody.create(
                mapper.writeValueAsString(payload),
                MediaType.get("application/json")
        );

        Request request = new Request.Builder()
                .url(BASE_URL + "payment_intents")
                .header("Authorization", "Basic " + encodeAPIKey(API_KEY))
                .post(body)
                .build();

        try (Response response = client.newCall(request).execute()) {
            String responseBody = response.body() != null ? response.body().string() : "";

            if (response.isSuccessful()) {
                Map<String, Object> responseMap = mapper.readValue(responseBody, Map.class);
                return (String) ((Map<String, Object>) responseMap.get("data")).get("id");
            } else {
                throw new IOException("Failed to create Payment Intent. Status: " + response.code() + ", Response: " + responseBody);
            }
        }
    }

    public void createGCashSource(String paymentIntentId, int amount, String currency) throws IOException {
        String uniqueToken = UUID.randomUUID().toString();

        Map<String, Object> billing = new HashMap<>();
        billing.put("name", "John Doe");
        billing.put("email", "john.doe@example.com");

        Map<String, Object> redirect = new HashMap<>();
        redirect.put("success", "https://example.com/success?token=" + uniqueToken);

        redirect.put("failed", "https://example.com/failed");

        Map<String, Object> sourceAttributes = new HashMap<>();
        sourceAttributes.put("type", "gcash");
        sourceAttributes.put("amount", amount);
        sourceAttributes.put("currency", currency);
        sourceAttributes.put("billing", billing);
        sourceAttributes.put("redirect", redirect);
        sourceAttributes.put("payment_intent_id", paymentIntentId);

        Map<String, Object> sourceData = new HashMap<>();
        sourceData.put("attributes", sourceAttributes);

        Map<String, Object> payload = new HashMap<>();
        payload.put("data", sourceData);

        RequestBody body = RequestBody.create(
                mapper.writeValueAsString(payload),
                MediaType.get("application/json")
        );

        Request request = new Request.Builder()
                .url(BASE_URL + "sources")
                .header("Authorization", "Basic " + encodeAPIKey(API_KEY))
                .post(body)
                .build();

        Response response = client.newCall(request).execute();
        String responseBody = response.body().string();

        if (response.isSuccessful()) {
            Map<String, Object> responseMap = mapper.readValue(responseBody, Map.class);
            Map<String, Object> responseAttributes = (Map<String, Object>) ((Map<String, Object>) responseMap.get("data")).get("attributes");
            Map<String, Object> redirectUrls = (Map<String, Object>) responseAttributes.get("redirect");
            String checkoutUrl = (String) redirectUrls.get("checkout_url");

            if (checkoutUrl != null) {
                redirectToPayment(checkoutUrl);
            } else {
                System.err.println("Checkout URL is null.");
            }
        } else {
            throw new IOException("Failed to create GCash Source. Response: " + responseBody);
        }
    }

    public static String encodeAPIKey(String apiKey) {
        return Base64.getEncoder().encodeToString((apiKey + ":").getBytes());
    }

    public static void redirectToPayment(String checkoutUrl) {
        try {
            URI uri = new URI(checkoutUrl);
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().browse(uri);
            } else {
                System.out.println("Desktop is not supported. Please manually visit: " + checkoutUrl);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean handleRedirect(String redirectUrl) {
        try {
            URI uri = new URI(redirectUrl);
            String query = uri.getQuery();

            // Parse the query parameters into a map
            Map<String, String> params = new HashMap<>();
            if (query != null) {
                for (String param : query.split("&")) {
                    String[] pair = param.split("=");
                    if (pair.length == 2) {
                        params.put(pair[0], pair[1]);
                    }
                }
            }

            // Check for the token parameter in the URL
            String token = params.get("token");
            if (token != null) {
                System.out.println("Payment Success with Token: " + token);
                return true; // Payment successful, return true
            } else {
                System.err.println("No token found in redirect URL");
                return false; // No token found, payment failed or cancelled
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false; // Error occurred, return false
        }
    }


}
