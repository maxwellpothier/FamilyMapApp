package Server.Handler;

import DataAccess.DataAccessException;
import Requests.LoadRequest;
import Results.LoadResult;
import Service.LoadService;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.util.Scanner;

public class LoadHandler implements HttpHandler {
  LoadService service = new LoadService();
  Gson gson = new Gson();

  @Override
  public void handle(HttpExchange exchange) throws IOException {
    try {
      if (exchange.getRequestMethod().equals("POST")) {
        Scanner scanner = new Scanner(exchange.getRequestBody()).useDelimiter("\\A");
        String reqBody = scanner.hasNext() ? scanner.next() : "";
        LoadRequest req = gson.fromJson(reqBody, LoadRequest.class);
        LoadResult res = service.Load(req);
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
    } catch (DataAccessException | IOException inputException) {
      exchange.sendResponseHeaders(HttpURLConnection.HTTP_SERVER_ERROR, 0);
      exchange.getResponseBody().close();
      inputException.printStackTrace();
    }
  }

  private void writeString(String str, OutputStream oss) throws IOException {
    OutputStreamWriter osw = new OutputStreamWriter(oss);
    osw.write(str);
    osw.flush();
  }
}
