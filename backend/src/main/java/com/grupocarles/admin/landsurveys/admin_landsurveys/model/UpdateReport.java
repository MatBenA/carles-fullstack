package com.grupocarles.admin.landsurveys.admin_landsurveys.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
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
    private LocalDate creationDate;

    @OneToMany(mappedBy = "updateReport", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserReport> userReportList = new ArrayList<>();
}
