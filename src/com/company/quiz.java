//Kevin Dugas
//Program 5
//CS 202
//December 2, 2020
//quiz.java

/*
Quiz is a derived class of the media abstract base class and the video indirect base class. It is similar to
the comment class, except it also includes the answer question method which is invoked using downcasting.
 */

package com.company;

class quiz extends media{
    protected String question;
    protected String answer;



    //Default constructor
    public quiz(){
        this.question = new String();
        this.answer = new String();
    }



    //Copy constructor that invokes the media copy constructor and uses the read method to set data members
    public quiz(quiz src){
        super(src);

        this.question = new String();
        this.answer = new String();

        read(src.question, src.answer);
    }



    //Abstract function that copies the arguments into the data members
    public void read(String question, String answer){
        this.question = question;
        this.answer = answer;
    }



    //Sets the data members using user interaction
    public void input(){
        System.out.print("\nAdd new question: ");
        this.question = input.nextLine();

        System.out.print("Please provide the answer: ");
        this.answer = input.nextLine();
    }


    //Display method
    public int display() {
        if (this.question == null || this.answer == null)
            return 0;

        System.out.println("\nQuiz question: " + this.question);
        return 1;
    }


    //User provides a string which is checked against the answer data member
    public void answer_question(){
        String attempt = new String();
        System.out.print("\nWhat is your answer: ");
        attempt = input.nextLine();
        if(attempt.equalsIgnoreCase(this.answer))
            System.out.print("Correct!");
        else
            System.out.println("Incorrect! :'( Such sad");
    }
}
