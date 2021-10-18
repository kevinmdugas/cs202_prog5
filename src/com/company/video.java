//Kevin Dugas
//Program 5
//CS 202
//December 2, 2020
//video.java

/*
The video class is the most indirect base class of the hierarchy (aside from the util class). Each instance
of it manages a linear linked list of dynamically bound media objects. Having the media objects be derived
from.
 */

package com.company;


class video extends util{
    protected String title;         //Title of the video
    protected String date;          //Date video is uploaded
    protected int views;            //View counter
    protected int [] length;        //Int array that holds the hours, minutes, and seconds of a video
    protected media head;           //Head of the LLL of media objects



    //Default constructor that initializes each member to their zero equivalents
    public video(){
        this.title = new String();
        this.date = new String();
        this.views = 0;
        this.length = new int[3];
        for(int i = 0; i < 3; ++i)
            this.length[i] = 0;
        this.head = null;
    }



    //Copy constructor that sets data members and copies the src's LLL of media
    public video(video src){
        this.title = new String();
        this.date = new String();
        this.length = new int[3];
        read(src.title, src.date, src.views, src.length);
        copy_media(src);
    }



    //Constructor with args that copies arguments into data members
    public video(String src_title, String src_date, int src_views, int [] src_length){
        this.title = new String();
        this.date = new String();
        this.length = new int[3];
        read(src_title, src_date, src_views, src_length);
        this.head = null;
    }



    //Method used to copy arguments into the current object's data members
    public void read(String src_title, String src_date, int src_views, int [] src_length){
        this.title = src_title;
        this.date = src_date;
        this.views = src_views;
        for(int i = 0; i < 3; ++i)
            this.length[i] = src_length[i];
    }



    //Wrapper function used to copy the LLL of another video object
    public void copy_media(video src){
        this.head = copy_media(this.head, src.head);
    }



    //Recursive function to copy the LLL of another video object
    protected media copy_media(media dest, media src){
        if(src == null)
            return dest;

        if(src instanceof quiz)
            dest = new quiz((quiz)src);

        else
            dest = new comment((comment)src);

        dest.read(this.title, this.date, this.views, this.length);

        dest.connect_next(copy_media(dest.go_next(), src.go_next()));
        return dest;
    }



    //Populate video data members via user input
    public void input(){
        System.out.print("\nVideo title: ");
        this.title = input.nextLine();

        boolean valid = false;
        int temp;
        do {
            //Input hours
            System.out.print("\nEnter the video length in hours, minutes and seconds\nHours: ");
            temp = input.nextInt();
            input.nextLine();

            if(temp >= 0){

                //Input Minutes
                this.length[0] = temp;
                System.out.print("Minutes: ");
                temp = input.nextInt();
                input.nextLine();

                if(temp >= 0 && temp < 60){

                    //Input Seconds
                    this.length[1] = temp;
                    System.out.print("Seconds: ");
                    temp = input.nextInt();
                    input.nextLine();

                    if(temp >= 0 && temp < 60){

                        //Inputted time is valid
                        this.length[2] = temp;
                        valid = true;
                    }
                }
            }
            if(valid == false)
                System.out.println("\nInvalid time inputted, try again");

        }while(valid == false);

        System.out.print("Upload date: ");
        this.date = input.nextLine();
    }



    //Display current video object
    public int display(){

        if(this.title == null || this.date == null)
            return 0;

        System.out.println("\n\t\t~~~~ Video Details ~~~~\n\nTitle: " + this.title);

        System.out.print("Length (hr:min:sec): ");
        for(int i = 0; i < 3; ++i) {

            if(this.length[i] < 10)
                System.out.print("0" + this.length[i]);
            else
                System.out.print(this.length[i]);

            if(i < 2)
                System.out.print(":");
            else
                System.out.println();
        }

        System.out.println("Upload Date: " + this.date);
        System.out.println("Views: " + this.views);
        System.out.println("\nSaved media\n-----------");

        int count = display(this.head);
        if(count == 0)
            System.out.println("No media saved for this video");
        else
            System.out.println("\nTotal number of media items saved for this video: " + count + "\n-----------");

        return 1;
    }



    //Recursive method to display each media object in the LLL
    protected int display(media head){
        if(head == null)
            return 0;
        return head.display() + display(head.go_next());
    }


    //Wrapper method for the recursive browse_media method
    public void browse_media(){
        this.head = browse_media(this.head);
    }


    //For each node in the LLL, determine the media type using downcasting and present options to the user
    // accordingly. If the object is a video, the user can attempt to answer the question or delete it.
    protected media browse_media(media head){
        if(head == null){
            System.out.println("\nNo other media saved");
            return null;
        }
        char response;
        System.out.println("\n\t\t ---- | Media Browsing | ----");
        head.display();
        System.out.println("\nWhat would you like to do:");

        if(head instanceof quiz){

            System.out.print("1) Answer question\n2) Delete question\n3) Next media item \n4) Go back\n\nOption #");
            response = input.next().charAt(0);
            response = Character.toLowerCase(response);
            input.nextLine();

            if(response == '1') {
                ((quiz) head).answer_question();
                head = browse_media(head);
            }

            else if(response == '2') {
                System.out.println("\nQuiz question deleted");
                head = head.go_next();
                head = browse_media(head);
            }

            else if(response == '3')
                head.connect_next(browse_media(head.go_next()));
        }

        else{
            System.out.print("1) Delete Comment\n2) Next media item\n3) Go back to Video Browsing\n\nOption #");
            response = input.next().charAt(0);
            response = Character.toLowerCase(response);
            input.nextLine();

            if(response =='1') {
                System.out.println("Comment deleted");
                head = head.go_next();
                head = browse_media(head);
            }

            else if(response == '2')
                head.connect_next(browse_media(head.go_next()));

        }
        return head;
    }



    //Use upcasting to create a media object depending on the type determined by the user and add it to
    // the LLL.
    public void create_media(){
        media to_add = null;
        char response;
        do{
            System.out.print("\nWhat type: \n1) Quiz question\n2) Comment\n\nOption #");
            response = input.next().charAt(0);
            response = Character.toLowerCase(response);
            input.nextLine();

            if(response == '1') {
                to_add = new quiz();
            }

            else if(response =='2') {
                to_add = new comment();
            }

            else
                System.out.println("Invalid choice, try again.");
        }while(response != '1' && response != '2');
        to_add.input();
        add_media(to_add, this.title);
    }



    //Check to see if the passed in string matches the title of the current video and if it does,
    // reset all data members to zero
    public int delete_vid(String title_match) {
        if (this.title.equalsIgnoreCase(title_match)) {
            this.title = new String();
            this.date = new String();
            for(int i = 0; i < 3; ++i)
                this.length[i] = 0;
            this.views = 0;
            this.head = null;
            return 1;
        }
        return 0;
    }


    //Wrapper function that checks the title of the current video with the passed in string and adds the
    // media object to the LLL if they match
    public int add_media(media to_add, String title){
        if(title.equalsIgnoreCase(this.title)) {
            this.head = add_media(this.head, to_add);
            return 1;
        }
        return 0;
    }


    //Recursive function that uses downcasting to determine the object type and adding the new object
    // to the current position only if they match types. This keeps the media objects grouped by type so that
    // all quiz questions are displayed first, followed by all comments (or vice-versa). Also uses FUNCTION
    // OVERLOADING to call the video class' read method so that each media object has meta data about the
    // video that contains it
    protected media add_media(media head, media to_add){

        //If list is empty, perform downcasting to create new object of correct type
        if(head == null) {

            if(to_add instanceof quiz) {
                head = new quiz((quiz) to_add);
            }
            else
                head = new comment((comment)to_add);

            //Read in metadata
            head.read(this.title, this.date, this.views, this.length);
        }

        //If the current object is the same type as the added object, add the new object in front of it
        else if(head instanceof quiz && to_add instanceof quiz){
            media temp = head;
            head = new quiz((quiz)to_add);
            head.connect_next(temp);

            //Read in metadata
            head.read(this.title, this.date, this.views, this.length);
        }

        //If the current object is the same type as the added object, add the new object in front of it
        else if(head instanceof comment && to_add instanceof comment) {
            media temp = head;
            head = new comment((comment)to_add);
            head.connect_next(temp);

            //Read in metadata
            head.read(this.title, this.date, this.views, this.length);
        }

        //Otherwise, traverse until either end of the list or we find a node containing the same media type
        else
            head.connect_next(add_media(head.go_next(), to_add));

        return head;
    }


    //Increment the views data member and tell the user how smart they now are
    public void watch_video(){
        ++this.views;
        System.out.println("\nYou watched the video and learned more than you ever thought possible");
    }
}