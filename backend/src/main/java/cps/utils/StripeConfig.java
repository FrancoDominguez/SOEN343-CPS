// StripeConfig.java
package cps.utils;
import org.springframework.context.annotation.Configuration;

import com.stripe.Stripe;

import io.github.cdimascio.dotenv.Dotenv;

@Configuration
public class StripeConfig {
    public StripeConfig() {
        Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();
        String secretKey = dotenv.get("STRIPE_SECRET_KEY");

        if (secretKey == null || secretKey.isEmpty()) {
            throw new IllegalStateException("Stripe secret key not configured");
        }

        Stripe.apiKey = secretKey;
    }
}
