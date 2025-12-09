package com.planchella;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.planchella.DTOs.UserDTO;
import com.planchella.domain.User;
import com.planchella.mappers.UserMapper;
import com.planchella.repositories.users.DBUserRepository;
import com.planchella.repositories.users.IUserRepository;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:4200")
public class UserRoutes {

    IUserRepository userRepo;

    public UserRoutes() {
        this.userRepo = new DBUserRepository();
    }

    @GetMapping("/{user_id}")
    public UserDTO getUser(@PathVariable Long user_id) {
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
