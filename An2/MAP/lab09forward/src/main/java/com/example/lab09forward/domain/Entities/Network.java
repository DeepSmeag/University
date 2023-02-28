package com.example.lab09forward.domain.Entities;

//package socialnetwork.domain.Entities;
//
//import java.io.File;
//import java.io.FileNotFoundException;
//import java.sql.Date;
//import java.util.*;
//

/**
 * Class models a Network
 */
public class Network {
    /**
     * Constructor for Network class
     */
    private Network() {
    }
}
//    // Singleton pattern
//    private static ArrayList<User> users;
//    private static ArrayList<Friendship> friendships;
//
//    private static Network instance;
//
//    public static synchronized Network getInstance() {
//        if (instance == null) {
//            instance = new Network();
//        }
//        if(users == null){
//            users = new ArrayList<User>();
//        }
//        if(friendships == null){
//            friendships = new ArrayList<Friendship>();
//        }
//        return instance;
//    }
//
//
//    public void buildNetworkFromFile(String usersFile, String friendshipsFile) {
//        users.clear();
//        friendships.clear();
//        buildUserListFromFile(usersFile);
//        buildFriendshipsListFromFile(friendshipsFile);
//    }
//
//    private void buildUserListFromFile(String fileName) {
//        try {
//            File file = new File(fileName);
//            Scanner reader = new Scanner(file);
//            while (reader.hasNextLine()) {
//                String data = reader.nextLine();
//                List<String> paramList = Arrays.asList(data.split(","));
//                UUID uid = UUID.fromString(paramList.get(0));
//                String firstName = paramList.get(1);
//                String lastName = paramList.get(2);
////                User user = new User(uid, firstName, lastName);
////                users.add(user);
//            }
//            reader.close();
//        } catch (FileNotFoundException e) {
//            System.out.println("An error occurred.");
//            e.printStackTrace();
//        }
//    }
//
//    private void buildFriendshipsListFromFile(String fileName) {
//        try {
//            File file = new File(fileName);
//            Scanner reader = new Scanner(file);
//            while (reader.hasNextLine()) {
//                String data = reader.nextLine();
//                List<String> paramList = Arrays.asList(data.split(","));
//                UUID uid1 = UUID.fromString(paramList.get(0));
//                UUID uid2 = UUID.fromString(paramList.get(1));
//                User user1 = this.findUserByUUID(uid1);
//                User user2 = this.findUserByUUID(uid2);
//                Date since = Date.valueOf(paramList.get(2));
//                Friendship friendship1 = new Friendship(user1, user2, since);
//                Friendship friendship2 = new Friendship(user2, user1, since);
//                friendships.add(friendship1);
//                friendships.add(friendship2);
//            }
//            reader.close();
//        } catch (FileNotFoundException e) {
//            System.out.println("An error occurred.");
//            e.printStackTrace();
//        } catch (Exception e) {
//            System.out.println("Input files are corrupted.");
//            e.printStackTrace();
//        }
//    }
//
////    private User findUserByUUID(UUID uuid) {
////        for (var user : users) {
//////            if (user.getUid().equals(uuid)) {
////                return user;
////            }
////        }
////        return null;
////    }
//
//    public ArrayList<UUID> buildFriendListFromUUID(UUID uuid){
//        ArrayList<UUID> friendsList = new ArrayList<UUID>();
////        for(var friendship: friendships){
////            if(friendship.getUser1().getUid() == uuid){
////                friendsList.add(friendship.getUser2().getUid());
////            }
////        }
//        return friendsList;
//    }
//
//
//
//    public void testingPrintUsers() {
//        for(var user: users) {
//            System.out.println(user);
//        }
//    }
//    public void testingPrintFriendships() {
//        for(var friendship: friendships) {
//            System.out.println(friendship);
//        }
//    }
//
//}
