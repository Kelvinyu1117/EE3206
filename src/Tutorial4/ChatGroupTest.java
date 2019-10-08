package Tutorial4;// Student name: Wu Kwun Yu
// Student ID  : 54845500

// Submission deadline: Friday, 11 Oct, 12 noon
// Implement the method getFriends in class ChatGroupTest

import java.net.CookieHandler;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ChatGroupTest {
    public static List<User> getFriends(List<ChatGroup> groups, User u) {
        // Implement this static method.
        // Users in the same chat group are considered to be friends.

        // Return a list of users (without duplicate) that are friends
        // of u derived from the list of chat groups.
        // Result list does not contain u himself/herself.

        ArrayList<User> result = new ArrayList();

        // Your codes

        for (int i = 0, groupIndex = 0; i < groups.size(); i++) {
            if (groups.get(i).getUser(u.getTel()) != null) {
                groupIndex = i;
                User[] fdsList = groups.get(groupIndex).getMembers();

                for (User usr : fdsList) {
                    if (usr.compareTo(u) != 0) {
                        int j = Collections.binarySearch(result, usr);
                        if (j < 0)
                            result.add((-j) - 1, usr);
                    }
                }
            }
        }

        return result;
    }

    // -------------------------------------- methods given to you

    public static void main(String[] args) {
        String[] names = {"AU Cheuk Ming", "AU Chun Kit", "BUTT Ka Kiu", "CHAN Ho Yin",
                "CHAN Ka Hung", "CHAN Tsz Nga", "CHEN Yingqi", "CHEUNG Man Kit",
                "CHIN Tsz Ho", "CHU Yat Long", "CHOW Shing Lok", "FU Ting",
                "HO Ka Chun", "HUI Tsz Him", "KO Tin Long", "KUO Kuan Ting",
                "KWOK Wah Chung", "LAM Hau Sze", "LAM Ka Shing", "LAU Cheuk Yiu",
                "LAW Ka Yu", "LEE Hung Hin", "LEUNG Tsz Kit", "LO Tsz Yue",
                "MA Tsz Kwan", "NG Chak Ka", "NG Sai Hin", "RAJNIKANTH Ajay",
                "SENTHIL KUMAR Nikil", "SIN Yat Ming", "TAM Chin Kiu", "TANG Chun Ki",
                "TSE Sin Man", "TSEUNG Mak Man", "WONG Chak Kong", "WONG Chong Nam",
                "WONG Ka Chung", "WONG Ka Ying", "WONG Kin Wai", "WU Kwun Yu",
                "WU Zijian", "XU Tianxiao", "YAU Hiu Kwan", "YEAP Su Jin",
                "YIM Kwok Woon", "YING Fu Chiu", "YOUNG Wang Kin", "YU Sai Kit",
                "YU Tsz Ho", "YUEN Pui Yee"};

        String[] tels = new String[50];
        tels[0] = "+1 (312)345-6789";  // non-local tel numbers
        tels[1] = "+1 (514)440-1234";
        tels[2] = "+86 139 2345 9876";

        int t = 91234567;
        for (int i = 3; i < tels.length; i++) {
            int k = (int) (Math.random() * 7000000);
            tels[i] = Integer.toString(t + k);  // local tel number
        }

        ArrayList<ChatGroup> groups = createChatGroups(names, tels);

        System.out.print(groups.get(0));

        User u1 = new User(names[13].toUpperCase(), tels[13]);
        User u2 = new User(names[28].toUpperCase(), tels[28]);
        User u3 = new User(names[33].toUpperCase(), tels[33]);

        testAddUser(groups.get(0), u1);
        testAddUser(groups.get(0), u2);

        testRemoveUser(groups.get(1), u1);
        testRemoveUser(groups.get(1), u3);

        testGetUser(groups.get(1), tels[2]);
        testGetUser(groups.get(1), tels[17]);
        testGetUser(groups.get(1), tels[30]);

        testGetFriends(groups, u1);
    }

    private static void testAddUser(ChatGroup g, User u) {
        System.out.println("\n--------------------------------------");
        System.out.println("Test ChatGroup.addUser()");
        System.out.println("Add user " + u);
        g.addUser(u);
        System.out.print(g);
    }

    private static void testRemoveUser(ChatGroup g, User u) {
        System.out.println("\n--------------------------------------");
        System.out.println("Test ChatGroup.removeUser()");
        System.out.println("Remove user " + u);
        g.removeUser(u);
        System.out.print(g);
    }

    private static void testGetUser(ChatGroup g, String tel) {
        System.out.println("\n--------------------------------------");
        System.out.println("Test ChatGroup.getUser()");
        User u = g.getUser(tel);
        if (u != null)
            System.out.println(u + " is a member of " + g.getGroupName());
        else
            System.out.println("Tel " + tel + " is not found in " + g.getGroupName());

    }

    private static void testGetFriends(List<ChatGroup> groups, User u) {
        List<User> friendList = getFriends(groups, u);
        System.out.println("\n---------------------------------------");
        System.out.println("Test ChatGroup.getFriend()");
        System.out.println("** Friends of : " + u + " **");
        friendList.forEach(System.out::println);
    }

    private static ArrayList<ChatGroup> createChatGroups(String[] names, String[] tels) {
        ArrayList<ChatGroup> groups = new ArrayList();

        int[] g1 = {13, 18, 7, 36};
        int[] g2 = {2, 3, 8, 22, 1, 26, 27, 49, 33, 31, 17};
        int[] g3 = {7, 9, 13, 28};
        int[] g4 = {1, 3, 5, 16, 32, 19, 49, 25, 30, 42};
        int[] g5 = {9, 12, 13};

        int gid = 1;
        groups.add(createGroup(names, tels, g1, gid++));
        groups.add(createGroup(names, tels, g2, gid++));
        groups.add(createGroup(names, tels, g3, gid++));
        groups.add(createGroup(names, tels, g4, gid++));
        groups.add(createGroup(names, tels, g5, gid++));
        return groups;
    }

    private static ChatGroup createGroup(String[] names, String[] tels, int[] m, int gid) {
        ChatGroup group = new ChatGroup("group-" + gid, gid);
        for (int i = 0; i < m.length; i++)
            group.addUser(new User(names[m[i]], tels[m[i]]));

        return group;
    }
}
