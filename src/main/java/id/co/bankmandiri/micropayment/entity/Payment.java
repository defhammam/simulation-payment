package id.co.bankmandiri.micropayment.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name="trx_payment")
@Data
public class Payment {
    @Id
    @GeneratedValue(generator="mandiri-uuid")
    @GenericGenerator(name="mandiri-uuid", strategy="uuid2")
    private String id;
    private Long paymentDate;
    private Integer amountPaid;
    @ManyToOne
    @JoinColumn(name="account_id")
    private Account account;
}
