package Server.Handler;

import DataAccess.DataAccessException;
import Results.PersonResult;
import Service.PersonService;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;

public class PersonHandler implements HttpHandler {
  PersonService service = new PersonService();
  Gson gson = new Gson();
  String response = "Error: Request denied.";
  PersonResult res;

  @Override
  public void handle(HttpExchange exchange) throws IOException {
    try {
      if (exchange.getRequestMethod().equals("GET")) {
        if (exchange.getRequestHeaders().containsKey("Authorization")) {
          String token = exchange.getRequestHeaders().getFirst("Authorization");
          String uri = exchange.getRequestURI().toString();

          if (uri.equals("/person")) {
            res = service.Person(token);
            response = gson.toJson(res);
          } else if (uri.startsWith("/person/")) {
            res = service.Person(uri.substring(8), token);
            response = gson.toJson(res);
          } else {
            response = "Error: Request is not valid.";
          }

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
      }
    } catch (DataAccessException e) {
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
