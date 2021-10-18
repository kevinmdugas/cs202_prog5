//Kevin Dugas
//Program 5
//CS 202
//December 2, 2020
//Main.java

/*
Client class that serves as the top level menu interface for the entire program.
Options included here will allow the user to create new playlists, which will send them through
three layers of submenus: Playlist Manager, Video Browsing, and Media Browsing. Managing the
BST object storing all playlist items happens here. The main program from Program 4 was essentially
broken up into individual methods and integrated into the tree class since that class become the
highest level of data abstraction over the playlist class.
 */


package com.company;
import java.io.*;
import java.util.Scanner;


class util{
    protected Scanner input;

    util(){
        this.input = new Scanner(System.in);
    }
}

public class Main {

    public static void main(String[] args) throws IOException {
        Scanner input = new Scanner(System.in);
        char nav;
        String response = new String();
        tree course = new tree();
        playlist temp = null;

        do {
            System.out.print("\n\n\t\t| Main Menu |\n\nChoose from the following options\n1) Create new playlist\n2) Manage existing playlist\n3) Delete a playlist\n4) Display all playlists\n5) Delete all playlists\n6) Quit\n\nOption #");
            nav = input.next().charAt(0);
            nav = Character.toLowerCase(nav);
            input.nextLine();

            //Create new playlist. An IOException is caught here if user tries file upload and can't find the file
            if(nav == '1'){
                try {
                    temp = course.initialize_playlist(temp);
                    System.out.println("\nPlaylist created, use the following menu to manage it");
                    course.manage_playlist(temp);
                    course.add_playlist(temp);
                    temp = null;
                }catch(IOException e){System.out.println("File not found, cannot create playlist");}
            }

            //Manage existing playlist
            else if(nav == '2'){
                System.out.print("\nWhat's the name of the playlist you'd like to manage: ");
                response = input.nextLine();
                if(course.retrieve_playlist(response) == false)
                    System.out.println("\nNo match found");
            }

            //Delete single playlist
            else if(nav == '3'){
                System.out.print("\nWhat's the name of the playlist you'd like to delete: ");
                response = input.nextLine();
                if(course.delete_playlist(response) == true)
                    System.out.println("Playlist deleted");
                else
                    System.out.println("\nNo match found");
            }

            //Display all playlists
            else if(nav == '4'){
                int count = course.display();
                System.out.println("\nTotal number of saved playlists: " + count);
            }

            //Delete all playlists
            else if(nav == '5') {
                course = new tree();
                System.out.println("\nAll playlists deleted");
            }

        } while (nav != '6');

    }

}





