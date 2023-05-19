package com.mondorevive.TRESPOT.pianoRevisione.revisione;

import com.mondorevive.TRESPOT.utils.SpecificationsUtils;
import jakarta.persistence.criteria.JoinType;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

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
}
