package cps.services;

import cps.models.User;
import java.sql.ResultSet;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import io.github.cdimascio.dotenv.Dotenv;

public final class AuthenticationService {
  private static String secretKey;

  static {
    Dotenv dotenv = Dotenv.load();
    secretKey = dotenv.get("JWT_SECRET_KEY");
  }

  private AuthenticationService() {
    // prevent instantiation
  }

  public static String login(String email, String password) throws Exception {
    ResultSet rs;
    Mysqlcon mysqlConnection = new Mysqlcon();
    mysqlConnection.connect();
    String queryString = String.format("SELECT * FROM client WHERE email='%s'", email);
    mysqlConnection.executeQuery(queryString);
    rs = mysqlConnection.getResultSet();
    User user;
    if (rs.next()) {
      int userId = rs.getInt("userId");
      String userFirstname = rs.getString("firstname");
      String userLastname = rs.getString("lastname");
      String userEmail = rs.getString("email");
      String userPassword = rs.getString("password");
      user = new User(userId, userFirstname, userLastname, userEmail, userPassword);
    } else {
      throw new Exception(String.format("User with email '%s' not found", email));
    }
    if (!user.validatePassword(password)) {
      throw new Exception("The entered password was incorrect");
    }
    String token = generateToken(user);
    mysqlConnection.close();
    return token;
  }

  // this function should be a decorator that wraps around any other function
  // that requires the user to be authenticated
  public void Authenticate(String token) {
    // validate the JWT
    // fetch and save the user's information somewhere
  }

  private static String generateToken(User user) {
    Algorithm algorithm = Algorithm.HMAC256(secretKey);
    String token = JWT.create()
        .withIssuer("auth0")
        .withClaim("userId", user.getUserId())
        .withClaim("firstname", user.getFirstname())
        .withClaim("lastname", user.getLastname())
        .withClaim("email", user.getEmail())
        .sign(algorithm);
    return token;
  }
}