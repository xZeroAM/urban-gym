package com.app.urbangym.entitys;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PostLoad;
import javax.persistence.PostPersist;
import javax.persistence.PostUpdate;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.scheduling.annotation.Scheduled;

@Entity
@Table(name = "MEMBERSHIPS")
public class Membership {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID_MATRICULA")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DNI_USER")
    private User user;

    @NotNull(message = "Tiene que elegir un plan")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_PLAN")
    private Plan plan;

    @Column(name = "START_DATE")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "La fecha de inicio no puede estar vacía")
    private LocalDate startDate;

    @Column(name = "END_DATE")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "La fecha de fin no puede estar vacía")
    private LocalDate endDate;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "MEMBERSHIP_STATUS_ID", unique = true)
    private MembershipStatus membershipStatus;

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Plan getPlan() {
        return this.plan;
    }

    public void setPlan(Plan plan) {
        this.plan = plan;
    }

    public LocalDate getStartDate() {
        return this.startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return this.endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public MembershipStatus getMembershipStatus() {
        return this.membershipStatus;
    }

    public void setMembershipStatus(MembershipStatus membershipStatus) {
        this.membershipStatus = membershipStatus;
    }

    @PostLoad
    @PostPersist
    @PostUpdate
    public void updateStatus() {
        LocalDate now = LocalDate.now();
        boolean isActive = now.isEqual(startDate) || now.isAfter(startDate) && now.isBefore(endDate.plusDays(1));

        membershipStatus.setStatus(isActive);
    }

    @Scheduled(cron = "0 0 0 * * ?")
    public void updateMembershipStatus() {
        updateStatus();
    }

}
