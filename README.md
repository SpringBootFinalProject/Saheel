# Saheel (صَهيل) -  Management Platform
This project is part of the **Web Development Program Using Java and SpringBoot** presented by **Tuwaiq Academy**.


## Overview
A comprehensive REST API for managing equestrian businesses, stables, and horse care operations. This system provides structured endpoints for:

-  **Stable Management** (staff, facilities, services)  
-  **Horse Tracking** (ownership, health, memberships)  
-  **Course Scheduling** (enrollments, payments, reviews)  
-  **Multi-role Access** (admins, stable owners, horse owners, customers)  

## Key Features
 **Role-based workflows** for different user types  
 **End-to-end operations** from horse care to financials  
 **Review systems** for courses and stables  
 **Modern RESTful design** with JSON responses  

## Ideal for
- Equestrian businesses  
- Riding schools  
- Horse boarding facilities  
- Veterinary clinics with stable partnerships  

## Why Use This API?
- **Structured** – Logical endpoint organization mirroring real-world operations  
- **Scalable** – Supports everything from small stables to large enterprises  
- **User-Centric** – Dedicated flows for each stakeholder type  
## Table of Contents  
- [Models](#models)  
- [Controllers](#controllers)  
  - [Admin](#admin-controller)  
  - [Breeder](#breeder-controller)  
  - [Course](#course-controller)  
  - [CourseEnrollment](#courseenrollment-controller)  
  - [CourseReview](#coursereview-controller)  
  - [Customer](#customer-controller)  
  - [Horse](#horse-controller)  
  - [HorseOwner](#horseowner-controller)  
  - [Membership](#membership-controller)  
  - [Notification](#notification-controller)  
  - [Payment](#payment-controller)  
  - [Stable](#stable-controller)  
  - [StableOwner](#stableowner-controller)  
  - [StaffManager](#staffmanager-controller)  
  - [Trainer](#trainer-controller)  
  - [Veterinary](#veterinary-controller)  
  - [VeterinaryVisit](#veterinaryvisit-controller)  
- [Services](#services)  
- [Contact Information](#contact)  

## Models
# Invoice Model

## Fields

| Field Name         | Data Type           | Description                                                                 |
|--------------------|---------------------|-----------------------------------------------------------------------------|
| `id`               | Integer             | Unique identifier for the invoice.                                           |
| `paymentId`        | String              | ID for the payment (can be used to track the payment status).                |
| `status`           | String              | Payment status (values: "pending", "initiated", "failed", "paid").           |
| `totalPrice`       | Double              | The total price of the course or service for which the invoice is generated. |
| `dateTime`         | LocalDateTime       | Timestamp for when the invoice was created.                                  |
| `courseEnrollment` | CourseEnrollment    | The course enrollment associated with this invoice (mapped by ID).          |
| `customer`         | Customer            | The customer who is associated with this invoice.                            |


# Stable Model

## Fields

| Field Name             | Data Type           | Description                                                                 |
|------------------------|---------------------|-----------------------------------------------------------------------------|
| `id`                   | Integer             | Unique identifier for the stable.                                           |
| `name`                 | String              | Name of the stable (cannot be empty).                                        |
| `description`          | String              | Description of the stable (cannot be empty).                                 |
| `capacity`             | Integer             | Maximum capacity of the stable (cannot be zero).                             |
| `location`             | String              | Location of the stable (cannot be empty).                                    |
| `totalRating`          | Double              | Total rating of the stable (default value is 0.0).                           |
| `totalNumberOfRatings`| Double              | Total number of ratings received by the stable (default value is 0.0).      |
| `totalNumberOfHorses`  | Integer             | Total number of horses in the stable (default value is 0).                  |
| `stableOwner`          | StableOwner         | The owner of the stable.                                                    |
| `breeders`             | List<Breeder>       | List of breeders associated with the stable.                                |
| `trainers`             | List<Trainer>       | List of trainers associated with the stable.                                |
| `veterinaries`         | List<Veterinary>    | List of veterinaries associated with the stable.                            |
| `courses`              | List<Course>        | List of courses associated with the stable.                                 |
| `memberships`          | List<Membership>    | List of memberships associated with the stable.                             |
| `stableReviews`        | List<StableReview>  | List of reviews associated with the stable.                                 |

### User
| Field         | Type    | Constraints                                      | Description                       |
|---------------|---------|--------------------------------------------------|-----------------------------------|
| id            | Integer | Auto-generated                                   | Unique identifier                 |
| username      | String  | @NotEmpty, Unique                                | Login username                    |
| password      | String  | @Pattern(8+ chars, UPPER/lower/number)           | Encrypted password                |
| role          | String  | ADMIN / CUSTOMER / HORSEOWNER / STABLEOWNER      | User role                         |
| fullName      | String  | @NotEmpty                                        | User's full name                  |
| age           | Integer | @NotNull                                         | User's age                        |
| email         | String  | @Email, Unique                                   | User's email                      |
| phoneNumber   | String  | @Pattern(+966XXXXXXXX), Unique                   | Phone number                      |

### Horse
| Field          | Type    | Description          |
|----------------|---------|----------------------|
| id             | Integer | Unique identifier    |
| name           | String  | Horse name           |
| gender         | String  | male/female          |
| weightInKG     | Double  | Horse weight         |
| heightInCM     | Integer | Horse height         |
| age            | Integer | Horse age            |
| passportNumber | String  | Unique passport ID   |
| isMedicallyFit | Boolean | Health status        |

### Membership
| Field          | Type      | Description           |
|----------------|-----------|-----------------------|
| id             | Integer   | Unique identifier     |
| membershipType | String    | monthly/yearly        |
| price          | Double    | Membership fee        |
| startDate      | LocalDate | Membership start      |
| endDate        | LocalDate | Membership end        |
| isActive       | Boolean   | Active status         |

### Veterinary
| Field             | Type      | Constraints             | Description                  |
|-------------------|-----------|--------------------------|------------------------------|
| id                | Integer   | Auto-generated           | Unique identifier            |
| fullName          | String    | @NotEmpty                | Vet's full name              |
| age               | Integer   | @NotNull                 | Vet's age                    |
| email             | String    | @Email, Unique           | Vet's email                  |
| yearsOfExperience | Integer   |                          | Experience in years          |
| isActive          | Boolean   | Default: false           | Activation status            |
| rating            | Double    |                          | Vet rating                   |
| stable            | Stable    | ManyToOne                | Associated stable            |
| horses            | List<Horse> | OneToMany             | Treated horses               |
| veterinaryVisits  | List<VeterinaryVisit> | OneToMany | Related visit records         |

### VeterinaryVisit
| Field           | Type           | Constraints             | Description                     |
|-----------------|----------------|--------------------------|---------------------------------|
| id              | Integer        | Auto-generated           | Unique identifier               |
| reason          | String         | @NotNull                 | Reason for visit                |
| medicalReport   | String         |                          | Medical findings                |
| visitDateTime   | LocalDateTime  | @NotNull                 | Scheduled date/time             |
| isCompleted     | Boolean        | Default: false           | Completion status               |
| veterinary      | Veterinary     | ManyToOne (JsonIgnored)  | Attending veterinary            |
| horse           | Horse          | ManyToOne (JsonIgnored)  | Horse involved in the visit     |

### TwilioConfigurationProperties *(Configuration Class)*
| Field                     | Type    | Constraints                             | Description                         |
|---------------------------|---------|-----------------------------------------|-------------------------------------|
| accountSid                | String  | @NotBlank, @Pattern(AC...)              | Twilio Account SID                  |
| authToken                 | String  | @NotBlank                               | Twilio Auth Token                   |
| messagingSid              | String  | @NotBlank, @Pattern(MG...)              | Messaging Service SID               |
| fromPhoneNumber           | String  | (Optional)                              | Phone number used for sending SMS   |
| newArticleNotification    | Object  | Contains contentSid                     | Nested notification configuration   |


## Controllers  

### Admin Controller  
| Endpoint | Method | Description |  
|----------|--------|-------------|  
| `/most-horses` | GET | Get owners with most horses |  
| `/send-welcome-to-all-customer` | POST | Send welcome emails to customers |  
| `/approve-stable-owner/{stableId}` | PUT | Approve stable owner registration |  
| `/get-unapproved-stable-owners` | GET | List unapproved stable owners |  

### Horse Controller  
| Endpoint | Method | Description |  
|----------|--------|-------------|  
| `/get-owner-horses` | GET | List owner's horses |  
| `/add-horse-by-owner` | POST | Register new horse |  
| `/assign/{horseId}` | POST | Assign horse to membership |  
| `/gift/{horseId}/to/{newOwnerId}` | PUT | Transfer horse ownership |  

### Membership Controller  
| Endpoint | Method | Description |  
|----------|--------|-------------|  
| `/request-membership/{stableId}` | POST | Request new membership |  
| `/renew-membership/{id}` | PUT | Renew existing membership |  
| `/get-expired-memberships` | GET | List expired memberships |  

### Payment Controller  
| Endpoint | Method | Description |  
|----------|--------|-------------|  
| `/card/{courseEnrollmentId}` | POST | Process course payment |  
| `/membership/{membershipId}` | POST | Process membership payment |  
| `/get-status` | GET | Check payment status |  

## Services  

### Key Service Methods  

## CourseService - Key Services Summary

| Method Name                  | Description                                                                 |
|-----------------------------|-----------------------------------------------------------------------------|
| `getStableCourses`          | Returns all courses for a given stable.                                    |
| `addCourseByOwner`          | Allows a stable owner to add a new course with trainer assignment.         |
| `updateCourse`              | Updates course information (name, time, trainer, etc.).                    |
| `cancelCourse`              | Cancels a course and marks all enrollments as canceled.                    |
| `getAvailableCourses`       | Returns list of courses that are not full and still open for enrollment.   |
| `getTopRatedCourse`         | Calculates and returns the top-rated course with its average rating.       |
| `findTopRatedCourse`        | Helper to find the course with highest average rating.                     |
| `getCoursesByTrainer`       | Lists all courses taught by a specific trainer.                            |
| `getCoursesByDate`          | Fetches courses scheduled on a specific date.                              |
| `changeEnrollmentsCourseStatus` | Marks all enrollments for a course as canceled.                        |
| `getStableOwnerOrThrow`     | Retrieves a stable owner or throws exception if not found.                 |
| `getStableOrThrow`          | Retrieves a stable or throws exception if not found.                       |
| `checkIfStableBelongsToOwner` | Ensures stable belongs to the specified owner.                          |
| `checkIfCourseBelongsToStable` | Ensures course belongs to the specified stable.                        |
## CourseEnrollmentService - Key Services Summary

| Method Name                                | Description                                                                 |
|-------------------------------------------|-----------------------------------------------------------------------------|
| `getAllCourseEnrollmentByStableOwner`     | Returns all enrollments for a specific course if it belongs to the owner.  |
| `enrollToCourse`                           | Enrolls a customer in a course after checking capacity and eligibility.     |
| `cancelEnrollment`                         | Allows a customer to cancel their enrollment if within allowed timeframe.   |
| `getCanceledEnrollments`                  | Returns all canceled enrollments for a specific stable.                     |
| `getCustomerOrThrow`                      | Retrieves a customer or throws an exception if not found.                   |
| `getCourseOrThrow`                        | Retrieves a course or throws an exception if not found.                     |
| `getCourseEnrollmentOrThrow`             | Retrieves a course enrollment or throws an exception if not found.         |
| `createInvoice`                           | Creates a pending invoice for a course enrollment.                          |
## MembershipService - Key Services Summary

| Method Name              | Description                                                                 |
|--------------------------|-----------------------------------------------------------------------------|
| `getOwnerActiveMembership` | Retrieves the active membership for a horse owner, including horse count. |
| `requestMembership`        | Adds a new membership for an owner, based on type (monthly/yearly), and creates an invoice. |
| `renewMembership`          | Renews an existing membership by updating type, price, and duration.      |
| `cancelMembership`         | Cancels a membership, unlinks all associated horses, and adjusts stable capacity. |
| `getExpiredMemberships`    | Retrieves all memberships whose end date is before today.                |
| `createMembershipInvoice`  | Generates a pending invoice for a membership and links it to the membership. |


## Contact Information  
For questions or support:  
- **Email**: abrar4013@gmail.com
- **Email** ayman.f.alharbi@gmail.com
- **Email** Aboor.1048@gmail.com
## LinkedIn
- **Abrar Saud** [link](https://www.linkedin.com/in/abrar-saud/)
- **Ayman Alharbi** https://www.linkedin.com/in/ayman-alharbi1
- **Abeer secondName** link
