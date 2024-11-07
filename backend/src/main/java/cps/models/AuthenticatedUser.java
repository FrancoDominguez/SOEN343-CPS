package cps.models;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import io.github.cdimascio.dotenv.Dotenv;
import cps.exceptions.UserNotFoundException;

public class AuthenticatedUser {
  private User user;
  private String token;

  public void Login(String email, String password) throws UserNotFoundException {
    // find user in database based on email
    // if doesn't find throw error
    // compare db password with input password
    // if matches, generate and return JWT
    // if doesn't match throw error

    this.token = generateToken("1");
  }

  // this function takes in the jwt and validates it
  public void Authenticate(String token) {
  }

  private String generateToken(String userId) {
    Dotenv dotenv = Dotenv.load();
    String sercretKey = dotenv.get("JWT_SECRET_KEY");
    Algorithm algorithm = Algorithm.HMAC256(sercretKey);
    String token = JWT.create()
        .withIssuer("auth0")
        .withClaim("userId", userId)
        .sign(algorithm);
    return token;
  }
}