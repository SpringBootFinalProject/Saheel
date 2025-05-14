package com.example.saheel.Service;

import com.example.saheel.Api.ApiException;
import com.example.saheel.DTO.HorseOwnerDTO;
import com.example.saheel.Model.*;
import com.example.saheel.Repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final StableOwnerRepository stableOwnerRepository;
    private final UserRepository userRepository;
    private final EmailService emailService;
    private final MembershipRepository membershipRepository;
    private final HorseOwnerRepository horseOwnerRepository;
    private final CustomerRepository customerRepository;


    // ( #18 of 50 endpoints)
    // This method finds the horse owner who owns the most horses.
    // It can return more than one owner if they have the same number of horses.
    public List<HorseOwner> getOwnersWithMostHorses(Integer adminId) {
        User admin = userRepository.findUserByIdAndRole(adminId, "ADMIN");
        if (admin == null) {
            throw new ApiException("Only admins can perform this action.");
        }
        return horseOwnerRepository.findHorseOwnersWithMostHorses();
    }

    // ( #25 of 50 endpoints )
    // This method sends a welcome email to all customers in the system.
    public void sendWelcomeEmailsToAllCustomer(Integer adminId) {
        User admin = userRepository.findUserByIdAndRole(adminId, "ADMIN");
        if (admin == null) {
            throw new ApiException("Only admins can perform this action.");
        }
        List<User> customers = userRepository.findAllByRole("CUSTOMER");
        for (User customer : customers) {
            sendWelcomeEmail(customer);
        }
    }

    // ( #26 of 50 endpoints )
    // This method sends a welcome email to all horse owners in the system.
    public void sendWelcomeEmailsToAllHorseOwner(Integer adminId) {
        User admin = userRepository.findUserByIdAndRole(adminId, "ADMIN");
        if (admin == null) {
            throw new ApiException("Only admins can perform this action.");
        }
        List<User> horseowners = userRepository.findAllByRole("HORSEOWNER");
        for (User horseowner : horseowners) {
            sendWelcomeEmail(horseowner);
        }
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

    // ( #27 of 50 endpoints )
    // This method allows an admin to approve a stable owner account.
    public void approveStableOwner(Integer adminId, Integer stableOwnerId) {
        User admin = userRepository.findUserByIdAndRole(adminId, "ADMIN");
        if (admin == null) {
            throw new ApiException("Only admins can perform this action.");
        }

        StableOwner owner = stableOwnerRepository.findStableOwnerById(stableOwnerId);
        if (owner == null) {
            throw new ApiException("Stable owner not found");
        }

        owner.setIsApproved(true);
        stableOwnerRepository.save(owner);
        emailService.sendEmail(
                owner.getUser().getEmail(),
                "Your Saheel account is now active! / تم تفعيل حسابك في منصة صهيل",
                "مرحبًا " + owner.getUser().getFullName() + "،\n\n"
                        + "تمت مراجعة حسابك وتفعيله بنجاح، يمكنك الآن الدخول إلى المنصة والبدء في استخدام خدماتنا.\n\n"
                        + "Welcome " + owner.getUser().getFullName() + ",\n"
                        + "Your Saheel account has been reviewed and successfully activated.\n"
                        + "You can now log in and start using the platform.\n\n"
                        + "شكراً لانضمامك إلى صهيل!\n"
                        + "Thank you for joining Saheel!"
        );

    }


    public void sendWelcomeEmailsToNewMembers(Integer adminId) {
        User admin = userRepository.findUserByIdAndRole(adminId, "ADMIN");
        if (admin == null) {
            throw new ApiException("Only admins can perform this action.");
        }

        LocalDate today = LocalDate.now();
        List<Membership> memberships = membershipRepository.findByStartDate(today);

        for (Membership membership : memberships) {
            HorseOwner owner = membership.getHorseOwner();
            User user = owner.getUser();

            System.out.println("Memberships found: " + memberships.size());

            String to = user.getEmail();
            String name = user.getFullName();

            String subject = "تفاصيل اشتراكك في صهيل / Your Saheel Membership Details";
            String body = buildMembershipWelcomeBody(name, membership);

            emailService.sendEmail(to, subject, body);
        }
    }

    private String buildMembershipWelcomeBody(String name, Membership membership) {
        String type = membership.getMembershipType().equals("saheel++") ? "السنوية / saheel++" : "6شهور / saheel";
        String price = membership.getPrice() + " ريال";
        String start = membership.getStartDate().toString();
        String end = membership.getEndDate().toString();
        String horseLimit = membership.getMembershipType().equals("yearly") ? "6" : "3";

        return "مرحبًا " + name + "،\n\n"
                + "نشكرك على اشتراكك في باقة " + type + " على منصة صهيل.\n"
                + "تفاصيل الاشتراك:\n"
                + "• السعر: " + price + "\n"
                + "• البداية: " + start + "\n"
                + "• النهاية: " + end + "\n"
                + "• عدد الخيول المسموح: " + horseLimit + "\n"
                + "• لا يمكن تجاوز الحد الأعلى للخيول\n"
                + "• الاشتراك غير قابل للاسترداد بعد التفعيل\n"
                + "• للإدارة الحق في إلغاء العضوية في حال المخالفة\n\n"

                + "Hello " + name + ",\n\n"
                + "Thank you for subscribing to the " + type + " package on Saheel.\n"
                + "Membership Details:\n"
                + "• Price: SAR " + membership.getPrice() + "\n"
                + "• Start Date: " + start + "\n"
                + "• End Date: " + end + "\n"
                + "• Allowed horses: " + horseLimit + "\n"
                + "• No exceeding horse limit\n"
                + "• No refund after activation\n"
                + "• Admin has the right to suspend your membership if rules are broken\n\n"
                + "Thank you for joining us!\n"
                + "Saheel Team";
    }
    public void notifyMembershipExpiringSoon(Integer admin_Id) {
        User admin = userRepository.findUserByIdAndRole(admin_Id, "ADMIN");
        if (admin == null) {
            throw new ApiException("Only admins can perform this action.");
        }

        List<Membership> memberships = membershipRepository.findAllByIsActiveTrue();
        LocalDate today = LocalDate.now();

        for (Membership membership : memberships) {
            LocalDate endDate = membership.getEndDate();
            if (endDate == null) continue;

            long daysBetween = ChronoUnit.DAYS.between(today, endDate);
            if (daysBetween == 3 || daysBetween == 2) {
                for (Horse horse : membership.getHorses()) {
                    HorseOwner owner = horse.getHorseOwner();
                    if (owner == null || owner.getUser() == null) continue;

                    User user = owner.getUser();
                    String horseName = horse.getName();

                    String subject = "تنبيه بانتهاء الاشتراك / Membership Expiry Reminder";
                    String body = "مرحبًا " + user.getFullName() + "،\n\n"
                            + "نود إعلامك بأن اشتراك الحصان \"" + horseName + "\" سينتهي بعد " + daysBetween + " أيام، بتاريخ " + endDate + ".\n"
                            + "يرجى تجديد الاشتراك لتفادي إيقاف الخدمات.\n\n"
                            + "Dear " + user.getFullName() + ",\n"
                            + "This is a reminder that your horse \"" + horseName + "\"'s membership will expire in " + daysBetween + " days, on " + endDate + ".\n"
                            + "Please renew to avoid service interruption.\n\n"
                            + "شكراً لاستخدامك صهيل.\nThank you for using Saheel.";

                    emailService.sendEmail(user.getEmail(), subject, body);
                }
            }
        }
    }


    public List<StableOwner> getAllStableOwners(Integer adminId) {
        User admin = userRepository.findUserByIdAndRole(adminId, "ADMIN");
        if (admin == null) {
            throw new ApiException("Only admins can view stakeholders.");
        }

        return stableOwnerRepository.findAll();
    }

    public List<Customer> getAllCustomer(Integer adminId) {
        User admin = userRepository.findUserByIdAndRole(adminId, "ADMIN");
        if (admin == null) {
            throw new ApiException("Only admins can view Customer.");
        }

        return customerRepository.findAll();
    }

    public List<HorseOwner> getAllHorseOwner(Integer adminId) {
        User admin = userRepository.findUserByIdAndRole(adminId, "ADMIN");
        if (admin == null) {
            throw new ApiException("Only admins can view Stable Owner.");
        }

        return horseOwnerRepository.findAll();
    }

    public void changeMembershipStatus(Integer adminId, Integer membershipId) {
        // Get the membership and check if it's in the database.
        Membership membership = membershipRepository.findMembershipById(membershipId);
        if (membership == null)
            throw new ApiException("Membership not found.");

        if (userRepository.findUserByIdAndRole(adminId, "ADMIN") == null)
            throw new ApiException("Admin not found.");

        // Check the membership end date and check if the membership expired.
        if(LocalDate.now().isAfter(membership.getEndDate())){
            membership.setIsActive(false);
            membershipRepository.save(membership);
        } else
            throw new ApiException("The membership has not expired yet.");
    }


}
