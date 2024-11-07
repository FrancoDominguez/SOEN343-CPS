package cps.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import io.github.cdimascio.dotenv.Dotenv;
import cps.exceptions.UserNotFoundException;

public class AuthenticationService {

  public void Login(String email, String password) throws UserNotFoundException {
    // find user in database based on email
    // if doesn't find throw error
    // compare db password with input password
    // if matches, generate and return JWT
    // if doesn't match throw error
  }

  // this function should be a decorator that wraps around any other function
  // that requires the user to be authenticated
  public void Authenticate(String token) {
    // validate the JWT
    // fetch and save the user's information somewhere
  }

  private String generateToken(String userId, String firstname, String lastname, String email) {
    Dotenv dotenv = Dotenv.load();
    String sercretKey = dotenv.get("JWT_SECRET_KEY");
    Algorithm algorithm = Algorithm.HMAC256(sercretKey);
    String token = JWT.create()
        .withIssuer("auth0")
        .withClaim("userId", userId)
        .withClaim("firstname", firstname)
        .withClaim("lastname", lastname)
        .withClaim("email", email)
        .sign(algorithm);
    return token;
  }
}