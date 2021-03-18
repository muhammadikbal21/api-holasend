package com.enigmacamp.api.holasend.entities;

import com.enigmacamp.api.holasend.enums.PriorityEnum;
import com.enigmacamp.api.holasend.enums.TaskStatusEnum;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;

@Table
@Entity(name = "task")
@Data
public class Task extends AbstractEntity<String>{

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    @ManyToOne
    @JoinColumn(name = "destination_id")
    private Destination destination;

    @ManyToOne
    @JoinColumn(name = "request_by")
    private User requestBy;

    @ManyToOne
    @JoinColumn(name = "courier")
    private User courier;

    @Column(name = "pickup_time")
    private LocalDateTime pickUpTime;

    @Column(name = "delivered_time")
    private LocalDateTime deliveredTime;

    @ManyToOne
    @JoinColumn(name = "courier_activity_id")
    private CourierActivity courierActivity;

    @Enumerated
    @Column
    private TaskStatusEnum status;

    @Enumerated
    @Column
    private PriorityEnum priority;

    @Column
    private String notes;

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }
}
