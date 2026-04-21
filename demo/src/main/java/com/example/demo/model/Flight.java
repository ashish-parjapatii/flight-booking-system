package com.example.demo.model;
import jakarta.persistence.*;
import lombok.*;
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Flight {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String flightNumber;

    @ManyToOne
    @JoinColumn(name = "airlind_id", nullable = false)
    private Airline airline;

    @ManyToOne
    @JoinColumn(name = "source_airport_id", nullable = false)
    private Airport sourceAirport;

    @ManyToOne
    @JoinColumn(name = "destination_airport_id", nullable = false)
    private Airport destinationAirport;

    private Integer duration; //in minutes
}