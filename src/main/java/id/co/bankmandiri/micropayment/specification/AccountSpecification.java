package id.co.bankmandiri.micropayment.specification;

import id.co.bankmandiri.micropayment.dto.AccountSearchDto;
import id.co.bankmandiri.micropayment.entity.Account;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

public class AccountSpecification {
    public static Specification<Account> getSpecification(AccountSearchDto accountSearchDto) {
        return ((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (accountSearchDto.getPhoneNumber() != null) {
                Predicate foundPhonePredicate = criteriaBuilder.like(
                        root.get("customerPhone"), "%" + accountSearchDto.getPhoneNumber() + "%"
                );
                predicates.add(foundPhonePredicate);
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[]{}));
        });
    }
}
