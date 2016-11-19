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
import sdk.models.*;

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

    //review/{lectureId}


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
        HttpPut updateRequest = new HttpPut(ConnectionImpl.serverURL + "/Lecture/" + id);

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
            HttpPost postRequest = new HttpPost(ConnectionImpl.serverURL + "/lecture");

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


    public void addReview(Review review, final ResponseCallback<Review> responseCallback) {

        try {
            HttpPost postRequest = new HttpPost(ConnectionImpl.serverURL + "/student/review");

            postRequest.addHeader("Content-Type", "application/json");


            StringEntity jsonReview = new StringEntity(gson.toJson(review));
            postRequest.setEntity(jsonReview);

            connectionImpl.execute(postRequest, new ResponseParser() {

                public void payload(String json) {

                   Review newReview = gson.fromJson(json, Review.class);
                    responseCallback.success(newReview);


                }


                public void error(int status) {
                    responseCallback.error(status);
                }
            });


        } catch (UnsupportedEncodingException e) {

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

    public void getAllReviews(Integer lectureId, final ResponseCallback<ArrayList<Review>> responseCallback) {

        HttpGet getRequest = new HttpGet(ConnectionImpl.serverURL + "/review/" + lectureId);


        this.connectionImpl.execute(getRequest, new ResponseParser() {

            public void payload(String json) {

                ArrayList<Review> reviews = gson.fromJson(json, new TypeToken<ArrayList<Review>>(){}.getType());

                responseCallback.success(reviews);


            }

            public void error(int status) {
                responseCallback.error(status);
            }
        });

    }


    public void authLogin(String cbsMail, String password , final ResponseCallback<User> responseCallback){
        HttpPost postRequest = new HttpPost(ConnectionImpl.serverURL + "/login");


        final User userInfo = new User();
        userInfo.setCbsMail(cbsMail);
        userInfo.setPassword(password);

        try {
            StringEntity loginInfo = new StringEntity(this.gson.toJson(userInfo));
            postRequest.setEntity(loginInfo);
            postRequest.setHeader("Content-Type", "application/json");

            this.connectionImpl.execute(postRequest, new ResponseParser() {
                public void payload(String json) {
                    User userToken = gson.fromJson(json, User.class);

                    LoginService.setAccessToken(userToken);


                    responseCallback.success(userToken);
                }

                public void error(int status) {
                    responseCallback.error(status);


                }
            });
        }catch (UnsupportedEncodingException e){
            e.printStackTrace();
        }


    }




    public void findById(int id, final ResponseCallback<ArrayList<Course>> responseCallback) {
        HttpGet getRequest = new HttpGet(ConnectionImpl.serverURL + "/course/" + id);
      //getRequest.addHeader("Content-Type", "application/json");


        this.connectionImpl.execute(getRequest, new ResponseParser() {
            public void payload(String json) {
                System.out.println("Payload initialized");

                ArrayList<Course> courses = gson.fromJson(json, new TypeToken<ArrayList<Course>>(){}.getType());


                responseCallback.success(courses);
            }

            public void error(int status) {
                responseCallback.error(status);
            }
        });

    }




}
