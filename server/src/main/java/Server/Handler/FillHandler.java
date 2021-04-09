package Server.Handler;

import DataAccess.DataAccessException;
import Results.FillResult;
import Service.FillService;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;

public class FillHandler implements HttpHandler {
  FillService service = new FillService();
  Gson gson = new Gson();

  @Override
  public void handle(HttpExchange exchange) throws IOException {
    FillResult res;
    String response;
    try {
      if (exchange.getRequestMethod().equals("POST")) {

        String uri = exchange.getRequestURI().toString();
        uri = uri.substring(6);

        if (uri.contains("/")) {
          int temp = uri.indexOf("/");
          res = service.Fill(uri.substring(0, temp), Integer.parseInt(uri.substring(temp + 1)));
        } else {
          res = service.Fill(uri, 4);
        }
        response = gson.toJson(res);
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
