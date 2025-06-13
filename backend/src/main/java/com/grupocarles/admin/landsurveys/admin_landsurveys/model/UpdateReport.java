package com.grupocarles.admin.landsurveys.admin_landsurveys.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
public class UpdateReport {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    Long id;

    @Column(updatable = false)
    @CreationTimestamp
    private LocalDateTime creationDate;

    @OneToMany(mappedBy = "updateReport")
    private List<UserReport> userReportList;
}
