package lection_5.homework.mediator;

public class User {

    private Mediator mediator;
    private String name;

    public User(Mediator mediator, String name) {
        this.mediator = mediator;
        this.name = name;
    }

    public void sendMessage(String message) {
        System.out.println("User " + name + " sending message: " + message);
        mediator.sendMessage(this, message);
    }

    public void receiveMessage(String message) {
        System.out.println("User " + name + " received message: " + message);
    }
}
