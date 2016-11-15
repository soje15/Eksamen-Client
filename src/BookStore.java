import sdk.connection.ResponseCallback;
import sdk.models.Book;
import sdk.services.BookService;

import java.util.ArrayList;

/**
 * Created by sorenkolbyejensen on 14/11/2016.
 */
public class BookStore {
    public static void main(String[] args) {

        BookService bookService = new BookService();
        Book bookToUpdate = new Book();

        bookToUpdate.setSubtitle("Subtitle");
        bookService.update("5829a87a3f5250a6bd2d25d0" , bookToUpdate, new ResponseCallback<Book>() {

            public void success(Book data) {
                             int i = 1;
                System.out.println("Success");
            }

            public void error(int status) {
                int i = 2;
                System.out.println("fail");
            }
        });





    }
    /*
    public static void main(String[] args) {
        BookService bookService = new BookService();
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
        BookService bookService = new BookService();

        Book book = new Book();
        book.setTitle("En meget sjov bog");
        book.setSubtitle("En meget sjov subtitle");
        book.setPrice(299);
        book.setEdition(1);
        book.setPagecount(200);

        bookService.create(book, new ResponseCallback<Book>() {
            public void success(Book data) {
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
        BookService bookService = new BookService();
        bookService.getAll(new ResponseCallback<ArrayList<Book>>() {

            public void success(ArrayList<Book> bookShelf) {

                for(Book book:bookShelf){


                    System.out.println("Title:\t" + book.getTitle());
                    System.out.println("Subtitle:\t" + book.getSubtitle());
                    System.out.println("Authors:\t" + book.getAuthors());
                    System.out.println("AuthorsID:\t" + book.getAuthorIds());
                    System.out.println("Edition:\t" + book.getEdition());
                    System.out.println("PageCount:\t" + book.getPagecount());
                    System.out.println("Price:\t" +book.getPrice());
                    System.out.println("Publisher:\t" + book.getPublisher());
                    System.out.println("Created At:\t" + book.getCreatedAt());


                }


            }

            public void error(int status) {

            }
        });



    }

*/


}
