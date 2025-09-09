package com.nexus.platform.service;

import com.nexus.platform.dto.InvestorDTO;
import com.nexus.platform.entity.InvestorProfile;
import com.nexus.platform.entity.Role;
import com.nexus.platform.mapper.InvestorMapper;
import com.nexus.platform.repository.InvestorProfileRepository;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class InvestorService {

    private final InvestorProfileRepository investorProfileRepository;

    public List<InvestorDTO> getAllInvestors(String searchQuery, List<String> stages, List<String> interests) {
        Specification<InvestorProfile> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (StringUtils.hasText(searchQuery)) {
                String likePattern = "%" + searchQuery.toLowerCase() + "%";
                predicates.add(cb.or(
                        cb.like(cb.lower(root.get("user").get("name")), likePattern),
                        cb.like(cb.lower(root.get("user").get("bio")), likePattern)
                ));
            }

            if (stages != null && !stages.isEmpty()) {
                predicates.add(root.join("investmentStage", JoinType.INNER).in(stages));
            }

            if (interests != null && !interests.isEmpty()) {
                predicates.add(root.join("investmentInterests", JoinType.INNER).in(interests));
            }

            predicates.add(cb.equal(root.get("user").get("role"), Role.INVESTOR));

            return cb.and(predicates.toArray(new Predicate[0]));
        };

        List<InvestorProfile> profiles = investorProfileRepository.findAll(spec);
        return profiles.stream()
                .map(profile -> InvestorMapper.toDTO(profile.getUser(), profile))
                .collect(Collectors.toList());
    }
}
