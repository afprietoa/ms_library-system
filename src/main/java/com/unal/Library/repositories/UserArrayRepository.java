package com.unal.Library.repositories;

import com.unal.Library.models.User;
import com.unal.Library.models.common.Role;
import com.unal.Library.structures.DoublyLinkedList;
import com.unal.Library.structures.DynamicArrayList;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.FileReader;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Repository
public class UserArrayRepository implements InterfaceRepository<User> {
    DynamicArrayList<User> users;
    List<User> userList;

    public UserArrayRepository(){
        this.users = new DynamicArrayList<>();
        this.userList = new ArrayList<>();
    }
    @Override
    public List<User> findAll() {
        return toList();
    }

    public List<User> toList(){
        for(int i=1; i<=users.length();i++){
            userList.add(users.find(i));
        }
        return userList;
    }

    @Override
    public Optional<User> findById(int id) {
        int i = 0;
        while(i < users.length()) {
            if(users.find(i).getId() == id) {
                return Optional.of(users.find(i));
            }
            i++;
        }
        return null;
    }

    @Override
    public void save(User newUser) {
        users.popBack();
    }

    @Override
    public void edit(User user, User NewUser) {
        users.exchange(NewUser, users.getPosition(user));
    }
    public Optional<User> validateLogin(String email, String password){

        int i = 0;
        while(i < users.length()) {
            if(users.find(i).getEmail().equals(email) && users.find(i).getPassword().equals(password)) {
                return Optional.of(users.find(i));
            }
            i++;
        }
        return null;
    }

    /**
     *
     * @param password
     * @return
     */
    public String convertToSHA256(String password){
        MessageDigest md = null;
        try{
            md = MessageDigest.getInstance("SHA-256");
        }
        catch(NoSuchAlgorithmException e){
            e.printStackTrace();
            return null;
        }
        StringBuffer sb = new StringBuffer();
        byte[] hash = md.digest(password.getBytes());
        for(byte b: hash)
            sb.append(String.format("%02x", b));
        return sb.toString();
    }

    @Override
    public void delete(int id) {
        users.delete(findById(id).get());

    }
    @PostConstruct
    private void init() {
        String path="src/main/java/com/unal/Library/data/usuarios10mil.csv";
        String tuple;

        try (BufferedReader buffer =
                     new BufferedReader(new FileReader(path))) {
            while((tuple = buffer.readLine()) != null){
                String[] cellValues = tuple.split(",");
                User user = new User(
                        Integer.parseInt(cellValues[0]),
                        cellValues[1].concat(" ").concat(cellValues[2]),
                        cellValues[3],
                        cellValues[4],
                        this.convertToSHA256(cellValues[3]),
                        String.valueOf(Arrays.asList(Role.values()).get((int)(Math.floor(Math.random()*Role.values().length))))
                );
                users.pushBack(user);
            }
        } catch (Exception e){
            System.out.println(e);
        }
    }
}
