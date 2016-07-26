package ru.kubsu.mms.core.db.models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by DZRock on 17.05.2016.
 */
@Entity
@Table(name = "tech_checkouts")
public class TechCheckout {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "checkout_date")
    @Temporal(TemporalType.DATE)
    private Date checkoutDate;
    @Column(name = "comment")
    private String comment;
    @ManyToOne
    @JoinColumn(name = "responsible_user_id",referencedColumnName = "id")
    private User responsibleUser;
    @ManyToOne
    @JoinColumn(name = "tech_control_id",referencedColumnName = "id")
    private TechControl techControl;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "tech_ckeckout_files",joinColumns = @JoinColumn(name = "id_tech"), inverseJoinColumns = @JoinColumn(name = "id_file"))
    private List<File> files = new ArrayList<>();

    public List<File> getFiles() {
        return files;
    }

    public void setFiles(List<File> files) {
        this.files = files;
    }

    public TechControl getTechControl() {
        return techControl;
    }

    public void setTechControl(TechControl techControl) {
        this.techControl = techControl;
    }

    public User getResponsibleUser() {
        return responsibleUser;
    }

    public void setResponsibleUser(User responsibleUser) {
        this.responsibleUser = responsibleUser;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCheckoutDate() {
        return checkoutDate;
    }

    public void setCheckoutDate(Date checkoutDate) {
        this.checkoutDate = checkoutDate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
