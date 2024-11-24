package cps.DomainLayer;

import cps.exceptions.UnauthorizedException;
import cps.models.ClientModel;
import cps.DAO.ClientDAO;
import cps.DTO.RequestBodies.SignupRequestbody;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
// import com.auth0.jwt.interfaces.DecodedJWT;
import io.github.cdimascio.dotenv.Dotenv;

public final class AuthenticationService {
  private static String secretKey;
  private static ClientDAO userDAO = new ClientDAO();

  static {
    Dotenv dotenv = Dotenv.load();
    secretKey = dotenv.get("JWT_SECRET_KEY");
  }

  private AuthenticationService() {
    // prevent instantiation
  }

  // example of how to use the mysql connector
  public static String login(String email, String password) throws Exception {
    ClientModel user = userDAO.fetchByEmail(email);

    if (user == null) {
      throw new Exception("Email is incorrect");
    }

    if (!user.validatePassword(password)) {
      throw new Exception("The entered password was incorrect");
    }

    String token = generateToken(user);
    return token;
  }

  // this function should be a decorator that wraps around any other function
  // that requires the user to be authenticated
  public void Authenticate(String token) throws UnauthorizedException {

    // validate the JWT
    // fetch and save the user's information somewhere
  }

  public static void createUser(SignupRequestbody data) {
    try {
      String firstname = data.getFirstname();
      String lastname = data.getLastname();
      String email = data.getEmail();
      String password = data.getPassword();

      ClientModel newUser = new ClientModel(firstname, lastname, email, password);
      userDAO.insert(newUser);
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }

  private static String generateToken(ClientModel user) {
    Algorithm algorithm = Algorithm.HMAC256(secretKey);
    String token = JWT.create()
        .withIssuer("auth0")
        .withClaim("userId", user.getId())
        .withClaim("firstname", user.getFirstname())
        .withClaim("lastname", user.getLastname())
        .withClaim("email", user.getEmail())
        .sign(algorithm);
    return token;
  }
}