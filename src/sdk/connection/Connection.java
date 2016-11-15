package sdk.connection;


import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class Connection {

    //Server URL defineret som statisk felt-variabel
  public static String serverURL = "https://momentify.eu.ngrok.io/api";
  private CloseableHttpClient httpClient;


  public Connection(){
    this.httpClient = HttpClients.createDefault();
  }

// Laver et kald på serveren, og venter på at serveren giver et svar tilbage.
  public void execute(HttpUriRequest uriRequest, final ResponseParser parser){

    // Create a custom response handler
    ResponseHandler<String> responseHandler = new ResponseHandler<String>() {

      public String handleResponse(final HttpResponse response) throws IOException {
        int status = response.getStatusLine().getStatusCode();



          //Execute metoden kigger på om status på serveren er mellem 200/300. Da alt under 400/500 betyder der skete en
          //Fejl i http protokollen.
        if (status >= 200 && status < 300) {

            //Returnere en entity
          HttpEntity entity = response.getEntity();
          return entity != null ? EntityUtils.toString(entity) : null;
        } else {
          //Handle error
            parser.error(status);
        }
        return null;
      }

    };

    try {

        //Her returneres vores JSON til klienten.
      String json = this.httpClient.execute(uriRequest, responseHandler);

        parser.payload(json);

      //Handle successful response

    } catch (IOException e) {
      e.printStackTrace();
    }

  }

}
