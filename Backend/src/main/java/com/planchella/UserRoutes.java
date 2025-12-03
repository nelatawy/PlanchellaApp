package com.planchella;

import com.planchella.DTOs.UserDTO;

import com.planchella.domain.User;
import com.planchella.mappers.UserMapper;
import org.hibernate.cfg.Configuration;
import org.springframework.web.bind.annotation.*;
import com.planchella.repositories.users.IUserRepository;
import com.planchella.repositories.users.DBUserRepository;
import com.planchella.repositories.users.MockUserRepository;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:4200")
public class UserRoutes {

    IUserRepository userRepo;
    public UserRoutes() {
        this.userRepo = new DBUserRepository();
    }

    @GetMapping("/{user_id}")
    public UserDTO getUser(@PathVariable Long user_id){
        User user = this.userRepo.getUser(user_id);
        return UserMapper.domainToDTO(user);
    }

    @PatchMapping("/{user_id}")
    public void updateUser(@PathVariable Long user_id, @RequestBody UserDTO data) {
        User newUserData = UserMapper.DTOtoDomain(data);
        User user = this.userRepo.getUser(user_id);
        user.updateByDelta(newUserData);
        this.userRepo.saveUser(user);
        System.out.println(data.picUrl);
        System.out.println(data.accountUrl);
        System.out.println(data.name);
    }

    @PutMapping
    public void addUser(@RequestBody UserDTO data) {
        User user = UserMapper.DTOtoDomain(data);
        this.userRepo.saveUser(user);
    }

    @DeleteMapping("/{user_id}")
    public void deleteUser(@PathVariable Long user_id) {
        this.userRepo.deleteUser(user_id);
    }


}
