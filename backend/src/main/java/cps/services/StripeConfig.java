// StripeConfig.java
package cps.services;

import org.springframework.context.annotation.Configuration;
import com.stripe.Stripe;

@Configuration
public class StripeConfig {
    public StripeConfig() {
        // Set your Stripe Secret Key
        Stripe.apiKey = "sk_test_51QJSNLDKF4U2gF7ZGUkw01LQnan2cRPlhJDAtFIog2GpK9G5LoAdFVKR94k9AXJiuFewPO70nJbb7XWxaV4fSrgG00djB7W7Pp";
    }
}