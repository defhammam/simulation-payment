package id.co.bankmandiri.micropayment.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name="t_customer_account")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerAccount {
    @Id
    @GeneratedValue(generator="mandiri-uuid")
    @GenericGenerator(name="mandiri-uuid", strategy="uuid2")
    private String id;

    /*@Column(name="customer_id")
    private String idCustomer;*/

    @ManyToOne
    @JoinColumn(name="bank_id")
    private Bank bank;

    //@Column(nullable=false)
    private Long balance;
}
