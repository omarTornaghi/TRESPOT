package com.mondorevive.TRESPOT.pianoRevisione.revisione;

import com.mondorevive.TRESPOT.utils.SpecificationsUtils;
import jakarta.persistence.criteria.JoinType;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

public class RevisioneSpecifications {
    public static Specification<Revisione> epcTagContains(String valore) {
        return (root, query, builder) -> {
            root.join("storicoCauzione", JoinType.INNER).join("cauzione", JoinType.INNER);
            if(StringUtils.isBlank(valore)) return builder.conjunction();
            return SpecificationsUtils.createLikeBuilder(builder,root.get("storicoCauzione").get("cauzione").get("epcTag"),valore);
        };
    }
    public static Specification<Revisione> matricolaContains(String valore) {
        return (root, query, builder) -> {
            if(StringUtils.isBlank(valore)) return builder.conjunction();
            return SpecificationsUtils.createLikeBuilder(builder,root.get("storicoCauzione").get("cauzione").get("matricola"),valore);
        };
    }

    public static Specification<Revisione> idOperatoreEqual(String valore) {
        return (root, query, builder) -> {
            if(StringUtils.isBlank(valore)) return builder.conjunction();
            Long id = Long.parseLong(valore);
            return builder.equal(root.get("storicoCauzione").get("utente").get("id"),id);
        };
    }

    public static Specification<Revisione> conformitaTotaleEqual(String valore) {
        return (root, query, builder) -> {
            if(StringUtils.isBlank(valore)) return builder.conjunction();
            return builder.equal(root.get("conformitaTotale"), Boolean.parseBoolean(valore));
        };
    }

    public static Specification<Revisione> dataRevisioneDopo(LocalDateTime valore) {
        return (root, query, builder) -> builder.greaterThanOrEqualTo(root.get("dataRevisione"),valore);
    }

    public static Specification<Revisione> dataRevisionePrima(LocalDateTime valore) {
        return (root, query, builder) -> builder.lessThanOrEqualTo(root.get("dataRevisione"),valore);
    }

}
