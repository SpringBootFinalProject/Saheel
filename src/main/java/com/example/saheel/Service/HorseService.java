package com.example.saheel.Service;

import com.example.saheel.Api.ApiException;
import com.example.saheel.Model.Horse;
import com.example.saheel.Model.HorseOwner;
import com.example.saheel.Model.User;
import com.example.saheel.Repository.HorseOwnerRepository;
import com.example.saheel.Repository.HorseRepository;
import com.example.saheel.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HorseService {
    private final HorseRepository horseRepository;
    private final UserRepository userRepository;
    private final HorseOwnerRepository horseOwnerRepository;

    public List<Horse> getOwnerHorses(Integer horseOwnerId) {
        // Get the horse owner and check if it's in the database.
        HorseOwner horseOwner = getHorseOwnerOrThrow(horseOwnerId);
        return horseRepository.findAll();
    }

    public void addHorseByOwner(Integer horseOwnerId, Horse horse){
        // Get the horse owner and check if it's in the database.
        HorseOwner horseOwner = getHorseOwnerOrThrow(horseOwnerId);

        //Add the horse owner to the horse and save the object in the database.
        horse.setHorseOwner(horseOwner);
        horseRepository.save(horse);
    }

    public void updateHorse(Integer horseOwnerId, Integer horseId, Horse horse){
        // Get the horse and check if it's in the database.
        Horse oldHorse = getHorseOrThrow(horseId);

        // Get the horse owner and check if it's in the database.
        HorseOwner horseOwner = getHorseOwnerOrThrow(horseOwnerId);

        // Check if the horse belongs to the owner.
        checkIfHorseBelongsToOwner(horse,horseOwner);

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

    public void deleteHorse(Integer horseOwnerId, Integer horseId){
        // Get the horse and check if it's in the database.
        Horse horse = getHorseOrThrow(horseId);

        // Get the horse owner and check if it's in the database.
        HorseOwner horseOwner = getHorseOwnerOrThrow(horseOwnerId);

        // Check if the horse belongs to the owner.
        checkIfHorseBelongsToOwner(horse,horseOwner);
        //Delete
        horseRepository.delete(horse);
    }

    public HorseOwner getHorseOwnerOrThrow(Integer horseOwnerId){
        // Get the horse owner and check if it's in the database.
        HorseOwner horseOwner = horseOwnerRepository.findHorseOwnerById(horseOwnerId);
        if(horseOwner == null) throw new ApiException("Horse owner not found.");
        return horseOwner;
    }

    public Horse getHorseOrThrow(Integer horseId){
        // Get the horse and check if it's in the database.
        Horse horse = horseRepository.findHorseById(horseId);
        if(horse == null) throw new ApiException("Horse not found.");
        return horse;
    }

    public void checkIfHorseBelongsToOwner(Horse horse, HorseOwner horseOwner){
        if(horse.getHorseOwner() != horseOwner) throw new ApiException("Horse dose not belong to the owner.");
    }

}