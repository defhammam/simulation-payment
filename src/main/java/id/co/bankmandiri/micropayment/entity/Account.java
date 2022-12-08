package id.co.bankmandiri.micropayment.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name="mst_bank")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Account {
    @Id
    @GeneratedValue(generator="mandiri-uuid")
    @GenericGenerator(name="mandiri-uuid", strategy="uuid2")
    private String id;

    @Column(nullable=false)
    private String customerPhone;

    @Column(nullable=false)
    private Integer balance;

    @Column(nullable=false)
    private Boolean isDeleted;
}
