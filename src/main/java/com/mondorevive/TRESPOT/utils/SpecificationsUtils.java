package com.mondorevive.TRESPOT.utils;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import java.text.MessageFormat;

public class SpecificationsUtils {
    public static <T> Specification<T> attributeContainsText(String attributo, String text){
        return (root, query, builder) -> {
            if (StringUtils.isBlank(text)) return builder.conjunction();
            return createLikeBuilder(builder,root.get(attributo),text);
        };
    }

    public static Predicate createLikeBuilder(CriteriaBuilder builder, Expression<String> objectPath, String text) {
        return builder.like(builder.function("REPLACE",
                        String.class,
                        builder.lower(objectPath),
                        builder.literal(" "),
                        builder.literal("")),
                MessageFormat.format("%{0}%", StringUtils.deleteWhitespace(text.toLowerCase())));
    }

    public static <T> Specification<T> attributeContainsText(String attributo1, String attributo2, String text, boolean fetch){
        return (root, query, builder) -> {
            if(fetch)root.fetch(attributo1);
            if (StringUtils.isBlank(text)) return builder.conjunction();
            return builder.like(builder.function("REPLACE",
                            String.class,
                            builder.lower(root.get(attributo1).get(attributo2)),
                            builder.literal(" "),
                            builder.literal("")),
                    MessageFormat.format("%{0}%", StringUtils.deleteWhitespace(text.toLowerCase())));
        };
    }
}
