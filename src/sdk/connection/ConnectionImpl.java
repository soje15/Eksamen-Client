package sdk.connection;


import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class ConnectionImpl {

  /**
   * This "ConnectionImpl" class, has the job of creating our HTTP-client
   * and overall, handling our connection to the server.
   *
   * It thus contains our execute method, which will execute the prepared HTTP-requests.
   * Furthermore, it contains our Responsehandler, which will handle any sucessful or
   * unsucessful transactions between the client & the server.
   *
   * Credit goes to Jesper Bruun Hansen, for assistance in creating this class.
   */

  //SERVER URL defined as a static variable.
  public static String serverURL = "http://localhost:6112/api";




  /**
   * HTTP client is created.
   */
  private CloseableHttpClient httpClient;

  public ConnectionImpl(){
    this.httpClient = HttpClients.createDefault();
  }


  /**
   * This function will execute a request to the server and await response.
   * It is thus used in our service-class.
   * @param uriRequest
   * @param parser
   */
  public void execute(HttpUriRequest uriRequest, final ResponseParser parser){



    // A custom responsehandler is created.
    ResponseHandler<String> responseHandler = new ResponseHandler<String>() {

      public String handleResponse(final HttpResponse response) throws IOException {

        //Statuscode from response gets allocated to our status variable.
        int status = response.getStatusLine().getStatusCode();

        //This if-statement, serves to check if the HTTP code is within the accepted spectrum
        //200-300 generally means, that connection was made successfully
        //400-500 generally means, that something went wrong in the HTTP protocol.
        if (status >= 200 && status < 300) {
          System.out.println("Connected to " + serverURL);

          //Returns HTTP entity. (Entities are in this context,
          //Entities that can be recieved or sent with a HTTP message.
          HttpEntity entity = response.getEntity();
          return entity != null ? EntityUtils.toString(entity) : null;
        } else {
          //Handle error to parser
            parser.error(status);
        }
        return null;
      }

    };


    /**
     * Client will attempt to execute request and allocate it to a string.
     */
    try {

        //Attempt to execute requests and allocate it to a string.
      String json = this.httpClient.execute(uriRequest, responseHandler);

      // Payload method will only be cast, if a JSON string is returned.
      if(json != null){
        parser.payload(json);
      }



    } catch (IOException e) {
      e.printStackTrace();
    }

  }

}
