package com.example.saheel.Service;

import com.example.saheel.Api.ApiException;
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

    // ( #9 of 50 endpoints )
    // register HorseOwner
    public void registerHorseOwner(HorseOwnerDTO horseOwnerDTO) {
        // Create and configure the user account.
        User user = new User();
        user.setUsername(horseOwnerDTO.getUsername());
        user.setPassword(new BCryptPasswordEncoder().encode(horseOwnerDTO.getPassword()));
        user.setRole("HORSEOWNER");
        user.setPhoneNumber(horseOwnerDTO.getPhoneNumber());
        user.setFullName(horseOwnerDTO.getFullName());
        user.setAge(horseOwnerDTO.getAge());
        user.setEmail(horseOwnerDTO.getEmail());

        // Save the user and link it to the new horse owner.
        userRepository.save(user);

        HorseOwner owner = new HorseOwner();
        owner.setUser(user);
        horseOwnerRepository.save(owner);
    }

    // Update horse owner
    public void updateHorseOwner(Integer id, HorseOwnerDTO horseOwnerDTO) {
        // Get the horse owner and check if it's in the database.
        HorseOwner horseOwner = horseOwnerRepository.findHorseOwnerById(id);
        if (horseOwner == null) {
            throw new ApiException("HorseOwner not found");
        }

        // Update user information.
        User user = horseOwner.getUser();
        user.setUsername(horseOwnerDTO.getUsername());
        user.setPassword(new BCryptPasswordEncoder().encode(horseOwnerDTO.getPassword()));
        user.setFullName(horseOwnerDTO.getFullName());
        user.setEmail(horseOwnerDTO.getEmail());
        user.setAge(horseOwnerDTO.getAge());

        // Save changes
        userRepository.save(user);
        horseOwnerRepository.save(horseOwner);
    }

    // Delete a horse owner
    public void deleteHorseOwner(Integer id) {
        // Get the horse owner and check if it's in the database.
        HorseOwner horseOwner = horseOwnerRepository.findHorseOwnerById(id);
        if (horseOwner == null) {
            throw new ApiException("HorseOwner not found");
        }
        User user = horseOwner.getUser();

        // Delete the user and the horse owner.
        horseOwnerRepository.delete(horseOwner);
        userRepository.delete(user);
    }
}
