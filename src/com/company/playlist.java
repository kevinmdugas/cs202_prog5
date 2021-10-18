//Kevin Dugas
//Program 5
//CS 202
//December 2, 2020
//playlist.java

/*
The playlist contains the array of video objects, each of which contain a LLL of dynamically bound media
objects. Included are methods to copy an entire playlist object, including the array of LLL, inputting video
objects, and browsing saved objects.
*/

package com.company;
import java.io.*;

class playlist extends util{
    protected playlist left;        //Unused until program 5
    protected playlist right;       //Unused until program 5
    protected video [] sequence;    //Array of video objects
    protected int vid_count;        //Number of saved videos
    protected String topic;         //Topic of the playlist



    //Default constructor that initializes members to zero and calls the initialize playlist method to allow
    //the user to determine the maximum capacity of the video array
    public playlist(){
        this.left = null;
        this.right = null;
        this.topic = new String();
        this.sequence = null;
        initialize_playlist();
    }



    //Copy constructor that that invokes the copy_playlist method to make a copy of the argument
    public playlist(playlist src){
        this.left = null;
        this.right = null;
        this.topic = new String();
        copy_playlist(src);
    }


    //Constructor with args that initializes playlist object with each of the arguments
    public playlist(String topic, int vid_count, video[] sequence){
        this.left = null;
        this.right = null;
        this.topic = topic;
        this.vid_count = vid_count;
        this.sequence = sequence;
    }



    //Interacts with the user to create a new playlist object and setting the size of the video array
    public void initialize_playlist(){
        System.out.print("\nWhat is the topic of this playlist: ");
        this.topic = input.nextLine();

        System.out.print("How many videos on this playlist will there be at most: ");
        this.sequence = new video[input.nextInt()];
        for(int i = 0; i < this.sequence.length; ++i)
            this.sequence[i] = new video();
        this.vid_count = 0;
    }



    //Take the playlist object passed in and make a copy of each of the data members, as well as the
    // array of videos. New video copies are made by invoking the video class' copy constructor
    public int copy_playlist(playlist src){
        if(src.sequence == null || this.topic == null)
            return 0;

        this.topic = src.topic;
        this.vid_count = src.vid_count;
        this.sequence = new video[src.sequence.length];

        for(int i = 0; i < this.sequence.length; ++i){
            if(i < vid_count)
                this.sequence[i] = new video(src.sequence[i]);
            else
                this.sequence[i] = new video();
        }
        return 1;
    }



    //Interact with the user to create video objects and add them to the current playlist. If the
    // array is filled with objects, the user can chose to increase the size of the array to add more
    // videos. The number of videos added is returned to the client
    public int input_vids(){
        char response;

        do {
            //If Array of videos is full
            if(vid_count == this.sequence.length) {

                //Ask if user would like to increase video capacity
                System.out.print("\nMaximum number of videos for this playlist has been reached. Would you like to add more? (y/n): ");
                response = input.next().charAt(0);
                response = Character.toLowerCase(response);
                input.nextLine();

                //If user wants to increase capacity
                if(response == 'y'){

                    //Hold on to current sequence and ask how many more to add
                    video [] temp = this.sequence;
                    System.out.print("How many more videos would you like to add: ");
                    int increase = input.nextInt();
                    input.nextLine();

                    //Create new video array with the increased capacity and copy the current videos into it
                    this.sequence = new video[vid_count+increase];
                    for(int i = 0; i < this.sequence.length; ++i){
                        if(i < vid_count)
                            this.sequence[i] = new video(temp[i]);
                        else
                            this.sequence[i] = new video();
                    }
                }
            }

            //Otherwise, create new video
            else {
                this.sequence[this.vid_count].input();
                ++this.vid_count;

                System.out.print("\nCreate another video? (y/n): ");
                response = input.next().charAt(0);
                response = Character.toLowerCase(response);
                input.nextLine();
            }
        } while(response == 'y');

        return this.vid_count;
    }



    //This method allows the user to traverse through the array of video objects and either watch the video,
    // add media to the LLL saved in the current video, manage the LLL of media, or go to the next video
    public void browse_videos(){
        if(this.vid_count == 0) {
            System.out.println("\nNo saved videos to browse");
            return;
        }
        char response;

        int i = 0;
        while(i < vid_count){
            this.sequence[i].display();
            System.out.println("\n\t\t---- | Browse Videos | ----");
            System.out.print("\nWhat would you like to do\n1) Watch video\n2) Add media \n3) Answer quiz questions and manage media\n4) Go to next video\n5) Go back to Manage Playlist\n\nOption #");

            response = input.next().charAt(0);
            response = Character.toLowerCase(response);
            input.nextLine();

            if(response == '1')
                this.sequence[i].watch_video();

            else if(response == '2')
                this.sequence[i].create_media();

            else if(response == '3')
                this.sequence[i].browse_media();

            else if(response == '4')
                ++i;

            else if(response == '5')
                i = this.vid_count;

            else
                System.out.println("\nInvalid selection");
        }
    }


    //Deletes the current playlist by setting all members to zero and returning the number of erased
    // video objects
    public int delete_playlist(){
        this.sequence = null;
        int temp = this.vid_count;
        this.vid_count = 0;
        this.topic = new String();
        return temp;
    }



    //Delete video if the passed in string matches one of the titles of a saved video object.
    // If a deleted video is not the last of the saved objects in the array, all of the items
    // after it are shifted to the left so that there are no null references with objects
    public int delete_vid(String title_match){

        //Check each index of the array of vids
        for(int i = 0; i < this.vid_count; ++i){

            //If match is found and deleted
            if(this.sequence[i].delete_vid(title_match) == 1){

                //If the video that was deleted isn't the last video
                if(i < this.vid_count - 1) {

                    //Shift the data
                    for (int j = i + 1; j < this.vid_count; ++j) {
                        this.sequence[i] = this.sequence[j];
                        ++i;
                    }
                }
                this.sequence[vid_count-1] = null;
                --this.vid_count;
                return 1;
            }
        }
        return 0;

    }



    //Display each video object of the sequence array
    public int display(){
        if(this.sequence == null || this.topic == null)
            return 0;

        System.out.println("\n\n======= Playlist Display: " + this.topic + " =======");

        for(int i = 0; i < this.vid_count; ++i){
            this.sequence[i].display();
        }
        return vid_count;
    }


    //String compare returning the integer result
    public int compare(playlist to_compare){
        return this.topic.compareToIgnoreCase(to_compare.topic);
    }


    public int compare_string(String to_compare){
        return this.topic.compareToIgnoreCase(to_compare);
    }

    //Node functionality that will be used in Program 5
    public playlist go_left(){
        return this.left;
    }

    //Node functionality that will be used in Program 5
    public playlist go_right(){
        return this.right;
    }

    //Node functionality that will be used in Program 5
    public void connect_left(playlist left){
        this.left = left;
    }

    //Node functionality that will be used in Program 5
    public void connect_right(playlist right){
        this.right = right;
    }

}
