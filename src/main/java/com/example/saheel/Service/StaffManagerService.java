package com.example.saheel.Service;

import com.example.saheel.Api.ApiException;
import com.example.saheel.Model.*;
import com.example.saheel.Repository.*;
import com.twilio.rest.voice.v1.dialingpermissions.CountryReader;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StaffManagerService {

    private final TrainerRepository trainerRepository;
    private final BreederRepository breederRepository;
    private final VeterinaryRepository veterinaryRepository;
    private final StableRepository stableRepository;
    private final HorseRepository horseRepository;
    private final MembershipRepository membershipRepository;
    private final VeterinaryVisitRepository veterinaryVisitRepository;
    private final UserRepository userRepository;
    private final JavaMailSender mailSender;
    private final CourseRepository courseRepository;
    private final StableOwnerRepository stableOwnerRepository;


    //( #30 of 50 endpoints)
    //move breeder To Another Stable -Abeer
    public void moveBreederToAnotherStable(Integer stableOwner_Id, Integer breeder_Id, Integer stable_Id) {

        Breeder breeder = breederRepository.findBreederById(breeder_Id);
        if (breeder == null) {
            throw new ApiException("Error Breeder is not found");
        }

        if (!breeder.getStable().getStableOwner().getId().equals(stableOwner_Id))
            throw new ApiException("The trainer does not belongs to the stable owner.");

        Stable newStable = stableRepository.findStableById(stable_Id);
        if (newStable == null) {
            throw new ApiException("Target stable not found");
        }

        if (breeder.getStable() != null && breeder.getStable().getId().equals(stable_Id)) {
            throw new ApiException("Error: This veterinary is already assigned to this stable");
        }
        // Check that this stable belongs to the registered owner.
        if (!newStable.getStableOwner().getId().equals(stableOwner_Id)) {
            throw new ApiException("Error : You are not the owner of this stable");
        }

        List<Horse> horses = horseRepository.findHorsesByBreederId(breeder_Id);
        if (!horses.isEmpty()) {
            throw new ApiException(" Cannot move,because breeder have horses .");
        }

        breeder.setStable(newStable);
        breederRepository.save(breeder);
    }

    //( #32 of 50 endpoints)
    //move Trainer To Another Stable -Abeer
    public void moveTrainerToAnotherStable(Integer stableOwner_Id, Integer trainer_Id, Integer stable_Id) {

        Trainer trainer = trainerRepository.findTrainerById(trainer_Id);
        if (trainer == null) {
            throw new ApiException("Error Trainer is not found");
        }
        if (!trainer.getStable().getStableOwner().getId().equals(stableOwner_Id))
            throw new ApiException("The trainer does not belongs to the stable owner.");
        Stable newStable = stableRepository.findStableById(stable_Id);
        if (newStable == null) {
            throw new ApiException("Target stable not found");
        }
        if (trainer.getStable() != null && trainer.getStable().getId().equals(stable_Id)) {
            throw new ApiException("Error: This veterinary is already assigned to this stable");
        }
        // Check that this stable belongs to the registered owner.
        if (!newStable.getStableOwner().getId().equals(stableOwner_Id)) {
            throw new ApiException("Error : You are not the owner of this stable");
        }
        // Check if the trainer has courses at his current stable.
        if (!courseRepository.findCourseByTrainerAndDateBetween(trainer, LocalDateTime.now(), LocalDateTime.now().plusYears(5)).isEmpty())
            throw new ApiException("Trainer has a courses at his current stable.");

        trainer.setStable(newStable);
        trainerRepository.save(trainer);
    }

    //( #33 of 50 endpoints)
    //move veterinary To Another Stable -Abeer
    public void moveVeterinaryToAnotherStable(Integer stableOwner_Id, Integer veterinary_Id, Integer stable_Id) {
        Stable newStable = stableRepository.findStableById(stable_Id);
        if (newStable == null) {
            throw new ApiException("Target stable not found");
        }
        // Check that this stable belongs to the registered owner.
        if (!newStable.getStableOwner().getId().equals(stableOwner_Id)) {
            throw new ApiException("Error : You are not the owner of this stable");
        }

        Veterinary veterinary = veterinaryRepository.findVeterinaryById(veterinary_Id);
        if (veterinary == null) {
            throw new ApiException("Error veterinary is not found");
        }
        if (!veterinary.getStable().getStableOwner().getId().equals(stableOwner_Id))
            throw new ApiException("The veterinary does not belongs to the stable owner.");

        if (veterinary.getStable() != null && veterinary.getStable().getId().equals(stable_Id)) {
            throw new ApiException("Error: This veterinary is already assigned to this stable");
        }


        List<Horse> horses = horseRepository.findHorsesByBreederId(veterinary_Id);
        if (!horses.isEmpty()) {
            throw new ApiException(" Cannot move,because veterinary have horses .");
        }

        veterinary.setStable(newStable);
        veterinaryRepository.save(veterinary);

    }

    //( #31 of 50 endpoints)
//to assignBreederToHorse -Abeer
    public void assignBreederToHorse(Integer stableOwner_Id, Integer breeder_Id, Integer horse_Id) {

        Horse horse = horseRepository.findHorseById(horse_Id);
        if (horse == null) {
            throw new ApiException("Error: Horse is not found");
        }

        Membership membership = membershipRepository.findByHorsesIdAndIsActiveTrue(horse_Id);
        if (membership == null) {
            throw new ApiException("Error: This horse does not have an active membership");
        }

        if (!membership.getStable().getStableOwner().getId().equals(stableOwner_Id)) {
            throw new ApiException("Error: You are not the owner of the stable this horse belongs to");
        }

        Breeder breeder = breederRepository.findBreederById(breeder_Id);
        if (breeder == null) {
            throw new ApiException("Error: Breeder not found");
        }

        if (!breeder.getStable().getId().equals(membership.getStable().getId())) {
            throw new ApiException("Error: Breeder and horse must belong to the same stable");
        }

        if (horse.getBreeder() != null && horse.getBreeder().getId().equals(breeder_Id)) {
            throw new ApiException("Error: This horse is already assigned to this breeder");
        }


        int count = horseRepository.countByBreederId(breeder_Id);
        if (count >= 2) {
            throw new ApiException("Error: each breeder only cares for 2 horses");
        }


        if (!breeder.getIsActive()) {
            breeder.setIsActive(true);
        }
        breederRepository.save(breeder);

        horse.setBreeder(breeder);
        horseRepository.save(horse);

    }

    //( #34 of 50 endpoints)
//assign veterinary it horse-Abeer
    public void assignVeterinaryToHorse(Integer stableOwner_Id, Integer veterinary_Id, Integer horse_Id) {
       // Membership membership = membershipRepository.findMembershipById(horse_Id);
//        if (membership == null) {
//            throw new ApiException("Error: This horse does not have an active membership");
//        }

        Horse horse = horseRepository.findHorseById(horse_Id);
        if (horse == null) {
            throw new ApiException("Error: Horse is not found");
        }

        Veterinary veterinary = veterinaryRepository.findVeterinaryById(veterinary_Id);
        if (veterinary == null) {
            throw new ApiException("Error: veterinary is not found");
        }

//        if (!membership.getStable().getStableOwner().getId().equals(stableOwner_Id)) {
//            throw new ApiException("Error: You are not the owner of the stable this horse belongs to");
//        }

        if (horse.getVeterinary() != null && horse.getVeterinary().getId().equals(veterinary_Id)) {
            throw new ApiException("Error: This horse is already assigned to this veterinary");
        }

        int count = horseRepository.countByVeterinaryId(veterinary_Id);
        if (count >= 2) {
            throw new ApiException("Error: each veterinarian only cares for 2 horses");
        }

        if (!veterinary.getIsActive()) {
            veterinary.setIsActive(true);
        }
        veterinaryRepository.save(veterinary);

        horse.setVeterinary(veterinary);
        horseRepository.save(horse);
    }


    //( #36 of 50 endpoints)
    //get list of hours by veterinary
    public List<Horse> getHorsesByVeterinary(Integer veterinary_Id) {
        List<Horse> horses = horseRepository.findHorsesByVeterinaryId(veterinary_Id);
        if (horses.isEmpty()) {
            throw new ApiException("Error: no horses to thes veterinary");
        }

        return horses;
    }

    //( #40 of 50 endpoints)
    //get list of hours by breeder
    public List<Horse> getHorsesByBreeder(Integer breeder_Id) {
        List<Horse> horses = horseRepository.findHorsesByBreederId(breeder_Id);
        if (horses.isEmpty()) {
            throw new ApiException("Error: no horses to thes breder");
        }

        return horses;
    }

    public String requestVisitToVet(Integer horseOwner_Id, Integer horse_Id, Integer membershipId, LocalDateTime dateTime) {
        // Get the membership and check if it's in the database.
        Membership membership = membershipRepository.findMembershipById(membershipId);
        if (membership == null) throw new ApiException("Membership not found.");

        Horse horse = horseRepository.findHorseById(horse_Id);
        if (horse == null) {
            throw new ApiException("Error: Horse is not found");
        }

        if (horse.getHorseOwner() == null) {
            throw new ApiException("Error: This horse does not have an owner.");
        }

        if (!horse.getHorseOwner().getId().equals(horseOwner_Id)) {
            throw new ApiException("Error: You do not own this horse");
        }

        if (horse.getVeterinary() == null) throw new ApiException("Horse does not have an assigned veterinary.");

        Veterinary veterinary = horse.getVeterinary();
        if (veterinaryVisitRepository.findVeterinaryVisitByVeterinaryAndVisitDateTime(veterinary, dateTime) != null)
            throw new ApiException("The horse veterinary is busy at " + dateTime);


        VeterinaryVisit visit = new VeterinaryVisit();
        visit.setHorse(horse);
        visit.setVeterinary(veterinary);
        visit.setReason("Request a medical examination to enter the stable");
        visit.setVisitDateTime(dateTime);
        visit.setIsCompleted(false);

        veterinaryVisitRepository.save(visit);
        return "A veterinary visit was successfully performed for the horse: " + horse.getName();
    }

    //( #42 of 50 endpoints)
    //mark Visit AsCompleted
    public void markHorseAsFit(Integer stableOwner_Id, Integer visit_Id, String medicalReport) {
        VeterinaryVisit visit = veterinaryVisitRepository.findVeterinaryVisitById(visit_Id);
        if (visit == null) {
            throw new ApiException("The visit does not exist");
        }
        StableOwner stableOwner = stableOwnerRepository.findStableOwnerById(stableOwner_Id);
        if (stableOwner == null) {
            throw new ApiException("The visit does not exist");
        }

        if (visit.getIsCompleted()) {
            throw new ApiException("The visit is already completed.");
        }

        Horse horse = visit.getHorse();
        if (horse == null) {
            throw new ApiException("Error: The horse does not exist");
        }
        visit.setIsCompleted(true);
        horse.setIsMedicallyFit(true);
        visit.setMedicalReport(medicalReport);
        visit.setVisitDateTime(LocalDateTime.now());


        veterinaryVisitRepository.save(visit);
        horseRepository.save(horse);

        HorseOwner owner = horse.getHorseOwner();
        if (owner != null && owner.getUser() != null) {
            String subject = "تقرير الفحص البيطري - Veterinary Report: " + horse.getName();
            String result = "الحصان لائق طبيًا - The horse is medically fit";
            String formattedDate = visit.getVisitDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
            String body = "السلام عليكم " + owner.getUser().getFullName() + ",\n\n"
                    + "تم فحص حصانك " + horse.getName() + ".\n"
                    + "Your horse " + horse.getName() + " has been examined.\n\n"
                    + " النتيجة / Result: " + result + "\n"
                    + " وقت الزيارة / Visit Date: " + formattedDate + "\n"
                    + " تقرير الطبيب / Vet Report:\n" + medicalReport + "\n\n"
                    + "شكراً لاستخدامك منصة صهيل.\n"
                    + "Thank you for using Saheel platform";
            sendEmailToUser(owner.getUser().getId(), subject, body, "saheelproject@gmail.com");
        }
    }

    public void markHorseAsUnFit(Integer stableOwner_Id, Integer visit_Id, String medicalReport) {
        VeterinaryVisit visit = veterinaryVisitRepository.findVeterinaryVisitById(visit_Id);
        if (visit == null) {
            throw new ApiException("The visit does not exist");
        }
        StableOwner stableOwner = stableOwnerRepository.findStableOwnerById(stableOwner_Id);
        if (stableOwner == null) {
            throw new ApiException("The visit does not exist");
        }

        if (visit.getIsCompleted()) {
            throw new ApiException("The visit is already completed.");
        }

        Horse horse = visit.getHorse();
        if (horse == null) {
            throw new ApiException("Error: The horse does not exist");
        }
        visit.setIsCompleted(true);
        horse.setIsMedicallyFit(false);
        visit.setMedicalReport(medicalReport);
        visit.setVisitDateTime(LocalDateTime.now());


        veterinaryVisitRepository.save(visit);
        horseRepository.save(horse);

        HorseOwner owner = horse.getHorseOwner();
        if (owner != null && owner.getUser() != null) {
            String subject = "تقرير الفحص البيطري - Veterinary Report: " + horse.getName();
            String result = "الحصان غير لائق طبيًا - The horse is not medically fit";
            String formattedDate = visit.getVisitDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

            String body = "السلام عليكم " + owner.getUser().getFullName() + ",\n\n"
                    + "تم فحص حصانك " + horse.getName() + ".\n"
                    + "Your horse " + horse.getName() + " has been examined.\n\n"
                    + "النتيجة / Result: " + result + "\n"
                    + "وقت الزيارة / Visit Date: " + formattedDate + "\n"
                    + "تقرير الطبيب / Vet Report:\n" + medicalReport + "\n\n"
                    + "شكراً لاستخدامك منصة صهيل.\n"
                    + "Thank you for using Saheel platform.";
            sendEmailToUser(owner.getUser().getId(), subject, body, "saheelproject@gmail.com");
        }
    }


    public void sendEmailToUser(Integer userId, String subject, String body, String from) {
        User user = userRepository.findUserById(userId);
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(user.getEmail());
        message.setSubject(subject);
        message.setText(body);
        message.setFrom(from);
        mailSender.send(message);
    }


}