package com.unal.Library.repositories;

import com.unal.Library.models.User;
import com.unal.Library.models.common.Role;
import com.unal.Library.structures.DoublyLinkedList;
import com.unal.Library.structures.SinglyLinkedList;
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
public class UserSinglyListRepository implements InterfaceRepository<User>{
    SinglyLinkedList<User> users;
    List<User> userList;

    public UserSinglyListRepository(){
        this.users = new SinglyLinkedList<>();
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
        SinglyLinkedList<User>.Node<User> aux = null;
        for(
                aux = users.head;
                (aux!=null) && (!(aux.key.getId()==id));
                aux=aux.next
        );
        if (aux == null){
            return null;
        }
        return Optional.of(aux.key);
    }

    @Override
    public void save(User newUser) {
        users.pushBack(newUser);
    }

    @Override
    public void edit(User user, User NewUser) {
        users.search(user).key = NewUser;
    }
    public Optional<User> validateLogin(String email, String password){
        SinglyLinkedList<User>.Node<User> aux = null;
        for(aux = users.head; (aux!=null); aux=aux.next){
            if (aux.key.getEmail().equals(email) && aux.key.getPassword().equals(password)){
                return Optional.of(aux.key);
            }
        }

        if (aux == null){
            return null;
        }
        return Optional.of(aux.key);
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
        users.erase(users.getPosition(findById(id).get()));
        //users.delete(findById(id).get());
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
