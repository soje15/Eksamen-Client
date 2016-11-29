package sdk.service;

import encrypter.Digester;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
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
    private Digester digester;

    public Service() {
        this.connectionImpl = new ConnectionImpl();
        this.gson = new Gson();
    }


    //review/{lectureId}


    /**
     *
     *
     *
     * OBS: brug StringEntity, når du skal ind og "Indsætte" data - eks create, update.
     *
     * @param
     * @param responseCallback
     */


    public void deleteReviewComment(Review review, final ResponseCallback<Boolean> responseCallback){

        try {
            HttpPut putRequest = new HttpPut(ConnectionImpl.serverURL + "/student/reviewcomment/");
            putRequest.addHeader("Content-Type", "application/json");

            StringEntity jsonReview = new StringEntity(gson.toJson(review));
            putRequest.setEntity(jsonReview);

            connectionImpl.execute(putRequest, new ResponseParser() {

                public void payload(String json) {
                    String decryptedJSON = Digester.decrypt(json);

                    Boolean isDeleted = gson.fromJson(decryptedJSON, Boolean.class);

                    System.out.println(isDeleted);

                    responseCallback.success(isDeleted);


                }

                public void error(int status) {
                    responseCallback.error(status);
                }


            });
        } catch (Exception e) {

        }


    }









    public void updateReview(Review review, final ResponseCallback<Boolean> responseCallback){

        try {
        HttpPut putRequest = new HttpPut(ConnectionImpl.serverURL + "/student/review/");

            putRequest.addHeader("Content-Type", "application/json");

            StringEntity jsonReview = new StringEntity(gson.toJson(review));
            putRequest.setEntity(jsonReview);

        connectionImpl.execute(putRequest, new ResponseParser() {

            public void payload(String json) {
                String decryptedJSON = Digester.decrypt(json);

                Boolean isDeleted = gson.fromJson(decryptedJSON, Boolean.class);

                System.out.println(isDeleted);

                responseCallback.success(isDeleted);


            }

            public void error(int status) {
            responseCallback.error(status);
            }


        });
        } catch (Exception e) {

        }


    }


    public void deleteReview(int userid, int reviewID, final ResponseCallback<Boolean> responsecallback) {

        HttpPut putRequest = new HttpPut(ConnectionImpl.serverURL + "/review/" + userid + reviewID);

        putRequest.addHeader("Content-Type", "application/json");




        //Bruger execute metoden, til at tage vores Http-request og parse den til serveren.
          connectionImpl.execute(putRequest, new ResponseParser() {

              public void payload(String json) {
                  String decryptedJSON = Digester.decrypt(json);
                  boolean isDeleted  = gson.fromJson(decryptedJSON, Boolean.class);
                  responsecallback.success(isDeleted);

              }

              public void error(int status) {
                  responsecallback.error(status);
              }
          });


    }



    public void addReview(Review review, final ResponseCallback<String> responseCallback) {

        try {

            HttpPost postRequest = new HttpPost(ConnectionImpl.serverURL + "/student/review");

            postRequest.addHeader("Content-Type", "application/json");


            StringEntity jsonReview = new StringEntity(gson.toJson(review));
            postRequest.setEntity(jsonReview);

            connectionImpl.execute(postRequest, new ResponseParser() {

                public void payload(String json) {
                    String decryptedJSON = Digester.decrypt(json);

                   String isAdded = gson.fromJson(decryptedJSON, String.class);
                    responseCallback.success(isAdded);


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

    public void getAllLectures(String code, final ResponseCallback<ArrayList<Lecture>> responseCallback) {

        HttpGet getRequest = new HttpGet(ConnectionImpl.serverURL + "/lecture/" + code);


        this.connectionImpl.execute(getRequest, new ResponseParser() {

            public void payload(String json) {

                String decryptedJSON = Digester.decrypt(json);

                //TypeToken anvendes, fordi den ellers ikke fatter at den skal bruge Lecture klassen.

               ArrayList<Lecture> lectures = gson.fromJson(decryptedJSON, new TypeToken<ArrayList<Lecture>>(){}.getType());



                responseCallback.success(lectures);


            }

            public void error(int status) {
                responseCallback.error(status);
            }
        });

    }


    public void getAllLecturesByUserID(Integer userID, final ResponseCallback<ArrayList<Lecture>> responseCallback) {

        HttpGet getRequest = new HttpGet(ConnectionImpl.serverURL + "/lectureByID/" + userID);


        this.connectionImpl.execute(getRequest, new ResponseParser() {

            public void payload(String json) {

                String decryptedJSON = Digester.decrypt(json);

                //TypeToken anvendes, fordi den ellers ikke fatter at den skal bruge Lecture klassen.

                ArrayList<Lecture> lectures = gson.fromJson(decryptedJSON, new TypeToken<ArrayList<Lecture>>(){}.getType());



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
                String decryptedJSON = Digester.decrypt(json);

                ArrayList<Review> reviews = gson.fromJson(decryptedJSON, new TypeToken<ArrayList<Review>>(){}.getType());

                responseCallback.success(reviews);


            }

            public void error(int status) {
                responseCallback.error(status);
            }
        });

    }


    public void authLogin(String cbsMail, String password , final ResponseCallback<User> responseCallback){
        HttpPost postRequest = new HttpPost(ConnectionImpl.serverURL + "/login");

        String doubleHashedPassword = Digester.hashWithSalt(Digester.hashWithSalt(password));

        String encryptedUsername = Digester.encrypt(cbsMail);
        String encryptedPassword = Digester.encrypt(doubleHashedPassword);

        final User userInfo = new User();
        userInfo.setCbsMail(encryptedUsername);
        userInfo.setPassword(encryptedPassword);

        try {

            StringEntity loginInfo = new StringEntity(this.gson.toJson(userInfo));
            postRequest.setEntity(loginInfo);

            postRequest.setHeader("Content-Type", "application/json");

            this.connectionImpl.execute(postRequest, new ResponseParser() {
                public void payload(String json) {

                    String decryptedjson = Digester.decrypt(json);


                    User userToken = gson.fromJson(decryptedjson, User.class);



                    responseCallback.success(userToken);
                }

                public void error(int status) {
                    responseCallback.error(status);


                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }


    }




    public void getCourses(int id, final ResponseCallback<ArrayList<Course>> responseCallback) {
        HttpGet getRequest = new HttpGet(ConnectionImpl.serverURL + "/course/" + id);
      //getRequest.addHeader("Content-Type", "application/json");


        this.connectionImpl.execute(getRequest, new ResponseParser() {
            public void payload(String json) {
                String decryptedJSON = Digester.decrypt(json);

                ArrayList<Course> courses = gson.fromJson(decryptedJSON, new TypeToken<ArrayList<Course>>(){}.getType());


                responseCallback.success(courses);
            }

            public void error(int status) {
                responseCallback.error(status);
            }
        });

    }




}
