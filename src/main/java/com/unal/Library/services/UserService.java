package com.unal.Library.services;

import com.unal.Library.models.User;
import com.unal.Library.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    /**
     *
     * @return
     */
    public List<User> index(){
        List<User> resultList = (List<User>) this.userRepository.findAll();
        if (resultList.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "There is not any user in the list.");
        return  resultList;
    }

    /**
     * @param id
     * @return
     */
    public Optional<User> show(int id){
        Optional<User> result = this.userRepository.findById(id);
        if(result.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "The requested user.id does not exist.");
        return  result;
    }

    /**
     *
     * @param newUser
     * @return
     */
    public User create(User newUser){
        if(newUser.getId() != null){
            Optional<User> tempUser = this. userRepository.findById(newUser.getId());
            if(tempUser != null && tempUser.isPresent()){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                        "ID is yet in the database.");
            }
        }
        newUser.setId();
        if((newUser.getEmail() != null) && (newUser.getNickname() != null) &&
                (newUser.getPassword() != null)){
            newUser.setPassword(userRepository.convertToSHA256(newUser.getPassword()));
            this.userRepository.save(newUser);
            return newUser;
        }else
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Mandatory fields had not been provided.");
    }
    /**
     *
     * @param id
     * @param user
     * @return
     */
    public  User update(int id, User user){
        if(id > 0){
            Optional<User> tempUser = this.userRepository.findById(id);
            if(tempUser.isPresent()){
                if(user.getNickname() != null)
                    //tempUser.get().setNickname(user.getNickname());
                    if (user.getPassword() != null)
                        user.setPassword(userRepository.convertToSHA256(user.getPassword()));
                System.out.println(user.getId());
                this.userRepository.edit(tempUser.get(), user);
                return tempUser.get();
            }
            else{
                throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "User.id does not exist in database.");
            }
        }else{
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "User.id cannot be negative.");
        }
    }

    /**
     *
     * @param id
     * @return
     */
    public Boolean delete(int id){
        if(id>0) {
            this.userRepository.delete(id);
            return true;
        }else
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "User cannot be deleted.");
    }


    public User login(User user){
        User result;
        if((user.getPassword() != null) && (user.getEmail() != null)){
            String email = user.getEmail();
            String password = userRepository.convertToSHA256(user.getPassword());
            Optional<User> tempUser = userRepository.validateLogin(email, password);
            if(tempUser == null){
                System.out.println(user.getId());
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,
                        "Invalid login.");
            }else {
                result = tempUser.get();
            }
        }
        else
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Mandatory fields had not been provided.");
        return result;
    }


    public  User edit(int id, User user){
        if(id > 0){
            Optional<User> tempUser = this.userRepository.findById(id);
            if(tempUser.isPresent()){
                if(user.getName() != null){

                    tempUser.get().setName(user.getName());
                }
                if(user.getNickname() != null){

                    tempUser.get().setNickname(user.getNickname());
                }
                if(user.getEmail() != null){

                    tempUser.get().setEmail(user.getEmail());
                }
                if (user.getPassword() != null){
                    tempUser.get().setPassword(userRepository.convertToSHA256(user.getPassword()));
                }
                if(user.getRole() != null){

                    tempUser.get().setRole(user.getRole());
                }
                this.userRepository.edit(tempUser.get(), tempUser.get());
                return tempUser.get();
            }
            else{
                throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "User.id does not exist in database.");
            }
        }else{
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "User.id cannot be negative.");
        }
    }

    public  User update(User user){
        if(user.getId() > 0){
            Optional<User> tempUser = this.userRepository.findById(user.getId());
            if(tempUser.isPresent()){
                if(user.getNickname() != null)
                    tempUser.get().setNickname(user.getNickname());
                if (user.getPassword() != null)
                    tempUser.get().setPassword(userRepository.convertToSHA256(user.getPassword()));
                this.userRepository.edit(tempUser.get(), user);
                return user;
            }
            else{
                throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "User.id does not exist in database.");
            }
        }else{
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "User.id cannot be negative.");
        }
    }


}
