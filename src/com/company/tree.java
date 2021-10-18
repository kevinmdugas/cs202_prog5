//Kevin Dugas
//Program 5
//CS 202
//December 2, 2020
//tree.java

/*
This class manages the BST of playlist objects. Methods include creating and editing new playlist object,
deleting them from the BST, and displaying all objects in the tree. This class also includes the method that
reads from a file to create a new playlist object. The user must provide the full path to the file in order to avoid
the function throwing an exception. The file chosen by the user must also be formatted in exactly the same
way as the file "upload.txt" included with this program. The README file gives a breakdown of each field of the "upload.txt"
file
 */

package com.company;
import java.io.*;

class tree extends util {
    protected playlist root;
    protected boolean delete_flag;

    //Default constructor that sets data members to zero
    public tree() {
        this.root = null;
        this.delete_flag = false;
    }


    //Wrapper function that adds the passed in playlist object to the tree
    public boolean add_playlist(playlist to_add) {
        if(to_add == null)
            return false;
        this.root = add_playlist(this.root, to_add);
        return true;
    }


    //Recursive function that adds the passed in playlist object to the tree
    protected playlist add_playlist(playlist root, playlist to_add) {
        if (root == null) {
            root = new playlist(to_add);
            return root;
        }

        //If the new playlist comes first alphabetically, go left
        if (to_add.compare(root) < 0)
            root.connect_left(add_playlist(root.go_left(), to_add));

        //Else, go right
        else
            root.connect_right(add_playlist(root.go_right(), to_add));

        return root;
    }



    //Wrapper function that calls the recursive delete. If a match is found and deleted in the recursive function
    // the delete_flag is changed to true, which signals this function to return true.
    public boolean delete_playlist(String match){
        this.root = delete_playlist(this.root, match);

        if(this.delete_flag){
            this.delete_flag = false;
            return true;
        }
        return false;
    }



    //Recursive delete that performs standard BST deletion and changes the delete_flag to true if a match is found.
    // Passed in arg is used to find a match.
    public playlist delete_playlist(playlist root, String match){
        if(root == null)
            return null;

        int compare = root.compare_string(match);

        //If match is found
        if(compare == 0){

            //If root is a leaf
            if(root.go_left() == null && root.go_right() == null)
                root = null;

            //Else if root has only a left child
            else if(root.go_right() == null)
               root = root.go_left();

            //Else if root has only a right child
            else if(root.go_left() == null)
                root = root.go_right();

            //Else if root has left and right child
            else{
                playlist cur = root.go_right();

                //If root's right child has no left child
                if(cur.go_left() == null){
                    root.copy_playlist(cur);
                    root.connect_right(cur.go_right());
                }

                //If root's right child has a left child, we find the IOS
                else{
                    playlist prev = cur;
                    while(cur.go_left() != null){
                        prev = cur;
                        cur = cur.go_left();
                    }
                    root.copy_playlist(cur);
                    prev.connect_left(cur.go_right());
                }
            }
            this.delete_flag = true;
        }

        else if(compare > 0)
            root.connect_left(delete_playlist(root.go_left(), match));

        else
            root.connect_right(delete_playlist(root.go_right(), match));

        return root;
    }



    //Wrapper function that returns success if a match is found and a node is altered
    public boolean retrieve_playlist(String to_match){
        return retrieve_playlist(this.root, to_match);
    }



    //Recursive method that searches a tree for a match against the string argument and invokes
    // the manage_playlist method if the right node is found
    protected boolean retrieve_playlist(playlist root, String to_match) {
        if(root == null)
            return false;

        int compare = root.compare_string(to_match);

        if(compare == 0)
            return manage_playlist(root);

        else if(compare > 0)
            return retrieve_playlist(root.go_left(), to_match);

        return retrieve_playlist(root.go_right(), to_match);
    }


    //Standard display wrapper method displaying every playlist in the tree
    public int display() {
        if (this.root == null)
            return 0;

        System.out.println("\n=====| Displaying all saved playlists |=====");

        return display(this.root);
    }



    //Standard recursive wrapper method displaying every playlist in the tree
    protected int display(playlist root) {
        if (root == null)
            return 0;
        int pcount = display(root.go_left()) + 1;
        int vcount = root.display();
        System.out.println("\nTotal number of videos in this playlist: " + vcount);
        return pcount + display(root.go_right());
    }



    //Allows the user to initialize a playlist object either manually by uploading videos themselves or
    // by providing the path to a file containing the correct format necessary for the file to read from.
    // If the playlist is already populated, the user is informed that re-initializing the playlist will
    // erase currently saved information.
    public playlist initialize_playlist(playlist to_create) throws IOException {
        char nav;
        int count;

        //Make sure user knows initializing will erase current playlist
        if (to_create != null) {
            System.out.print("\nInitializing a new playlist will erase all contents of the current playlist. Continue? (y/n): ");
            nav = input.next().charAt(0);
            nav = Character.toLowerCase(nav);
            input.nextLine();
        } else
            nav = 'y';

        //Choose between manual creation or uploading
        if (nav == 'y') {
            System.out.print("\nWould you like to (1) create a new playlist manually or (2) upload one from an external file (Choose option 1 or 2): ");
            nav = input.next().charAt(0);
            nav = Character.toLowerCase(nav);
            input.nextLine();

            //If choice is valid
            if (nav == '1' || nav == '2') {

                //Delete current playlist
                if (to_create != null) {
                    count = to_create.delete_playlist();
                    System.out.print("\nPlaylist containing " + count + " videos deleted");
                }

                //Create manual playlist
                if (nav == '1')
                    to_create = new playlist();

                    //Upload playlist
                else
                    to_create = upload_playlist(to_create);
            } else
                System.out.println("Invalid input");
        } else
            System.out.println("Initialization cancelled");

        return to_create;
    }


    //Reads strings from a file and uses it to create a playlist using the reference passed in as an arg.
    // If the provided file name does not have any chars to read from, the playlist is returned without being
    // altered. If it isn't empty and the playlist ref is not null, the playlist is deleted
    public playlist upload_playlist(playlist dest) throws IOException {
        String response = new String();

        //Get filename. Must provide the FULL PATH to the file, otherwise an exception will be thrown
        System.out.println("\nThis option reads text from a file and creates a new playlist with it. \n\nIMPORTANT: the text in the provided file must be formatted in precisely the right way in order for the playlist to be created correctly.");
        System.out.print("Look at the file \"upload.txt\" included with this program to see how this file must be formatted, and the \"README.txt\" file for a breakdown of each required field. \n\nWhat's the path to the file you'd like to upload from: ");
        response = input.next();

            //Make new inputstream object
            DataInputStream in = new DataInputStream(new FileInputStream(response));

            //If the file is not empty, delete the current playlist
            if (in.available() > 0) {
                if (dest != null)
                    dest.delete_playlist();
            } else
                return dest;

            //Initialize reader variables
            String[] text = new String[3];      //text[0] = playlist topic, text[1] = video title, text[2] = video date
            int[] data = new int[6];            //data[0] = video array size, data[1] = vid_count, data [2] = video views
            int[] length = new int[3];         //length[0] = hours, length[1] = mins, length[2] = secs

            //Read playlist topic, size of video array, vid_count, and separating newline
            text[0] = in.readLine();                               //Playlist topic
            data[0] = Integer.parseInt(in.readLine());             //Video array size
            data[1] = Integer.parseInt(in.readLine());             //Video array size
            in.readLine();                                         //Read separator newline

            //Initialize temp video array and read in each video data
            video[] temp_array = new video[data[0]];

            for (int i = 0; i < temp_array.length; ++i) {
                if (i < data[1]) {
                    text[1] = in.readLine();                             //Video title
                    text[2] = in.readLine();                             //Video upload date
                    data[2] = Integer.parseInt(in.readLine());           //Video views
                    length[0] = Integer.parseInt(in.readLine());         //Hours
                    length[1] = Integer.parseInt(in.readLine());         //Mins
                    length[2] = Integer.parseInt(in.readLine());         //Secs

                    temp_array[i] = new video(text[1], text[2], data[2], length);   //Create video object
                    in.readLine();                                       //Read separator newline
                } else
                    temp_array[i] = new video();
            }

            dest = new playlist(text[0], data[1], temp_array);
            in.close();

        return dest;
    }


    //Alter the playlist object passed in as an arg by adding videos to its array of videos, deleting videos by name,
    // allowing the user to manage individual videos, or displaying the playlist. Returns false if the arg is null
    public boolean manage_playlist(playlist to_manage){
        if(to_manage == null){
            return false;
        }

        char nav;
        int count;

        do {
            System.out.println("\n\t\t---- | Manage Playlist | ----");
            System.out.print("\nChoose from the following options:\n1) Upload new videos\n2) Delete videos\n3) Browse videos and manage media\n4) Display playlist\n5) Go back to Main Menu\n\nOption #");
            nav = input.next().charAt(0);
            nav = Character.toLowerCase(nav);
            input.nextLine();

            //Upload videos
            if(nav == '1') {
                count = to_manage.input_vids();
                System.out.println("\nNumber of videos created: " + count);
            }

            //Delete video
            else if(nav == '2'){
                count = delete_vid(to_manage);
                System.out.println("\nNumber of videos deleted: " + count);
            }

            //Browse videos
            else if(nav == '3')
                to_manage.browse_videos();

            //Display
            else if(nav == '4') {
                count = to_manage.display();
                System.out.println("\nTotal number of videos in this playlist: " + count);
            }

        }while(nav != '5');

        return true;
    }


    //Takes a string from the user and checks it against the titles of the saved videos in the
    // current playlist. If a match is found, the video is deleted from the array and the data
    // is shifted if necessary. Returns the number of videos deleted.
    public int delete_vid(playlist topic){
        String response = new String();
        int count = 0;
        char resp;

        do {
            System.out.print("\nWhat's the name of the video to delete: ");
            response = input.nextLine();
            if(topic.delete_vid(response) == 1) {
                ++count;
                System.out.println("Video Deleted");
            }
            else
                System.out.println("No match found");

            System.out.print("\nAgain? (y/n): ");
            resp = input.next().charAt(0);
            resp = Character.toLowerCase(resp);
            input.nextLine();
        }while(resp == 'y');

        return count;
    }
}



