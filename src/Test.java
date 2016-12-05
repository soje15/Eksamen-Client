import controller.ViewHandler;
import view.MainView;
import sdk.service.Service;

import java.util.Scanner;

/**
 * Created by sorenkolbyejensen on 14/11/2016.
 */



public class Test {
    public static void main(String[] args) {
        Service service = new Service();
        Scanner inputreader = new Scanner(System.in);
        System.out.println("Running test");

        ViewHandler viewHandler = new ViewHandler();
        MainView mainView = new MainView(service, inputreader, viewHandler);
        viewHandler.setMainView(mainView);
        viewHandler.getMainView().MainMenu();
    }
}

    /*

    public static void main(String[] args) {
        Service service = new Service();
        service.getAllLecturesByUserID(6, new ResponseCallback<ArrayList<LectureDTO>>() {
            public void success(ArrayList<LectureDTO> data) {
                for (LectureDTO lecture : data) {
                    System.out.println(lecture.getDescription());
                }
            }

            public void error(int status) {

            }
        });
    }
}

*/
    /*
    public static void main(String[] args) {
        Service service = new Service();
        String code = "BALJO1001U_XJA_E16";


        String encryptedCode = Digester.encrypt(code);
        System.out.println(encryptedCode);
        String decryptedCode = Digester.decrypt(encryptedCode);

        System.out.println(decryptedCode);

        service.getAllLectures(code, new ResponseCallback<ArrayList<LectureDTO>>() {

            public void success(ArrayList<LectureDTO> data) {

                for(LectureDTO lecture: data) {

                    System.out.println(lecture.getDescription());
                }


            }

            public void error(int status) {

            }
        });


    }
}
*/
    /*

    public static void main(String[] args) {
        Service service = new Service();
        ReviewDTO review = new ReviewDTO();
        UserDTO user = new UserDTO();

        review.setId(10);
        review.setUserId(2);
        String ID = "17";

      service.deleteReviewComment(review, new ResponseCallback<Boolean>() {
          public void success(Boolean data) {
              System.out.println(data);
          }

          public void error(int status) {
              System.out.println(status);

          }
      });
        }
    }

    */
    /*
    public static void main(String[] args) {
        Service service = new Service();

        int ID = 4;

        service.getAllReviews(ID, new ResponseCallback<ArrayList<ReviewDTO>>() {
            public void success(ArrayList<ReviewDTO> data) {
                for (ReviewDTO reviews:data) {
                    System.out.println("test" + reviews.getComment());
                }
            }

            public void error(int status) {

            }
        });

    }
    */
    /*
    public static void main(String[] args) {
        Service service = new Service();

        ReviewDTO review = new ReviewDTO();
        review.setId(9);
        review.setUserId(2);
        review.setLectureId(6);
        review.setRating(5);
        review.setComment("hej");

        service.addReview(review, new ResponseCallback<ReviewDTO>() {
            public void success(ReviewDTO data) {
                System.out.println(data);
            }

            public void error(int status) {
                System.out.println(status);
            }
        });
    }
    */

/*

    public static void main(String[] args) {
        Digester digester = new Digester();
        Service service = new Service();

        String cbsMail = "virk@virk.dk";
        String password = "virk";

        String hashPassword = digester.hashWithSalt(password);
        String doubleHashed = digester.hashWithSalt(hashPassword);

        System.out.println(doubleHashed);

   service.authLogin(cbsMail, doubleHashed, new ResponseCallback<UserDTO>() {
       public void success(UserDTO data) {
           System.out.println(data.getType());

       }

       public void error(int status) {
           System.out.println(status);
       }
   });
    }

}
*/
    /*
    public static void main(String[] args) {
        ConfigLoader.parseConfig();

        Service service = new Service();


        service.getAll("BALJO1001U_XJA_E16", new ResponseCallback<ArrayList<LectureDTO>>() {

            public void success(ArrayList<LectureDTO> data) {
                System.out.println("win");

                for (LectureDTO lecture:data) {
                    System.out.println("Test" + lecture.getDescription());
                }


            }

            public void error(int status) {
                System.out.println("error");
                System.exit(0);
            }
        });
        */

        /*
        service.findById("3", new ResponseCallback<ArrayList<CourseDTO>>() {


            public void success(ArrayList<CourseDTO> data) {

                    System.out.println("Success");
                    //System.out.println(data());


            }



            public void error(int status) {
                System.out.println("error");
            }
        });

*/


//        MainController mainMenuView = new MainController(controller, service);

    //      mainMenuView.presentMenu2();




    /*
    public static void main(String[] args) {

        Service bookService = new Service();
        LectureDTO bookToUpdate = new LectureDTO();

        bookToUpdate.setSubtitle("Subtitle");
        bookService.update("5829a87a3f5250a6bd2d25d0" , bookToUpdate, new ResponseCallback<LectureDTO>() {

            public void success(LectureDTO data) {
                             int i = 1;
                System.out.println("Success");
            }

            public void error(int status) {
                int i = 2;
                System.out.println("fail");
            }
        });



*/


    /*
    public static void main(String[] args) {
        Service bookService = new Service();
        bookService.delete("5829a8463f5250a6bd2d25c8", new ResponseCallback<Integer>() {


            public void success(Integer data) {
                int i = 1;
                System.out.println("Sucess");

            }

            public void error(int status) {
                System.out.println("error");
                int i = 1;

            }
        });


    }
    */

/*
    public static void main(String[] args) {
        Service bookService = new Service();

        LectureDTO book = new LectureDTO();
        book.setTitle("En meget sjov bog");
        book.setSubtitle("En meget sjov subtitle");
        book.setPrice(299);
        book.setEdition(1);
        book.setPagecount(200);

        bookService.create(book, new ResponseCallback<LectureDTO>() {
            public void success(LectureDTO data) {
                int i = 1;
            }

            public void error(int status) {
                int i = 1;
            }
        });

    }

*/
     /*
    public static void main(String[] args) {
        Service bookService = new Service();
        bookService.getAll(new ResponseCallback<ArrayList<LectureDTO>>() {

            public void success(ArrayList<LectureDTO> bookShelf) {

                for(LectureDTO book:bookShelf){


                    System.out.println("Title:\t" + book.getTitle());
                    System.out.println("Subtitle:\t" + book.getSubtitle());
                    System.out.println("Authors:\t" + book.getReviews());
                    System.out.println("AuthorsID:\t" + book.getAuthorIds());
                    System.out.println("Edition:\t" + book.getEdition());
                    System.out.println("PageCount:\t" + book.getPagecount());
                    System.out.println("Price:\t" +book.getPrice());
                    System.out.println("UserDTO:\t" + book.getTeacher());
                    System.out.println("Created At:\t" + book.getCreatedAt());


                }


            }

            public void error(int status) {

            }
        });



    }

*/

