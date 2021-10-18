//Kevin Dugas
//Program 5
//CS 202
//December 2, 2020
//media.java

/*
The media class is the abstract base class for the two derived classes, comment and quiz. It
has no other purpose but to serve as a link between the two derived classes and the video class,
and for node functionality
 */

package com.company;

abstract class media extends video{
    protected media next;

    public abstract void read(String primary, String secondary);
    public abstract void input();
    public abstract int display();


    //Default constructor
    public media(){
        this.next = null;
    }


    //Copy constructor that invokes the video class' copy constructor
    public media(media src){
        super(src);
        this.next = null;
    }


    //Node function to return the next data member
    public media go_next(){
        return this.next;
    }


    //Node function to set the next member
    public void connect_next(media next){
        this.next = next;
    }
}
