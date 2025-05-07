package com.example.saheel.Service;

import com.example.saheel.DTO.HorseOwnerDTO;
import com.example.saheel.Model.Horse;
import com.example.saheel.Model.HorseOwner;
import com.example.saheel.Model.User;
import com.example.saheel.Repository.HorseOwnerRepository;
import com.example.saheel.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HorseOwnerService {
    private final HorseOwnerRepository horseOwnerRepository;
    private final UserRepository userRepository;

    // get all  Horses
    public List<HorseOwner> getAllHorses() {
        return horseOwnerRepository.findAll();
    }

    // register HorseOwner
    public void registerHorseOwner(HorseOwnerDTO horseOwnerDTO) {
        User user = new User();
        user.setUsername(horseOwnerDTO.getUsername());
        user.setPassword(new BCryptPasswordEncoder().encode(horseOwnerDTO.getPassword()));
        user.setRole("horseowner");
        user.setFullName(horseOwnerDTO.getFullName());
        user.setAge(horseOwnerDTO.getAge());
        user.setEmail(horseOwnerDTO.getEmail());

        userRepository.save(user);
        HorseOwner owner = new HorseOwner();
        owner.setUser(user);
        horseOwnerRepository.save(owner);
    }

    public void updateHorseOwner(Integer id, HorseOwnerDTO horseOwnerDTO) {
        HorseOwner horseOwner = horseOwnerRepository.findHorseOwnerById(id);
        if( horseOwner == null){
            throw new RuntimeException("HorseOwner not found");
        }

        User user = horseOwner.getUser();
        user.setUsername(horseOwnerDTO.getUsername());
        user.setPassword(new BCryptPasswordEncoder().encode(horseOwnerDTO.getPassword()));
        user.setFullName(horseOwnerDTO.getFullName());
        user.setEmail(horseOwnerDTO.getEmail());
        user.setAge(horseOwnerDTO.getAge());

        userRepository.save(user);
        horseOwnerRepository.save(horseOwner);
    }

    public void deleteHorseOwner(Integer id) {
        HorseOwner horseOwner = horseOwnerRepository.findHorseOwnerById(id);
        if( horseOwner == null){
            throw new RuntimeException("HorseOwner not found");
        }
        User user = horseOwner.getUser();

        horseOwnerRepository.delete(horseOwner);
        userRepository.delete(user);
    }


}
