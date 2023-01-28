package com.driver;

import java.util.*;

import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;

@Repository
public class WhatsappRepository {

    //Assume that each user belongs to at most one group
    //You can use the below mentioned hashmaps or delete these and create your own.
    private HashMap<Group, List<User>> groupUserMap;
    private HashMap<Group, List<Message>> groupMessageMap;
    private HashMap<Message, User> senderMap;
    private HashMap<User, List<User>> adminMap;
    private HashSet<User> userMobile;
    private int customGroupCount;
    private int messageId;

    public WhatsappRepository(){
        this.groupMessageMap = new HashMap<Group, List<Message>>();
        this.groupUserMap = new HashMap<Group, List<User>>();
        this.senderMap = new HashMap<Message, User>();
       // this.adminMap = new HashMap<User, List<User>>();                       //to make a personal chat
        this.userMobile = new HashSet<>();                                //to ceat or check user
        this.customGroupCount = 0;
        this.messageId = 0;
    }

    //add_user
    public Boolean createUser(String name, String mobile){
        User user = new User(name, mobile);
        if(userMobile.contains(user))
            return true;
        else
            userMobile.add(user);
            return false;
    }

    //creat - group
     int j = 1;
    public Group createGroup(List<User> users){
        // The list contains at least 2 users where the first user is the admin. A group has exactly one admin.
        // If there are only 2 users, the group is a personal chat and the group name should be kept as the name of the second user(other than admin)
        // If there are 2+ users, the name of group should be "Group count". For example, the name of first group would be "Group 1", second would be "Group 2" and so on.
        // Note that a personal chat is not considered a group and the count is not updated for personal chats.
        // If group is successfully created, return group.

        //For example: Consider userList1 = {Alex, Bob, Charlie}, userList2 = {Dan, Evan}, userList3 = {Felix, Graham, Hugh}.
        //If createGroup is called for these userLists in the same order, their group names would be "Group 1", "Evan", and "Group 2" respectively.

       if(users.size() == 2){
           Group group = new Group(users.get(1).getName(),2);
           groupUserMap.put(group,users);
           return group;
       }else{
           customGroupCount++;
           String s = "Group "+customGroupCount;
       }


           Group group = new Group("s", users.size());
           groupUserMap.put(group,users);
           return group;

    }

    //creat message
    public int createMessage(String content){
        // The 'i^th' created message has message id 'i'.
        // Return the message id.
        Message message = new Message(messageId,content,new Date());
        return message.getId();
    }


    public int sendMessage(Message message, User sender, Group group) throws Exception{
        //Throw "Group does not exist" if the mentioned group does not exist
        //Throw "You are not allowed to send message" if the sender is not a member of the group
        //If the message is sent successfully, return the final number of messages in that group.


        if(!groupUserMap.containsKey(group)){
            throw new Exception("Group does not exist");
        }
        List<User> g = groupUserMap.get(group);
        if (!g.contains(sender)) {
            throw new Exception("You are not allowed to send message");
        }

        // add message to group's messages and return final number of messages

        List<Message> m = groupMessageMap.get(group);
        m.add(message);
        groupMessageMap.put(group,m);

        return m.size();
    }

    public String changeAdmin(User approver, User user, Group group) throws Exception{
        //Throw "Group does not exist" if the mentioned group does not exist
        //Throw "Approver does not have rights" if the approver is not the current admin of the group
        //Throw "User is not a participant" if the user is not a part of the group
        //Change the admin of the group to "user" and return "SUCCESS". Note that at one time there is only one admin and the admin rights are transferred from approver to user.

        if(!groupUserMap.containsKey(group)){
            throw new Exception("Group does not exist");
        }

        List<User> g = groupUserMap.get(group);
        if(!g.contains(user)){
            throw new Exception("User is not a participant");
        }
        User us = g.get(0);
        if(!us.equals(approver)){
            throw new Exception("Approver does not have rights");
        }
        return "SUCCESS";
    }


//    public int removeUser(User user) throws Exception{
//        //This is a bonus problem and does not contains any marks
//        //A user belongs to exactly one group
//        //If user is not found in any group, throw "User not found" exception
//        //If user is found in a group and it is the admin, throw "Cannot remove admin" exception
//        //If user is not the admin, remove the user from the group, remove all its messages from all the databases, and update relevant attributes accordingly.
//        //If user is removed successfully, return (the updated number of users in the group + the updated number of messages in group + the updated number of overall messages)
//
//
//    }
//
//
//    public String findMessage(Date start, Date end, int K) throws Exception{
//        //This is a bonus problem and does not contains any marks
//        // Find the Kth latest message between start and end (excluding start and end)
//        // If the number of messages between given time is less than K, throw "K is greater than the number of messages" exception
//
//
//    }
}
