//Kevin Dugas
//Program 5
//CS 202
//December 2, 2020
//comment.java

package com.company;

class comment extends media{
    protected String text;
    protected String username;



    //Default constructor
    public comment(){
        this.text = new String();
        this.username = new String();
    }



    //Copy constructor that invokes the media copy constructor and uses the read method to set data members
    public comment(comment src){
        super(src);

        this.text = new String();
        this.username = new String();

        read(src.text, src.username);
    }



    //Abstract function that copies the arguments into the data members
    public void read(String text, String username){
        this.text = text;
        this.username = username;
    }



    //Sets the data members using user interaction
    public void input(){
        System.out.print("\nAdd new comment: ");
        this.text = input.nextLine();

        System.out.print("Who posted this comment: ");
        this.username = input.nextLine();
    }



    //Display method
    public int display() {
        if (this.text == null || this.username == null)
            return 0;

        System.out.println("\nComment posted by " + this.username + ": " + this.text);
        return 1;
    }
}

