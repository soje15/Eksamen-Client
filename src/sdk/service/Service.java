package sdk.service;

import controller.ViewHandler;
import encrypter.EncryptionHandler;
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
 *
 * This service class, has the job to prepare HTTP-requests to the server. It does so, with the following interfaces:
 * 1. ResponseCallBack
 * 2. ResponseParser
 *
 * Aswell as the apache HTTPclient library, to prepare the requests.
 *
 * The class contains the following functions:
 *
 * updateReview
 * deleteReview
 * GetAllLecture
 * GetAllLectureByID
 * getReviews
 * getCourses
 * authLogin
 * getAllReviews
 *
 *
 * Much of the code in this class is repeated, and thus it will only be commented once.
 * A reference to a similar method with comments, will be provided when needed.
 *
 */


public class Service {
    private ConnectionImpl connectionImpl;
    private Gson gson;
    private EncryptionHandler encryptionHandler;


    /**
     *Initializing our connection and gson-serializer.
     */
    public Service() {
        this.connectionImpl = new ConnectionImpl();
        this.gson = new Gson();
    }


    /**
     * Main used for testing purposes.
     */
    public static void main(String[] args) {
   Service service = new Service();
        service.getReviews(6, new ResponseCallback<ArrayList<ReviewDTO>>() {
            public void success(ArrayList<ReviewDTO> data) {
                for(ReviewDTO reviews: data) {
                    System.out.println(reviews.getComment());
                }
            }

            public void error(int status) {

            }
        });
    }





    /**
     *This function, will parse a review object with an empty password.
     * Using a putRequests, in order to manipulate data on the server.
     *
     * The function takes the following parameters:
     * @param review
     * @param responseCallback
     */
    public void deleteReviewComment(ReviewDTO review, final ResponseCallback<Boolean> responseCallback) {

        try {

            //We define which type of requests we wish. In this case it is a put request.
            HttpPut putRequest = new HttpPut(ConnectionImpl.serverURL + "/student/reviewcomment/");

            //json is added to the header, so the server knows it is getting it in JSON-format.
            putRequest.addHeader("Content-Type", "application/json");

            //Converting our object to an entity, that can be sent to the server.
            StringEntity jsonReview = new StringEntity(gson.toJson(review));
            putRequest.setEntity(jsonReview);

            //Request is executed
            connectionImpl.execute(putRequest, new ResponseParser() {

                //Json recieved in payload.
                public void payload(String json) {

                    //Json decrypted.
                    String decryptedJSON = EncryptionHandler.decrypt(json);

                    //We set what we expect to recieve from the server (a Boolean)
                    //And use gson, to convert our json to java-language.
                    Boolean isDeleted = gson.fromJson(decryptedJSON, Boolean.class);


                    //call sucessmethod, upon sucessful transaction.
                    responseCallback.success(isDeleted);


                }


                //call error method, upon unsucessful transaction.
                public void error(int status) {
                    responseCallback.error(status);
                }


            });

            //Exception caught
        } catch (Exception e) {
            e.printStackTrace();

        }


    }


    /**
     *This method serves to update a review.
     *- Please refer to "deleteReviewComment" for further
     * code comments.
     *
     * @param review
     * @param responseCallback
     */
    public void updateReview(ReviewDTO review, final ResponseCallback<Boolean> responseCallback) {

        try {
            HttpPut putRequest = new HttpPut(ConnectionImpl.serverURL + "/student/review/");

            putRequest.addHeader("Content-Type", "application/json");

            StringEntity jsonReview = new StringEntity(gson.toJson(review));
            putRequest.setEntity(jsonReview);

            connectionImpl.execute(putRequest, new ResponseParser() {

                public void payload(String json) {
                    String decryptedJSON = EncryptionHandler.decrypt(json);

                    Boolean isDeleted = gson.fromJson(decryptedJSON, Boolean.class);

                    responseCallback.success(isDeleted);

                }
                public void error(int status) {
                    responseCallback.error(status);
                }

            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets all reviews from the server.
     *
     * @param lectureId
     * @param responseCallback
     */

    public void getAllReviews(Integer lectureId, final ResponseCallback<ArrayList<ReviewDTO>> responseCallback) {

        try {
            HttpGet getRequest = new HttpGet(ConnectionImpl.serverURL + "/review/" + lectureId);


            this.connectionImpl.execute(getRequest, new ResponseParser() {

                public void payload(String json) {
                    String decryptedJSON = EncryptionHandler.decrypt(json);

                    ArrayList<ReviewDTO> reviews = gson.fromJson(decryptedJSON, new TypeToken<ArrayList<ReviewDTO>>() {
                    }.getType());

                    responseCallback.success(reviews);


                }

                public void error(int status) {
                    responseCallback.error(status);
                }
            });

        }catch(Exception e ){
            System.out.println("Something went wrong");
        }
    }


    /**
     * This method is meant for admin users, to return reviews with UserID.
     * So that the admin may delete any review from any user.
     * @param responseCallback
     */
    public void getAllReviewsInclUserId(final ResponseCallback<ArrayList<ReviewDTO>> responseCallback){

        try {
            HttpGet getRequest = new HttpGet(ConnectionImpl.serverURL + "/admin/getReviews/");

            this.connectionImpl.execute(getRequest, new ResponseParser() {

                public void payload(String json) {
                    String decryptedJSON = EncryptionHandler.decrypt(json);

                    ArrayList<ReviewDTO> reviews = gson.fromJson(decryptedJSON, new TypeToken<ArrayList<ReviewDTO>>() {
                    }.getType());

                    responseCallback.success(reviews);


                }

                public void error(int status) {
                    responseCallback.error(status);
                }
            });

        }catch(Exception e){
            System.out.println("Something went wrong");
        }

    }

    /**
     *This method serves to delete a review.
     * Since the purpose is actually "softdeleting"
     * a put request is used.
     *
     *
     * @param userid
     * @param reviewID
     * @param responsecallback
     */
    public void deleteReview(int userid, int reviewID, final ResponseCallback<Boolean> responsecallback) {

        try {
            HttpPut putRequest = new HttpPut(ConnectionImpl.serverURL + "/review/" + userid + reviewID);

            putRequest.addHeader("Content-Type", "application/json");


            //Bruger execute metoden, til at tage vores Http-request og parse den til serveren.
            connectionImpl.execute(putRequest, new ResponseParser() {

                public void payload(String json) {
                    String decryptedJSON = EncryptionHandler.decrypt(json);
                    boolean isDeleted = gson.fromJson(decryptedJSON, Boolean.class);
                    responsecallback.success(isDeleted);

                }

                public void error(int status) {
                    responsecallback.error(status);
                }
            });

        }catch(Exception e){
            System.out.println("Something went wrong");
        }

    }

    /**
     *
     * This method serves to add a review on the server
     * A post request is used in this instance, since
     * we wish to add something.
     *
     * @param review
     * @param responseCallback
     */
    public void addReview(ReviewDTO review, final ResponseCallback<String> responseCallback) {

        try {

            HttpPost postRequest = new HttpPost(ConnectionImpl.serverURL + "/student/review");

            postRequest.addHeader("Content-Type", "application/json");


            StringEntity jsonReview = new StringEntity(gson.toJson(review));
            postRequest.setEntity(jsonReview);

            connectionImpl.execute(postRequest, new ResponseParser() {

                public void payload(String json) {
                    String decryptedJSON = EncryptionHandler.decrypt(json);

                    String isAdded = gson.fromJson(decryptedJSON, String.class);
                    responseCallback.success(isAdded);


                }


                public void error(int status) {
                    responseCallback.error(status);
                }
            });


        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();

        }

    }

    /**
     *Returns all lectures from  users ID.
     *
     *
     * @param userID
     * @param responseCallback
     */
    public void getAllLecturesFromUserID(Integer userID, final ResponseCallback<ArrayList<LectureDTO>> responseCallback) {

        try {
            HttpGet getRequest = new HttpGet(ConnectionImpl.serverURL + "/lectureByID/" + userID);


            this.connectionImpl.execute(getRequest, new ResponseParser() {

                public void payload(String json) {

                    String decryptedJSON = EncryptionHandler.decrypt(json);

                    //Type token is used, so that GSON can understand that it needs to make an array
                    //containing Lecture-objects.
                    ArrayList<LectureDTO> lectures = gson.fromJson(decryptedJSON, new TypeToken<ArrayList<LectureDTO>>() {
                    }.getType());


                    responseCallback.success(lectures);

                }

                public void error(int status) {
                    responseCallback.error(status);
                }
            });
        }catch(Exception e){
            System.out.println("Something went wrong");
        }

    }



    /**
     *
     * This method allows users to use the "login" method.
     * it returns certain user paramters, upon sucessfull transaction.
     *
     *
     * @param cbsMail
     * @param password
     * @param responseCallback
     */
    public void loginUser(String cbsMail, String password, final ResponseCallback<UserDTO> responseCallback) {

      try {
          HttpPost postRequest = new HttpPost(ConnectionImpl.serverURL + "/login");

          String encryptedUsername = EncryptionHandler.encrypt(cbsMail);
          String encryptedPassword = EncryptionHandler.encrypt(password);

          final UserDTO userInfo = new UserDTO();
          userInfo.setCbsMail(encryptedUsername);
          userInfo.setPassword(encryptedPassword);

          try {

              StringEntity loginInfo = new StringEntity(gson.toJson(userInfo));
              postRequest.setEntity(loginInfo);

              postRequest.setHeader("Content-Type", "application/json");

              this.connectionImpl.execute(postRequest, new ResponseParser() {
                  public void payload(String json) {

                      String decryptedjson = EncryptionHandler.decrypt(json);


                      UserDTO userToken = gson.fromJson(decryptedjson, UserDTO.class);


                      responseCallback.success(userToken);
                  }

                  public void error(int status) {
                      responseCallback.error(status);


                  }
              });
          } catch (Exception e) {
              System.out.println("Something went wrong");
          }

      }catch(Exception e){
          System.out.println("Something went wrong");
      }

    }

    /**
     *This function returns all courses from a userID.
     * @param userId
     * @param responseCallback
     */
    public void getCourses(int userId, final ResponseCallback<ArrayList<CourseDTO>> responseCallback) {

        try {
            HttpGet getRequest = new HttpGet(ConnectionImpl.serverURL + "/course/" + userId);


            this.connectionImpl.execute(getRequest, new ResponseParser() {
                public void payload(String json) {
                    String decryptedJSON = EncryptionHandler.decrypt(json);

                    ArrayList<CourseDTO> courses = gson.fromJson(decryptedJSON, new TypeToken<ArrayList<CourseDTO>>() {
                    }.getType());


                    responseCallback.success(courses);
                }

                public void error(int status) {
                    responseCallback.error(status);
                }
            });
        } catch(Exception e){
            System.out.println("Something went wrong");
        }

    }



    /**
     * This function returns all reviews, belonging to a specific userID.
     * @param userId
     * @param responseCallback
     */

    public void getReviews(int userId, final ResponseCallback<ArrayList<ReviewDTO>> responseCallback) {

        try {
            HttpGet getRequest = new HttpGet(ConnectionImpl.serverURL + "/student/getReviews/" + userId);

            this.connectionImpl.execute(getRequest, new ResponseParser() {

                public void payload(String json) {
                    String decryptedJSON = EncryptionHandler.decrypt(json);

                    ArrayList<ReviewDTO> reviews = gson.fromJson(decryptedJSON, new TypeToken<ArrayList<ReviewDTO>>() {
                    }.getType());

                    responseCallback.success(reviews);
                }

                public void error(int status) {
                    responseCallback.error(status);
                }
            });

        }catch(Exception e){
            System.out.println("Something went wrong");
        }
    }


    public void getCourseParticipants(int courseId, final ResponseCallback<Integer> responseCallback) {

        try {
            HttpGet getRequest = new HttpGet(ConnectionImpl.serverURL + "/teacher/courseParticipants/" + courseId);


            this.connectionImpl.execute(getRequest, new ResponseParser() {

                public void payload(String json) {
                    String decryptedJSON = EncryptionHandler.decrypt(json);

                    Integer courseParticipants = gson.fromJson(decryptedJSON, Integer.class);

                    responseCallback.success(courseParticipants);
                }

                public void error(int status) {
                    responseCallback.error(status);
                }
            });

        }
    catch (Exception e){
        System.out.println("Something went wrong in the connection");
    }


}}
