package Server.Handler;

import DataAccess.DataAccessException;
import Requests.LoginRequest;
import Results.LoginResult;
import Service.LoginService;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.sql.SQLException;
import java.util.Scanner;

public class LoginHandler implements HttpHandler {
  Gson gson = new Gson();
  LoginService service = new LoginService();

  @Override
  public void handle(HttpExchange exchange) throws IOException {
    try {
      if (exchange.getRequestMethod().equals("POST")) {
        Scanner scanner = new Scanner(exchange.getRequestBody()).useDelimiter("\\A");
        String reqBody = scanner.hasNext() ? scanner.next() : "";
        LoginRequest req = gson.fromJson(reqBody, LoginRequest.class);
        LoginResult res = service.login(req);
        String response = gson.toJson(res);
        if (res.getSuccess()) {
          exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
          OutputStream resBody = exchange.getResponseBody();
          writeString(response, resBody);
          resBody.close();
        } else {
          exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
          OutputStream resBody = exchange.getResponseBody();
          writeString(response, resBody);
          resBody.close();
        }
      }
    } catch (IOException | DataAccessException | SQLException e) {
      e.printStackTrace();
      exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
      exchange.getResponseBody().close();
    }
  }

  private void writeString(String str, OutputStream oss) throws IOException {
    OutputStreamWriter osw = new OutputStreamWriter(oss);
    osw.write(str);
    osw.flush();
  }
}
