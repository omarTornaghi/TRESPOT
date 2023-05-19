package com.mondorevive.TRESPOT.cauzione;

import com.mondorevive.TRESPOT.utils.SpecificationsUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

public class CauzioneSpecifications {
    public static Specification<Cauzione> epcTagContains(String text) {
        return SpecificationsUtils.attributeContainsText("epcTag", text);
    }
    public static Specification<Cauzione> matricolaContains(String text) {
        return SpecificationsUtils.attributeContainsText("matricola", text);
    }

    public static Specification<Cauzione> idTipologiaCauzioneEquals(String valore) {
        return (root, query, builder) -> {
           if(StringUtils.isBlank(valore)) return builder.conjunction();
           Long id = Long.parseLong(valore);
           return builder.equal(root.get("tipologiaCauzione").get("id"),id);
        };
    }

    public static Specification<Cauzione> idMagazzinoEquals(String valore) {
        return (root, query, builder) -> {
            if(StringUtils.isBlank(valore)) return builder.conjunction();
            Long id = Long.parseLong(valore);
            return builder.equal(root.get("magazzino").get("id"),id);
        };
    }

    public static Specification<Cauzione> idStatoCauzioneEquals(String valore) {
        return (root, query, builder) -> {
            if(StringUtils.isBlank(valore)) return builder.conjunction();
            Long id = Long.parseLong(valore);
            return builder.equal(root.get("statoCauzione").get("id"),id);
        };
    }
}
