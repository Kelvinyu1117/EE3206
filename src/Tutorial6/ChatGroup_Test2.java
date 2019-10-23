package Tutorial6;
// Student name:
// Student ID  : 

// Submission deadline: Friday, 25 Oct, 12 noon.

import java.util.*;

import static java.util.Comparator.comparing;

import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ChatGroup_Test2 {

    public static List<User> getFriends_FunctionUtil(List<ChatGroup> groups, User u) {
        // Implementation using FunctionUtil.

        // Users in the same chat group are considered to be friends.
        // Return a list of users (without duplicate) that are friends 
        // of u derived from the list of chat groups.
        // Result list does not contain u himself/herself.

        BiConsumer<List<User>, ChatGroup> ChatGpToUsr = (res, gp) -> {
            if (gp.getUser(u.getTel()).isPresent()) {
                res.addAll(Arrays.asList(gp.getMembers()));
            }
        };

        BiConsumer<List<User>, User> selectValidUser = (res, usr) -> {
            if (!usr.getTel().equals(u.getTel())) {
                if (res.isEmpty() || !res.get(res.size() - 1).getTel().equals(usr.getTel())) {
                    res.add(usr);
                }
            }
        };

        List<User> fdList = FunctionUtil.transform(groups, ChatGpToUsr);
        fdList.sort(User::compareTo);
        fdList = FunctionUtil.transform(fdList, selectValidUser);

        return fdList;
    }


    public static List<User> getFriends_Stream(List<ChatGroup> groups, User u) {
        // Implementation using Stream API.

        // Try different approaches to remove duplicates in the result list.
        // 1. Design your own accumulator.
        // 2. Use Collectors.toSet().
        // 3. Use Collectors.toMap().

        Supplier<ArrayList<User>> s = ArrayList::new;
        BiConsumer<ArrayList<User>, User> a = (res, usr) -> {
            if (!usr.getTel().equals(u.getTel())) {
                if (res.isEmpty() || !res.get(res.size() - 1).getTel().equals(usr.getTel())) {
                    res.add(usr);
                }
            }
        };
        BiConsumer<ArrayList<User>, ArrayList<User>> c = ArrayList::addAll;

        // approach 1
        ArrayList<User> fdList1 = new ArrayList<User>();
        fdList1.addAll(groups.stream()
                .filter(gp -> gp.getUser(u.getTel()).isPresent())
                .flatMap(gp -> Arrays.stream(gp.getMembers()))
                .filter(usr -> !usr.getTel().equals(u.getTel()))
                .sorted()
                .collect(s, a, c)
        );


        //approach 2
        ArrayList<User> fdList2 = new ArrayList<>();
        fdList2.addAll(groups.stream()
                .filter(gp -> gp.getUser(u.getTel()).isPresent())
                .flatMap(gp -> Arrays.stream(gp.getMembers()))
                .filter(usr -> !usr.getTel().equals(u.getTel()))
                .collect(Collectors.toMap(User::getTel, Function.identity(), (tel1, tel2) -> {
                    return tel1;
                }))
                .values()
        );

        fdList2.sort(User::compareTo);

        // approach 3
        ArrayList<User> fdList3 = new ArrayList<User>();
        fdList3.addAll(groups.stream()
                .filter(gp -> gp.getUser(u.getTel()).isPresent())
                .flatMap(gp -> Arrays.stream(gp.getMembers()))
                .filter(usr -> !usr.getTel().equals(u.getTel()))
                .collect(Collectors.toSet())
        );

        fdList3.sort(User::compareTo);

        return fdList3;
    }


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

        User u = new User(names[13].toUpperCase(), tels[13]);


        List<User> friendList = getFriends_FunctionUtil(groups, u);
        System.out.println("Test getFriends_FunctionUtil()");
        System.out.println("Friends of " + u);
        friendList.forEach(System.out::println);

        System.out.println("\n----------------------------------------");
        List<User> friendList_2 = getFriends_Stream(groups, u);
        System.out.println("Test getFriends_Stream()");
        System.out.println("Friends of " + u);
        friendList_2.forEach(System.out::println);

    }

    private static ArrayList<ChatGroup> createChatGroups(String[] names, String[] tels) {
        ArrayList<ChatGroup> groups = new ArrayList();

        tels[0] = "+1 (312)345-6789";  // non-local tel numbers
        tels[1] = "+1 (514)440-1234";
        tels[2] = "+86 139 2345 9876";

        int t = 91234567;
        for (int i = 3; i < tels.length; i++) {
            int k = (int) (Math.random() * 7000000);
            tels[i] = Integer.toString(t + k);  // local tel number
        }

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
