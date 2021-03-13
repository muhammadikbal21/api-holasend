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

    private LocalDateTime pickUpTime;

    private LocalDateTime deliveredTime;

    private TaskStatusEnum status;

    private PriorityEnum priority;

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }
}
