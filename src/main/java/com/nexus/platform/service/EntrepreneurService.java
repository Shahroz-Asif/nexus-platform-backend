package com.nexus.platform.service;

import com.nexus.platform.dto.EntrepreneurDTO;
import com.nexus.platform.entity.Role;
import com.nexus.platform.entity.Startup;
import com.nexus.platform.mapper.EntrepreneurMapper;
import com.nexus.platform.repository.StartupRepository;
import jakarta.persistence.criteria.Predicate;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class EntrepreneurService {

    private final StartupRepository startupRepository;

    public List<EntrepreneurDTO> getAllEntrepreneurs(String searchQuery, List<String> industries, String fundingRange) {
        Specification<Startup> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (StringUtils.hasText(searchQuery)) {
                String likePattern = "%" + searchQuery.toLowerCase() + "%";
                predicates.add(cb.or(
                        cb.like(cb.lower(root.get("startupName")), likePattern),
                        cb.like(cb.lower(root.get("pitchSummary")), likePattern),
                        cb.like(cb.lower(root.get("industry")), likePattern),
                        cb.like(cb.lower(root.get("user").get("name")), likePattern)
                ));
            }

            if (industries != null && !industries.isEmpty()) {
                predicates.add(root.get("industry").in(industries));
            }

            if (StringUtils.hasText(fundingRange)) {
                // Assuming fundingRange is in a format like "< 500000", "500000-1000000", "> 5000000"
                // This is a simplified example. A more robust implementation would parse this more carefully.
                if (fundingRange.contains("-")) {
                    String[] parts = fundingRange.split("-");
                    BigDecimal min = new BigDecimal(parts[0]);
                    BigDecimal max = new BigDecimal(parts[1]);
                    predicates.add(cb.between(root.get("fundingNeeded"), min, max));
                } else if (fundingRange.startsWith("<")) {
                    BigDecimal max = new BigDecimal(fundingRange.substring(1).trim());
                    predicates.add(cb.lessThan(root.get("fundingNeeded"), max));
                } else if (fundingRange.startsWith(">")) {
                    BigDecimal min = new BigDecimal(fundingRange.substring(1).trim());
                    predicates.add(cb.greaterThan(root.get("fundingNeeded"), min));
                }
            }
            
            predicates.add(cb.equal(root.get("user").get("role"), Role.ENTREPRENEUR));

            return cb.and(predicates.toArray(new Predicate[0]));
        };

        List<Startup> startups = startupRepository.findAll(spec);
        return startups.stream()
                .map(startup -> EntrepreneurMapper.toDTO(startup.getUser(), startup))
                .collect(Collectors.toList());
    }
}
