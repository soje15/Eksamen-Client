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
     * @param review
     * @param responseCallback
     */
    public void deleteReviewComment(ReviewDTO review, final ResponseCallback<Boolean> responseCallback) {

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


    /**
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

    /**
     *
     * @param userid
     * @param reviewID
     * @param responsecallback
     */
    public void deleteReview(int userid, int reviewID, final ResponseCallback<Boolean> responsecallback) {

        HttpPut putRequest = new HttpPut(ConnectionImpl.serverURL + "/review/" + userid + reviewID);

        putRequest.addHeader("Content-Type", "application/json");


        //Bruger execute metoden, til at tage vores Http-request og parse den til serveren.
        connectionImpl.execute(putRequest, new ResponseParser() {

            public void payload(String json) {
                String decryptedJSON = Digester.decrypt(json);
                boolean isDeleted = gson.fromJson(decryptedJSON, Boolean.class);
                responsecallback.success(isDeleted);

            }

            public void error(int status) {
                responsecallback.error(status);
            }
        });


    }

    /**
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
     * @param responseCallback
     */

    /*

    public void getAllLectures(String code, final ResponseCallback<ArrayList<LectureDTO>> responseCallback) {

        HttpGet getRequest = new HttpGet(ConnectionImpl.serverURL + "/lecture/" + code);

        this.connectionImpl.execute(getRequest, new ResponseParser() {

            public void payload(String json) {

                String decryptedJSON = Digester.decrypt(json);

                //TypeToken anvendes, fordi den ellers ikke fatter at den skal bruge LectureDTO klassen.
                ArrayList<LectureDTO> lectures = gson.fromJson(decryptedJSON, new TypeToken<ArrayList<LectureDTO>>() {
                }.getType());

                responseCallback.success(lectures);
            }

            public void error(int status) {
                responseCallback.error(status);
            }
        });

    }
*/

    /**
     *
     * @param userID
     * @param responseCallback
     */
    public void getAllLecturesByUserID(Integer userID, final ResponseCallback<ArrayList<LectureDTO>> responseCallback) {

        HttpGet getRequest = new HttpGet(ConnectionImpl.serverURL + "/lectureByID/" + userID);


        this.connectionImpl.execute(getRequest, new ResponseParser() {

            public void payload(String json) {

                String decryptedJSON = Digester.decrypt(json);

                //TypeToken anvendes, fordi den ellers ikke forstår at den skal bruge LectureDTO klassen.
                ArrayList<LectureDTO> lectures = gson.fromJson(decryptedJSON, new TypeToken<ArrayList<LectureDTO>>() {
                }.getType());


                responseCallback.success(lectures);

            }

            public void error(int status) {
                responseCallback.error(status);
            }
        });

    }

    public void getAllReviews(Integer lectureId, final ResponseCallback<ArrayList<ReviewDTO>> responseCallback) {

        HttpGet getRequest = new HttpGet(ConnectionImpl.serverURL + "/review/" + lectureId);


        this.connectionImpl.execute(getRequest, new ResponseParser() {

            public void payload(String json) {
                String decryptedJSON = Digester.decrypt(json);

                ArrayList<ReviewDTO> reviews = gson.fromJson(decryptedJSON, new TypeToken<ArrayList<ReviewDTO>>() {
                }.getType());

                responseCallback.success(reviews);


            }

            public void error(int status) {
                responseCallback.error(status);
            }
        });

    }

    /**
     *
     * @param cbsMail
     * @param password
     * @param responseCallback
     */
    public void authLogin(String cbsMail, String password, final ResponseCallback<UserDTO> responseCallback) {
        HttpPost postRequest = new HttpPost(ConnectionImpl.serverURL + "/login");

        String doubleHashedPassword = Digester.hashWithSalt(Digester.hashWithSalt(password));

        String encryptedUsername = Digester.encrypt(cbsMail);
        String encryptedPassword = Digester.encrypt(doubleHashedPassword);

        final UserDTO userInfo = new UserDTO();
        userInfo.setCbsMail(encryptedUsername);
        userInfo.setPassword(encryptedPassword);

        try {

            StringEntity loginInfo = new StringEntity(this.gson.toJson(userInfo));
            postRequest.setEntity(loginInfo);

            postRequest.setHeader("Content-Type", "application/json");

            this.connectionImpl.execute(postRequest, new ResponseParser() {
                public void payload(String json) {

                    String decryptedjson = Digester.decrypt(json);


                    UserDTO userToken = gson.fromJson(decryptedjson, UserDTO.class);


                    responseCallback.success(userToken);
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
     *
     * @param id
     * @param responseCallback
     */
    public void getCourses(int id, final ResponseCallback<ArrayList<CourseDTO>> responseCallback) {
        HttpGet getRequest = new HttpGet(ConnectionImpl.serverURL + "/course/" + id);
        //getRequest.addHeader("Content-Type", "application/json");


        this.connectionImpl.execute(getRequest, new ResponseParser() {
            public void payload(String json) {
                String decryptedJSON = Digester.decrypt(json);

                ArrayList<CourseDTO> courses = gson.fromJson(decryptedJSON, new TypeToken<ArrayList<CourseDTO>>() {
                }.getType());


                responseCallback.success(courses);
            }

            public void error(int status) {
                responseCallback.error(status);
            }
        });

    }


}
