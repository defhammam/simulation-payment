package id.co.bankmandiri.micropayment.specification;

import id.co.bankmandiri.micropayment.constant.Noun;
import id.co.bankmandiri.micropayment.dto.AccountSearchDto;
import id.co.bankmandiri.micropayment.entity.Account;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

public class AccountSpecification {
    public static Specification<Account> getSpecification(AccountSearchDto accountSearchDto) {
        // To search for account based on amount of balance
        return ((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (accountSearchDto.getBalanceUpperRange() != null && accountSearchDto.getBalanceLowerRange() != null) {
                Predicate betweenPredicate = criteriaBuilder.between(
                        root.get(Noun.BALANCE),
                        accountSearchDto.getBalanceUpperRange(),
                        accountSearchDto.getBalanceLowerRange()
                );
                predicates.add(betweenPredicate);
            }
            if (accountSearchDto.getBalanceUpperRange() != null) {
                Predicate fromPredicate = criteriaBuilder.greaterThanOrEqualTo(
                        root.get(Noun.BALANCE), accountSearchDto.getBalanceUpperRange()
                );
                predicates.add(fromPredicate);
            }
            if (accountSearchDto.getBalanceLowerRange() != null) {
                Predicate untilPredicate = criteriaBuilder.lessThanOrEqualTo(
                        root.get(Noun.BALANCE), accountSearchDto.getBalanceLowerRange()
                );
                predicates.add(untilPredicate);
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[]{}));
        });
    }
}
