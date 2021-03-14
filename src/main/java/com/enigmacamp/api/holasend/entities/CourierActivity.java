package com.enigmacamp.api.holasend.entities;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Table
@Entity(name = "courier_activity")
@Data
public class CourierActivity extends AbstractEntity<String> {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    @ManyToOne
    @JoinColumn(name = "courier_id")
    private User courier;

    @Column(name = "date")
    private LocalDate date;

    @Column(name = "leaving_time")
    private LocalDateTime leavingTime;

    @Column(name = "return_time")
    private LocalDateTime returnTime;

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }
}
