package lection_5.homework.mediator;

import java.util.ArrayList;
import java.util.List;

public class ChatMediator implements Mediator {

    private List<User> userList;

    public ChatMediator() {
        this.userList = new ArrayList<>();
    }

    public void addUser(User user) {
        userList.add(user);
    }

    @Override
    public void sendMessage(User user, String message) {
        userList.stream().filter(e -> !e.equals(user)).forEach(e -> e.receiveMessage(message));
    }
}
