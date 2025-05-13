package com.example.saheel.Service;

import com.example.saheel.Api.ApiException;
import com.example.saheel.Model.Horse;
import com.example.saheel.Model.HorseOwner;
import com.example.saheel.Model.Membership;
import com.example.saheel.Model.User;
import com.example.saheel.Repository.HorseOwnerRepository;
import com.example.saheel.Repository.HorseRepository;
import com.example.saheel.Repository.MembershipRepository;
import com.example.saheel.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class    HorseService {
    private final HorseRepository horseRepository;
    private final UserRepository userRepository;
    private final HorseOwnerRepository horseOwnerRepository;
    private final MembershipRepository membershipRepository;

    //14
    public List<Horse> getOwnerHorses(Integer horseOwnerId) {
        // Get the horse owner and check if it's in the database.
        HorseOwner horseOwner = getHorseOwnerOrThrow(horseOwnerId);
        if (horseOwner == null) throw new ApiException("Horses Owner not found.");

        return horseRepository.findByHorseOwner(horseOwner);
    }

    public void addHorseByOwner(Integer horseOwnerId, Horse horse) {
        // Get the horse owner and check if it's in the database.
        HorseOwner horseOwner = getHorseOwnerOrThrow(horseOwnerId);

        //Add the horse owner to the horse and save the object in the database.
        horse.setHorseOwner(horseOwner);
        horseRepository.save(horse);
    }


    // ( #17 of 50 endpoints )
    public void assignHorseToMembership(Integer horseId, Integer ownerId) {
        HorseOwner owner = horseOwnerRepository.findHorseOwnerById(ownerId);
        if (owner == null) {
            throw new ApiException("Horse owner not found");
        }

        Horse horse = getHorseOrThrow(horseId);
        if (horse == null) {
            throw new ApiException("Horse not found");
        }

//        // Check if the horse is medically fit.
//        if (!horse.getIsMedicallyFit().equals(false)) {
//            throw new ApiException("Horse is Medically unfit");
//        }

        Membership membership = membershipRepository.findByHorseOwnerAndIsActive(owner, true);
        if (membership == null) {
            throw new ApiException("Owner has no active membership");
        }

        if(!membership.getHorseOwner().equals(owner)) throw new ApiException("The membership does not belongs to the owner.");

        int maxHorses = membership.getMembershipType().equalsIgnoreCase("saheel") ? 3 : 6;

        int currentCount = horseRepository.countByMembership(membership);
        System.out.println("Current count for owner " + owner.getId() + " = " + currentCount);
        if (currentCount >= maxHorses) {
            throw new ApiException("Maximum number of horses reached for this membership");
        }


        horse.setHorseOwner(owner);
        horse.setMembership(membership);
        horseRepository.save(horse);
    }


    public void updateHorse(Integer horseOwnerId, Integer horseId, Horse horse) {
        // Get the horse and check if it's in the database.
        Horse oldHorse = getHorseOrThrow(horseId);

        // Get the horse owner and check if it's in the database.
        HorseOwner horseOwner = getHorseOwnerOrThrow(horseOwnerId);

        // Check if the horse belongs to the owner.
        checkIfHorseBelongsToOwner(horse, horseOwner);

        //Update the horse.
        oldHorse.setName(horse.getName());
        oldHorse.setAge(horse.getAge());
        oldHorse.setWightInKG(horse.getWightInKG());
        oldHorse.setPassportNumber(horse.getPassportNumber());
        oldHorse.setHeightInCM(horse.getHeightInCM());
        oldHorse.setGender(horse.getGender());

        // Save
        horseRepository.save(oldHorse);
    }

    public void deleteHorse(Integer horseOwnerId, Integer horseId) {
        // Get the horse and check if it's in the database.
        Horse horse = getHorseOrThrow(horseId);

        // Get the horse owner and check if it's in the database.
        HorseOwner horseOwner = getHorseOwnerOrThrow(horseOwnerId);

        // Check if the horse belongs to the owner.
        checkIfHorseBelongsToOwner(horse, horseOwner);
        //Delete
        horseRepository.delete(horse);
    }

    public HorseOwner getHorseOwnerOrThrow(Integer horseOwnerId) {
        // Get the horse owner and check if it's in the database.
        HorseOwner horseOwner = horseOwnerRepository.findHorseOwnerById(horseOwnerId);
        if (horseOwner == null) throw new ApiException("Horse owner not found.");
        return horseOwner;
    }

    public Horse getHorseOrThrow(Integer horseId) {
        // Get the horse and check if it's in the database.
        Horse horse = horseRepository.findHorseById(horseId);
        if (horse == null) throw new ApiException("Horse not found.");
        return horse;
    }

    public void checkIfHorseBelongsToOwner(Horse horse, HorseOwner horseOwner) {
        if (horse.getHorseOwner() != horseOwner) throw new ApiException("Horse dose not belong to the owner.");
    }


    // ( #19 of 50 endpoints)
    // This method gets all horses for one specific owner
    // that do not have a membership.
    public List<Horse> getHorsesWithoutMembershipByOwner(Integer ownerId) {
        return horseRepository.findByHorseOwnerIdAndMembershipIsNull(ownerId);
    }

    // ( #20 of 50 endpoints)
    // This method sends (gifts) a horse to a new owner.
    // The horse must have an active membership to be gifted.
    public void giftHorseToOwner(Integer oldOwnerId, Integer horseId, Integer newOwnerId) {
        // Get the old owner
        HorseOwner horseOwner = horseOwnerRepository.findHorseOwnerById(oldOwnerId);
        if (horseOwner == null) throw new ApiException("Old horse owner nto found.");

        Horse horse = horseRepository.findHorseById(horseId);
        if (horse == null) {
            throw new ApiException("Horse not found");
        }

        if (!horseOwner.getHorses().contains(horse)) throw new ApiException("The horse does not belongs to the owner.");

        if (horse.getMembership() == null || !horse.getMembership().getIsActive()) {
            throw new RuntimeException("Only horses with active memberships can be gifted.");
        }

        HorseOwner newOwner = horseOwnerRepository.findHorseOwnerById(newOwnerId);
        if (newOwner == null) {
            throw new ApiException(" New Horse owner not found");
        }


        horse.setHorseOwner(newOwner);
        horseRepository.save(horse);
    }

}