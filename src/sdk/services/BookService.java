package sdk.services;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sun.tools.internal.ws.processor.model.Response;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import sdk.connection.Connection;
import sdk.connection.ResponseCallback;
import sdk.connection.ResponseParser;
import sdk.models.Book;
import sdk.models.Delete;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

/**
 * Created by sorenkolbyejensen on 14/11/2016.
 */

public class BookService {
    private Connection connection;
    private Gson gson;

    public BookService() {
        this.connection = new Connection();
        this.gson = new Gson();
    }

    /**
     *
     * Følgende metode opdatere en bog ud fra et ID.
     *
     * OBS: brug StringEntity, når du skal ind og "Indsætte" data - eks create, update.
     *
     * @param id
     * @param book
     * @param responseCallback
     */


    public void update(String id, Book book, final ResponseCallback<Book> responseCallback){

        try {
        HttpPut updateRequest = new HttpPut(Connection.serverURL + "/books/" + id);

        updateRequest.addHeader("Content-Type", "application/json");
        updateRequest.addHeader("authorization", "NTxX4aHJ974xlJY6N3xFJXBB1gG7w8G8u8C20IFwp5Qvd4v1kHWf9PjBb1bc5Ei8");


            StringEntity jsonBook = new StringEntity(gson.toJson(book));
            updateRequest.setEntity(jsonBook);




        connection.execute(updateRequest, new ResponseParser() {

            public void payload(String json) {

                Book updatedBook = gson.fromJson(json, Book.class);
                responseCallback.success(updatedBook);


            }

            public void error(int status) {
            responseCallback.error(status);
            }


        });
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


    }


    public void delete(String id, final ResponseCallback<Integer> responsecallback) {

        /*
        Skal huske ID med i argumentet(I path /books/id -
         */
        HttpDelete deleteRequest = new HttpDelete(Connection.serverURL + "/books/" + id);

        //ud fra headeren, bliver den bedt om at authorize med en token.
        deleteRequest.addHeader("Content-Type", "application/json");
        deleteRequest.addHeader("authorization", "NTxX4aHJ974xlJY6N3xFJXBB1gG7w8G8u8C20IFwp5Qvd4v1kHWf9PjBb1bc5Ei8");


        //Bruger execute metoden, til at tage vores Http-request og parse den til serveren.
          connection.execute(deleteRequest, new ResponseParser() {

              public void payload(String json) {
                  Delete delete = gson.fromJson(json, Delete.class);
                  responsecallback.success(delete.getCount());

              }

              public void error(int status) {
                  responsecallback.error(status);
              }
          });


    }

    /**
     *  Følgende metode opretter bøger, ved hjælp af Entity metoden.
     * @param book
     * @param responseCallback
     */
    public void create(Book book, final ResponseCallback<Book> responseCallback) {

        try {
            HttpPost postRequest = new HttpPost(Connection.serverURL + "/books");

            postRequest.addHeader("Content-Type", "application/json");
            postRequest.addHeader("authorization", "NTxX4aHJ974xlJY6N3xFJXBB1gG7w8G8u8C20IFwp5Qvd4v1kHWf9PjBb1bc5Ei8");


            StringEntity jsonBook = new StringEntity(gson.toJson(book));
            postRequest.setEntity(jsonBook);

            connection.execute(postRequest, new ResponseParser() {

                public void payload(String json) {

                    Book newBook = gson.fromJson(json, Book.class);
                    responseCallback.success(newBook);

                }


                public void error(int status) {
                    responseCallback.error(status);
                }
            });


        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }

    //Metode til at hente alt der ilgger på serveren
    //returnere void, grundet måden vi har implementeret callbacks.

    /**
     *
     * @param responseCallback
     */

    public void getAll(final ResponseCallback<ArrayList<Book>> responseCallback) {
        HttpGet getRequest = new HttpGet(Connection.serverURL + "/books");
        this.connection.execute(getRequest, new ResponseParser() {

            public void payload(String json) {


                //TypeToken anvendes, fordi den ellers ikke fatter at den skal bruge book klassen.
                ArrayList<Book> books = gson.fromJson(json, new TypeToken<ArrayList<Book>>() {
                }.getType());
                responseCallback.success(books);


            }

            public void error(int status) {
                responseCallback.error(status);
            }
        });

    }


}
