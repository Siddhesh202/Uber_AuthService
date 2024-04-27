package com.example.authservice.dto;

import com.example.authservice.models.Passenger;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PassengerDto {

    private String id;

    private String name;

    private String email;

    private String password;  // encrypted password

    private String phoneNumber;

    private Date createdAt;

    public static PassengerDto from(Passenger passenger) {
        PassengerDto result = PassengerDto.builder()
                .id(passenger.getId().toString())
                .name(passenger.getName())
                .email(passenger.getEmail())
                .password(passenger.getPassword())
                .phoneNumber(passenger.getPhoneNumber())
                .createdAt(passenger.getCreatedAt())
                .build();

        return result;
    }

}
