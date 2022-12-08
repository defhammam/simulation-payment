package id.co.bankmandiri.micropayment.specification;

import id.co.bankmandiri.micropayment.dto.PaymentSearchDto;
import id.co.bankmandiri.micropayment.entity.Payment;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

public class PaymentSpecification {
    public static Specification<Payment> getSpecification(PaymentSearchDto paymentSearchDto) {
        return ((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (paymentSearchDto.getBeginDate() != null && paymentSearchDto.getEndDate() != null) {
                Predicate betweenPredicate = criteriaBuilder.between(
                        root.get("paymentDate"),
                        paymentSearchDto.getBeginDate(),
                        paymentSearchDto.getEndDate()
                );
                predicates.add(betweenPredicate);
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        });
    }
}
