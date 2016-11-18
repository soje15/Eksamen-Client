package sdk.services;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import sdk.connection.ConnectionImpl;
import sdk.connection.ResponseCallback;
import sdk.connection.ResponseParser;
import sdk.models.Course;
import sdk.models.Lecture;
import sdk.models.Delete;
import sdk.models.User;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

/**
 * Created by sorenkolbyejensen on 14/11/2016.
 */

public class Service {
    private ConnectionImpl connectionImpl;
    private Gson gson;

    public Service() {
        this.connectionImpl = new ConnectionImpl();
        this.gson = new Gson();
    }

    /**
     *
     * Følgende metode opdatere en bog ud fra et ID.
     *
     * OBS: brug StringEntity, når du skal ind og "Indsætte" data - eks create, update.
     *
     * @param id
     * @param lecture
     * @param responseCallback
     */


    public void update(String id, Lecture lecture, final ResponseCallback<Lecture> responseCallback){

        try {
        HttpPut updateRequest = new HttpPut(ConnectionImpl.serverURL + "/books/" + id);

        updateRequest.addHeader("Content-Type", "application/json");
       // updateRequest.addHeader("authorization", "NTxX4aHJ974xlJY6N3xFJXBB1gG7w8G8u8C20IFwp5Qvd4v1kHWf9PjBb1bc5Ei8");


            StringEntity jsonBook = new StringEntity(gson.toJson(lecture));
            updateRequest.setEntity(jsonBook);




        connectionImpl.execute(updateRequest, new ResponseParser() {

            public void payload(String json) {

                Lecture updatedLecture = gson.fromJson(json, Lecture.class);
                responseCallback.success(updatedLecture);


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
        HttpDelete deleteRequest = new HttpDelete(ConnectionImpl.serverURL + "/books/" + id);

        //ud fra headeren, bliver den bedt om at authorize med en token.
        deleteRequest.addHeader("Content-Type", "application/json");



        //Bruger execute metoden, til at tage vores Http-request og parse den til serveren.
          connectionImpl.execute(deleteRequest, new ResponseParser() {

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
     * @param lecture
     * @param responseCallback
     */
    public void create(Lecture lecture, final ResponseCallback<Lecture> responseCallback) {

        try {
            HttpPost postRequest = new HttpPost(ConnectionImpl.serverURL + "/books");

            postRequest.addHeader("Content-Type", "application/json");


            StringEntity jsonBook = new StringEntity(gson.toJson(lecture));
            postRequest.setEntity(jsonBook);

            connectionImpl.execute(postRequest, new ResponseParser() {

                public void payload(String json) {

                    Lecture newLecture = gson.fromJson(json, Lecture.class);
                    responseCallback.success(newLecture);

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

    public void getAll(String code, final ResponseCallback<ArrayList<Lecture>> responseCallback) {

        HttpGet getRequest = new HttpGet(ConnectionImpl.serverURL + "/lecture/" + code);


        this.connectionImpl.execute(getRequest, new ResponseParser() {

            public void payload(String json) {

                System.out.println("Payload initialized");

                //TypeToken anvendes, fordi den ellers ikke fatter at den skal bruge Lecture klassen.



                ArrayList<Lecture> lectures = gson.fromJson(json, new TypeToken<ArrayList<Lecture>>(){}.getType());

                System.out.println("Json converted");

                responseCallback.success(lectures);


            }

            public void error(int status) {
                responseCallback.error(status);
            }
        });

    }




    public void login(String cbsMail, String password, final ResponseCallback<User> responseCallback) {

        HttpPost postRequest = new HttpPost(ConnectionImpl.serverURL + "/login");

        postRequest.addHeader("Content-Type", "application/json");

        User login = new User();
        login.setUsername(cbsMail);
        login.setPassword(password);

        try {

            StringEntity loginDetails = new StringEntity(this.gson.toJson(login));

            this.connectionImpl.execute(postRequest, new ResponseParser() {
                public void payload(String json) {
                    System.out.println("Payload initialized");

                    User user = gson.fromJson(json, User.class);

                    responseCallback.success(user);
                }

                public void error(int status) {
                    responseCallback.error(status);
                }
            });

        } catch (Exception e) {
            System.out.println(e);
        }

    }


    public void findById(String id, final ResponseCallback<Course> responseCallback) {
        HttpGet getRequest = new HttpGet(ConnectionImpl.serverURL + "/course/" + id);
      //getRequest.addHeader("Content-Type", "application/json");


        this.connectionImpl.execute(getRequest, new ResponseParser() {
            public void payload(String json) {
                System.out.println("Payload initialized");

                Course course = gson.fromJson(json, Course.class);

                responseCallback.success(course);
            }

            public void error(int status) {
                responseCallback.error(status);
            }
        });

    }




}
