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
    private final EmailService emailService;


    // get all  Horses
    public List<HorseOwner> getAllHorses() {
        return horseOwnerRepository.findAll();
    }

    // ( #9 of 50 endpoints )
    // register HorseOwner
    public void registerHorseOwner(HorseOwnerDTO horseOwnerDTO) {
        if (userRepository.existsByUsername(horseOwnerDTO.getUsername())) {
            throw new ApiException("This Username is already in use");
        }

        if (userRepository.existsByEmail(horseOwnerDTO.getEmail())) {
            throw new ApiException("This Email is already in use");
        }

        if (userRepository.existsByPhoneNumber(horseOwnerDTO.getPhoneNumber())) {
            throw new ApiException("This Phone Number is already in use");
        }
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
        sendWelcomeEmail(user);
        horseOwnerRepository.save(owner);
    }

    // Update horse owner
    public void updateHorseOwner(Integer id, HorseOwnerDTO horseOwnerDTO) {
        // Get the horse owner and check if it's in the database.
        HorseOwner horseOwner = horseOwnerRepository.findHorseOwnerById(id);
        if (horseOwner == null) {
            throw new ApiException("HorseOwner not found");
        }

        if (userRepository.existsByEmail(horseOwnerDTO.getUsername())) {
            throw new ApiException("This Username is already in use");
        }
        if (userRepository.existsByEmail(horseOwnerDTO.getEmail())) {
            throw new ApiException("This email is already in use");
        }
        if (userRepository.existsByEmail(horseOwnerDTO.getPhoneNumber())) {
            throw new ApiException("This Phone Number is already in use");
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

    private void sendWelcomeEmail(User user) {
        String to = user.getEmail();
        String subject = "Welcome to Saheel / مرحبًا بك في منصة صهيل\"";
        String body = "أهلاً بك " + user.getFullName() + " في منصة صهيل!\n"
                + "نتمنى لك تجربة ممتعة ومليئة بالإنجازات في عالم الفروسية 🐎\n\n"

                + "Dear " + user.getFullName() + ",\n"
                + "Welcome to Saheel Platform!\n"
                + "We hope you enjoy a great experience full of achievements in the world of horsemanship 🐎";

        emailService.sendEmail(to, subject, body);
    }
}
