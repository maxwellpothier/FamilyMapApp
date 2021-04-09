package Server.Handler;

import DataAccess.DataAccessException;
import Results.EventResult;
import Service.EventService;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;

public class EventHandler implements HttpHandler {
  @Override
  public void handle(HttpExchange exchange) throws IOException {
    String response;
    Gson gson = new Gson();
    EventService service = new EventService();
    EventResult res = new EventResult("Error: Fatal res error.", false);

    try {
      if (exchange.getRequestMethod().equals("GET")) {
        if (exchange.getRequestHeaders().containsKey("Authorization")) {
          String authToken = exchange.getRequestHeaders().getFirst("Authorization");
          String uri = exchange.getRequestURI().toString();

          if (uri.equals("/event")) {
            res = service.event(authToken);
            if (res == null) {
              assert false;
              response = "{ \"message\": \"" + res.getMessage() + "\"}";
            } else {
              response = gson.toJson(res);
            }
          } else if (uri.substring(0, 7).equals("/event/")) {
            res = service.event(uri.substring(7), authToken);
            if (!(res.getMessage() == null)) {
              response = "{ \"message\": \"" + res.getMessage() + "\"}";
            } else {
              response = gson.toJson(res);
            }
          } else {
            response = "Error: Request is invalid.";
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
    } catch (IOException | DataAccessException e) {
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
