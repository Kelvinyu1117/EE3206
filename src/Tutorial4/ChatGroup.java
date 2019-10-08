package Tutorial4;// Student name: Wu Kwun Yu
// Student ID  : 54545500

// Submission deadline: Friday, 11 Oct, 12 noon
// Implement methods addUser, removeUser, and getUser in class ChatGroup.

import java.util.ArrayList;
import java.util.Collections;

public class ChatGroup {
    private final String groupName;
    private final int groupID;
    private final ArrayList<User> members;  // Elements in the list are ordered by tel

    public ChatGroup(String gName, int gid) {
        groupName = gName;
        groupID = gid;
        members = new ArrayList();
    }

    public String getGroupName() {
        return groupName;
    }

    public int getGroupID() {
        return groupID;
    }

    public User[] getMembers()  // DO NOT modify the methods given to you
    {
        return members.toArray(new User[members.size()]);
    }

    @Override
    public String toString() {
        StringBuilder buffer = new StringBuilder();
        buffer.append("Chat group : " + groupName + ", group ID : " + groupID + "\n");
        buffer.append("Members : \n");
        for (User u : members)
            buffer.append(u + "\n");

        return buffer.toString();
    }

    public User getUser(String tel) {
        // Implement this method.
        // Return the user in the group with the given tel.
        // Return null if no user with the tel is found.
        int index = Collections.binarySearch(members, new User("", tel));
        if (index >= 0)
            return members.get(index);
        else
            return null; // dummy return statement
    }

    public void addUser(User u) {
        // Implement this method. Add a user to the group.
        // Take note of the representation requirements of the class.
        int j = Collections.binarySearch(members, u);

        if (j < 0)
            members.add((-j) - 1, u);
    }

    public void removeUser(User u) {
        // Implement this method. Remove the user from the group.
        // Take note of the representation requirements of the class.
        int j = Collections.binarySearch(members, u);

        if (j >= 0)
            members.remove(j);

    }
}

// ------------------------------------------------------------------

class User implements Comparable<User> {
    private String name;
    private String tel;

    public User(String n, String t) {
        name = n;
        tel = t;
    }

    public String getName() {
        return name;
    }

    public String getTel() {
        return tel;
    }

    @Override
    public String toString() {
        return name + ", " + tel;
    }

    @Override
    public int compareTo(User other) {
        return tel.compareTo(other.tel);
    }

}

