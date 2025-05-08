package com.example.saheel.Controller;


import com.example.saheel.Api.ApiResponse;
import com.example.saheel.DTO.HorseOwnerDTO;
import com.example.saheel.Model.User;
import com.example.saheel.Service.HorseOwnerService;
import com.example.saheel.Service.HorseService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/saheel/horse-owner")
@RequiredArgsConstructor
public class HorseOwnerController {
    private final HorseOwnerService horseService;


    @PostMapping("/register")
    public ResponseEntity<?> registerHorseOwner(@Valid @RequestBody HorseOwnerDTO horseOwnerDTO) {
        horseService.registerHorseOwner(horseOwnerDTO);
        return ResponseEntity.status(200).body(new ApiResponse("Horse owner registered successfully"));
    }


    // TODO:  @AuthenticationPrincipal User user
    // For now, we’re using the ID directly until we implement proper authorization.
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateHorseOwner(@PathVariable Integer id, @Valid @RequestBody HorseOwnerDTO horseOwnerDTO) {
//  TODO:       horseService.updateHorseOwner(user.getId(), horseOwnerDTO);
        horseService.updateHorseOwner(id, horseOwnerDTO);
        return ResponseEntity.status(200).body(new ApiResponse("Horse owner updated"));
    }

    // delete HorseOwner
    // TODO:  @AuthenticationPrincipal User user
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteHorseOwner(@PathVariable Integer id) {
        horseService.deleteHorseOwner(id);
// TODO:     horseService.deleteHorseOwner(user.getId());
        return ResponseEntity.status(200).body(new ApiResponse("Horse owner deleted"));
    }
}
