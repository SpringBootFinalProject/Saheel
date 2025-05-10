package com.example.saheel.Service;

import com.example.saheel.Api.ApiException;
import com.example.saheel.Model.*;
import com.example.saheel.Repository.StableOwnerRepository;
import com.example.saheel.Repository.StableRepository;
import com.example.saheel.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StableService {

    private final StableRepository stableRepository;

    private final UserRepository userRepository;
    private final StableOwnerRepository stableOwnerRepository;
    private final JavaMailSender mailSender;

    public List<Stable> getOwnerHorses() {
        return stableRepository.findAll();
    }

    //get stable by ID - Abeer
    public Stable getStableById(Integer stable_Id) {
        Stable stable = stableRepository.findStableById(stable_Id);
        if (stable == null) {
            throw new ApiException("Error : stable not found");
        }
        return stable;
    }

     //( #29 of 50 endpoints)
    //add stable to stable owner- Abeer
    public void addStable(User user,Stable stable){
        StableOwner stableOwner = stableOwnerRepository.findStableOwnerByUser(user);
        if (stableOwner == null ){
            throw new ApiException("Error : Stable owner is not fond");
        }
        stable.setStableOwner(stableOwner);
        stableRepository.save(stable);
        String subject = "تأكيد تسجيل الإسطبل وقواعد التشغيل";
        String body = "مرحبا " + user.getFullName() + ",\n\nتم تسجيل إسطبلك بنجاح، وهذه هي القواعد الخاصة:\n" +
                "1-يُسمح بوجود حد أقصى قدره 5 خيالة في نفس التوقيت داخل الإسطبل.\n" +
                "2-ارتداء الخوذة والسترة الواقية إلزامي أثناء التدريب.\n" +
                "3-يمنع التصوير داخل الإسطبل دون إذن رسمي من الإدارة.\n" +
                "4-إضافة المدربين، المربين، والبيطريين تتم حصريًا من قبل مالك الإسطبل.\n" +
                "5-لا يُقبل اشتراك أي حصان بدون إرفاق شهادة صحية معتمدة من طبيب بيطري.\n" +
                "6-يجب أن يكون الحصان مسجلاً باسم مالك حالي ومرفقًا بسجل طبي حديث.\n" +
                "7-لا يمكن للخيال الانضمام إلى كورسات التدريب إلا بعد إنشاء حساب وتفعيله من قبل الإدارة.\n" +
                "8-الاشتراك شهري ويتم تجديده تلقائيًا ما لم يُلغَ قبل انتهاء المدة بـ 3 أيام.\n" +
                "9- يحق للإدارة تعليق عضوية أي خيال أو صاحب حصان يخالف شروط الاستخدام.\n" +
                "10- نقل الحصان من إسطبل إلى آخر يتطلب موافقة الطرفين وتحديث بيانات الاشتراك.\n" +
                "11-في الحالات الطارئة، يتم الرجوع إلى الطبيب البيطري المعتمد لدى الإسطبل.!";
        String from = "saheelproject@gmail.com";

        sendEmailToUser(user.getId(),subject,body,from);
    }

    //update stable - Abeer
    public void updateStable(Integer stableOwner_Id,Integer stable_Id, Stable stable) {
        StableOwner oldStableOwner = stableOwnerRepository.findStableOwnerById(stableOwner_Id);
        if (oldStableOwner == null) {
            throw new ApiException("Error: StableOwner not found");
        }

        Stable oldStable = stableRepository.findStableById(stable_Id);
        if (oldStable == null ){
            throw new ApiException("Error : stable is not found");
        }
        oldStable.setName(stable.getName());
        oldStable.setCapacity(stable.getCapacity());
        stableRepository.save(stable);
    }

    //delete stable
    public void deleteStable(Integer stableOwner_Id, Integer stable_Id) {
        StableOwner stableOwner = stableOwnerRepository.findStableOwnerById(stableOwner_Id);
        if (stableOwner == null) {
            throw new ApiException("Error: StableOwner not found");
        }

        Stable stable = stableRepository.findStableById(stable_Id);
        if (stable == null) {
            throw new ApiException("Error : Stable is not found");
        }
        User user = stableOwner.getUser();

        userRepository.delete(user);
        stableRepository.delete(stable);
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

    public List<Stable> getAvailableStables(){
        // Get all the stables
        List<Stable> stables = stableRepository.findAll();

        // List to store the available stables.
        List<Stable> availableStables = new ArrayList<>();

        // Get the available stables.
        for (Stable stable : stables) {
            int countOfHorses = 0;
            for (Membership membership : stable.getMemberships()) countOfHorses += membership.getHorses().size();
            if(countOfHorses < stable.getCapacity()) availableStables.add(stable);
        }
        return availableStables;
    }

}
