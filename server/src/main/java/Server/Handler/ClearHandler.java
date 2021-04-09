package Server.Handler;

import DataAccess.DataAccessException;
import Results.ClearResult;
import Service.ClearService;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;

public class ClearHandler implements HttpHandler {
  @Override
  public void handle(HttpExchange exchange) throws IOException {
    try {
      if (exchange.getRequestMethod().equals("POST")) {
        ClearService service = new ClearService();
        ClearResult res = service.clear();

        String response = "{ \"message\": \"" + res.getMessage() + "\"}";

        if (res.getSuccess()) {
          exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
          OutputStream respBody = exchange.getResponseBody();
          writeString(response, respBody);
          respBody.close();
        } else {
          exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
          OutputStream respBody = exchange.getResponseBody();
          writeString(response, respBody);
          respBody.close();
        }
      }
    } catch (IOException | DataAccessException e) {
      exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
      exchange.getResponseBody().close();
      e.printStackTrace();
    }
  }

  private void writeString(String str, OutputStream oss) throws IOException {
    OutputStreamWriter osw = new OutputStreamWriter(oss);
    osw.write(str);
    osw.flush();
  }
}
