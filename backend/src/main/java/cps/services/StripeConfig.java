// StripeConfig.java
package cps.services;
import org.springframework.context.annotation.Configuration;

import com.stripe.Stripe;

import io.github.cdimascio.dotenv.Dotenv;

@Configuration
public class StripeConfig {
    public StripeConfig() {
        // Set your Stripe Secret Key
        Dotenv dotenv = Dotenv.load();
        Stripe.apiKey = dotenv.get("STRIPE_SECRET_KEY");
    }
}