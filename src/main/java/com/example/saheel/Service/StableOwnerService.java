package com.example.saheel.Service;

import com.example.saheel.Api.ApiException;
import com.example.saheel.DTO.StableOwnerDTO;
import com.example.saheel.Model.StableOwner;
import com.example.saheel.Model.User;
import com.example.saheel.Repository.StableOwnerRepository;
import com.example.saheel.Repository.StableRepository;
import com.example.saheel.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StableOwnerService {

    private final StableOwnerRepository stableOwnerRepository;
    private final StableRepository stableRepository;
    private final UserRepository userRepository;
    private final EmailService emailService;


    // get StableOwner by ID - Abeer
    public StableOwner getStableOwnerById(Integer stableOwner_Id) {
        StableOwner stableOwner = stableOwnerRepository.findStableOwnerById(stableOwner_Id);
        if (stableOwner == null) {
            throw new ApiException("Error : StableOwner is not found");
        }

        return stableOwner;
    }

    //#1
    //register stableOwner
    public void registerStableOwner(StableOwnerDTO stableOwnerDTO) {
        User user = new User();
        user.setUsername(stableOwnerDTO.getUsername());
        user.setPassword(new BCryptPasswordEncoder().encode(stableOwnerDTO.getPassword()));
        user.setRole("STABLEOWNER");
        user.setFullName(stableOwnerDTO.getFullName());
        user.setAge(stableOwnerDTO.getAge());
        user.setEmail(stableOwnerDTO.getEmail());
        user.setPhoneNumber(stableOwnerDTO.getPhoneNumber());
        userRepository.save(user);

        StableOwner stableOwner = new StableOwner(null, user, null, null);
        stableOwner.setIsApproved(false);
        stableOwner.setUser(user);
        stableOwnerRepository.save(stableOwner);

        emailService.sendEmail(
                stableOwnerDTO.getEmail(),
                "Welcome to Saheel - Your account is under review",
                buildStableWelcomeEmail(stableOwnerDTO.getFullName())
        );
    }

    private String buildStableWelcomeEmail(String name) {
        return "أهلاً بك " + name + " في منصة صهيل!\n"
                + "نحن سعداء بانضمامك إلينا كصاحب اسطبل.\n\n"
                + "سيتم مراجعة طلبك من قبل الإدارة خلال فترة قصيرة.\n"
                + "يرجى الانتظار حتى يتم تفعيل حسابك من قبل فريقنا.\n\n"
                + "Welcome " + name + " to Saheel Platform!\n"
                + "Your stable registration is received and under review.\n"
                + "We will activate your account soon after verification.";
    }


    //update StableOwner - Abeer
    public void updateStableOwner(Integer stableOwner_Id, StableOwnerDTO stableOwnerDTO) {
        StableOwner stableOwner = stableOwnerRepository.findStableOwnerById(stableOwner_Id);
        if (stableOwner == null) {
            throw new RuntimeException("HorseOwner not found");
        }
        User user = stableOwner.getUser();
        user.setUsername(stableOwnerDTO.getUsername());
        user.setPassword(new BCryptPasswordEncoder().encode(stableOwnerDTO.getPassword()));
        user.setFullName(stableOwnerDTO.getFullName());
        user.setEmail(stableOwnerDTO.getEmail());
        user.setAge(stableOwnerDTO.getAge());
        user.setPhoneNumber(stableOwnerDTO.getPhoneNumber());

        // Save changes
        userRepository.save(user);
        stableOwnerRepository.save(stableOwner);
    }

    //delete stable
    public void deleteStableOwner(Integer stableOwner_Id) {
        StableOwner stableOwner = stableOwnerRepository.findStableOwnerById(stableOwner_Id);
        if (stableOwner == null) {
            throw new ApiException(" Error : Stable is not found");
        }
        User user = stableOwner.getUser();

        userRepository.delete(user);
        stableOwnerRepository.delete(stableOwner);
    }


}
